package sandraProject.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ViewInformation_Pom {
	
	WebDriver driver;
	
	public ViewInformation_Pom(WebDriver d ) {
		driver = d;
	}
	
	@FindBy(xpath = "//span[contains(text(),'Information')]")
	WebElement viewInformationLink;
	
	@FindBy(xpath = "//h3[@class='panel-title']")// //h3[@class='panel-title']
	WebElement informationText;
	
	@FindBy(xpath = "//h3[@class='page-header']")////h3[@class='page-header']
	WebElement userDisplay;
	
	@FindBy(xpath="//div[@class='panel-title']")
	WebElement pageText;
	
	public boolean informationLink() {
		viewInformationLink.click();
		return true;
	}
	public boolean informationText() throws Exception {
		if(informationText.isDisplayed()) {
			System.out.println("Information Text Displayed");
			return true;
		}
		return false;
	}
	public boolean usernameDisplay() {
		
		if(userDisplay.isDisplayed()) {
			System.out.println("Username  Text Displayed");
			return true;
		}
		return false;
	}
    public boolean pageContains(String expText) {
    	String actText = pageText.getText();
    	if(actText.contains(expText)) {
    		//System.out.println(actText);
    		return true;
    		
    	}else {
    		return false;
    	}
    	
    }
public boolean isUserCorrect(String expUsername) {
		
		if(userDisplay.getText().equalsIgnoreCase(expUsername)) {
			System.out.println("Username  Matched");
			return true;
		}
		return false;
	}
}
