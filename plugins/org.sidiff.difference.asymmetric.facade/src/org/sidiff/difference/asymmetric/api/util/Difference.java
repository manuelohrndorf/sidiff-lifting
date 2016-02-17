package org.sidiff.difference.asymmetric.api.util;

import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class Difference {

	private SymmetricDifference symmetric;
	private AsymmetricDifference asymmetric;

	public Difference() {
		super();
	}

	public Difference(SymmetricDifference symmetric, AsymmetricDifference asymmetric) {
		super();
		this.symmetric = symmetric;
		this.asymmetric = asymmetric;
	}

	public SymmetricDifference getSymmetric() {
		return symmetric;
	}

	public void setSymmetric(SymmetricDifference symmetric) {
		this.symmetric = symmetric;
	}

	public AsymmetricDifference getAsymmetric() {
		return asymmetric;
	}

	public void setAsymmetric(AsymmetricDifference asymmetric) {
		this.asymmetric = asymmetric;
	}
}
