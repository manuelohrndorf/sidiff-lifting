package org.sidiff.editrule.generator.serge.ui.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.sidiff.common.exceptions.ExceptionUtil;
import org.sidiff.editrule.generator.exceptions.EditRuleGenerationException;
import org.sidiff.editrule.generator.exceptions.WrongSettingsInstanceException;
import org.sidiff.editrule.generator.serge.Serge;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;

public final class EditRuleGenerationJob extends Job {

	private SergeSettings settings;
	
	public EditRuleGenerationJob(SergeSettings settings) {
		super("Generating Edit Rules");
		this.settings = settings;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, "Generating Edit Rules", 100);

		Serge serge = new Serge();
		try {					
			serge.init(settings, progress.split(50));
			serge.generateEditRules(progress.split(50));
		} catch (EditRuleGenerationException | WrongSettingsInstanceException e) {
			return ExceptionUtil.toStatus(e);
		}

		if (monitor.isCanceled()){
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}
}