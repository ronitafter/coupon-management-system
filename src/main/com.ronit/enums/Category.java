package com.ronit.enums;

public enum Category {

	FOOD(1, "FO"), ELECTRICITY(2, "EL"), RESTAURANT(3, "RE"), VACATION(4, "VA");

	private int id;
	private String shortcut;

	private Category() {
		
	}
	private Category(int id, String shortcut) {
		this.id = id;
		this.shortcut = shortcut;

	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
