package ExcelOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import de.redsix.pdfcompare.FileUtils;
import utilities.RuntimeObj;

public class ExecutionRecord {

	
	
//		java.util.Date date=new java.util.Date();
//		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
//		String folderDate=simpleDateFormat.format(date);
//		
//		File f=new File("D:\\ExecutionFolder "+folderDate);
//		f.mkdir();
		
		
	FileOutputStream fileOut;
	XSSFWorkbook workbook;
	String filenme;
	 XSSFSheet sheet;
	 RuntimeObj runtime;
		public void createSheet() throws IOException {
		   
			
			try {
	            String filename = "D:/NewExcelFile.xls" ;
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

	            HSSFRow rowhead = sheet.createRow((short)0);
	            rowhead.createCell(0).setCellValue("SrNo.");
	            rowhead.createCell(1).setCellValue("ScenarioId");
	            rowhead.createCell(2).setCellValue("Status");
	     
	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            runtime=new RuntimeObj();
	            runtime.setRuntimeFile(new File(filename));
	            this.filenme=filename;
	            fileOut.close();
	            //workbook.close();
	            System.out.println("Your excel file has been generated!");
	            
	           

	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }		

}
		
		public void UpdateResult(String status,String scenarioId)
		{
			
			try {
				
	            String filename = "D:/NewExcelFile.xls" ;
				//File f=runtime.getRuntimeFile();
	            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filename)));
	            HSSFSheet sheet = workbook.getSheet("FirstSheet");
	            //HSSFSheet sheet = workbook.createSheet("FirstSheet"); 
	            
	            int rowcounter=sheet.getLastRowNum()+1;
	            HSSFRow row = sheet.createRow((short)rowcounter);
	            row.createCell(0).setCellValue(rowcounter);
	            row.createCell(1).setCellValue(scenarioId);
	            row.createCell(2).setCellValue(status);
	            //row.createCell(3).setCellValue("sankumarsingh@gmail.com");

	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            //workbook.close();
	            System.out.println("Your excel file has been generated!");

	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }		

			
		}
		
}
