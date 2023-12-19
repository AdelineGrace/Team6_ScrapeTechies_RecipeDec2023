package utilities;


	import java.io.BufferedReader;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.Properties;

import enums.BrowserType;

	public class ConfigReader {

		private static Properties properties;
		private static final String propertyFilePath = "src/test/resources/Config/config.properties";

		public static void loadProperty() 
		{
			BufferedReader reader;
			try 
			{
				reader = new BufferedReader(new FileReader(propertyFilePath));
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
				throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
			}
		}
		
		public static String getProperty( String key) {
			return properties.getProperty(key);
		}

		public static BrowserType getBrowserType() 
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

		public static long getImplicitWait() 
		{
			String implicitlyWait = properties.getProperty("implicitWait");
			if (implicitlyWait != null)
				return Long.parseLong(implicitlyWait);
			else
				throw new RuntimeException("implicitlyWait not specified in the config.properties file.");
		}
		
		public static long getPageLoadWait() 
		{
			String pageLoadWait = properties.getProperty("pageLoadWait");
			if (pageLoadWait != null)
				return Long.parseLong(pageLoadWait);
			else
				throw new RuntimeException("pageLoadWait not specified in the config.properties file.");
		}

		public static String getWebUrl() 
		{
			String url = properties.getProperty("weburl");
			if (url != null)
				return url;
			else
				throw new RuntimeException("webpage url is not specified in the config.properties file.");
		}
		
		public static String getScreenshotPath() 
		{
			String path = properties.getProperty("screenshotPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("screenshotPath not specified in the config.properties file.");
		}
		
		public static String getIngredientsInputExcelPath() {
			String path = properties.getProperty("ingredientsInputExcelPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("ingredientsInputExcelPath not specified in the config.properties file.");
		}
		
		public static String getAllergiesInputExcelPath() {
			String path = properties.getProperty("allergiesInputExcelPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("allergiesInputExcelPath not specified in the config.properties file.");
		}
		
		public static String getAllRecipesOutputExcelPath() {
			String path = properties.getProperty("allRecipesOutputExcelPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("allRecipesOutputExcelPath not specified in the config.properties file.");
		}
		
		public static String getEliminationOutputExcelPath() {
			String path = properties.getProperty("eliminationOutputExcelPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("eliminationOutputExcelPath not specified in the config.properties file.");
		}
		
		public static String getToAddOutputExcelPath() {
			String path = properties.getProperty("toAddOutputExcelPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("toAddOutputExcelPath not specified in the config.properties file.");
		}
		
		public static String getAllergiesOutputExcelPath() {
			String path = properties.getProperty("allergiesExcelPath");
			if (path != null)
				return path;
			else
				throw new RuntimeException("allergiesExcelPath not specified in the config.properties file.");
		}
		
		public static String getOutputRowHeaders() {
			String outputRowHeaders = properties.getProperty("outputRowHeaders");
			if (outputRowHeaders != null)
				return outputRowHeaders;
			else
				throw new RuntimeException("outputRowHeaders not specified in the config.properties file.");
		}

	}
