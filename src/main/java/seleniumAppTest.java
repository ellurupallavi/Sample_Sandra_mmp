import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class seleniumAppTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
       WebDriver driver = new ChromeDriver();
       driver.get("https://money.rediff.com/losers/bse/daily/groupa?src=gain_lose");
       
     List<WebElement> currentPriceList= driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[4]"));
     List<WebElement> companyNameList= driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[1]"));
     
       System.out.println("Size of the List: "+ currentPriceList.size());
       
       for(int i=0; i<currentPriceList.size();i++) {
    	   if(companyNameList.get(i).getText().trim().equals("Vedanta")) 
    	   System.out.println(currentPriceList.get(i).getText());
    	   break;
    	   
       }
       HashMap<String,String> CompanySharePrice = new HashMap<String,String>();
       for(int i = 0; i < currentPriceList.size(); i++) {
    	  // System.out.println("Key :: "+companyNameList.get(i).getText().trim()+ " Value :: "+currentPriceList.get(i).getText().trim());
    	   CompanySharePrice.put(companyNameList.get(i).getText().trim(),currentPriceList.get(i).getText().trim());
    	   
       }
       System.out.println(CompanySharePrice.keySet());
       System.out.println(CompanySharePrice.values());
       //Reading Data from Xls
       FileInputStream fis = new FileInputStream(new File("SharePriceList.xls"));
       //get the workbook
       Workbook wb = Workbook.getWorkbook(fis);
       //get the sheet
       Sheet sheet = wb.getSheet(0);
       int rows = sheet.getRows();
       int coloumns = sheet.getColumns();
       
       HashMap<String,String> SharePriceExcelValues = new HashMap<String,String>();
       
       for(int i=0;i<rows;i++) {
    	   for(int j = 0; j< coloumns;j++) {
    		   Cell cell = sheet.getCell(j, i);
    		   System.out.println("Value:: "+ cell.getContents());
    		   SharePriceExcelValues.put(sheet.getCell(j,i).getContents().trim(),sheet.getCell(j+1,i).getContents().trim());
    		   j=j+1;
    	   }
    	   
       }
       
       System.out.println(SharePriceExcelValues.keySet());
       System.out.println(SharePriceExcelValues.values());
       
       Iterator cspIterator = CompanySharePrice.entrySet().iterator();
       Iterator spvexlIterator = SharePriceExcelValues.entrySet().iterator(); 
       
       // Iterate through the hashmap 
      
       while (spvexlIterator.hasNext()) { 
           Map.Entry spvxlElement = (Map.Entry)spvexlIterator.next(); 
           
           while(cspIterator.hasNext()) {
        	   Map.Entry csplElement = (Map.Entry)cspIterator.next();
        	   
        	   if(spvxlElement.getKey().equals(csplElement.getKey())) {
        		   System.out.println("Key Matched : "+ spvxlElement.getKey().toString());
        		   if(spvxlElement.getValue().equals(csplElement.getValue())) {
        			   System.out.println("Value Matched : "+ spvxlElement.getValue().toString());
        		   }else {
        			   System.out.println("Value not Matched");
        		   }
        	   }
           }
           

       } 
       
       driver.quit();
       Runtime.getRuntime().exec("killall chromedriver");
	}

}
