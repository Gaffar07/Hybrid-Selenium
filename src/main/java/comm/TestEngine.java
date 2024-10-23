package comm;

import java.io.File;
import java.text.SimpleDateFormat;
import ExcelOperations.ExecutionRecord;
import utilities.*;

public class TestEngine {

	public static void main(String[] args) throws Exception {
		
		
		java.util.Date date=new java.util.Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		String folderDate=simpleDateFormat.format(date);
		
		File f=new File("D:\\ExecutionFolder "+folderDate);
		f.mkdir();
		String folderpath=f.getAbsolutePath();
		System.out.println("updated!!!");
		//System.out.println(folderpath);
		
		
		RuntimeObj obj=new RuntimeObj();
		obj.setRuntimefolder(folderpath);
		
		System.out.println(obj.getRuntimefolder());
		
		ExecutionRecord record=new ExecutionRecord();
		record.createSheet();
		//record.UpdateResult();
		System.out.println(obj.getRuntimefolder());
		// TODO Auto-generated method stub
		
		
		frameworkService  service =new frameworkService(folderpath);
		service.prepareScenario();
		service.LaunchExecution();
		//System.out.println(System.getProperty("user.dir"));
		//System.out.println(s);
		//System.out.println("hello UIPath");
		
		
		
		
	}

}
