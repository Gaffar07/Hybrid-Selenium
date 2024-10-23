package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.testng.Reporter;

import constants.PropertyConfigs;

import comm.testExecutionsuite;


public class SetUpWebdriver {
	static String saveDownloadedFile=null;
	
	public static String getSaveDownloadedFile() {
		return saveDownloadedFile;
	}

	public static void setSaveDownloadedFile(String saveDownloadedFile) {
		SetUpWebdriver.saveDownloadedFile = saveDownloadedFile;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public static WebDriver setupWebDriver(String driverType,String testScenario_Id) throws Exception {
		try
		{
		WebDriver driver = null;
		 DesiredCapabilities capabilities=null;
        switch (driverType) {
            case "Desktop":              
                ChromeOptions options = new ChromeOptions();
                options.setBinary(getAppPath() + getAppExeName());               
                capabilities =new  DesiredCapabilities();
                capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setJavascriptEnabled(true);
                capabilities.setBrowserName("chrome");
                setupDriver("webdriver.chrome.driver", "chromedriver");
                driver = new ChromeDriver(capabilities);
                break;
            case "Chrome":
                 capabilities = DesiredCapabilities.chrome();
                 setupDriver("webdriver.chrome.driver", "chromedriver");
                 capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                 capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                 driver = new ChromeDriver(capabilities);
                 
                break;
            case "Ie":
                setupDriver("webdriver.ie.driver", "IEDriverServer");
                ChromeOptions options1 = new ChromeOptions();
                options1.setBinary(getAppPath() + getAppExeName()); 
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                capabilities.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1); 
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                driver = new InternetExplorerDriver(capabilities);
                break;
            case "Firefox":
                setupDriver("webdriver.gecko.driver", "geckodriver");
                driver = new FirefoxDriver();
                break;
            default:
                setupDriver("webdriver.chrome.driver", "chromedriver");
                driver = new ChromeDriver(getChromeOptions());
        }
		/*
		 * capabilities.setCapability("name", testScenario_Id);
		 * capabilities.setCapability("idleTimeout", 200); driver = new
		 * RemoteWebDriver(new URL("http://localhost:4445/wd/hub"),capabilities);
		 */
  		driver.navigate().to(ConfigReader.getInstance().getValue(PropertyConfigs.TcsURL));
  		System.out.println(driver.getTitle());
 		driver.manage().window().maximize();
 		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}catch (Exception e) {
		System.out.println(e.getMessage());
		//TestExecutionSuite.setScenarioStatus(testScenario_Id,"FAILED");
		return null;
	}
	}

    public static void setupDriver(String key, String driverExeName) {
        System.setProperty(key, System.getProperty("user.dir") + File.separator + "drivers" + File.separator + driverExeName + ".exe");
    }


