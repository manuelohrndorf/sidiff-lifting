package org.sidiff.uml2.classification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
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

/**
 * 
 * @author Robert Müller
 *
 */
public class Uml2ClassificationApplication implements IApplication {

	private static final URI UML_XMI_URI = URI.createPlatformPluginURI("/org.sidiff.uml2.classification/models/UML.transformed.xmi", true);
	private static final URI UML_ECORE_URI = URI.createPlatformPluginURI("/org.sidiff.uml2.classification/models/UML.ecore", true);
	private static final URI SLICING_CONFIG_URI = URI.createPlatformPluginURI("/org.sidiff.uml2.classification/configs/UML_Datatypes.scfg", true);

	@Override
	public Object start(IApplicationContext context) throws Exception {
		DirectoryDialog directoryDialog = new DirectoryDialog(new Shell(Display.getCurrent()), SWT.NONE);
		directoryDialog.setText("Select output folder");
		String outputDirectory = directoryDialog.open();
		if(outputDirectory != null) {
			for(Package nestedPackage : getSpecifiedSubPackages()) {
				createSlice(nestedPackage, outputDirectory);
			}
		}
		return null;
	}

	private List<Package> getSpecifiedSubPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put("http://www.omg.org/spec/UML/20131001", UMLPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", UMLResource.Factory.INSTANCE);
		Resource resource = resourceSet.getResource(UML_XMI_URI, true);
		Package rootPackage = (Package)EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
		return rootPackage.getNestedPackages();
	}

	private void createSlice(Package nestedPackage, String outputDirectory) {
		System.out.println("Package: " + nestedPackage.getName());
		Set<String> coreClasses = new HashSet<>();
		for(PackageableElement element : nestedPackage.getPackagedElements()) {
			if(element instanceof Class) {
				collectSuperClasses(coreClasses, (Class)element);
			}
		}
		System.out.println("  Core: " + coreClasses);

		try {
			ISlicer slicer = new StructureBasedSlicer();
			slicer.init(loadSlicingConfiguration(coreClasses));
			Resource umlMetaModel = new ResourceSetImpl().getResource(UML_ECORE_URI, true);
			ModelSlice slice = slicer.slice(umlMetaModel.getContents());
			URI outputUri = URI.createFileURI(outputDirectory + "/UML." + nestedPackage.getName() + ".ecore");
			SlicerUtil.serializeModelSlice(outputUri, slice.export(umlMetaModel));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
	}

	private ISlicingConfiguration loadSlicingConfiguration(Set<String> classNames) {
		Resource slicingConfigRes = new ResourceSetImpl().getResource(SLICING_CONFIG_URI, true);
		SlicingConfiguration config = (SlicingConfiguration)slicingConfigRes.getContents().get(0);
		for(SlicedEClass slicedClass : config.getSlicedEClasses()) {
			if(slicedClass.getType().equals(EcorePackage.Literals.ECLASS)) {
				Constraint constraint = ConfigurationFactory.eINSTANCE.createConstraint();
				constraint.setExpression(String.join(" ", classNames));
				slicedClass.setConstraint(constraint);
				break;
			}
		}
		return config;
	}

	private void collectSuperClasses(Set<String> coreClasses, Class clazz) {
		if(!coreClasses.contains(clazz.getName())) {
			coreClasses.add(clazz.getName());
			for(Class superClass : clazz.getSuperClasses()) {
				collectSuperClasses(coreClasses, superClass);
			}
		}
	}

	@Override
	public void stop() {
	}
}
