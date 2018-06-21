package org.sidiff.remote.common.commands;

import java.io.File;
import java.io.Serializable;

import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public abstract class Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8746690092613427167L;

	/**
	 * 
	 */
	protected ECommand eCommand;
	
	/**
	 * 
	 */
	protected int attachment_size;
	
	/**
	 * 
	 */
	protected String attachment_name;	
	
	/**
	 * 
	 */
	protected transient File attachment;

	/**
	 * 
	 * @param session
	 * @param eCommand
	 * @param attachment
	 */
	public Command(File attachment) {
		super();
		if(attachment != null) {
			this.attachment_size = (int) attachment.length();
			this.attachment_name = attachment.getName();
		}else {
			this.attachment_size = 0;
			this.attachment_name = "";
		}
	}
	


	/**
	 * 
	 * @return
	 */
	public ECommand getECommand() {
		return eCommand;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAttachmentSize() {
		return attachment_size;
	}

	/**
	 * 
	 * @return
	 */
	public String getAttachmentName() {
		return attachment_name;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAttached() {
		return attachment_size > 0;
	}

	/**
	 * 
	 * @return
	 */
	public File getAttachment() {
		return attachment;
	}
	
	/**
	 * 
	 * @param attachment
	 */
	public void setAttachment(File attachment) {
		this.attachment = attachment; 
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Command: " + eCommand);
		return stringBuilder.toString();
	}
}
