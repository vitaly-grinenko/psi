package net.grinenko.psi;

public enum ReportType {
	PYP, MYP, DP;
	
	public static ReportType checkValueOf(String test) {
		try {
			return ReportType.valueOf(test.trim().toUpperCase());
		} catch (IllegalArgumentException ignore) {}
		
		return null;
	}
}
