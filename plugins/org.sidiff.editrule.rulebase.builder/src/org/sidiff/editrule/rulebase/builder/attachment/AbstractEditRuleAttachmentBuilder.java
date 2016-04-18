package org.sidiff.editrule.rulebase.builder.attachment;

public abstract class AbstractEditRuleAttachmentBuilder implements IEditRuleAttachmentBuilder {

	/**
	 * The unique ID of this attachment builder.
	 */
	private String id;

	@Override
	public void init(String id) {
		this.id = id;
	}

	@Override
	public String getID() {
		return id;
	}
}
