package org.st411ar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
	private static final String PATH = "https://www.nix.ru/hardware-review/cpu-benchmark-performance.html";

	private static final int CELL_INDEX_RANK = 0;
	private static final int CELL_INDEX_NAME = 1;
	private static final int CELL_INDEX_PERFORMANCE = 3;
	private static final int CELL_INDEX_PRICE = 4;

    public static void main( String[] args ) {
    	try {
	    	Document doc = Jsoup.connect(PATH).get();
    		Element table = doc.select("#PriceTable").first();
    		Element tableBody = table.children().first();
    		Elements rows = tableBody.children();

    		List<CpuModel> cpus = new ArrayList<>();
    		for (int i = 1; i < rows.size(); i++) {
    			Element row = rows.get(i);
    			Elements cells = row.children();

    			int rank = Integer.parseInt(cells.get(CELL_INDEX_RANK).text());

    			String name = cells.get(CELL_INDEX_NAME).text();

    			String parsedPerformance = cells.get(CELL_INDEX_PERFORMANCE).text();
    			String formatedPerformance = parsedPerformance.substring(0, parsedPerformance.length() - 1);
    			double performance = Double.parseDouble(formatedPerformance);

    			String parsedPrice = cells.get(CELL_INDEX_PRICE).text();
    			String formatedPrice = parsedPrice.replace(" ", "");
    			int price = Integer.parseInt(formatedPrice);

    			cpus.add(new CpuModel(rank, name, performance, price));
    		}

    		Collections.sort(cpus, new PerformanceCostComparator());
    		int performanceCostRank = 1;
    		for (CpuModel cpu : cpus) {
	    		System.out.println(performanceCostRank++ + "\t" + cpu);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}