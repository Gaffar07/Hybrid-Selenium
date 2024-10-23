package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import constants.PropertyConfigs;

public class ConfigReader {
	
	private static Properties props = null;
    private static ConfigReader instance = null;
    private static final String DEFAULT_PROPERTIES = "Config.properties";
    private static InputStream input = null;
    //private static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator +"java" + File.separator + "resources" + File.separator;
    private static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator;

    private ConfigReader() throws Exception {
        props = new Properties();
        try {
            if (Files.isReadable(Paths.get(CONFIG_PATH + DEFAULT_PROPERTIES))) {
            	String pathOfAbsolute = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
                String propertiesFilePath = CONFIG_PATH+DEFAULT_PROPERTIES;
                propertiesFilePath = propertiesFilePath.replace("file:/", "").replace("/", "\\");
                Paths.get(new URI(pathOfAbsolute));
                input =  ClassLoader.getSystemResourceAsStream(propertiesFilePath);
               input = new FileInputStream(propertiesFilePath);
               props.load( input );
                //props.load(ConfigReader.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES));
                if (Files.isReadable(Paths.get(CONFIG_PATH + PropertyConfigs.MasterConfigProperties + ".properties"))) {
                	String pathOfAbsolute1 = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
                    String propertiesFilePath1 = CONFIG_PATH + PropertyConfigs.MasterConfigProperties + ".properties";
                    propertiesFilePath1 = propertiesFilePath1.replace("file:/", "").replace("/", "\\");
                   Paths.get(new URI(pathOfAbsolute1));
                    input =  ClassLoader.getSystemResourceAsStream(propertiesFilePath1);
                   input = new FileInputStream(propertiesFilePath1);
                   props.load( input );
                    //props.load(ConfigReader.class.getClassLoader().getResourceAsStream(PropertyConfigs.MasterConfigProperties + ".properties"));
                }
                else {
                    throw new IOException("Properties file config-" + PropertyConfigs.MasterConfigProperties + ".properties does not exist");
                }
            } else {
                throw new IOException("Properties file does not exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static synchronized ConfigReader getInstance() throws Exception {
        if (instance == null) instance = new ConfigReader();
        return instance;
    }

    public String getValue(String key) {
        if (System.getProperty(key) != null) {
            return System.getProperty(key);
        } else if (props.containsKey(key)) {
            return props.getProperty(key);
        }
        return null;
    }
    
    
    public String StoreValueToConfig(String Key,String value,String Comment) throws IOException {
 	   String Path="D:\\Gaffar\\test\\Config.properties";
     FileOutputStream outputstream=new FileOutputStream(Path);
 	   props.setProperty(Key, value);
 	   props.save(outputstream, Comment);
 	   return Path;	   
    }
}
