package utilities;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.seleniumhq.jetty9.http.DateGenerator;
import org.testng.Reporter;

import com.github.javafaker.Faker;

import constants.ExecutionSpeed;




@SuppressWarnings("deprecation")
public class GenericMethods {

	WebDriver driver;
	protected WebDriverWait wait;
	static ExecutionSpeed myType;
	protected String lastNamee;
	
	protected ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
		@Override
		public Boolean apply(WebDriver input) {
			return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
		}
	};

	public static  HashMap<String, String> quoteNumber = new HashMap<>();


	public static String getQuoteNo(String testId) {
		return quoteNumber.get(testId);
	}

	public static void setQuoteNo(String quoteNo,String testId) {
		quoteNumber.put(testId,quoteNo);
		//ConcurrentHashMap<String,String> quoteMap = TestExecutionSuite.scenarioStatus.get(testId);
		//quoteMap.put("QuoteNumber", quoteNo);
	}

	public GenericMethods() {
	}
	public GenericMethods(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, 700);
		getEnumSpeed();
		lastNamee=getRandomString();
	}


	public int getuniqueApplicationNo() {
//		String SALTCHARS = "0123456789";
//		StringBuilder salt = new StringBuilder();
//		Random rnd = new Random();
//		while (salt.length() < 5) { // length of the random string.
//			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//			salt.append(SALTCHARS.charAt(index));
//		}
//		String saltStr = salt.toString();
//		return saltStr;
		Random r = new Random( );
	    return ((1 + r.nextInt(9)) * 10000 + r.nextInt(100000));

	}
	
	public String getuniqueChequeNumber() {
//		String SALTCHARS = "0123456789";
//		StringBuilder salt = new StringBuilder();
//		Random rnd = new Random();
//		while (salt.length() < 5) { // length of the random string.
//			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//			salt.append(SALTCHARS.charAt(index));
//		}
//		String saltStr = salt.toString();
//		return saltStr;
		Random r = new Random( );
	    return ((1 + r.nextInt(9)) * 100000 + r.nextInt(100000))+"";

	}

	public static boolean executionFlag=true;
	public  int counter=1;



	public synchronized static String getRandomString()
	{
		Faker faker = new Faker();
		String firstName = faker.name().firstName();
		return firstName;

	}

	public int getSize(By by, String webElementName) throws InterruptedException {
		List<WebElement> webElements = new ArrayList<>();
		if(isElementClickable(by)){
			webElements = driver.findElements(by);
		}
		
		
		Reporter.log("Clicked on <B> "+ webElementName +"</B> ");
		return webElements.size();
	}
	
	public boolean isElementDisplayed(By webElement, String webElementName) throws InterruptedException {
		
		return waitForElementToBeDisplayed(webElement)==null?false:true;
	}
	
	public boolean isElementEnabled(By webElement) throws InterruptedException {
		
		return waitForElementToBeEnabled(webElement)==null?false:true;
	}
	
	public boolean isElementClickable(By webElement) throws InterruptedException {
		boolean flag = false;
			try {
				WebElement element = waitForElementToBeClickable(webElement);
				if(element!=null) flag = true;
			} catch (Exception e) {
				flag = false;
			}
			return flag;
	}

	//Anmol 11-06-2020 WaitClick
	public void click(By by, String webElementName) throws InterruptedException {
		try {
			WebElement webElement =  waitForElementToBeClickable(by);

			highlighter(webElement);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
		} catch (Exception e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			WebElement webElement =  waitForElementToBeClickable(by);

			highlighter(webElement);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
		}
		fetchTextFromAngularApplicationClass();
		
		Reporter.log("Clicked on <B> "+ webElementName +"</B> ");
	}
	
	public void clickForLogin(By by, String webElementName) throws InterruptedException {

		WebElement webElement =  driver.findElement(by);
		//Thread.sleep(WaitTime.veryLow);
		highlighter(webElement);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
		//Thread.sleep(WaitTime.veryLow);
		Reporter.log("Clicked on <B> "+ webElementName +"</B> ");
	}
	
	public void clickWebElement(WebElement webElement, String webElementName) throws InterruptedException {

		webElement =  waitForElementToBeClickable(webElement);

		highlighter(webElement);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
		
		Reporter.log("Clicked on <B> "+ webElementName +"</B> ");
	}

	public void actionClick(WebDriver driver,By by, String webElementName) throws InterruptedException {
		WebElement webElement = waitForElementToBeClickable(by);

		highlighter(webElement);
		Actions act=new Actions(driver);
		act.moveToElement(webElement).perform();
		webElement.sendKeys(Keys.ENTER);
		
		Reporter.log("Clicked on <B>"+ webElementName +"</B> ");

	}

	public void sendTwoKeys(By by,CharSequence command1,CharSequence command2, String webElementName) throws InterruptedException {
		try {
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);

			webElement.sendKeys(command1,command2);
		} catch (Exception e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);

			webElement.sendKeys(command1,command2);
		}
		
		
		fetchTextFromAngularApplicationClass();
		Reporter.log("Sent Key on <B>"+ webElementName +"</B> ");

	}

	public void sendOneKeys(By by,CharSequence command1, String webElementName) throws InterruptedException {
		try {
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);

			webElement.sendKeys(command1);
		} catch (Exception e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);

			webElement.sendKeys(command1);
		}
		

		fetchTextFromAngularApplicationClass();
		Reporter.log("Sent Key on  <B>"+ webElementName +"</B> ");

	}
	
	public void sendTwoKeysForLogin(By by,CharSequence command1,CharSequence command2, String webElementName) throws InterruptedException {
		try {
			WebElement webElement = driver.findElement(by);

			highlighter(webElement);

			webElement.sendKeys(command1,command2);
		} catch (Exception e) {
			wait.until(expectedCondition);
			
			WebElement webElement = driver.findElement(by);

			highlighter(webElement);

			webElement.sendKeys(command1,command2);
		}
		
		
		
		Reporter.log("Sent Key on  <B>"+ webElementName +"</B> ");

	}
	
	public void sendOneKeyForLogin(By by,CharSequence command1, String webElementName) throws InterruptedException {
		try {
			WebElement webElement = driver.findElement(by);

			highlighter(webElement);

			webElement.sendKeys(command1);
		} catch (Exception e) {
			wait.until(expectedCondition);
			
			WebElement webElement = driver.findElement(by);

			highlighter(webElement);

			webElement.sendKeys(command1);
		}
		
		Reporter.log("Sent Key on  <B>"+ webElementName +"</B> ");

	}

	  public void sendOneKeyWebElement(WebElement by,CharSequence command1, String webElementName) throws InterruptedException {
	        try {
	            WebElement webElement = waitForElementToBeClickable(by);

	            highlighter(webElement);

	            webElement.sendKeys(command1);
	        } catch (Exception e) {
	            wait.until(expectedCondition);
	            fetchTextFromAngularApplicationClass();
	            
	            WebElement webElement = waitForElementToBeClickable(by);

	            highlighter(webElement);

	            webElement.sendKeys(command1);
	        }
	        

	        fetchTextFromAngularApplicationClass();
	        Reporter.log("Sent Key on  <B>"+ webElementName +"</B> ");

	    }
	  
	  public String fetchTextselectWebElement(WebElement by, String parameter) {
			WebElement element = waitForElementToBeDisplayed(by);

			
			String data = element.getAttribute(parameter).trim();
			return data;
		}

	//Anmolclear and send keys Without js
	public void clearAndSenKeysStale(By by, String data,String fieldName) throws InterruptedException {
		WebElement webElement = waitForElementToBeClickable(by);
		
		highlighter(webElement);

		webElement.sendKeys(data);
		
		Reporter.log("<B>"+ data +"</B> is entered in  "+fieldName+" text field");
	}
	
	public void clearAndSenKeysLogin(By by, String data,String fieldName) throws InterruptedException {
		WebElement webElement = driver.findElement(by);
		
		highlighter(webElement);

		webElement.sendKeys(data);
		
		Reporter.log("<B>"+ data +"</B> is entered in  "+fieldName+" text field");
	}

	public void selectFromDropdownByVisibleTextStale(By by, String visibleText,String fieldname) throws InterruptedException {
		Thread.sleep(WaitTime.low);
		WebElement webElement = waitForElementToBeEnabled(by);
		highlighter(webElement);
		Select select = new Select(webElement);
		select.selectByVisibleText(visibleText);
		
		Reporter.log("<B>"+ visibleText +"</B> is selected from "+ fieldname);
	}
	
	public void selectFromDropdownByVisibleLogin(By by, String visibleText,String fieldname) throws InterruptedException {
		
				WebElement webElement = driver.findElement(by);
				
				wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(webElement, By.tagName("option")));
				
				Select select = new Select(webElement);
				
				select.selectByVisibleText(visibleText);
				Reporter.log("<B>"+ visibleText +"</B> is selected from "+ fieldname);
	}



	public void clearAndSenKeys(By by, String data,String fieldName) throws InterruptedException {
		try {
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);
			webElement.clear();
			webElement.sendKeys(data);
		}catch(StaleElementReferenceException |ElementNotInteractableException e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);
			
			webElement.clear();
			webElement.sendKeys(data);
		}

		fetchTextFromAngularApplicationClass();
		Reporter.log("<B>"+ data +"</B> is entered in  "+fieldName+" text field");
	}
	
	public void clearAndSenKeysWebElement(WebElement by, String data,String fieldName) throws InterruptedException {
		try {
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);
			webElement.clear();
			webElement.sendKeys(data);
		}catch(StaleElementReferenceException |ElementNotInteractableException e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			WebElement webElement = waitForElementToBeClickable(by);

			highlighter(webElement);
			
			webElement.clear();
			webElement.sendKeys(data);
		}

		fetchTextFromAngularApplicationClass();
		Reporter.log("<B>"+ data +"</B> is entered in  "+fieldName+" text field");
	}


	public static void clrscr() throws InterruptedException{
		//Clears Screen in java

		try
		{
			Runtime r = Runtime.getRuntime ();
			Process p = r.exec ("mode.com con cols=80 lines=25");
			try
			{
				p.waitFor();
			}
			catch (InterruptedException ie) {}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

	}


	public void selectFromDropdownByVisibleText(By by, String visibleText,String fieldname) throws InterruptedException {

		//wait.until(ExpectedConditions.elementToBeClickable(webElement));
		try {
		WebElement webElement = waitForElementToBeClickable(by);
		
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(webElement, By.tagName("option")));
		
		Select select = new Select(webElement);
		
		select.selectByVisibleText(visibleText);
		Thread.sleep(WaitTime.veryLow);
		}catch(StaleElementReferenceException e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			WebElement webElement = waitForElementToBeClickable(by);
			wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(webElement, By.tagName("option")));
			Select select = new Select(webElement);
			select.selectByVisibleText(visibleText);
			Thread.sleep(WaitTime.veryLow);
		}
		fetchTextFromAngularApplicationClass();
		Reporter.log("<B>"+ visibleText +"</B> is selected from "+ fieldname);
	}
	
	public void selectFromDropdownByVisibleTextWebElement(WebElement by, String visibleText,String fieldname) throws InterruptedException {

		//wait.until(ExpectedConditions.elementToBeClickable(webElement));
		try {
		WebElement webElement = waitForElementToBeClickable(by);
		
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(webElement, By.tagName("option")));
		
		Select select = new Select(webElement);
		
		select.selectByVisibleText(visibleText);
		Thread.sleep(WaitTime.veryLow);
		}catch(StaleElementReferenceException e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			WebElement webElement = waitForElementToBeClickable(by);
			wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(webElement, By.tagName("option")));
			Select select = new Select(webElement);
			select.selectByVisibleText(visibleText);
			Thread.sleep(WaitTime.veryLow);
		}
		fetchTextFromAngularApplicationClass();
		Reporter.log("<B>"+ visibleText +"</B> is selected from "+ fieldname);
	}

	public void selectFromDropdownById(By by, int index, String fieldname) throws InterruptedException {
		WebElement webElement = waitForElementToBeEnabled(by);

		highlighter(webElement);
		Select selectindex = new Select(webElement);
		selectindex.selectByIndex(index);
		try {
			Thread.sleep(WaitTime.veryLow);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Reporter.log("index <B>"+ index +"</B> is selected from "+ fieldname);
	}

	public void selectFromDropdownByValue(By by, String value, String fieldname) throws InterruptedException {
		WebElement webElement = waitForElementToBeEnabled(by);

		highlighter(webElement);
		Select selectvalue = new Select(webElement);
		selectvalue.selectByValue(value);
		try {
			Thread.sleep(WaitTime.veryLow);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.log("<B>"+ value +"</B> is selected from "+ fieldname);
	}

	public void selectCheckBox(By by, String checkBoxName) throws InterruptedException {
		WebElement webElement = waitForElementToBeClickable(by);

		highlighter(webElement);
		if(!webElement.isSelected()) {
			webElement.click();
			Reporter.log("<B>"+ checkBoxName +"</B> is checked");
		}
		
	}
	public void uncheckCheckbox(By by, String checkBoxName) throws InterruptedException {
		WebElement webElement = waitForElementToBeClickable(by);

		highlighter(webElement);
		if(webElement.isSelected()) {
			webElement.click();
			Reporter.log("<B>"+ checkBoxName +"</B> is Unchecked");
		}
		else {

			Reporter.log("<B>"+ checkBoxName +"</B> is Already Checked");
		}
		
	}

	public void switchToWindow(WebDriver driver) throws InterruptedException {
		fetchTextFromAngularApplicationClass();
		String parentWindow = driver.getWindowHandle();
		
		boolean flag = true;
		int counter = 0;
		while(flag && counter<=5) {
			Set<String> multiWindows = driver.getWindowHandles();
			for(String winHandles : multiWindows) {
				try {
					if(!winHandles.equalsIgnoreCase(parentWindow)){
						driver.switchTo().window(winHandles);
						driver.manage().window().maximize();
						Reporter.log("Switched to <B>"+driver.getTitle()+"</B> window");
						flag = false;
					}else {
						flag = true;
					}
				} catch (Exception e) {
					flag = true;
				}
				

			}
			counter++;
		}
	}



	public void selectRadioButton(By by, String radioButtonName) throws InterruptedException {
		WebElement webElement = waitForElementToBeClickable(by);

		highlighter(webElement);
		if(!webElement.isSelected()) {
			webElement.click();
			Reporter.log("<B>"+radioButtonName+"</B> is selected");
		}
		try {
			Thread.sleep(WaitTime.veryLow);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void highlighter(WebElement webElement) {
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid yellow'",webElement);
	}


	// Added by Amiya on 23-08-2018 for splitting proposal into prefix and suffix
	public String ProposalNumberPrefix(String proposalNumber) {
		String prefix=proposalNumber;
		if(proposalNumber.length()==10) {
			prefix=proposalNumber.substring(0, proposalNumber.length()-1);
		}
		return prefix;
	}

	public String ProposalNumberSuffix(String proposalNumber) {
		String suffix="";
		if(proposalNumber.length()==10) {
			suffix=proposalNumber.substring(proposalNumber.length()-1);
		}
		return suffix;
	}

	@Deprecated
	public void clickOnShowView(WebDriver driver) throws InterruptedException {

		if(driver.findElement(By.id("menuButton")).getText().equalsIgnoreCase("Show Menu")) {
			Reporter.log("<B>Landing Page</B>");
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			highlighter(driver.findElement(By.id("menuButton")));
			//((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'",driver.findElement(By.id("menuButton")) );
			executor.executeScript("arguments[0].click();", driver.findElement(By.id("menuButton")));
			//driver.findElement(By.id("menuButton")).click();
			Reporter.log("Clicked on <B>Show Menu</B> link");	
			
		}
	}

	// TODO Amiya added 28/08/2018
	public int numberOfDays(String dateBefore, String dateAfter) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = simpleDateFormat.parse(dateBefore);
		Date date2 = simpleDateFormat.parse(dateAfter);
		int daysBetween = (int) ((date2.getTime() - date1.getTime())/(1000*60*60*24));
		return daysBetween;
	}
	/*// TODO Amiya added 28/08/2018
	public void setSessionDate(XSSFWorkbook workbook, String userName, String date_in_dd_MMM_yy) throws InterruptedException, SQLException, Exception {
		UPDATE TUSES@INDIGO_INGENIUM
			SET USER_SESN_PRCES_DT='02-NOV-17' WHERE USER_ID='BHUSHAN';
			COMMIT;

		//TODO updated by Amiya 21-09-2018		

		DatabaseConnectionUtils databaseConnectionUtils=new DatabaseConnectionUtils();
		Connection databaseconnection=databaseConnectionUtils.connection_With_Database(workbook, "DatabaseLogin", "Ingenium_UAT");
		Statement statement = databaseconnection.createStatement();
	   try
	      {
		statement.executeUpdate("UPDATE TUSES SET USER_SESN_PRCES_DT='"+date_in_dd_MMM_yy+"' WHERE USER_ID='"+userName.toUpperCase()+"'");
	      }
        catch (Exception e) {
			if(date_in_dd_MMM_yy.contains("-")) {
				date_in_dd_MMM_yy.replaceAll("-", "/");
			}
			SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format2=new SimpleDateFormat("dd/MMM/yyyy");
			Date date=format1.parse(date_in_dd_MMM_yy);	
			statement.executeUpdate("UPDATE TUSES SET USER_SESN_PRCES_DT='"+format2.format(date)+"' WHERE USER_ID='"+userName.toUpperCase()+"'");
		}
       finally {
    	databaseconnection.commit();
   		databaseconnection.close();
   		Reporter.log("Session date <B>"+ date_in_dd_MMM_yy + "</B> set for user <B>"+userName+"</B>");
	}
	}
	 */
	//TODO Amiya Added 30/08/2018
	public boolean isDisplayed(By by) {
		WebElement element = waitForElementToBeDisplayed(by);
		boolean flag= true;
		try {
			if(element.isDisplayed()) {
				flag = true;

			}		
			
		}catch (NoSuchElementException e) {
			// TODO: handle exception
			flag = false;
		}

		return flag;
	}
	
	public void selectFamily(By FamilySize, String data) throws InterruptedException {
		HashMap<String, Integer> BtnPress = new HashMap<String, Integer>();
		BtnPress.put("Self + Spouse", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3", 1);
		BtnPress.put("Self + Kid1 + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father + Mother", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Mother + Mother-in-law", 1);
		BtnPress.put("Kid1 + Kid2 + Kid3", 0);
		BtnPress.put("Self",0 );
		BtnPress.put("Kid1 + Kid2 + Kid3 + Father + Mother + Father-in-law + Mother-in-law",0 );
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Mother",1 );
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3", 1);
		BtnPress.put("Self + Father + Mother", 1);
		BtnPress.put("Self + Father",1);
		BtnPress.put("Self + Spouse + Father + Mother", 1);
		BtnPress.put("Self + Father + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Father + Father-in-law", 1);
		BtnPress.put("Self + Mother + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Mother + Father-in-law", 1);
		BtnPress.put("Self + Father + Mother + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Father + Mother + Father-in-law", 1);
		BtnPress.put("Self + Father + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Father + Mother-in-law",1 );
		BtnPress.put("Self + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Father + Father-in-law",1 );
		BtnPress.put("Self + Spouse + Kid1 + Father + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Mother + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Mother + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Father + Mother + Father-in-law",1 );
		BtnPress.put("Self + Spouse + Kid1 + Father + Mother + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Father + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Father + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Father + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Father + Father-in-law + Mother-in-law",1 );
		BtnPress.put("Self + Kid1 + Mother + Father-in-law + Mother-in-law",1 );
		BtnPress.put("Self + Spouse + Kid1 + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Father + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Father-in-law",1 );
		BtnPress.put("Self + Kid1 + Kid2 + Father + Father-in-law",1 );
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Mother + Father-in-law",1 );
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Mother + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Father + Mother + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father + Mother + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Father + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Father-in-law + Mother-in-law",1 );
		BtnPress.put("Self + Kid1 + Kid2 + Father + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Father + Mother + Father-in-law + Mother-in-law",1 );
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Mother", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Mother + Father-in-law",1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father + Mother + Father-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Mother-in-law",1 );
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father + Father-in-law + Mother-in-law",1 );
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father + Father-in-law",1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father + Mother-in-law",1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Mother + Mother-in-law",1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father + Father-in-law + Mother-in-law",1);
		BtnPress.put("Self + Spouse + Kid1", 1);
		BtnPress.put("Self + Spouse + Mother", 1);
		BtnPress.put("Spouse + Father",11 );
		BtnPress.put("Spouse + Father + Father-in-law", 3);
		BtnPress.put("Spouse + Father + Mother", 6);
		BtnPress.put("Spouse + Father + Mother + Father-in-law + Mother-in-law", 2);
		BtnPress.put("Spouse + Father-in-law", 3);
		BtnPress.put("Spouse + Kid1 + Kid2", 1);
		BtnPress.put("Spouse + Mother", 6);
		BtnPress.put("Spouse + Mother + Father-in-law", 3);
		BtnPress.put("Spouse + Mother-in-law",2);
		BtnPress.put("Self + Kid1 + Father", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Mother", 1);
		BtnPress.put("Self + Spouse + Father", 1);
		BtnPress.put("Self + Spouse + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Father + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Father + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Mother + Father-in-law + Mother-in-law",1 );
		BtnPress.put("Self + Father + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Father + Mother + Father-in-law + Mother-in-law",1 );
		BtnPress.put("Self + Kid1", 1);
		BtnPress.put("Self + Kid1 + Kid2", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Spouse + Kid1 + Kid2 + Kid3 + Father + Mother + Father-in-law + Mother-in-law", 2);
		BtnPress.put("Father + Mother", 1);
		BtnPress.put("Father-in-Law + Mother-in-Law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father + Mother", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Mother-in-law",1);
		BtnPress.put("Self + Kid1 + Kid2 + Kid3 + Father + Mother + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Mother + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father + Mother + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father + Mother", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Mother", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father-in-law", 1);
		BtnPress.put("Self + Spouse + Kid1 + Kid2 + Kid3 + Father-in-law + Mother-in-law", 1);
		BtnPress.put("Self + Father-in-law", 1);
		BtnPress.put("Self + Mother-in-law", 1);
		BtnPress.put("Self + Mother",1);
		BtnPress.put("Self + Spouse + Mother-in-law",1);
		BtnPress.put("Spouse + Kid1",49);
		BtnPress.put("Spouse + Kid1 + Kid2",33);
		BtnPress.put("Spouse + Kid1 + Kid2 + Kid3",17);
		
		try {
			Thread.sleep(WaitTime.low);
			fetchTextFromAngularApplicationClass();
			//waitForElementToBeClickable(driver.findElement(FamilySize)).sendKeys(data);
			Thread.sleep(WaitTime.low);
			for(String key: BtnPress.keySet()){
				if(key.equalsIgnoreCase(data))
				{
					int btnpressed = BtnPress.get(data);
					By familysize = By.xpath("//input[@id='familysize']");
					WebElement webElement = driver.findElement(familysize);
					for(int x = 0 ; x<btnpressed;x++)
					{
						webElement.sendKeys(Keys.ARROW_DOWN);
						Thread.sleep(1000);
					}
					webElement.sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					webElement.sendKeys(Keys.TAB);
			        
			    }
			}
		}catch (StaleElementReferenceException | InterruptedException e) {
			System.out.println("Inside Stale");
			wait.until(expectedCondition);
			
			//waitForElementToBeClickable(driver.findElement(FamilySize)).clear();
			//waitForElementToBeClickable(driver.findElement(FamilySize)).sendKeys(data);
			
			for(String key: BtnPress.keySet()){
				if(key.equalsIgnoreCase(data))
				{
					int btnpressed = BtnPress.get(data);
					By familysize = By.xpath("//input[@id='familysize']");
					WebElement webElement = driver.findElement(familysize);
					for(int x = 0 ; x<btnpressed;x++)
					{
						//wait.untiltil(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@id='familysize']"))));
						
						webElement.sendKeys(Keys.ARROW_DOWN);
						Thread.sleep(100);
					}
					webElement.sendKeys(Keys.ENTER);
					
					webElement.sendKeys(Keys.TAB);
			        
			    }
			}
		}
		Reporter.log("Family Size"+": <B>"+data+" </B>");
	}
	

	//TODO Amiya Added 30/08/2018

	public String fetchTextFromApplication(By by, String fieldName) {
		WebElement element = waitForElementToBeDisplayed(by);

		//highlighter(element);
		
		String data = element.getText().trim();
		Reporter.log(fieldName+": <B>"+data+" </B> fetched from "+fieldName);
		return data;
	}
	
	public String fetchTextFromApplicationWebElement(WebElement webElement,String fieldName) {
		WebElement element = waitForElementToBeDisplayed(webElement);

		//highlighter(element);
		String data = element.getText().trim();
		Reporter.log(fieldName+": <B>"+data+" </B> fetched from "+fieldName);
		return data;
	}

	//TODO Amiya Added 30/08/2018
	
	public String fetchTextFromAngularApplication(By by, String fieldName) {
		WebElement element = waitForElementToBeDisplayed(by);

		highlighter(element);
		String data = element.getAttribute("value").trim();
		Reporter.log(fieldName+": <B>"+data+" </B> fetched from "+fieldName);
		return data;
	}
	
	
	
	public void fetchTextFromAngularApplicationClass() {
		String className = "";
		while(!className.equals("pageloader ng-hide")) {
			try{
				WebElement element = driver.findElement(By.xpath("//div[@ng-show='showLoader']"));
				className = element.getAttribute("class").trim();
			}catch(Exception e) {
				return;
			}
		}
		
	}

	@Deprecated
	public String fetchTextFromAngularApplicationUsingJquery(String element,int i, String fieldName) {

		String data = (String) ((JavascriptExecutor)driver).executeScript("return $('input["+element+"]:eq("+i+")').val();");
		Reporter.log(fieldName+": <B>"+data+" </B> fetched from "+fieldName);
		return data.trim();
	}

	//TODO Amiya 31-08-2018
	@Deprecated
	public boolean errorCapture(List<WebElement> element, String msg) {
		Boolean flag=false;
		outer:   if(element.size()!=0) {
			if(element.get(0).getText().equalsIgnoreCase(msg)) {
				highlighter(element.get(0));
				Reporter.log("<B><Font Color=\"Orange\">Message: "+element.get(0).getText());
				flag=true;

			}
			break outer;
		}
		Reporter.log("</Font></B>");
		return flag;
	}

	/*//TODO Amiya 04-09-2018
	public void verifyErrorMessage (WebDriver driver,String testScenarioID, XSSFWorkbook workbook, com.codoid.products.fillo.Connection conn,String stepGroup,CustomAssert customAssert,String Sheetname) throws Exception {
		Properties dataRow = ExcelRead.readRowDataInProperties(workbook, Sheetname, testScenarioID,stepGroup);
		if(!dataRow.getProperty("ErrorMessage").equalsIgnoreCase("")) {
			customAssert.verifyAssert(true, errorCapture(driver, dataRow.getProperty("ErrorMessage")), "Verify Error Message");
		}
	}*/
	//TODO Amiya added 24/09/2018

	@Deprecated
	public void navigateToIngeniumURL(WebDriver driver) throws Exception {
		if(ConfigReader.getInstance().getValue("ExecutionMode").equalsIgnoreCase("Migration")) {
			driver.get(ConfigReader.getInstance().getValue("IngeniumMigrationURL"));
			
		}else if(ConfigReader.getInstance().getValue("ExecutionMode").equalsIgnoreCase("Regression")) {
			driver.get(ConfigReader.getInstance().getValue("IngeniumURL"));
			
		}
		Reporter.log("Navigated to <B>"+ driver.getTitle() +"</B>");
	}


	//TODO Amiya 25-09-2018
	public void switchtoframe(WebDriver driver,Integer index) {
		driver.switchTo().frame(index);
		Reporter.log("Switch to frame()");
	}

	//TODO Amiya 25-09-2018
	public void switchtoframe(WebDriver driver,String name) throws InterruptedException {
		driver.switchTo().frame(name);
		Thread.sleep(3000);
	}

	//TODO Amiya 25-09-2SwitchToFrame018
	public void switchtodefaultframe(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	//TODO Amiya 28-09-2018
	public void switchToWindow(WebDriver driver, String windowname) throws InterruptedException {
		Set<String> windowhandles=driver.getWindowHandles();
		for(String winodow:windowhandles) {
			driver.switchTo().window(winodow);
		}
		//	driver.switchTo().window(windowname);
		Reporter.log("Switch to window("+driver.getTitle()+")");
		
		driver.switchTo().defaultContent();
		System.out.println(driver.getTitle());
	}

	//TODO Amiya 04/10/2018
	@Deprecated
	public void errorMessageCapture(WebDriver driver) {

		//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='messageArea']/div/table/tbody/tr"))));
		List<WebElement> ErrorList=driver.findElements(By.xpath("//*[@id='messageArea']/div/table/tbody/tr"));
		if(ErrorList.size()>0) {
			WebElement ErrorMessage;
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='messageArea']/div/table/tbody/tr"))));
			Reporter.log("<B><Font Color=\"Orange\">Message: </B>");
			for(int a=1;a<=ErrorList.size();a++) {	
				//highlighter(driver.findElement(By.xpath("//*[@id='messageArea']/div/table/tbody/tr["+(a)+"]")));
				ErrorMessage=driver.findElement(By.xpath("//*[@id='messageArea']/div/table/tbody/tr["+(a)+"]"));		
				highlighter(ErrorMessage);
				Reporter.log(ErrorMessage.getText());
			}
			Reporter.log("</Font>");
		}
		/*else
		{
			System.out.println("Message not displayed");
		}*/
	}
	//TODO Amiya added 04/10/2018
	public void clickWithoutJavaScript(By by, String webElementName) throws InterruptedException {
		try {
			WebElement webElement = waitForElementToBeClickable(by);
			highlighter(webElement);
			webElement.click();
		} catch (StaleElementReferenceException e) {
			wait.until(expectedCondition);
			fetchTextFromAngularApplicationClass();
			WebElement webElement = waitForElementToBeClickable(by);
			highlighter(webElement);
			webElement.click();
		}
		
		fetchTextFromAngularApplicationClass();
		Reporter.log("Clicked on <B>"+ webElementName +"</B> ");
	}

	/*public void javascriptClick(WebElement webElement, String webElementName) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		webElement element=(webElement)executor.executeScript();

	}
	 */
	public void click_driver(By by, String webElementName) throws InterruptedException {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//		highlighter(webElement);
		//executor.executeScript("arguments[0].click();", webElement);
		WebElement webElement = waitForElementToBeClickable(by);

		highlighter(webElement);
		webElement.click();
		
		Reporter.log("Clicked on <B>"+ webElementName +"</B> ");
	}

	//TODO Amiya 25-10-2018
	public void switchtoframe(WebElement iFrame, String Framename) {
		driver.switchTo().frame(iFrame);
		Reporter.log("switchtoframe("+Framename+")");
	}

	//TODO Amiya Added 05/11/2018
	@Deprecated
	public String fetchTextFromEditBox(WebElement element, String fieldName) {
		waitForElementToBeDisplayed(element);

		highlighter(element);
		String data = element.getAttribute("value").trim();
		Reporter.log(fieldName+": <B>"+data+" </B> fetched from "+fieldName);
		return data;
	}

	//TODO Amiya added 13/11/2018
	@Deprecated
	public String ageConvertMinorToMajor(String dateOfBirth, int numberOfYear) throws ParseException {	
		//Date DOB = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);
		//String date = new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth);
		String[] dateStr=dateOfBirth.split("/"); 
		int year=Integer.parseInt(dateStr[2]);
		year = year + numberOfYear;
		dateStr[2]=String.valueOf(year);
		String modifiedDateOfBirth=dateStr[0]+"/"+dateStr[1]+"/"+dateStr[2];
		return modifiedDateOfBirth;
	}

	//TODO Amiya added 13/11/2018
	@Deprecated
	public String policyAnniversaryDate(String dateOfCommencement, String convertedClientDOB) throws ParseException {		
		String[] dateOfCommencementStr=dateOfCommencement.split("/"); 
		String[] convertedClientDOBStr=convertedClientDOB.split("/"); 
		dateOfCommencementStr[2] = convertedClientDOBStr[2];
		String policyAnniversaryDate=dateOfCommencementStr[0]+"/"+dateOfCommencementStr[1]+"/"+convertedClientDOBStr[2];
		int numberOfDays = numberOfDays(convertedClientDOB, policyAnniversaryDate);
		if(numberOfDays<0) {
			int convertedClientDOBYear=Integer.parseInt(convertedClientDOBStr[2]);
			convertedClientDOBYear = convertedClientDOBYear + 1;
			dateOfCommencementStr[2] = String.valueOf(convertedClientDOBYear);
			policyAnniversaryDate=dateOfCommencementStr[0]+"/"+dateOfCommencementStr[1]+"/"+dateOfCommencementStr[2];
		}
		return policyAnniversaryDate;
	}

	//TODO Amiya added 15/11/2018
	@Deprecated
	public int getAgeInYears(String d1 , String d2) throws ParseException {
		Date first=new SimpleDateFormat("dd/MM/yyyy").parse(d1);  
		Date last=new SimpleDateFormat("dd/MM/yyyy").parse(d2);
		//DateFormat date=new SimpleDateFormat("dd/MM/yyyy");
		Calendar a = getMyCalendar(first);
		Calendar b = getMyCalendar(last);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
				(a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}
	//TODO Amiya added 15/11/2018
	@Deprecated
	public Calendar getMyCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	//TODO Amiya 21-11-2018
	@Deprecated
	public String addmonthstodate(String date, Integer monthstobeadded) {		
		if(date.contains("/")) {
			date=date.replaceAll("/", "-");
		}

		String[] dateStr=date.split("-"); 
		if(dateStr[2].length()==2) {
			String l2=dateStr[2];
			dateStr[2]=dateStr[0];
			dateStr[0]=l2;
			date=dateStr[0]+"-"+dateStr[1]+"-"+dateStr[2];
		}
		int m1=Integer.parseInt(dateStr[1]);
		int year=Integer.parseInt(dateStr[2]);
		int m2=m1+monthstobeadded;
		int m6=m2;
		while(m2>12) {
			m2=m2-12;
			year=year+1;
		}
		if(m2<10) {
			dateStr[1]="0"+String.valueOf(m2);
		}
		else {
			dateStr[1]=String.valueOf(m2);
		}
		dateStr[2]=String.valueOf(year);

		if(dateStr[0].equalsIgnoreCase("31") || ((dateStr[0].equalsIgnoreCase("30") || dateStr[0].equalsIgnoreCase("29"))&& dateStr[1].equalsIgnoreCase("02"))) {
			switch (dateStr[1]) {
			case "04":
				dateStr[0]="30";
				break;
			case "06":
				dateStr[0]="30";
				break;
			case "09":
				dateStr[0]="30";
				break;
			case "11":
				dateStr[0]="30";
				break;

			case "02":
				if((year%4)==0)
				{
					dateStr[0]="29";
				}
				else {
					dateStr[0]="28";
				}
				break;

			default:
				break;
			}	
		}
		date=dateStr[0]+"-"+dateStr[1]+"-"+dateStr[2];
		return date;
	}

	//TODO Amiya 29-11-2018
	@Deprecated
	public String subtractmonthstodate(String date, Integer monthstobedeleted) {		
		if(date.contains("/")) {
			date=date.replaceAll("/", "-");
		}
		String[] dateStr=date.split("-"); 

		int m1=Integer.parseInt(dateStr[1]);
		int year=Integer.parseInt(dateStr[2]);
		int m2=m1-monthstobedeleted;
		while(m2<1) {
			m2=m2+12;
			year=year-1;
		}
		if(m2<10) {
			dateStr[1]="0"+String.valueOf(m2);
		}
		else {
			dateStr[1]=String.valueOf(m2);
		}
		dateStr[2]=String.valueOf(year);

		if(dateStr[0].equalsIgnoreCase("31") || dateStr[0].equalsIgnoreCase("31")) {
			switch (dateStr[1]) {
			case "04":
				dateStr[0]="30";
				break;
			case "06":
				dateStr[0]="30";
				break;
			case "09":
				dateStr[0]="30";
				break;
			case "11":
				dateStr[0]="30";
				break;

			case "02":
				if((year%4)==0)
				{
					dateStr[0]="29";
				}
				else {
					dateStr[0]="28";
				}
				break;

			default:
				break;
			}	
		}
		date=dateStr[0]+"-"+dateStr[1]+"-"+dateStr[2];
		return date;
	}
	//TODO Amiya added 11/12/2018
	@Deprecated
	public String randomNumberGenerator() {
		String randomNumber = "";
		Random random = new Random();
		int num = 100000 + (int) (random.nextFloat() * 89990000);
		randomNumber = randomNumber + String.valueOf(num);
		return randomNumber;
	}
	//TODO Amiya added 11/12/2018
	public void selectCheckBoxWithJavaScript(By by, String checkBoxName) throws InterruptedException {
		WebElement webElement = waitForElementToBeClickable(by);

		highlighter(webElement);
		if(!webElement.isSelected()) {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", webElement);
			Reporter.log("<B>"+ checkBoxName +"</B> is checked");
		}
		
	}
	//TODO Amiya added 11/12/2018
	public void quitBrowser(WebDriver driver) throws InterruptedException {
		driver.quit();
		
	}
	//TODO Amiya added 14/01/2019
	@Deprecated
	public void exectionTime(long startTime_milisec, String batchName) throws InterruptedException {
		long endTime_milisec = System.currentTimeMillis();	
		NumberFormat numberFormat = new DecimalFormat("#0.00");
		Reporter.log("Time taken to execute <B><I><Font color=\"Blue\"><U>"+batchName+"</B></I></U> is "+ numberFormat.format((endTime_milisec - startTime_milisec) / 1000d) + " seconds </Font>");
	}

	//TODO Amiya added 28/01/2019
	//@Deprecated
//	public void datafromtprtx(XSSFWorkbook workbook, String ProposalNumber,String date,String username) throws SQLException {
//
//		DatabaseConnectionUtils databaseConnectionUtils=new DatabaseConnectionUtils();
//		java.sql.Connection dbconnection=databaseConnectionUtils.connection_With_Database(workbook, "DatabaseLogin", "Ingenium_UAT");
//		Statement ingeniumStatement = dbconnection.createStatement();
//
//		String query="select DOC_ID from tprtx where pol_id='"+ProposalNumber+"' and TRNXT_PRCES_DT='"+date+"' and user_id='"+username.toUpperCase()+"'";
//		try {
//			ResultSet rs=ingeniumStatement.executeQuery(query);
//			if(rs.next()) {
//				do {
//					Reporter .log("Document id created for <B>"+ProposalNumber+"</B> is <B>"+rs.getString("DOC_ID")+"</B>");
//				}	while(rs.next());
//			}
//		}
//		catch (Exception e) {
//			Reporter.log(e.toString());
//		}
//		finally {
//			dbconnection.close();
//			Reporter.log("Connection closed");
//		}
//	}

	//TODO Amiya Added 29/01/2019
	@Deprecated
	public String getDayByPassingDate(String dateInStr) throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateInStr);
		DateFormat dateFormat = new SimpleDateFormat("EEE");
		String dayOfWeek = dateFormat.format(date);
		return dayOfWeek;
	}
	//TODO Amiya Added 19/03/2019
	@Deprecated
	public String getNAVDate(String dateInStr) throws ParseException, InterruptedException {


		//TODO Amiya 04-04-2019
		if(dateInStr.contains("-"))
		{
			dateInStr=dateInStr.replaceAll("-", "/");
		}
		String dateSplit[] = dateInStr.split("/");
		//TODO Amiya 04-04-2019
		if(dateSplit[2].length()==2) {
			dateSplit[2]="20"+dateSplit[2];
			dateInStr=dateSplit[0]+"/"+dateSplit[1]+"/"+dateSplit[2];
		}

		int day = Integer.parseInt(dateSplit[0]);
		int month = Integer.parseInt(dateSplit[1]);
		//DateGenerator dateGenerator = new DateGenerator();
		if((day==15&&month==8)||(day==26&&month==1)||(day==2&&month==10)||(day==25&&month==12)) {
			//dateInStr = dateGenerator.dateGenerator("future:1", dateInStr);
		}
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateInStr);
		DateFormat dateFormat = new SimpleDateFormat("EEE");
		String dayOfWeek = dateFormat.format(date);
		if(dayOfWeek.equalsIgnoreCase("Sat")) {
			//dateInStr = dateGenerator.dateGenerator("future:2", dateInStr);
		}else if(dayOfWeek.equalsIgnoreCase("Sun")) {
			//dateInStr = dateGenerator.dateGenerator("future:1", dateInStr);
		}
		return dateInStr;
	}

	//TODO Amiya 24-04-2019
	@Deprecated
	public String ddmmyyTOddmmyyyy(String date) {
		if(date.contains("-")) {
			date.replaceAll("-", "/");
		}

		String[] datesplit=date.split("/");
		if(datesplit[2].length()==2)
			datesplit[2]="20"+datesplit[2];

		date=datesplit[0]+"/"+datesplit[1]+"/"+datesplit[2];
		return date;
	}

	//TODO Amiya Added 27/06/2019
	@Deprecated
	public String fetchTextFromApplication(WebElement element) {
		waitForElementToBeDisplayed(element);

		highlighter(element);
		String data = element.getText().trim();
		return data;
	}

	public WebElement fluentWait(final By locator,String name){
//		WebElement webElement=driver.findElement((locator));
//		highlighter(webElement);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(1500))
				.pollingEvery(Duration.ofMillis(100))
				.ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		}
				);
		Reporter.log("<B>"+ name +"</B>");
		return  foo;
	}

	//TODO Amiya Added 25/06/2019	
	@Deprecated
	public String getCurrency(Double str) {
		return String.format("%,.2f", str);	
	}
	//TODO Amiya Added 25/06/2019
	@Deprecated
	public String getIndCurrency(Float str) {
		return String.format("%,.2f", str);	
	}
	//TODO Amiya Added 25/06/2019	
	@Deprecated
	public String getIndianCurrency(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		char amountArray[] = str.toCharArray();
		int a = 0, b = 0;
		for (int i = amountArray.length - 1; i >= 0; i--) {
			if (a < 3) {
				stringBuilder.append(amountArray[i]);
				a++;
			} else if (b < 2) {
				if (b == 0) {
					stringBuilder.append(",");
					stringBuilder.append(amountArray[i]);
					b++;
				} else {
					stringBuilder.append(amountArray[i]);
					b = 0;
				}
			}
		}
		return stringBuilder.reverse().toString().concat(".00");
	}


	//
	public String getMessage(List<WebElement> message,String MessageType){
		try {
			String text="";
			if(message.size()!=0) {
				text=waitForElementToBeClickable(message.get(0)).getText();
				Reporter.log("<B> "+text +"</B>");
			}
			return text;

		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}
	@Deprecated
	public String getRoundOfDecimal(int i,Double double1) {
		return String.format("%."+i+"f", double1);
	}

	@Deprecated
	public File deleteFileInDirectory(File file) throws IOException {
		if (file != null) {		
			if (file.exists()) {
				//FileUtils.cleanDirectory(file);
			}
		}
		return file;

	}

	@Deprecated
	public String getDownloadedFileName(String fileDirectory) {
		String fileName=null;
		File folder = new File(fileDirectory);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				fileName=listOfFiles[i].getName();
			} else if (listOfFiles[i].isDirectory()) {
				fileName=listOfFiles[i].getName();
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		return fileName;

	}


	public void openNewbrowserTab(WebDriver driver) {
		/*Actions actions=new Actions(driver);
		actions.sendKeys(Keys.CONTROL+"t").build().perform();*/
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.open()");
		Reporter.log("<B>  Open new Browser Tab</B>");	
	}

	public void mouseHover(WebDriver driver,By by,String name) throws InterruptedException {
		WebElement element = waitForElementToBeDisplayed(by);
		Actions actions=new Actions(driver);
		actions.moveToElement(element).build().perform();
		Reporter.log("<B>  Hover over "+name+" </B>");
		highlighter(element);
		
	}
	
	public String fetchTextFromAngularApplicationWebElement(WebElement by, String parameter) {
        WebElement element = waitForElementToBeDisplayed(by);

        String data = new Select(element).getOptions().get(0).getText().trim();
        return data;
    }

	public void scrollIntoViewJavascript(WebElement element) {
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public Select dropDown(WebElement element) {
		try {
			waitForOptionToBePopulatedInList(element);
			Select select=new Select(element);
			highlighter(element);
			
			return select;
		}
		catch (Exception e) {
			return null;// TODO: handle exception
		}
	}


	@Deprecated
	public String getStringPrefix(String str) {
		String replaceSpecialCharecter=str.replaceAll(",", "");
		String splt[]=replaceSpecialCharecter.split("\\.");
		return splt[0];
	}


	public WebElement waitForElementToBeDisplayed(final WebElement element) {
		fetchTextFromAngularApplicationClass();
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);

		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					if (element.isDisplayed())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					return refreshWebElement(driver,element);
					
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}

	public WebElement waitForElementToBeDisplayed(final By by) {
		fetchTextFromAngularApplicationClass();
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					if (element.isDisplayed())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
					WebElement	element = webDriverWait.until(ExpectedConditions.refreshed(
							ExpectedConditions.presenceOfElementLocated(by)));
					return element;
				} 
				catch (NullPointerException ex) {
					return null;
				}
				catch (TimeoutException ex) {
					return null;
				}
			}
		});
	}

	public WebElement waitForElementToBeClickable(final By by) {
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
		//fetchTextFromAngularApplicationClass();
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					if (element.isEnabled() && element.isDisplayed())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
					WebElement elementNew = driver.findElement(by);
					WebElement	element = webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(elementNew)));
					return element;
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}

	public WebElement waitForElementToBePresent(final By by) {
		fetchTextFromAngularApplicationClass();
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					if (element.isEnabled() && element.isDisplayed())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
					WebElement	element = webDriverWait.until(ExpectedConditions.refreshed(
							ExpectedConditions.presenceOfElementLocated(by)));
					return element;
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}

	public WebElement waitForElementToBeClickable(final WebElement element) {
		fetchTextFromAngularApplicationClass();
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);

		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					if (element.isEnabled() && element.isDisplayed())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					return refreshWebElement(driver,element);
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}

	public WebElement waitForElementToBeEnabled(final WebElement element) {
		fetchTextFromAngularApplicationClass();
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);

		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					if (element.isEnabled())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					return refreshWebElement(driver,element);
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}

	public WebElement waitForElementToBeEnabled(final By by) {
		fetchTextFromAngularApplicationClass();
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>(){
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					if (element.isEnabled())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
					WebElement elementNew = driver.findElement(by);
					WebElement	element = webDriverWait.until(ExpectedConditions.refreshed(
					ExpectedConditions.elementToBeClickable(elementNew)));
					return element;
				} 
				catch (NullPointerException ex) {
					return null;
				}
				catch (TimeoutException ex) {
					return null;
				}
			}
		});
	}

	public WebElement waitForOptionToBePopulatedInList(final WebElement dropdownList) {
		fetchTextFromAngularApplicationClass();
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
		WebDriverWait webDriverWait = new WebDriverWait(driver, myType.getSeconds());
		webDriverWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					List<WebElement> options = dropdownList.findElements(By
							.tagName("option"));
					if (options.size() > 1) {
						return dropdownList;
					} 
					else
						return null;

				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("pageloader")));
					return refreshWebElement(driver,dropdownList);
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}
	
	public WebElement refreshWebElement(WebDriver webDriver, WebElement webEl) {
        String elementInfo = webEl.toString();
        elementInfo = elementInfo.substring(elementInfo.indexOf("->"));
        String elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
        elementLocator = elementLocator.substring(2, elementLocator.length() - 1);
        System.out.println(elementInfo);

        WebElement retWebEl = null;
        if (elementInfo.contains("-> link text:")) {
            retWebEl = webDriver.findElement(By.linkText(elementLocator));
        } else if (elementInfo.contains("-> name:")) {
            retWebEl = webDriver.findElement(By.name(elementLocator));
        } else if (elementInfo.contains("-> id:")) {
            retWebEl = webDriver.findElement(By.id(elementLocator));
        } else if (elementInfo.contains("-> xpath:")) {
            retWebEl = webDriver.findElement(By.xpath(elementLocator));
        } else if (elementInfo.contains("-> class name:")) {
            retWebEl = webDriver.findElement(By.className(elementLocator));
        } else if (elementInfo.contains("-> css selector:")) {
            retWebEl = webDriver.findElement(By.cssSelector(elementLocator));
        } else if (elementInfo.contains("-> partial link text:")) {
            retWebEl = webDriver.findElement(By.partialLinkText(elementLocator));
        } else if (elementInfo.contains("-> tag name:")) {
            retWebEl = webDriver.findElement(By.tagName(elementLocator));
        } else {
            System.out.println("No valid locator found. Couldn't refresh element");
        }
        return retWebEl;
    }
	
	public String panNoGenerator() {
		String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers="1234567890";
		String panNumber=null;

		StringBuilder sb = new StringBuilder();

		char[] tempFirstFiveChars = characters.toCharArray();
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			char firstFiveChars = tempFirstFiveChars[random.nextInt(tempFirstFiveChars.length)];
			sb.append(firstFiveChars);
		}
		sb.append("P");
		sb.append(lastNamee.charAt(0));
		char[] tempFourNumbers = numbers.toCharArray();
		
		
		for (int i = 0; i < 4; i++) {
			char fourNumbers = tempFourNumbers[random.nextInt(tempFourNumbers.length)];
			sb.append(fourNumbers);
		}

		char[] tempLastChar = characters.toCharArray();
		for (int i = 0; i < 1; i++) {
			char lastChar = tempLastChar[random.nextInt(tempLastChar.length)];
			sb.append(lastChar);
		}

		panNumber = sb.toString();	
		return panNumber;

	}

	private void getEnumSpeed() {
		String type;
		try {
			type = ConfigReader.getInstance().getValue("ExecutionSpeed");
			if (type != null && type.equalsIgnoreCase("fast")) {
				myType = ExecutionSpeed.FAST;
			} else if (type != null && type.equalsIgnoreCase("slow")) {
				myType = ExecutionSpeed.SLOW;
			} else {
				myType = ExecutionSpeed.MEDIUM;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}