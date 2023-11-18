package com.clothes.store.domain;

import java.util.LinkedList;
import java.util.List;

public enum GarmentType {

	COAT("Coat"), PANTS("Pants"), SHIRT("Shirt"), JACKET("Jacket"), COVERED("Covered"),
	SOCK("Sock"), SCARF("Scarf");

	private String description;

	GarmentType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static List<GarmentType> getGarmentType() {
		List<GarmentType> garmentTypes = new LinkedList<>();
		garmentTypes.add(GarmentType.COAT);
		garmentTypes.add(GarmentType.PANTS);
		garmentTypes.add(GarmentType.SHIRT);
		garmentTypes.add(GarmentType.JACKET);
		garmentTypes.add(GarmentType.COVERED);
		garmentTypes.add(GarmentType.SOCK);
		garmentTypes.add(GarmentType.SCARF);

		return garmentTypes;
	}

}
