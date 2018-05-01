package org.sidiff.integration.preferences.interfaces;

import java.util.Comparator;

/**
 * Interface for ordered steps of the SiDiff pipeline.
 * @author Daniel Roedder, Robert Müller
 * 
 */
public interface IOrderableStep {

	/**
	 * Returns the position of the given step. The position determines the order in which the steps are consumed.
	 * Steps with lower positions are consumed first. Positions are not required to be unique.
	 * The order of steps with equal positions is undefined.
	 * @return position of this step
	 */
	public int getPosition();

	/**
	 * Comparator to sort {@link IOrderableStep}s by their {@link #getPosition() position} in ascending order.
	 */
	Comparator<IOrderableStep> COMPARATOR = new Comparator<IOrderableStep>() {
		@Override
		public int compare(IOrderableStep o1, IOrderableStep o2) {
			return o1.getPosition() - o2.getPosition();
		}
	};
}
