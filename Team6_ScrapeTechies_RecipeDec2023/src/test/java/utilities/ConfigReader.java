package utilities;


	import java.io.BufferedReader;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.Properties;

import enums.BrowserType;

	public class ConfigReader {

		private static Properties properties;
		private final String configfile = "src/test/resources/Config/config.properties";

		public ConfigReader() 
		{
			BufferedReader reader;
			try 
			{
				reader = new BufferedReader(new FileReader(configfile));
				properties = new Properties();
				try 
				{
					properties.load(reader);
					reader.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
				throw new RuntimeException("config file not found at " + configfile);
			}
		}

		public BrowserType getBrowserType() 
		{
			String browserName = properties.getProperty("browserType");

			if (browserName == null || browserName.equals("chrome"))
				return BrowserType.CHROME;
			
			else if (browserName.equalsIgnoreCase("firefox"))
				return BrowserType.FIREFOX;
			
			else if (browserName.equals("edge"))
				return BrowserType.EDGE;
			
			else
				throw new RuntimeException(
						"This Browser name  in mentioned in config.properties : " + browserName);
		}

		public long getImplicitWait() 
		{
			String implicitlyWait = properties.getProperty("implicitWait");
			if (implicitlyWait != null)
				return Long.parseLong(implicitlyWait);
			else
				throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
		}

		public String getWebUrl() 
		{
			String url = properties.getProperty("weburl");
			if (url != null)
				return url;
			else
				throw new RuntimeException("webpage url is not specified in the config.properties file.");
		}
		
		public String getExcelPath() {
			String path = properties.getProperty("excelPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("excelPath not specified in the Configuration.properties file.");
		}

	}
