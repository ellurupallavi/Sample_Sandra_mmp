package sandraProject.pom;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MmpUtilities {
	public static void main(String[] args) {
		MmpUtilities obj = new MmpUtilities();
		String date = obj.getDate(10);
		System.out.println(date);
	}
	
	
public String getDate(int days) {
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, days);
	Date d =  cal.getTime();
	System.out.println(d);

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
	String date = sdf.format(d);
	System.out.println(date);
	return date.toString();
	
	
}
}
