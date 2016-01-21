package org.sidiff.difference.ui.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.candidates.CandidatesUtil;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.settings.settings.ISettingsChangedListener;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.settings.settings.MatchingSettings;
import org.sidiff.difference.settings.settings.MatchingSettingsItem;
import org.sidiff.difference.ui.widgets.table.AbstractTableEntryProvider;
import org.sidiff.difference.ui.widgets.table.DynamicTableEntryProviders;
import org.sidiff.difference.ui.widgets.table.ITableEntry;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matcher.SimilarityBasedMatcher;
import org.sidiff.service.IService;
import org.sidiff.similarities.ISimilarities;
import org.sidiff.similarities.SimilaritiesServiceUtil;
import org.sidiff.similaritiescalculation.ISimilaritiesCalculation;
import org.sidiff.similaritiescalculation.SimilaritiesCalculationUtil;

public class DifferencingServiceSelectionTableEntryProvider extends AbstractTableEntryProvider {

	private final int CANDIDATES_ID = 0;
	private final int CORRESPONDANCE_ID = 1;
	private final int SIMILARITY_ID = 2;
	private final int SIMILARITY_CALCULATION_ID = 3;

	private final MatchingSettings settings;
	private final String docType;
	
	private final DynamicTableEntryProviders[] subProviders;
	private final ServiceTableEntry<ICorrespondences, MatchingSettings> coEntry;
	private final ServiceTableEntry<ICandidates, MatchingSettings> caEntry;
	private final ServiceTableEntry<ISimilarities, MatchingSettings> siEntry;
	private final ServiceTableEntry<ISimilaritiesCalculation, MatchingSettings> scEntry;
	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			Object source = event.getSource();
			if (coEntry.equals(source)) {
				subProviders[CORRESPONDANCE_ID].setConfigurationObject(coEntry.getSelectedService(), docType);
				subProviders[CORRESPONDANCE_ID].setValue(getLabel(coEntry.getSelectedService()));
			} else if (caEntry.equals(source)) {
				subProviders[CANDIDATES_ID].setConfigurationObject(caEntry.getSelectedService(), docType);
				subProviders[CANDIDATES_ID].setValue(getLabel(caEntry.getSelectedService()));
			} else if (siEntry.equals(source)) {
				subProviders[SIMILARITY_ID].setConfigurationObject(siEntry.getSelectedService(), docType);
				subProviders[SIMILARITY_ID].setValue(getLabel(siEntry.getSelectedService()));
			} else if (scEntry.equals(source)) {
				subProviders[SIMILARITY_CALCULATION_ID].setConfigurationObject(scEntry.getSelectedService(), docType);
				subProviders[SIMILARITY_CALCULATION_ID].setValue(getLabel(scEntry.getSelectedService()));
			}
		}
	};
	private final ISettingsChangedListener settingsChangedListener = new ISettingsChangedListener() {
		@Override
		public void settingsChanged(Enum<?> item) {
			if (MatchingSettingsItem.MATCHER.equals(item)){
				boolean similaritesAllowed = similaritiesAllowed(settings.getMatcher());
				siEntry.setSelectable(similaritesAllowed);
				scEntry.setSelectable(similaritesAllowed);
			}
			/*
			else if (MatchingSettingsItem.CANDITATE_SERVICE.equals(item)){
				ICandidates n = settings.getCandidateService();
				ICandidates o = caEntry.getSelectedService();
				if ((n == null && o != null) || (n != null && !n.equals(o))){
					caEntry.setSelectedService(n);
				}
			} else if (MatchingSettingsItem.CORRESPONDENCE_SERVICE.equals(item)){
				ICorrespondences n = settings.getCorrespondencesService();
				ICorrespondences o = coEntry.getSelectedService();
				if ((n == null && o != null) || (n != null && !n.equals(o))){
					coEntry.setSelectedService(n);
				}
			} else if (MatchingSettingsItem.SIMILARTIY_SERVICE.equals(item)){
				ISimilarities n = settings.getSimilaritiesService();
				ISimilarities o = siEntry.getSelectedService();
				if ((n == null && o != null) || (n != null && !n.equals(o))){
					siEntry.setSelectedService(n);
				}
			}
			*/
		}
	};

	private String getLabel(IService service) {
		if (service != null) {
			return service.getServiceID();
		} else {
			return "";
		}
	}

	private boolean similaritiesAllowed(IMatcher matcher) {
		if (matcher != null) {
			if (matcher instanceof IncrementalMatcher) {
				for (IMatcher m : ((IncrementalMatcher) matcher).getMatchers()) {
					if (similaritiesAllowed(m)) return true;
				}
				return false;
			} else if (matcher instanceof SimilarityBasedMatcher){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	public DifferencingServiceSelectionTableEntryProvider(String name, String docType, MatchingSettings settings) {
		super(name, "");
		this.settings = settings;
		this.settings.addSettingsChangedListener(settingsChangedListener);
		this.docType = docType;
		boolean similaritesAllowed=similaritiesAllowed(settings.getMatcher());
		this.subProviders = new DynamicTableEntryProviders[4];
		this.subProviders[CANDIDATES_ID] = new DynamicTableEntryProviders("Candiadates Service", "");
		this.subProviders[CORRESPONDANCE_ID] = new DynamicTableEntryProviders("Correspondance Service", "");
		this.subProviders[SIMILARITY_ID] = new DynamicTableEntryProviders("Similarity Service", "");
		this.subProviders[SIMILARITY_CALCULATION_ID] = new DynamicTableEntryProviders("Similarity Calculation Service",
				"");
		this.caEntry = new ServiceTableEntry<ICandidates, MatchingSettings>(this, "Candidates Service", settings, MatchingSettingsItem.CANDITATE_SERVICE,true) {
			@Override
			protected Set<ICandidates> findAvailableServices() {
				return CandidatesUtil.getAvailableCandidatesServices();
			}

			@Override
			protected void setSetting(ICandidates service) {
				this.settings.setCandidateService(service);
			}

			@Override
			protected ICandidates getSetting() {
				return this.settings.getCandidatesService();
			}
		};
		this.coEntry = new ServiceTableEntry<ICorrespondences, MatchingSettings>(this, "Correspondance Service", settings, MatchingSettingsItem.CORRESPONDENCE_SERVICE, true) {
			@Override
			protected Set<ICorrespondences> findAvailableServices() {
				return CorrespondencesUtil.getAvailableCorrespondencesServices();
			}

			@Override
			protected void setSetting(ICorrespondences service) {
				this.settings.setCorrespondencesService(service);				
			}

			@Override
			protected ICorrespondences getSetting() {
				return this.settings.getCorrespondencesService();
			}
		};
		this.siEntry = new ServiceTableEntry<ISimilarities, MatchingSettings>(this, "Similarites Service", settings, MatchingSettingsItem.SIMILARTIY_SERVICE, similaritesAllowed) {
			@Override
			protected Set<ISimilarities> findAvailableServices() {
				return SimilaritiesServiceUtil.getAvailableSimilaritiesService();
			}

			@Override
			protected void setSetting(ISimilarities service) {
				this.settings.setSimilaritiesService(service);
			}

			@Override
			protected ISimilarities getSetting() {
				return settings.getSimilaritiesService();
			}
		};
		this.scEntry = new ServiceTableEntry<ISimilaritiesCalculation, MatchingSettings>(this, "Similarites Calculation Service", settings, MatchingSettingsItem.SIMILARITY_CALCULATION_SERVICE, similaritesAllowed) {
			@Override
			protected Set<ISimilaritiesCalculation> findAvailableServices() {
				return SimilaritiesCalculationUtil.getAvailableISimilaritiesCalculationServices();
			}

			@Override
			protected void setSetting(ISimilaritiesCalculation service) {
				this.settings.setSimilaritiesCalculationService(service);
			}

			@Override
			protected ISimilaritiesCalculation getSetting() {
				return this.settings.getSimilaritiesCalculationService();
			}
		};
		subProviders[CORRESPONDANCE_ID].setConfigurationObject(coEntry.getSelectedService(), docType);
		subProviders[CORRESPONDANCE_ID].setValue(getLabel(coEntry.getSelectedService()));
		subProviders[CANDIDATES_ID].setConfigurationObject(caEntry.getSelectedService(), docType);
		subProviders[CANDIDATES_ID].setValue(getLabel(caEntry.getSelectedService()));
		subProviders[SIMILARITY_ID].setConfigurationObject(siEntry.getSelectedService(), docType);
		subProviders[SIMILARITY_ID].setValue(getLabel(siEntry.getSelectedService()));
		subProviders[SIMILARITY_CALCULATION_ID].setConfigurationObject(scEntry.getSelectedService(), docType);
		subProviders[SIMILARITY_CALCULATION_ID].setValue(getLabel(scEntry.getSelectedService()));
		this.caEntry.addPropertyChangeListener(propertyChangeListener);
		this.coEntry.addPropertyChangeListener(propertyChangeListener);
		this.siEntry.addPropertyChangeListener(propertyChangeListener);
		this.scEntry.addPropertyChangeListener(propertyChangeListener);
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener){
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	public ISimilarities getSelectedSimilarityService(){
		return this.siEntry.getSelectedService();
	}
	
	public ICorrespondences getSelectedCorrespondanceService(){
		return this.coEntry.getSelectedService();
	}
	
	public ISimilaritiesCalculation getSelectedSimilaritiesCalculationService(){
		return this.scEntry.getSelectedService();
	}
	
	public ICandidates getSelectedCandidatesService(){
		return this.caEntry.getSelectedService();
	}
	
	@Override
	public List<ITableEntry> getEntries() {
		ArrayList<ITableEntry> entries = new ArrayList<ITableEntry>();
		entries.add(caEntry);
		entries.add(coEntry);
		entries.add(siEntry);
		entries.add(scEntry);
		return entries;
	}

	public DynamicTableEntryProviders[] getSubProviders() {
		return subProviders;
	}
	
	public void dispose(){
		settings.removeSettingsChangedListener(settingsChangedListener);
		coEntry.dispose();
		caEntry.dispose();
		siEntry.dispose();
		scEntry.dispose();
		for (AbstractTableEntryProvider provider : subProviders){
			provider.dispose();
		}
	}
}
