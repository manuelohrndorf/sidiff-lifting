package org.sidiff.remote.common.commands;

import java.io.File;
import java.io.Serializable;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

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
	protected Session session;
	
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
	public Command(Session session, File attachment) {
		super();
		this.session = session;
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
	public Session getSession() {
		return session;
	}

	/**
	 * 
	 * @param session
	 */
	public void setSession(Session session) {
		this.session = session;
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
}
