package org.sidiff.profileapplicator.services;



public class ProfileApplicator {
	
	private String inputFolderPath = null;
	private String inputConfigPath = null;
	private String outputFolderPath = null;
	
	private boolean createStereoType = true;
	private boolean deleteStereoType = true;
	private boolean addStereoType = true;
	private boolean renameStereoType = false;
	/**
	 * @return the inputFolderPath
	 */
	public String getInputFolderPath() {
		return inputFolderPath;
	}
	/**
	 * @param inputFolderPath the inputFolderPath to set
	 */
	public void setInputFolderPath(String inputFolderPath) {
		this.inputFolderPath = inputFolderPath;
	}
	/**
	 * @return the inputConfigPath
	 */
	public String getInputConfigPath() {
		return inputConfigPath;
	}
	/**
	 * @param inputConfigPath the inputConfigPath to set
	 */
	public void setInputConfigPath(String inputConfigPath) {
		this.inputConfigPath = inputConfigPath;
	}
	/**
	 * @return the ouputFolderPath
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}
	/**
	 * @param ouputFolderPath the ouputFolderPath to set
	 */
	public void setOutputFolderPath(String ouputFolderPath) {
		this.outputFolderPath = ouputFolderPath;
	}
	/**
	 * @return the createStereoType
	 */
	public boolean isCreateStereoType() {
		return createStereoType;
	}
	/**
	 * @param createStereoType the createStereoType to set
	 */
	public void setCreateStereoType(boolean createStereoType) {
		this.createStereoType = createStereoType;
	}
	/**
	 * @return the deleteStereoType
	 */
	public boolean isDeleteStereoType() {
		return deleteStereoType;
	}
	/**
	 * @param deleteStereoType the deleteStereoType to set
	 */
	public void setDeleteStereoType(boolean deleteStereoType) {
		this.deleteStereoType = deleteStereoType;
	}
	/**
	 * @return the addStereoType
	 */
	public boolean isAddStereoType() {
		return addStereoType;
	}
	/**
	 * @param addStereoType the addStereoType to set
	 */
	public void setAddStereoType(boolean addStereoType) {
		this.addStereoType = addStereoType;
	}
	/**
	 * @return the renameStereoType
	 */
	public boolean isRenameStereoType() {
		return renameStereoType;
	}
	/**
	 * @param renameStereoType the renameStereoType to set
	 */
	public void setRenameStereoType(boolean renameStereoType) {
		this.renameStereoType = renameStereoType;
	}
	
	//TODO

}
