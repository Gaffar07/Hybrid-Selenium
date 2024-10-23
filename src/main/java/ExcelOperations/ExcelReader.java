package ExcelOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.xml.XmlSuite;
import utilities.ConfigReader;


public class ExcelReader {
	
	
	private static final int STRING = 0;
	private static final int FORMULA = 0;
	private static final int BOOLEAN = 0;
	private static final int NUMERIC = 0;
	private static final int BLANK = 0;
	List<XmlSuite> suites;
	public ExcelReader()
	{
		
	}
	
	
	public static List getScriptStepFromScriptName( String scriptName) throws Exception {
		List<String> l1=new ArrayList();
		String CONFIG_PATH = ConfigReader.getInstance().getValue("TestDataFolder")+"\\0001_MasterTestSuite.xlsx";
		File f=new File(System.getProperty("user.dir")+"\\Data\\0001_MasterTestSuite.xlsx");
		
		FileInputStream fileInputStream=new FileInputStream(f);
		//String excelFilePath = System.getProperty("user.dir")+"\\Data\\0003_ScenarioSuite.xlsx";
		try (FileInputStream excelFile = new FileInputStream(f)) {
        	XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("MasterTestSuite");
            
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
	
	
	
	public static synchronized Properties getScenarioData( String scriptName) throws Exception {
		List<String> l1=new ArrayList();
		//String CONFIG_PATH = ConfigReader.getInstance().getValue("TestDataFolder")+"\\0001_MasterTestSuite.xlsx";
		File f=new File("D:\\TCSTestData\\0001_MasterTestSuite.xlsx");
		
		String excelFilePath = System.getProperty("user.dir")+"\\Data\\0002_TestDataSuite.xlsx";
		
		Properties prop = new Properties();
		try{
			XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(excelFilePath));
			
			//XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
			XSSFSheet sheet=workbook.getSheet("Data");
			//DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale

			Row titleRow = sheet.getRow(0);
			

			for (int i = 0; i<sheet.getPhysicalNumberOfRows(); i++) {
				//Row dataRow = sheet.getRow(i);
				
				if(sheet.getRow(i).getCell(1).getStringCellValue().toString().equals(scriptName))
				{
					for(int j=0;j<sheet.getRow(i).getPhysicalNumberOfCells();j++)
					{
						
							prop.put(sheet.getRow(0).getCell(j).getStringCellValue().toString(),sheet.getRow(i).getCell(j).getStringCellValue().toString());
							//System.out.println(prop);
						
					}
					
				}
				
			}
				
		}catch (Exception e){
			//Reporter.log(e.toString());
			return null;
		}
		

		return prop;
	}
	
	
	
	
	

}
