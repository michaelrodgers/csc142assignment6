import java.io.*;
import java.util.*;

public class Main {
	
	private static String file = "../../Documents/7_04_18.csv";
	
	public static void main(String[] args) throws FileNotFoundException {
		
		try {
		
			Scanner sc = new Scanner(new File(file));
			String[] geigerData = new String[64];
			String dateTime = new String();
			int cpm = 0;
			Boolean isInt = false;
			int high = 0;
			String date = new String();
			String previousDate = new String();
			previousDate = "4/6/2018";
			double sumTotal = 0.0;
			int count = 0;
			double dailyAverage = 0.0;
			double highestDailyAverage = 0.0;
			String campingTrip = new String();
			
			geigerData = sc.nextLine().split(",");
			while (sc.hasNextLine()) {
				dateTime = geigerData[0];
				try {
					Integer.parseInt(geigerData[2]); 
					isInt = true;
				}
				catch (Exception e) {
					isInt = false;
				}
                if (isInt) {
                	cpm = Integer.parseInt(geigerData[2]);
                	if (cpm >= high) {
                		high = cpm;
                	}            	
				}
                if (cpm >= (high-5)) {
                	System.out.println(geigerData[0] + "  " + geigerData[2]);
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
			System.out.println("We suspect the camping trip took place on: " + campingTrip);
			sc.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
