import java.util.List;
import java.io.*;
import java.util.*;
/**
 *	Population - <description goes here>
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	
 *	@since	
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";
	
	// File length
	private final int FILE_LENGTH = 31765;
	
	/** Reads data from file and stores itin a list **/
	public void readFile() throws IOException{
		cities = new ArrayList<City>();
		Scanner in = new Scanner(new File(DATA_FILE));
		in.useDelimiter("[\t\n]");
		for (int i=0;i<FILE_LENGTH;i++) {
			String currState = in.next();
			while (currState.equals("North") || currState.equals("South") || currState.equals("West") || currState.equals("District") || currState.equals("District of")) {
				currState += " " + in.next();
			}
			String currCity = in.next();
			String curr = "";
			while ((int)(curr = in.next()).charAt(0) <=90) {
				currCity += " " + curr;
			}
			String currType = curr;
			int currPop = in.nextInt();
			cities.add(new City(currCity,currState,currType,currPop));
		}
	}
	
	public List<City> sortPopAscending(List<City> currList) {
		List<City> sorted = new ArrayList<City>();
		while (currList.size() > 0) {
			int temp =-1;
			City currnum =new City("","","",Integer.MAX_VALUE);
			for (int i=0;i<currList.size();i++) {
				if (currList.get(i).getPopulation() < currnum.getPopulation()) {
					temp = i;
					currnum = currList.get(i);
				}
			}
			sorted.add(currnum);
			currList.remove(temp);
		}
		return sorted;
	}
	public List<City> sortPopDescending(List<City> currList) {
		if (currList.size() <= 1) {
			return currList;
		}
		return mergePopDescending(currList.subList(0,currList.size()/2),currList.subList(currList.size()/2,currList.size()));
	}
	public List<City> mergePopDescending(List<City> a, List<City> b) {
		List<City> merged = new ArrayList<City>();
		while (a.size() > 0 || b.size() > 0) {
			if (a.size() == 0) {
				merged.add(b.get(0));
				b.remove(0);
			}else{
				if (b.size() ==0) {
					merged.add(a.get(0));
					a.remove(0);
				}else{
					if (a.get(0).getPopulation() > b.get(0).getPopulation()) {
						merged.add(a.get(0));
						a.remove(0);
					}else{
						merged.add(b.get(0));
						b.remove(0);
					}
				}
			}
		}
		return merged;
	}
	public List<City> sortNameAscending(List<City> currList) {
		List<City> sorted = new ArrayList<City>();
		while (currList.size() > 0) {
			int temp =0;
			City currnum =new City("","","",0);
			for (int i=0;i<currList.size();i++) {
				if (currList.get(i).getCityName().compareTo(currnum.getCityName()) < 0) {
					temp = i;
					currnum = currList.get(i);
				}
			}
			sorted.add(currnum);
			currList.remove(temp);
		}
		return sorted;
	}
	public List<City> sortNameDescending(List<City> currList) {
		if (currList.size() <= 1) {
			return currList;
		}
		return mergeNameDescending(currList.subList(0,currList.size()/2),currList.subList(currList.size()/2,currList.size()));
	}
	public List<City> mergeNameDescending(List<City> a, List<City> b) {
		List<City> merged = new ArrayList<City>();
		while (a.size() > 0 || b.size() > 0) {
			if (a.size() == 0) {
				merged.add(b.get(0));
				b.remove(0);
			}else{
				if (b.size() ==0) {
					merged.add(a.get(0));
					a.remove(0);
				}else{
					if (a.get(0).getCityName().compareTo(b.get(0).getCityName()) > 0) {
						merged.add(a.get(0));
						a.remove(0);
					}else{
						merged.add(b.get(0));
						b.remove(0);
					}
				}
			}
		}
		return merged;
	}
	
	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}
	
	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}
	
	public static void main(String[] args) throws IOException{
		Population pain = new Population();
		pain.thing();
	}
	public void thing() throws IOException{
		readFile();
		System.out.println(sortNameAscending(cities));
	}
}
