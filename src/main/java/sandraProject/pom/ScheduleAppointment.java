package sandraProject.pom;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ScheduleAppointment {
	WebDriver driver;

	public ScheduleAppointment(WebDriver d) {
		driver = d;
	}

	@FindBy(xpath = "//span[contains(text(),'Schedule Appointment')]")
	WebElement scheduleApp;

	@FindBy(xpath = "(//h3[@class='panel-title'])[1]")
	WebElement currentApp;

	@FindBy(xpath = "//input[@value='Create new appointment']")
	WebElement createNewApp;

	@FindBy(xpath = "//h4[text()='Dr.Beth']/ancestor::ul/following-sibling::button")
	WebElement bookApp;
	
	String path ="//h4[contains(text(),'DOCTOR')]/ancestor::ul/following-sibling::button";

	@FindBy(xpath = "//input[@id='datepicker']")
	WebElement selDate;

	@FindBy(xpath = "//select[@name='time']")
	WebElement selTime;

	@FindBy(xpath = "//button[@id='ChangeHeatName']")
	WebElement continueBtn;

	@FindBy(xpath = "//textarea[@id='sym']")
	WebElement symptom;

	@FindBy(xpath = "//input[@value='Submit']")
	WebElement submitBtn;

	@FindBy(xpath = "//table/tbody/tr[2]/td[1]")
	WebElement tableData;
	
	@FindBy(xpath = "(//h3[@class='panel-title'])[2]")
	WebElement actDate;
	
	@FindBy(xpath = "//a[contains(text(),'Provider')]")
	WebElement actDocName;
	
	@FindBy(xpath = "//a[contains(text(),'Time')]")
	WebElement actTime;
	
	@FindBy(xpath = "//a[contains(text(),'Symptoms')]")
	WebElement actSympyom;

	
	public boolean scheduleAptTab() throws Exception{
		Thread.sleep(3000);
		if(scheduleApp.isDisplayed()) {
			scheduleApp.click();
			return true;
		}
		System.out.println("returning false");
		return false;
	}
	
	public boolean appointmentTextDisplayed() throws Exception {
		Thread.sleep(4000);
		if(currentApp.isDisplayed()) {
			System.out.println(currentApp.getText());
			return true;
		}
		return false;
		
	}
	public boolean clickCreateApt() {
		if(createNewApp.isDisplayed()) {
			createNewApp.click();
			return true;
			
		}
		return false;
	}
	public boolean bookAppt(String doctorName) {
		
		path= path.replace("DOCTOR", doctorName);
		
		driver.findElement(By.xpath(path)).click();
		return true;
		
	}
	public boolean fillAptDetailsAndSubmit(String date, String time,String symp) throws Exception {
		driver.switchTo().frame("myframe");
		Thread.sleep(2000);
		System.out.println("Date"+date);
		selDate.sendKeys(date);
		selDate.sendKeys(Keys.TAB);
		Select timeSelector = new Select(selTime);
		timeSelector.selectByVisibleText(time);
		selTime.sendKeys(Keys.TAB);
		//selTime.sendKeys(time);
		Thread.sleep(2000);
		continueBtn.click();
		Thread.sleep(2000);
		symptom.sendKeys(symp);
		submitBtn.click();
		return true;
		
	}
	public boolean verifyAppoinment(HashMap<String,String> hMap) throws Exception {
		scheduleApp.click();
		Thread.sleep(2000);
		String docName[] = actDocName.getText().split(":");
		String dateOfApp = actDate.getText();
		String timeOfApp[] = actTime.getText().split(":");
		String symptom[] = actSympyom.getText().split(":");
		System.out.println("Values from appointment "+docName[1]+" -"+dateOfApp+" -"+timeOfApp[1]+" -"+symptom[1]);
		
		if(hMap.get("doctorName").contains(docName[1])&&hMap.get("date").contains(dateOfApp)&&
				hMap.get("time").contains(timeOfApp[1].trim())&&hMap.get("symptom").contains(symptom[1])) {
			
			return true;
		}
		return false;
	}
	
}
