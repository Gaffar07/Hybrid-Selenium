package utilities;

import java.io.File;

public class RuntimeObj {
	
	
	private static String runtimefolder;
	 private static File runtimeFile;
	 private static String curentScenario;
	 public static   String thisexecutionapath;
	
	
	public static  String getExecutionapath() {
		return thisexecutionapath;
	}


	public static   void setExecutionapath(String executionapath) {
		thisexecutionapath=executionapath;
	}


	public RuntimeObj()
	{
		
		
	}
	
	
	public String getCurentScenario() {
		return curentScenario;
	}




	public  void setCurentScenario(String curentScenario) {
		this.curentScenario = curentScenario;
	}




	public File getRuntimeFile() {
		return runtimeFile;
	}




	public void setRuntimeFile(File runtimeFile) {
		this.runtimeFile = runtimeFile;
	}




	public String getRuntimefolder() {
		return runtimefolder;
	}




	public void setRuntimefolder(String runtimefolder) {
		this.runtimefolder = runtimefolder;
	}




	

}
