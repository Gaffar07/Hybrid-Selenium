package StepDefinition;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

//import com.codoid.products.fillo.Connection;

//import util.CustomAssert;
import Pages.*;

public class StepDefinition {
	//ExtentTest test;
	public StepDefinition(WebDriver driver)
	{
		//this.test=test;
	}
	
	public void tcsLogin(WebDriver driver, String testScenarioID,
			String step) throws Exception {
		TCSlogin tcslogin = new TCSlogin(driver );
		
		tcslogin.fillAndSubmitNewTCSLogin(driver, testScenarioID, step);
	}
	
	public void tcsLogout(WebDriver driver, String testScenarioID, String step) throws Exception {
		TCSLogout tcslogout = new TCSLogout(driver);
		tcslogout.ClickLogout(driver, testScenarioID, step);
	}
	
	

}
