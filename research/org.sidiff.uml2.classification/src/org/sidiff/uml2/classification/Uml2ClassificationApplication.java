package org.sidiff.uml2.classification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.structural.StructureBasedSlicer;
import org.sidiff.slicer.structural.configuration.ConfigurationFactory;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.util.SlicerUtil;
import org.sidiff.slicing.util.visualization.GraphUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class Uml2ClassificationApplication implements IApplication {

	private static final URI UML_XMI_URI = URI.createPlatformPluginURI("/org.sidiff.uml2.classification/models/UML.transformed.xmi", true);
	private static final URI UML_ECORE_URI = URI.createURI("platform:/plugin/org.eclipse.uml2.uml/model/UML.ecore", true);
	private static final URI SLICING_CONFIG_URI = URI.createPlatformPluginURI("/org.sidiff.uml2.classification/configs/UML_Datatypes.scfg", true);

	/**
	 * Map of class name to the package that contains the class.
	 */
	private Map<String,Package> class2package;

	/**
	 * Map of package to set of all core classes of that package.
	 */
	private Map<Package,Set<String>> package2coreClasses;

	private String outputDirectory;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		outputDirectory = selectOutputDirectory();
		if(outputDirectory != null) {
			startClassification();
		}
		return null;
	}

	private String selectOutputDirectory() {
		DirectoryDialog directoryDialog = new DirectoryDialog(new Shell(Display.getCurrent()), SWT.NONE);
		directoryDialog.setText("Select output folder");
		return directoryDialog.open();
	}

	private void startClassification() throws FileNotFoundException, IOException {
		class2package = new HashMap<>();
		package2coreClasses = new HashMap<>();

		System.out.println("Calculating core classes...");
		List<Package> subPackages = getSpecifiedSubPackages();
		for(Package nestedPackage : subPackages) {
			collectCoreClasses(nestedPackage);
		}

		System.out.println("Creating graph of package imports...");
		createPackageImportGraph(subPackages);

		System.out.println("Slicing UML meta model:");
		for(Package nestedPackage : subPackages) {
			createSlice(nestedPackage);
			createMandatoryReferenceGraph(nestedPackage);
			System.out.println(" - " + nestedPackage.getName());
		}
	}

	private List<Package> getSpecifiedSubPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put("http://www.omg.org/spec/UML/20131001", UMLPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", UMLResource.Factory.INSTANCE);
		Resource resource = resourceSet.getResource(UML_XMI_URI, true);
		Package rootPackage = (Package)EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
		return rootPackage.getNestedPackages();
	}

	private void collectCoreClasses(Package nestedPackage) {
		Set<String> coreClasses = new HashSet<>();
		for(PackageableElement element : nestedPackage.getPackagedElements()) {
			if(element instanceof Class) {
				class2package.put(element.getName(), nestedPackage);
				collectSuperClasses(coreClasses, (Class)element);
			}
		}
		package2coreClasses.put(nestedPackage, coreClasses);
	}

	private void collectSuperClasses(Set<String> coreClasses, Class clazz) {
		if(coreClasses.add(clazz.getName())) {
			for(Class superClass : clazz.getSuperClasses()) {
				collectSuperClasses(coreClasses, superClass);
			}
		}
	}

	private void createPackageImportGraph(List<Package> subPackages) throws IOException, FileNotFoundException {
		Object packageGraph = new Object();
		for(Package nestedPackage : subPackages) {
			GraphUtil.get(packageGraph).addNode(nestedPackage);
			for(Package importedPackage : nestedPackage.getImportedPackages()) {
				GraphUtil.get(packageGraph).addEdge("imports", nestedPackage, importedPackage);
			}
		}
		try (OutputStream out = new FileOutputStream(outputDirectory + "/UML.dot")) {
			out.write(GraphUtil.get(packageGraph).getOutput().getBytes());
		}
	}

	private void createSlice(Package nestedPackage) {
		try {
			Resource umlMetaModel = new ResourceSetImpl().getResource(UML_ECORE_URI, true);
			ISlicer slicer = new StructureBasedSlicer();
			Set<String> coreClasses = package2coreClasses.get(nestedPackage);
			slicer.init(loadSlicingConfiguration(coreClasses));
			ModelSlice slice = slicer.slice(umlMetaModel.getContents());
			URI outputUri = URI.createFileURI(outputDirectory + "/UML." + nestedPackage.getName() + ".ecore");
			Collection<EObject> exportedSlice = slice.export(object -> shouldCopy(umlMetaModel, coreClasses, object));
			postprocessModelSlice(nestedPackage.getName(), exportedSlice, coreClasses);
			SlicerUtil.serializeModelSlice(outputUri, exportedSlice);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ISlicingConfiguration loadSlicingConfiguration(Set<String> coreClasses) {
		Resource slicingConfigRes = new ResourceSetImpl().getResource(SLICING_CONFIG_URI, true);
		SlicingConfiguration config = (SlicingConfiguration)slicingConfigRes.getContents().get(0);
		for(SlicedEClass slicedClass : config.getSlicedEClasses()) {
			if(slicedClass.getType().equals(EcorePackage.Literals.ECLASS)) {
				Constraint constraint = ConfigurationFactory.eINSTANCE.createConstraint();
				constraint.setExpression(String.join(" ", coreClasses));
				slicedClass.setConstraint(constraint);
				break;
			}
		}
		return config;
	}

	private boolean shouldCopy(Resource umlMetaModel, Set<String> coreClasses, EObject object) {
		if(object instanceof EGenericType) {
			EClassifier classifier = ((EGenericType)object).getEClassifier();
			if(classifier instanceof EClass) {
				return coreClasses.contains(classifier.getName());
			}
		}
		return umlMetaModel.equals(object.eResource());
	}

	private void postprocessModelSlice(String packageName, Collection<EObject> exportedSlice, Set<String> coreClasses) throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(outputDirectory + "/UML." + packageName + ".txt")) {
			TreeIterator<EObject> iterator = EcoreUtil.getAllProperContents(exportedSlice, true);
			for(EObject object : (Iterable<EObject>)(() -> iterator)) {
				if(object instanceof EReference) {
					EReference reference = (EReference)object;
					// set lower bound to 0 for all references to non-core classes
					String refTypeName = reference.getEReferenceType().getName();
					if(reference.getLowerBound() > 0 && !coreClasses.contains(refTypeName)) {
						reference.setLowerBound(0);
						writer.println(reference.getEContainingClass().getName()
								+ " --- " + reference.getName()
								+ " --> " + refTypeName + " (in " + class2package.get(refTypeName).getName() + ")");
					}
				}
			}
		}
	}

	private void createMandatoryReferenceGraph(Package nestedPackage) throws FileNotFoundException, IOException {
		Set<String> coreClasses = package2coreClasses.get(nestedPackage);
		Resource umlMetaModel = new ResourceSetImpl().getResource(UML_ECORE_URI, true);
		Collection<EClass> coreEClasses =
				EcoreUtil.<EClass>getObjectsByType(umlMetaModel.getContents().get(0).eContents(), EcorePackage.Literals.ECLASS)
					.stream().filter(c -> coreClasses.contains(c.getName())).collect(Collectors.toList());

		Object graph = new Object();
		List<EClass> mandatoryClasses = new ArrayList<>(coreEClasses);
		for(int i = 0; i < mandatoryClasses.size(); i++) {
			EClass eClass = mandatoryClasses.get(i);
			Package srcPackage = class2package.get(eClass.getName());
			if(srcPackage == null) {
				continue;
			}
			GraphUtil.get(graph).addNode(srcPackage);

			for(EReference reference : eClass.getEReferences()) {
				EClass referencedType = reference.getEReferenceType();
				if(referencedType != null && reference.getLowerBound() >= 1 && !mandatoryClasses.contains(referencedType)) {
					mandatoryClasses.add(referencedType);
					Package tgtPackage = class2package.get(referencedType.getName());
					if(srcPackage != tgtPackage && tgtPackage != null) {
						GraphUtil.get(graph).addNode(tgtPackage);
						GraphUtil.get(graph).addEdge(eClass.getName() + "." + reference.getName(), srcPackage, tgtPackage);
					}
				}
			}
			for(EClass superClass : eClass.getESuperTypes()) {
				Package tgtPackage = class2package.get(superClass.getName());
				if(srcPackage != tgtPackage && tgtPackage != null) {
					GraphUtil.get(graph).addNode(tgtPackage);
					GraphUtil.get(graph).addEdge("super", srcPackage, tgtPackage);
				}
			}
		}
		try (OutputStream out = new FileOutputStream(outputDirectory + "/UML." + nestedPackage.getName() + ".dot")) {
			out.write(GraphUtil.get(graph).getOutput().getBytes());
		}
	}

	@Override
	public void stop() {
	}
}