    private static ChromeOptions getChromeOptions() throws Exception {
    	saveDownloadedFile=System.getProperty("user.dir") + File.separator +ConfigReader.getInstance().getValue(PropertyConfigs.DownloadPath);
        Map<String, Object> prefsMap = new HashMap<String, Object>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        prefsMap.put("download.default_directory", saveDownloadedFile);
        prefsMap.put("plugins.plugins_disabled", new String[] {
            "Chrome PDF Viewer"
        });
        prefsMap.put("plugins.always_open_pdf_externally", true);
        
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefsMap);
        options.addArguments("test-type"); 
        options.addArguments("--disable-extensions");
        options.addArguments("no-sandbox");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--start-maximized");
        options.addArguments("disable-infobars");
        //To run Chrome Headless mode
        //options.addArguments("--headless");
        return options;
    }

    public static String getAppPath() throws Exception {
        return (ConfigReader.getInstance().getValue(PropertyConfigs.APP_PATH));
    }

    public static String getAppExeName() throws Exception {
        return getAppName() + ".bat";
    }

    public static String getAppName() throws Exception {
        return String.format("start", ConfigReader.getInstance().getValue(PropertyConfigs.APP_PRODUCT));
    }

    public static String getAdminAppName() throws Exception {
        return String.format("start", ConfigReader.getInstance().getValue(PropertyConfigs.APP_PRODUCT));
    }

    public static synchronized String captureScreenShot(WebDriver driver, String snapshotFolder, String destinationFilePathLocation) throws IOException{
		String fileName = new String();
		RemoteWebDriver augmentedDriver = (RemoteWebDriver) driver;
		File screenshot = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);

		fileName = destinationFilePathLocation  + new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date())+ ".png";
		File destinationFilePath = new File(snapshotFolder + File.separator + fileName);
		FileUtils.copyFile(screenshot, destinationFilePath);
		Reporter.log("<br> <a target = \"_blank\" href=\"" + destinationFilePath +"\">"+
				"<img src=\""
				+ destinationFilePath 
				+ "\" alt=\"ScreenShot Not Available\" height=\"500\" width=\"600\"> </a>");
		System.out.println(destinationFilePath.getAbsolutePath());
		return destinationFilePath.getAbsolutePath();
	}
	public static void txtFileGenerator(String fileName,HashSet<String> data) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("E:\\screenshot\\Latest\\"+fileName+".txt", "UTF-8");
		for(String str:data) {
			writer.println(str);
		}
		writer.close();
	}
	public static HashSet<String> getWebElementsFromPage(WebDriver driver) throws InterruptedException{
		HashSet<String> set=new HashSet<String>();
		List<WebElement> elements=driver.findElements(By.xpath("//*"));
		for(WebElement e:elements) {
			set.add(e.getTagName()+"<-------------------------------------------------------------------->"+e.getText());
		}
		return set;
	}
	public static void copyingOldBuild() throws IOException {
		try {
		//	FileUtils.copyDirectory(new File("D:\\screenshot\\Latest"), new File("D:\\screenshot\\Old"));
			//FileUtils.cleanDirectory(new File("D:\\screenshot\\SrcCode"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean codeDeploymentUtility(String fileName) throws IOException {
		boolean deployflag=true;
		BufferedReader reader1 = new BufferedReader(new FileReader("E:\\screenshot\\Old\\"+fileName+".txt"));

		BufferedReader reader2 = new BufferedReader(new FileReader("E:\\screenshot\\Latest\\"+fileName+".txt"));

		String line1 = reader1.readLine();

		String line2 = reader2.readLine();

		boolean areEqual = true;

		int lineNum = 1;
		List<Integer> number=new ArrayList<>();

		while (line1 != null || line2 != null)
		{
			if(line1 == null || line2 == null)
			{
				areEqual = false;

				break;
			}
			else if(! line1.equalsIgnoreCase(line2))
			{
				areEqual = false;
				Reporter.log("<font color='Red'>Two files have different content. They differ at line "+lineNum+"</font>");
				System.out.println("Two files have different content. They differ at line "+lineNum);
				Reporter.log("<font color='Red'>"+fileName+" in old has "+line1+" and "+fileName+" in new has "+line2+" at line "+lineNum+"</font>");
				System.out.println(fileName+" in old has "+line1+" and "+fileName+" in new has "+line2+" at line "+lineNum);
				deployflag=false;
			}

			line1 = reader1.readLine();

			line2 = reader2.readLine();

			lineNum++;
		}

		if(areEqual)
		{
			Reporter.log("Two files have same content.");
			System.out.println("Two files have same content.");
		}
		/*	else
		{
				Reporter.log("Two files have same content.");
			System.out.println("Two files have different content. They differ at line "+lineNum);

			System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
		}*/

		reader1.close();

		reader2.close();
		return deployflag;
	}
	public static void sendMailAfterExecution() {
		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.rediffmailpro.com");

		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");

		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", "true");

		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

	}
	public static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor){
	    CommandExecutor executor = new HttpCommandExecutor(command_executor) {

	    @Override
	    public Response execute(Command command) throws IOException {
	        Response response = null;
	        if (command.getName() == "newSession") {
	            response = new Response();
	            response.setSessionId(sessionId.toString());
	            response.setStatus(0);
	            response.setValue(Collections.<String, String>emptyMap());

	            try {
	            	  Field commandCodec = null;
		                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
		                commandCodec.setAccessible(true);
		                commandCodec.set(this, new W3CHttpCommandCodec());

	                Field responseCodec = null;
	                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
	                responseCodec.setAccessible(true);
	                responseCodec.set(this, new W3CHttpResponseCodec());
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }

	        } else {
	            response = super.execute(command);
	        }
	        return response;
	    }
	    };

	    return new RemoteWebDriver(executor, new DesiredCapabilities());
	}

}
