package com.cscentr.microvisio.draw;

public enum Colors {

	BLACK("Black"), BLUE("Blue"), RED("Red"), GREEN("Green"), YELLOW("Yellow"), WHITE(
			"White");

	private String typeValue;

	private Colors(String type) {
		typeValue = type;
	}

	static public Colors getType(String pType) {
		for (Colors type : Colors.values()) {
			if (type.getTypeValue().equals(pType)) {
				return type;
			}
		}
		//throw new RuntimeException("unknown color");
		return null;
	}

	public String getTypeValue() {
		return typeValue;
	}
}