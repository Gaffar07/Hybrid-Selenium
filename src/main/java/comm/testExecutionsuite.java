package comm;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.util.FileUtils;

import ExcelOperations.ExcelReader;
import ExcelOperations.ExecutionRecord;
import StepDefinition.StepKeyword;
import comm.*;
import constants.PropertyConfigs;
import comm.TestEngine;
import comm.testExecutionsuite;
import utilities.ConfigReader;
import utilities.LoginUserFromSyncMap;
import utilities.MapOfUserIDAssignedToAllocation;
import utilities.RuntimeObj;

//@Listeners(TestListener.class)
public class testExecutionsuite implements ITestListener {
	
	private StringBuilder reportContent = new StringBuilder();
	
	

	WebDriver driver;
	ITestResult result;
	RuntimeObj runtime;
	String scenarioId;
	public static ConcurrentHashMap<String, ConcurrentHashMap<String,String>> scenarioStatus = new ConcurrentHashMap<>();
	//public String executionStatus = "";
//	ExtentSparkReporter htmlReporter;
//    ExtentReports extent;
//    ExtentTest test;
	//ATUTestRecorder recorder;
    
	
   
	
	@BeforeTest
	public void launch()
	{
		
		//htmlReporter = new ExtentSparkReporter("D:\\reports\\extentSparkReport.html");
		//htmlReporter.setAppendExisting(true);
		
		//ExtentHtmlReporter report =new ExtentHtmlReporter("demo.html");
	   // extent = new ExtentReports();
//       // extent.attachReporter(htmlReporter);
//        htmlReporter.config().setDocumentTitle("Test Report");
//        htmlReporter.config().setReportName("Test Report");
//        htmlReporter.config().setTheme(Theme.STANDARD);
//
//        // Optional: Add system information or any other configuration
//        extent.setSystemInfo("OS", "Windows");
//        extent.setSystemInfo("Browser", "Chrome");
//        extent.setSystemInfo("name", "Gaffar Shaikh");
//        extent.setSystemInfo("Product", "Fire"); 
//    
		
		System.out.println("browser started");
		System.setProperty("webdriver.edge.driver", "D:\\Gaffar\\test\\Drivers\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.get("https://acme-test.uipath.com/login");
        driver.manage().window().maximize();
        
        
        //new parameters needs to be deleted if nothing works
        //java.util.Date date=new java.util.Date();
		//SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
        //recorder = new ATUTestRecorder("D:\\ScriptVideos\\","new Video",false);
        //recorder.start();
        
        
        
	}
	@Parameters({ "ScriptReference", "ScenarioID", "Description", "Product", "Module","ScenarioUID"})
	
	@Test(testName = "ScenarioID")
	public void demo(String ScriptReference, String ScenarioID, String Description, String Product, String Module, String ScenarioUID) throws Exception
	{
		
//		test = extent.createTest(ScenarioUID);
//		test.log(Status.PASS, "Navigated to Acme Test");
//		test.pass("Navigated to Acme Test");
        
		this.scenarioId=ScenarioUID;
		System.out.println("Scenario id is : "+this.scenarioId);
		runtime=new RuntimeObj();		
		runtime.setCurentScenario(ScenarioUID);
		
		
		try
		{
			//List<String> l1=frameworkService.getScriptStepFromScriptName(ScriptReference);	
			List<String> l1=ExcelReader.getScriptStepFromScriptName(ScriptReference);
			
			
			for(String step:l1)
			{
				System.out.println(step);
				StepKeyword keyword=new StepKeyword(driver);
				
				keyword.executeTestStep(driver, ScenarioID,step);
				//WebDriver driver,String testScenarioID, String step,String stepGroup,String GSTNID,String ReturnType,XSSFWorkbook workbook,SoftAssert customAssert
				
			
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//assertTrue(false);
			throw e;
		}
		
	}
	
	
	
	public static void setScenarioStatus(String testId,String status){
		ConcurrentHashMap<String, String> statusMap = testExecutionsuite.scenarioStatus.get(testId);
		statusMap.put("Status", status);
	}
	
	
	
	/*@AfterSuite
	public void afterSuite() throws Exception {
		System.out.println("After Suite method executed");
		//ScenarioType
		System.out.println(scenarioStatus);
		//String ScanerioType=ConfigReader.getInstance().getValue(PropertyConfigs.ScanerioType);
		String ScanerioType="acme test";
		 XSSFWorkbook workbook = new XSSFWorkbook();
	    	XSSFSheet sheet = workbook.createSheet("Result Summary");
	    	int rowNumber = 0;
	    	XSSFRow rowheadHeader = sheet.createRow((short)rowNumber++);
	    	rowheadHeader.createCell(0).setCellValue("Scenario");
	    	rowheadHeader.createCell(1).setCellValue("Status");
	    	//rowheadHeader.createCell(2).setCellValue("QuoteNo");
	    	//rowheadHeader.createCell(3).setCellValue("PolicyNo");
	    	
	    	Iterator<Map.Entry<String, ConcurrentHashMap<String, String>>> parent = scenarioStatus.entrySet().iterator();
	    	while (parent.hasNext()) {
	    	    Map.Entry<String, ConcurrentHashMap<String, String>> parentPair = parent.next();
	    	    System.out.println("parentPair.getKey() :   " + parentPair.getKey() + " parentPair.getValue()  :  " + parentPair.getValue());

	    	    Iterator<Map.Entry<String, String>> child = (parentPair.getValue()).entrySet().iterator();
	    	    XSSFRow rowhead = sheet.createRow((short)rowNumber++);
	    	    rowhead.createCell(0).setCellValue(parentPair.getKey());
	    	    while (child.hasNext()) {
	    	    	
	    	    	String myKey = child.next().getKey();
	    	    	String value = parentPair.getValue().get(myKey);
	    	    	if(myKey.equalsIgnoreCase("Status")) {
	    	    		rowhead.createCell(1).setCellValue(value);
	    	    	}else if(myKey.equalsIgnoreCase("QuoteNumber")) {
	    	    		rowhead.createCell(2).setCellValue(value);
	    	    	}
		        	//txt.quickquote.setQuoteNo(QuoteNo);
		        	
//		        	 rowhead.createCell(2).setCellValue(ConfigReader.getInstance().getValue("Quote_No"));
	    	       // child.remove(); // avoids a ConcurrentModificationException
	    	    }

	    	}
	       
	        FileOutputStream fileOut = new FileOutputStream(frameworkService.outputpath+"\\LatestSheet.xlsx");
	        workbook.write(fileOut);
	        fileOut.close();
	        
	        //workbook.close();
	        String cssOut = ".invocation-failed,  .test-failed  { background-color: #E53030; }\n" + 
					".invocation-percent, .test-percent { background-color: #006600; }\n" + 
					".invocation-passed,  .test-passed  { background-color: #0D5D12; }\n" + 
					".invocation-skipped, .test-skipped { background-color: #A5A129; }\n" + 
					"\n" + 
					".main-page {\n" + 
					"  font-size: x-large;\n" + 
					"}\n" + 
					"\n" + 
					"body{background-color: #5A5353;color: white;}\n" + 
					"table{border-color: black;}\n" + 
					"";
	        FileWriter fw=new FileWriter(frameworkService.outputpath+"\\"+ScanerioType+"\\testng.css");    
	           fw.write(cssOut);    
	           fw.close();
	if(!scenarioStatus.isEmpty()) {
		File pass = new File(frameworkService.outputpath + "\\"+ScanerioType+"\\PASS");
		File fail = new File(frameworkService.outputpath + "\\"+ScanerioType+"\\FAIL");
		File assertion = new File(frameworkService.outputpath + "\\"+ScanerioType+"\\ASSERT");
		
		if(!pass.exists()) {
			pass.mkdir();
			
		}
		if(!fail.exists()) {
			fail.mkdir();
		}
		if(!assertion.exists()) {
			assertion.mkdir();
			
		}
		Set<String> keys = scenarioStatus.keySet();
		for (String key : keys) {
			System.out.println("=====================================================>" + key);
			ConcurrentHashMap<String,String> map = scenarioStatus.get(key);
			String status = map.get("Status");
			System.out.println(status);
			File htmlFileToMove = new File(frameworkService.outputpath + "\\"+ScanerioType+"\\" + key + ".html");
			File xmlFileToMove = new File(frameworkService.outputpath + "\\"+ScanerioType+"\\" + key + ".xml");

			if(status.equalsIgnoreCase("PASSED")) {
				htmlFileToMove.renameTo(new File(frameworkService.outputpath +"\\"+ScanerioType+"\\PASS\\" + key + ".html"));
				xmlFileToMove.renameTo(new File(frameworkService.outputpath +"\\"+ScanerioType+"\\PASS\\" + key + ".xml"));
			}else if(status.equalsIgnoreCase("FAILED")) {
				htmlFileToMove.renameTo(new File(frameworkService.outputpath +"\\"+ScanerioType+"\\FAIL\\" + key + ".html"));
				xmlFileToMove.renameTo(new File(frameworkService.outputpath + "\\"+ScanerioType+"\\FAIL\\" + key + ".xml"));
			}
			else {
				htmlFileToMove.renameTo(new File(frameworkService.outputpath +"\\"+ScanerioType+"\\ASSERT\\" + key + ".html"));
				xmlFileToMove.renameTo(new File(frameworkService.outputpath + "\\"+ScanerioType+"\\ASSERT\\" + key + ".xml"));
			}
		}
		
		
	}
	//TestExecutionSuite.fileInputStream.close();
	//TestExecutionSuite.workbook.close();
	System.out.println("After Suite method executed");
	}*/

	
	
	
//	@Override
//    public void onTestStart(ITestResult result) {
//        // Test Start
//        Reporter.log("<h3>Test " + result.getMethod().getMethodName() + " started</h3>");
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        // Log success and apply green background
//        Reporter.log("<div style='background-color:green;color:white;padding:10px;'>");
//        Reporter.log("<h4>Test Case: " + result.getMethod().getMethodName() + " PASSED</h4>");
//        Reporter.log("</div>");
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        // Log failure and apply red background
//        Reporter.log("<div style='background-color:red;color:white;padding:10px;'>");
//        Reporter.log("<h4>Test Case: " + result.getMethod().getMethodName() + " FAILED</h4>");
//        Reporter.log("<p>Error Message: " + result.getThrowable().getMessage() + "</p>");
//        
//        // Capture and attach screenshot
//        String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
//        Reporter.log("<p><b>Screenshot:</b><br><img src='" + screenshotPath + "' width='500' height='300'/></p>");
//        Reporter.log("</div>");
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        // Log skipped test with orange background
//        Reporter.log("<div style='background-color:orange;color:white;padding:10px;'>");
//        Reporter.log("<h4>Test Case: " + result.getMethod().getMethodName() + " SKIPPED</h4>");
//        Reporter.log("</div>");
//    }
//
//    @Override
//    public void onStart(ITestContext context) {
//        // Test context start
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        // Test context finish
//    }
//    
//    
//    private String captureScreenshot(String testName) {
//        // Capture screenshot
//        TakesScreenshot ts = (TakesScreenshot) driver;
//        File srcFile = ts.getScreenshotAs(OutputType.FILE);
//        String destFile = "/screenshots/" + testName + ".png";
//        
//        
//
//        try {
//        	
//        	Path sourcePath = Paths.get("style.css");
//	        Path targetPath = Paths.get("C:\\Users\\gaffa\\OneDrive\\Desktop\\Azure\\style.css");
//	        
//	       
//	            // Move file from source to target
//	            //Files.move(srcFile.getAbsolutePath(), destFile);
//	            System.out.println("File moved successfully!");
//        }
//	         catch (Exception e) {
//	            System.out.println("An error occurred while moving the file.");
//	            e.printStackTrace();
//	        }
//        	
//        	
        	
        	
        	//FileUtils fileUtils = new FileUtils();
        	//fileUtils.copyFile("", "");
//           FileUtils.copyFile(srcFile, new File(destFile));
//        } catch (IOException e) {
//            e.printStackTrace();
        
//        return null;
//    }

	
	
//	@Override
//    public void onTestStart(ITestResult result) {
//        reportContent.append("<tr><td>").append(result.getName()).append("</td><td>Started</td></tr>");
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        reportContent.append("<tr><td>").append(result.getName()).append("</td><td class='pass'>Pass</td></tr>");
//        try {
//            generateHtmlReport();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        reportContent.append("<tr><td>").append(result.getName()).append("</td><td class='fail'>Fail</td></tr>");
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        reportContent.append("<tr><td>").append(result.getName()).append("</td><td class='skipped'>Skipped</td></tr>");
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
////        try {
////            generateHtmlReport();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//    
//    private void generateHtmlReport() throws IOException {
//        String reportHtml = "<html>" +
//                "<head>" +
//                "<title>TestNG Report</title>" +
//                "<link rel='stylesheet' type='text/css' href='styles.css'/>" +
//                "</head>" +
//                "<body bgcolor='green'>" +
//                "<h1>Test Report</h1>" +
//                "<table border='1'>" +
//                "<tr><th>Test Case</th><th>Status</th></tr>" +
//                reportContent.toString() +
//                "</table>" +
//                "</body>" +
//                "</html>";
//
//        // Write to HTML file
//        FileWriter writer = new FileWriter(scenarioId+".html");
//        writer.write(reportHtml);
//        writer.close();
//        
//       
//        
//        //=========================================================================
//       // Path sourcePath = Paths.get("style.css");
//        //Path targetPath = Paths.get(runtime.getExecutionapath()+"\\style.css");
//        
//       
//        
//        
//     try
//     {
//    	 String s="body {\r\n"
// 				+ "    font-family: Arial, sans-serif;\r\n"
// 				+ "}\r\n"
// 				+ "\r\n"
// 				+ "h1 {\r\n"
// 				+ "    color: #4CAF50;\r\n"
// 				+ "}\r\n"
// 				+ "\r\n"
// 				+ "table {\r\n"
// 				+ "    width: 100%;\r\n"
// 				+ "    border-collapse: collapse;\r\n"
// 				+ "}\r\n"
// 				+ "\r\n"
// 				+ "th, td {\r\n"
// 				+ "    padding: 8px;\r\n"
// 				+ "    text-align: left;\r\n"
// 				+ "}\r\n"
// 				+ "\r\n"
// 				+ "th {\r\n"
// 				+ "    background-color: #f2f2f2;\r\n"
// 				+ "}\r\n"
// 				+ "\r\n"
// 				+ "td.pass {\r\n"
// 				+ "    color: green;\r\n"
// 				+ "}\r\n"
// 				+ "\r\n"
// 				+ "td.fail {\r\n"
// 				+ "    color: red;\r\n"
// 				+ "}\r\n"
// 				+ "\r\n"
// 				+ "td.skipped {\r\n"
// 				+ "    color: orange;\r\n"
// 				+ "}";
// 		
// 		FileOutputStream out=new FileOutputStream(runtime.getExecutionapath()+"\\"+"style.css");
// 		System.out.println("Execution path : "+runtime.getExecutionapath());
// 		out.write(s.getBytes());
// 		
//     }
//     catch(Exception e)
//     {
//    	 e.printStackTrace();
//     }
//	   
//	   
//	   
//	   
//    	  
//       }
//       
    

		
	@AfterMethod
	public void Status(ITestResult result) throws IOException
	{
		
		
		String s="body {\r\n"
 				+ "    font-family: Arial, sans-serif;\r\n"
 				+ "}\r\n"
 				+ "\r\n"
 				+ "h1 {\r\n"
 				+ "    color: #4CAF50;\r\n"
 				+ "}\r\n"
 				+ "\r\n"
 				+ "table {\r\n"
 				+ "    width: 100%;\r\n"
 				+ "    border-collapse: collapse;\r\n"
 				+ "}\r\n"
 				+ "\r\n"
 				+ "th, td {\r\n"
 				+ "    padding: 8px;\r\n"
 				+ "    text-align: left;\r\n"
 				+ "}\r\n"
 				+ "\r\n"
 				+ "th {\r\n"
 				+ "    background-color: #f2f2f2;\r\n"
 				+ "}\r\n"
 				+ "\r\n"
 				+ "td.pass {\r\n"
 				+ "    color: green;\r\n"
 				+ "}\r\n"
 				+ "\r\n"
 				+ "td.fail {\r\n"
 				+ "    color: red;\r\n"
 				+ "}\r\n"
 				+ "\r\n"
 				+ "td.skipped {\r\n"
 				+ "    color: orange;\r\n"
 				+ "}";
		
		
		
		FileOutputStream out=new FileOutputStream(runtime.getExecutionapath()+"\\"+"style.css");
 		System.out.println("Execution path : "+runtime.getExecutionapath());
 		out.write(s.getBytes());
		
		 String reportHtml = "<html>" +
	                "<head>" +
	                "<title>TestNG Report</title>" +
	                "<link rel='stylesheet' type='text/css' href='styles.css'/>" +
	                "</head>" +
	                "<body style='background-color:green'>" +
	                "<h1>Test Report</h1>" +
	                "<table border='1'>" +
	                "<tr><th>Test Case</th><th>Status</th></tr>" +
	                reportContent.toString() +
	                "</table>" +
	                "</body>" +
	                "</html>";
		 
		 
		 
		

	        // Write to HTML file
	        FileWriter writer = new FileWriter(runtime.getExecutionapath()+"\\"+scenarioId+".html");
	        writer.write(reportHtml);
	        writer.close();
	        
	        
	        
	 		
	 		
	        
		
		
		
		//extent.flush();
		//recorder.stop();
		ExecutionRecord record=new ExecutionRecord();
		String scenario=runtime.getCurentScenario();
		System.out.println(scenario);
		System.out.println("Scenario id is : "+scenario);
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			
			record.UpdateResult("pass",this.scenarioId);
			System.out.println("test Passed!!!");
		}
		
		else
		{
			record.UpdateResult("fail",this.scenarioId);
			System.out.println("test Failed!!");
		}
		
		//id release here
		 String user="";
	        if(MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.containsValue(scenario)){
	            user = LoginUserFromSyncMap.getKeyByValue(MapOfUserIDAssignedToAllocation.listOfUserIDForExecution,scenario);
	            if (!user.equals("")){
	                MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.put(user,"Free");
	            }
	            System.out.println("User ID is blank !!!");
	        }
	        System.out.println("");
	        System.out.println("Execution Done for: "+scenario+" User ID: "+user+" released");
	        System.out.println("After release of USER ID Updated__MAP shows as below");
	        System.out.println("Status wise MAP::"+MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.toString());
	        System.out.println("");
		
		
		
		
	}
	
	@AfterTest
	public void close()
	{
		
		driver.close();
		
	}
	
	@AfterSuite
	public void clean()
	{
		//extent.flush();
		
	}
	
//	private synchronized void UserIDRelease(String ScenarioUID){
//        String user="";
//        if(MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.containsValue(ScenarioUID)){
//            user = LoginUserFromSyncMap.getKeyByValue(MapOfUserIDAssignedToAllocation.listOfUserIDForExecution,ScenarioUID);
//            if (!user.equals("")){
//                MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.put(user,"Free");
//            }
//            System.out.println("User ID is blank !!!");
//        }
//        System.out.println("");
//        System.out.println("Execution Done for: "+ScenarioUID+" User ID: "+user+" released");
//        System.out.println("After release of USER ID Updated__MAP shows as below");
//        System.out.println("Status wise MAP::"+MapOfUserIDAssignedToAllocation.listOfUserIDForExecution.toString());
//        System.out.println("");
//        
//    }
	


}
