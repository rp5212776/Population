/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Rishabh Prabhu
 *	@since	
 */
public class City implements Comparable<City> {
	
	// fields
	private String cityName;
	private String stateName;
	private String cityType;
	private int population;
	
	// constructor
	public City (String a, String b, String c, int d) {
		cityName = a;
		stateName = b;
		cityType = c;
		population = d;
	}
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	 public int compareTo(City other) {
		 if (this.population != other.population) {
			 return this.population - other.population;
		 }
		 if (!this.stateName.equals(other.stateName)) {
			 return this.stateName.compareTo(other.stateName);
		 }
		 return this.cityName.compareTo(other.cityName);
	 }
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	public boolean equals(City other) {
		return this.compareTo(other)==0;
	}
	/**	Accessor methods */
	public String getCityName() {
		return cityName;
	}
	public String getStateName() {
		return stateName;
	}
	public String getCityType() {
		return cityType;
	}
	public int getPopulation() {
		return population;
	}
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", stateName, cityName, cityType,
						population);
	}
}
