package managers;

import utilities.ConfigReader;
import utilities.ExcelWriter;

public class FileReaderManager {

	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigReader configReader;
	private static ExcelWriter excelWriter;

	private FileReaderManager() {
	}

	 public static FileReaderManager getInstance( ) {
	      return fileReaderManager;
	 }

	 public ConfigReader getConfigReader() {
		 return (configReader == null) ? new ConfigReader() : configReader;
	 }
	 
	 public ExcelWriter getExcelWriter() {
		 return (excelWriter == null) ? new ExcelWriter(null) : excelWriter;
	 }
}
