package Pages;


import java.awt.AWTException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ExcelOperations.ExcelReader;
import utilities.*;

//import com.codoid.products.fillo.Connection;

//import util.CustomAssert;
//import util.ExcelRead;
//import util.GenericMethods;
//import util.LoginUserFromSyncMap;
//import util.MapOfUserIDAssignedToAllocation;
//import util.WaitTime;

public class TCSlogin extends GenericMethods 

{
	
	
	private By username = By.xpath("//input[@id='pUserName']");
	
	
	private By password = By.xpath("//input[@id='textfield2']");
	
	
	private By role = By.xpath("//input[@id='textfield4']");
	
	
	private By loginBTN = By.xpath("//strong[contains(text(),'LOGIN')]");
	
	
	private By GSTIN = By.xpath("//label[contains(text(),'GSTIN')]/parent::div/following-sibling::div/p-autocomplete/span/input");
	
	
	private By FinancialYear = By.xpath("//select[@id='finyear']");
	
	
	private By TaxPayerPeriod = By.xpath("//select[@id='taxper']");
	
	
	private By Submit = By.xpath("//button[@type='submit']");
	
	private By roleCLICK=By.xpath("//i[@id='roleIcon']");
	
	private By Role=By.xpath("//span[@id='rolePan']");
	
    WebDriverWait wait;
    //ExtentTest test;
	public TCSlogin(WebDriver driver) {
		super(driver);
		//this.test=test;
		
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, 30);	
	}
	
	
	
	
	
//	public void fillAndSubmitNewTCSLogin(WebDriver driver,String testCaseName, XSSFWorkbook workbook,String stepGroup,CustomAssert customAssert) throws Exception
//	{
//		Properties dataRow = ExcelRead.readRowDataInProperties(workbook, "TCS_LoginPage", testCaseName,stepGroup);
//		
//		
//		Reporter.log("<B>Login To Application</B>");
//		
//		switchtoframe(driver, "display");
//
//		
//		String user=LoginUserFromSyncMap.userIDForLogin(MapOfUserIDAssignedToAllocation.listOfUserIDForExecution,testCaseName);
//        System.out.println("");
//        System.out.println("Below details are from login Page::");
//        System.out.println(user+" USER ID sent for login.");
//        System.out.println("At the time of login using USER ID: "+user+" Updated MAP shows as below:");
//        System.out.println("Status wise MAP::"+MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.toString());
//		
//		clearAndSenKeysLogin(username,user, "UserName");
//		clearAndSenKeysLogin(password,dataRow.getProperty("Password"), "Password");
//		//clearAndSenKeys(role, dataRow.getProperty("Role"), "Role");
//		clickForLogin(loginBTN, "Login Button");
//	
//		switchtodefaultframe(driver);
//		
//        switchtoframe(driver, "head");
//   
//        String Role1= fetchTextFromApplication(Role, "Role");
//        
//    if(!Role1.equalsIgnoreCase("BOPS"))
//    {
//		click(roleCLICK,"Role Click");	
//		
//		click(By.xpath("//div[contains(text(),'BOPS')]"),"Selected Role as BOPS");
//    } 
//		switchtodefaultframe(driver);
//	}
	
	public void fillAndSubmitNewTCSLogin(WebDriver driver,String testCaseName,String step) throws AWTException, InterruptedException
	{
		
		//test.pass("Executing Step "+step);
		String user=LoginUserFromSyncMap.userIDForLogin(MapOfUserIDAssignedToAllocation.listOfUserIDForExecution,testCaseName);
	    System.out.println("");
	    System.out.println("Below details are from login Page::");
	    System.out.println(user+" USER ID sent for login.");
	    System.out.println("At the time of login using USER ID: "+user+" Updated MAP shows as below:");
	    System.out.println("Status wise MAP::"+MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.toString());
		
		
		try {
			Properties datarow=ExcelReader.getScenarioData(testCaseName);
			String s=datarow.getProperty("TypeOfOrganization");
			
			System.out.println(s);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Thread.sleep(5000);
		Actions act = new Actions(driver);
		act.sendKeys(Keys.chord(Keys.ESCAPE));
		
		
		
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		By element=By.xpath("//input[@id='email']");
		
		clearAndSenKeys(element, user, step);
		
//		test.pass("Value passed "+user);
//		test.log(Status.PASS, "value entered");
		//input[@id='password']
		By element1=By.xpath("//input[@id='password']");
		
		clearAndSenKeys(element1, "Gaffar@12345", step);
		//test.pass("password "+" Gaffar@12345");
		
		
		By element2=By.xpath("//button[@type='submit']");
		click(element2, step);
		
//		WebElement element3=driver.findElement(element2);
//		Select s=new Select(element3);
//		s.getOptions(); //this will give all available options of dropdown
//		s.getAllSelectedOptions();//this will give all selected options of dropdown
		
		
		
		
		
		
	}
	
}
	
