import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import org.apache.tools.ant.util.FileUtils;

public class filemove {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	 Path sourcePath = Paths.get("style.css");
	        Path targetPath = Paths.get("C:\\Users\\gaffa\\OneDrive\\Desktop\\Azure\\style.css");
	        
	        try {
	            // Move file from source to target
	            Files.move(sourcePath, targetPath);
	            System.out.println("File moved successfully!");
	        } catch (IOException e) {
	            System.out.println("An error occurred while moving the file.");
	            e.printStackTrace();
	        }
		
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
		
		FileOutputStream out=new FileOutputStream("C:\\Users\\gaffa\\OneDrive\\Desktop\\Azure\\style12.css");
		out.write(s.getBytes());
		
		
		
	}

}
