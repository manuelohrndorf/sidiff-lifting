package org.sidiff.repair.evaluation.run;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = "test 'tesaasdfasf�ladsfj' test";
		System.out.println(s.replaceAll("\\'.*\\'", ""));
	}

}
