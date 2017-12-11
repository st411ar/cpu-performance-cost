package org.st411ar;

import java.util.Comparator;

public class PerformanceCostComparator implements Comparator<CpuModel> {
	public int compare(CpuModel cpu1, CpuModel cpu2) {
		if (cpu1.getPerformanceCost() - cpu2.getPerformanceCost() < 0) {
			return -1;
		}
		return 1;
	}
}