package managers;

import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.ExcelWriter;

public class FileManager {

	private static FileManager fileReaderManager = new FileManager();
	private static ConfigReader configReader;
	private static ExcelWriter excelWriter;
	private static ExcelReader excelReader;
	
	private FileManager() {
	}

	 public static FileManager getInstance( ) {
	      return fileReaderManager;
	 }

	 public ConfigReader getConfigReader() {
		 return (configReader == null) ? new ConfigReader() : configReader;
	 }
	 
	 public ExcelWriter getExcelWriter() {
		 return (excelWriter == null) ? new ExcelWriter(null) : excelWriter;
	 }
	 
	 public ExcelReader getExcelReader() {
		 return (excelReader == null) ? new ExcelReader() : excelReader;
	 }
}
