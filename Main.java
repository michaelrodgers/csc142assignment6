package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class csc142assignment6 {

	private static String file = "/Users/lindsaygilbert/Desktop/Projects/java-class/CSC142Project2/7_04_18.csv";

	public static void main(String[] args) throws FileNotFoundException {

		try {

			Scanner sc = new Scanner(new File(file));
			String[] geigerData = new String[64];
			String dateTime = new String();
			int cpm = 0;
			Boolean isInt = false;
			int high = 0;
			int low = (int) Double.POSITIVE_INFINITY;
			String date = new String();
			String previousDate = new String();
			previousDate = "4/6/2018";
			double sumTotal = 0.0;
			int count = 0;
			int dataPoints = 0;
			double dailyAverage = 0.0;
			double highestDailyAverage = 0.0;
			String campingTrip = new String();

			geigerData = sc.nextLine().split(",");
			LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
			LinkedHashMap<Integer, Integer> histogramMap = new LinkedHashMap<Integer, Integer>();

			while (sc.hasNextLine()) {
				//date and time stamp
				dateTime = geigerData[0];

				try {
					Integer.parseInt(geigerData[2]);
					isInt = true;
				}
				catch (Exception e) {
					isInt = false;
				}
                if (isInt) {
                	dataPoints++;
                	cpm = Integer.parseInt(geigerData[2]);
                	int cpmCount = histogramMap.containsKey(cpm) ? histogramMap.get(cpm) : 0;
                	histogramMap.put(cpm, cpmCount + 1);
                	if (cpm >= high) {
                		high = cpm;
                	}
                	if (cpm < low) {
                		low = cpm;
                	}
				}
                if (cpm >= (high-5)) {
                	map.put(geigerData[0], cpm);
                }

                date = dateTime.split(" ")[0];
                if (date.contentEquals(previousDate)) {
                	sumTotal = sumTotal + cpm;
                	count = count + 1;
                	dailyAverage = sumTotal/count;
                	previousDate = date;
                } else if (count != 0) {
                	sumTotal = cpm;
                	count = 1;
                	if (dailyAverage > highestDailyAverage) {
                		highestDailyAverage = dailyAverage;
                		campingTrip = previousDate;
                	}
                	previousDate = date;
                }
				geigerData = sc.nextLine().split(",");
				while (geigerData.length <= 2) {geigerData = sc.nextLine().split(",");}
			}
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() >= (high - 5)) {
					System.out.println(entry.getKey() + " " + entry.getValue());
				}
			}

			int bin = 4;
			int scale = 1000;
			int binLow = 1;
			System.out.println("histogram of values, each * represents 1000");
			while (binLow + bin <= 40) {
				int frequency = 0;
				System.out.print(binLow + "-" + (binLow + bin) + " ");
				for (int i = binLow; i <= binLow + bin; i++) {
					for (Map.Entry<Integer, Integer> histogramEntry : histogramMap.entrySet()) {
						if (histogramEntry.getKey() == i) {
							frequency += Math.ceil(histogramEntry.getValue() / scale);
						}
					}

				}
				for (int j = 0; j <= frequency; j++) {
					System.out.print("*");
				}
				binLow = binLow + bin + 1;
				System.out.println("");
			}
			System.out.println("We suspect the camping trip took place on: " + campingTrip);
			sc.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
