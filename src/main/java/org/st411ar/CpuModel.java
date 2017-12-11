package org.st411ar;

import lombok.Data;

@Data
public class CpuModel {
	private int rank;
	private String name;
	private double performance;
	private int price;
	private double performanceCost;

	public CpuModel(int rank, String name, double performance, int price) {
		this.rank = rank;
		this.name = name;
		this.performance = performance;
		this.price = price;
		performanceCost = price / performance;
	}
}
