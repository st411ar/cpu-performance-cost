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
	private static final String PATH = 
            "https://www.nix.ru/hardware-review/cpu-benchmark-performance.html";

	private static final int CELL_INDEX_RANK = 0;
	private static final int CELL_INDEX_NAME = 1;
	private static final int CELL_INDEX_PERFORMANCE = 3;
	private static final int CELL_INDEX_PRICE = 4;

    public static void main( String[] args ) {
    	try {
	    	Document doc = Jsoup.connect(PATH).get();
            Elements rows = parseRows(doc);
    		List<CpuModel> cpus = parseCpuModels(rows);
    		Collections.sort(cpus, new PerformanceCostComparator());
            printCpus(cpus);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    private static Elements parseRows(Document doc) {
        return doc.select("#PriceTable")
                .stream()
                .findFirst()
                .map(table -> table.children())
                .orElse(new Elements())
                .stream()
                .findFirst()
                .map(tableBody -> tableBody.children())
                .orElse(new Elements());
    }

    private static List<CpuModel> parseCpuModels(Elements rows) {
        List<CpuModel> cpus = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            Elements cells = rows.get(i).children();
            CpuModel cpu = parseCpuModel(cells);
            cpus.add(cpu);
        }
        return cpus;
    }

    private static CpuModel parseCpuModel(Elements cells) {
        int rank = parseRank(cells);
        String name = parseName(cells);
        double performance = parsePerformance(cells);
        int price = parsePrice(cells);
        return new CpuModel(rank, name, performance, price);
    }

    private static int parseRank(Elements cells) {
        return Integer.parseInt(cells.get(CELL_INDEX_RANK).text());
    }

    private static String parseName(Elements cells) {
        return cells.get(CELL_INDEX_NAME).text();
    }

    private static Double parsePerformance(Elements cells) {
        String parsed = cells.get(CELL_INDEX_PERFORMANCE).text();
        String formated = parsed.substring(0, parsed.length() - 1);
        return Double.parseDouble(formated);
    }

    private static int parsePrice(Elements cells) {
        String parsed = cells.get(CELL_INDEX_PRICE).text();
        String formated = parsed.replace(" ", "");
        return Integer.parseInt(formated);
    }

    private static void printCpus(List<CpuModel> cpus) {
        int performanceCostRank = 1;
        for (CpuModel cpu : cpus) {
            System.out.println(performanceCostRank++ + "\t" + cpu);
        }
    }

}