package comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import utilities.ConfigReader;
import utilities.MapOfUserIDAssignedToAllocation;
import utilities.RuntimeObj;
public class frameworkService {
	
	public static String outputpath;
	List<XmlSuite> suites;
	public frameworkService(String outputpath) throws Exception
	{
		MapOfUserIDAssignedToAllocation.getInstance();
		this.outputpath=outputpath;
		
	}
	
	
	public void prepareScenario()
	{
		
		//String excelFilePath = "C:\\Users\\gaffa\\OneDrive\\Desktop\\0003_ScenarioSuite.xlsx";
		String excelFilePath = System.getProperty("user.dir")+"\\Data\\0003_ScenarioSuite.xlsx";
        try (FileInputStream excelFile = new FileInputStream(excelFilePath)) {
        	XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Create TestNG XmlSuite
            XmlSuite suite = new XmlSuite();
            suite.setName("DynamicSuite");
            suite.addListener("comm.CustomReporter");
           // suite.addListener("ExtentReportListener.extentreport");
            //suite.setThreadCount(5);
           // suite.setThreadCount(2);
            //suite.setParallel(XmlSuite.ParallelMode.TESTS);
            
            //suite.addListener("comm.TestListener");

           
            //suite.addListener("comm.TestListener");
            
            
            
            
            Map<String,String> m1=new HashMap<String,String>();
			 
			 m1.put("BrowserName", "EDGE");
			 m1.put("LoBName", "pricing");
			 m1.put("BrowTestScenario_RepositoryFileIndexserName", "TCS_Return\\TestDataSuite\\CalculationData\\0002_TestDataSuite.xlsx");
			 m1.put("TestData_RepositoryFile", "TCS_Return\\TestScenarioSuite\\CalculationScanerio\\0003_ScenarioSuite.xlsx");
            
            suite.setParameters(m1);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(0);
                //cell != null
                if (sheet.getRow(i).getCell(8).getStringCellValue().equals("Yes")) {
                    String testName = sheet.getRow(i).getCell(1).getStringCellValue().toString();
;
                    // Create TestNG XmlTest
                    XmlTest test = new XmlTest(suite);
                    test.setName(testName);
                    
                    

                    // Add class to the XmlTest
                    List<XmlClass> classes = new ArrayList<XmlClass>();
                    classes.add(new XmlClass("comm.testExecutionsuite"));
                    test.setXmlClasses(classes);
                    
                    Map<String,String> m2=new HashMap<String,String>();
	         		   
	         		   m2.put("ScriptReference", sheet.getRow(i).getCell(3).getStringCellValue().toString());
	         		   m2.put("ScenarioID", sheet.getRow(i).getCell(1).getStringCellValue().toString());
	         		   m2.put("Description", "Positive flow for various family construct");
	         		   m2.put("Product", sheet.getRow(i).getCell(7).getStringCellValue().toString());
	         		   m2.put("Module", sheet.getRow(i).getCell(2).getStringCellValue().toString());
	         		   m2.put("ScenarioUID", sheet.getRow(i).getCell(6).getStringCellValue().toString());
	         		    
	         		    test.setParameters(m2);
	         		    test.setVerbose(2);
	         		    
                }
            }

            // Write the TestNG XML to a file
            try (FileOutputStream fileOut = new FileOutputStream("testng.xml"))
            {
                //List<XmlSuite> suites = new ArrayList<>();
            	suites = new ArrayList<>();
                suites.add(suite);
                //new org.testng.xml.XmlSuite().toXml();
                fileOut.write(suite.toXml().getBytes());
                System.out.println("TestNG.xml file generated successfully.");    
            }

        } catch (IOException e) {
            e.printStackTrace();
            
        }		
		
	}
	
	public void LaunchExecution()
	{
		TestNG testng = new TestNG();
		testng.setOutputDirectory(outputpath);
		RuntimeObj.setExecutionapath(outputpath);
		//testng.setOutputDirectory("C:\\Users\\gaffa\\OneDrive\\Desktop\\CustomReport");
        testng.setXmlSuites(suites);
	    testng.run();
	}
	
	
	
	public static List getScriptStepFromScriptName( String scriptName) throws Exception {
		List<String> l1=new ArrayList();
		//]String CONFIG_PATH = ConfigReader.getInstance().getValue("TestDataFolder")+"\\0001_MasterTestSuite.xlsx";
		File f=new File(System.getProperty("user.dir")+"\\Data\\0001_MasterTestSuite.xlsx");
		
		FileInputStream fileInputStream=new FileInputStream(f);
		//String excelFilePath = System.getProperty("user.dir")+"\\Data\\0003_ScenarioSuite.xlsx";
		try (FileInputStream excelFile = new FileInputStream(f)) {
        	XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("MasterTestScriptStep");
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(0);
                //cell != null
                if (sheet.getRow(i).getCell(1).getStringCellValue().equals(scriptName)) {
                    //String testName = sheet.getRow(i).getCell(1).getStringCellValue().toString();
                	
                	
                	l1.add(sheet.getRow(i).getCell(3).getStringCellValue().toString());
                }
                
            }
                	
		}        
            
			
		
		return l1;
	}	
	

}
