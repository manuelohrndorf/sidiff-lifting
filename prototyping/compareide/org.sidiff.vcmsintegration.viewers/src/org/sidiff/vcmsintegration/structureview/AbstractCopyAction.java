package org.sidiff.vcmsintegration.structureview;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;

/**
 * @author Felix Breitweiser
 * Abstract template class to copy one side of a difference to the other.
 * Source and destination are determined by the subclass
 */
public abstract class AbstractCopyAction extends Action {
	
	public void run() {
		try {
			IStreamContentAccessor source = getSource();
			IEditableContent dest = getDestination();
			InputStream is = source.getContents();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	
			int nRead;
			byte[] data = new byte[16384];
	
			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
	
			buffer.flush();
	
			dest.setContent(buffer.toByteArray());
		} catch(IOException e) {
			e.printStackTrace();
		} catch(CoreException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract IStreamContentAccessor getSource();
	protected abstract IEditableContent getDestination();
}
