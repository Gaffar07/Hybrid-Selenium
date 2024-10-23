package Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

//import com.codoid.products.fillo.Connection;

//import util.CustomAssert;
//import util.ExcelRead;
//import util.GenericMethods;
//import util.WaitTime;


public class TCSLogout //extends GenericMethods
{
	
	ExtentTest test;
	private By Logout = By.xpath("//i[@title='Logout']");
	
	WebDriverWait wait;
	public TCSLogout(WebDriver driver) {
		//super(driver);
		//this.test=test;
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, 30);	
	}
	
	
	public void ClickLogout(WebDriver driver,String testCaseName, String step) throws Exception
	{
		//Properties dataRow = ExcelRead.readRowDataInProperties(workbook, "TCS_LoginPage", testCaseName,stepGroup);
		//Reporter.log("<B>Logout from Application</B>");
	
		
		//switchtodefaultframe(driver);
		
		//switchtoframe(driver, "head");
		
		//clickForLogin(Logout,"Logout Button");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	WebElement logout	=driver.findElement(By.xpath("//a[contains(text(),'Log Out')]"));
	logout.click();
//	test.pass("Logout click");
//	test.log(Status.PASS, "value entered");
//	
		
		
		
		//driver.switchTo().alert().accept();
		
		System.out.println("Complete");
		
  }
	
}

