package sandraProject.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver d) {
		driver = d;
	}

	@FindBy(xpath = "//a[contains(text(),'Patient')]")
	WebElement patientLogin;
	@FindBy(xpath = "//section[@id='testimonials']//a[@class='button button-alt'][contains(text(),'Login')]")//a[text()='Login']")
	WebElement login;
	@FindBy(xpath = "//input[@name='username']")
	WebElement userName;
	@FindBy(xpath = "//input[@name='password']")
	WebElement pwd;
	@FindBy(xpath="//input[@name='submit']")
	WebElement subBtn;
	@FindBy(xpath="//section[@id='testimonials']")
	WebElement loginSection;
	
    public boolean patientLogin() throws Exception{
    	patientLogin.click();
    	return true;
	
}
    public boolean loginbtn() throws Exception{
    	//driver.navigate();
    	login.click();
    	
    	if (userName.isDisplayed()) {
    	return true;
    	}else {
    		return false;
    	}
    	
    }
    public String login(String username,String passwd ) throws Exception {
    	
    	if (!userName.isDisplayed()) {
			return "Couldn't able to login";

		}
		userName.sendKeys(username);
		pwd.sendKeys(passwd);
		subBtn.click();
		return "";
    	
    }
}
