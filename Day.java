public class Day implements Cloneable, Comparable<Day>{

	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	//Constructor
	public Day(String sDay) {
		set(sDay);
	}

	public void set(String sDay) { //Set year,month,day based on a string like 01-Mar-2021
		String[] sDayParts = sDay.split("-");
		this.year = Integer.parseInt(sDayParts[2]); //Apply Integer.parseInt for sDayParts[2];
		this.day = Integer.parseInt(sDayParts[0]);
		this.month = MonthNames.indexOf(sDayParts[1])/3+1;
	}

	public void addthreedays() {
		if (month == 2){
			if (isLeapYear(year)){
				if (day >= 27){
					month += 1;
					day = (day+3)-29;
				}
				else day += 3;
			}
			else {
				if (day >= 26){
					month += 1;
					day = (day+3)-28;
				}
				else day += 3;
			}
		}
		else if (month == 12) {
			if (day >= 29){
				year += 1;
				month = 1;
				day = (day+3)-31;
			}
			else day += 3;
		}
		else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day >= 28){
				month += 1;
				day = (day+3)-30;
			}
			else day += 3;
		}
		else {
			if (day >= 29){
				month += 1;
				day = (day+3)-31;
			}
			else day += 3;
		}
	}

	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31;
			case 4: case 6: case 9: case 11:
					 return d<=30;
			case 2:
					 if (isLeapYear(y))
						 return d<=29;
					 else
						 return d<=28;
		}
		return false;
	}

	// Return a string for the day like dd MMM yyyy
	public String toString() {
		return day + "-"+ MonthNames.substring((month-1)*3,month*3) + "-"+ year;
	}

	public Day clone(){
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copy;
	}

	@Override
    public int compareTo(Day d) {
		if (this.year > d.year) return 1;
		else if (this.year < d.year) return -1;
		else if (this.month > d.month) return 1;
		else if (this.month < d.month) return -1;
		else if (this.day > d.day) return 1;
		else if (this.day < d.day) return -1;
        return 0;
    }
}
