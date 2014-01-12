package net.grinenko.psi;

public enum Period {
	QUARTER, SEMESTER;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
	public static Period checkValueOf(String test) {
		try {
			return Period.valueOf(test.trim().toUpperCase());
		} catch (IllegalArgumentException ignore) {}
		
		return null;
	}
}