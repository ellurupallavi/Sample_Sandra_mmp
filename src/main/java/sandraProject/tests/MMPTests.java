package sandraProject.tests;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import sandraProject.pom.LoginPage;
import sandraProject.pom.MmpUtilities;
import sandraProject.pom.ScheduleAppointment;
import sandraProject.pom.ViewInformation_Pom;

public class MMPTests {
	public WebDriver driver;

	// public String baseUrl =
	// "http://96.84.175.78/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";

	public String baseUrl = "http://96.84.175.78/MMP-Release2-Integrated-Build.6.8.000/";
	public String expText = "MMP";
	public String patientUsername = "ria1";
	public String patientPwd = "Ria12345";
	HashMap<String, String> hMap = new HashMap<String, String>();
	MmpUtilities objUtilities = new MmpUtilities();

	LoginPage loginPageObj = null;
	ViewInformation_Pom viewInfoPageObj = null;
	ScheduleAppointment schedApptObj = null;

	public Logger log;
	public String currDirectory = new File(System.getProperty("user.dir")).getAbsolutePath();

	@BeforeClass
	public void setupLogger() throws Exception {
		System.out.println(currDirectory);
		String logpath = currDirectory + File.separator + "logs" + File.separator;

		log = Logger.getLogger(logpath + MMPTests.class.getName() + "_result.txt");

		FileHandler handler = new FileHandler(logpath + MMPTests.class.getName() + "_result.txt");
		log.addHandler(handler);
		log.setLevel(Level.ALL);
		log.setUseParentHandlers(false);
		handler.setFormatter(new SimpleFormatter());
	}

	@Test(priority = 3)
	public void launch() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.MINUTES);

		driver.get(baseUrl);

		loginPageObj = PageFactory.initElements(driver, LoginPage.class);
		viewInfoPageObj = PageFactory.initElements(driver, ViewInformation_Pom.class);
		schedApptObj = PageFactory.initElements(driver, ScheduleAppointment.class);
		hMap.put("doctorName", "Beth");
		hMap.put("date", objUtilities.getDate(15));
		hMap.put("time", "10Am");
		hMap.put("symptom", "headAche");
	}

	@Test(priority = 5)
	public void loginTestcase() throws Exception {
		boolean rflag = false;
//		log.info("Checking Patient Login method");
//	    rflag =loginPageObj.patientLogin();
//		Assert.assertTrue(rflag, "Patient login not displayed");

		log.info("Checking loginbtn method");
		rflag = loginPageObj.loginbtn();
		log.info("Checking loginbtn method return " + rflag);
		Assert.assertTrue(rflag, " Loginbtn not displayed");

		log.info("Checking login method");
		String rflag1 = loginPageObj.login(patientUsername, patientPwd);
		Assert.assertTrue(rflag1.isEmpty(), rflag1);

	}

	@Test(priority = 8)
	public void viewinfo() throws Exception {
		log.info("Checking Information Link");
		boolean rflag = viewInfoPageObj.informationLink();
		Assert.assertTrue(rflag, "Link not Found");

		log.info("Checking Information Text");
		rflag = viewInfoPageObj.informationText();
		Assert.assertTrue(rflag, "Text Not Displayed");
//		
		log.info("Looking for Username Display");
		rflag = viewInfoPageObj.usernameDisplay();
		Assert.assertTrue(rflag, "Username Not Displayed");

		log.info("Looking for Actual Username Matched with Expected Username");
		rflag = viewInfoPageObj.isUserCorrect(patientUsername);
		Assert.assertTrue(rflag, "Actual UserName is not matching with Expexted");

		log.info("Looking in the Pagetext for expected Text Display");
		rflag = viewInfoPageObj.pageContains(expText);
		Assert.assertTrue(rflag, "Expected Text not displayed");
	}

	@Test(priority = 9)
	public void scheduleApptTests() throws Exception {
		boolean rflag = false;
		log.info("Looking for ScheduleAppointment Tab Display");
		rflag = schedApptObj.scheduleAptTab();
		Assert.assertTrue(rflag, "Appointment Tab not Displayed");

		log.info("Looking in the Pagetext for CurrentAppointment Text Display");
		rflag = schedApptObj.appointmentTextDisplayed();
		Assert.assertTrue(rflag, "CurrentAppointment Text not Displayed");

		log.info("Clicking on the CreateNewAppointment button");
		rflag = schedApptObj.clickCreateApt();
		Assert.assertTrue(rflag, "CreateNewAppointment button not Displayed");

		log.info("Booking An Appointment with Doctor");
		rflag = schedApptObj.bookAppt(hMap.get("doctorName"));
		Assert.assertTrue(rflag, "Doctor Name not Displayed");

		log.info("Filling Appointment Details and symptoms");
		rflag = schedApptObj.fillAptDetailsAndSubmit(hMap.get("date"), hMap.get("time"), hMap.get("symptom"));

		Assert.assertTrue(rflag, "Unable to fill Appointment Details");

		log.info("Verifying Appointment Details");
		rflag = schedApptObj.verifyAppoinment(hMap);

		Assert.assertTrue(rflag, "Appointment Details not Matched");

	}

	@AfterClass
	public void finalMethod() {
		//driver.quit();
		log.getHandlers()[0].close();
	}
}
