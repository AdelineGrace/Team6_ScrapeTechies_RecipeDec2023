package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import managers.FileManager;


public class ExcelReader {
	
	public static HashMap<String, Object> excelData;
	
	public void InitializeExcel()
	{
		excelData = LoadExcelData();
	}
	
	public HashMap<String, Object> LoadExcelData()
	{
		HashMap<String, Object> excelData = new HashMap<>();
		try
		{
			FileInputStream fs = new FileInputStream(FileManager.getInstance().getConfigReader().getExcelPath());
			try (XSSFWorkbook wb = new XSSFWorkbook(fs)) {
				int noOfSheets = wb.getNumberOfSheets();
				for(int i=0 ; i<noOfSheets ; i++)
				{
					HashMap<String, HashMap<String, String>> sheetData = null;
					XSSFSheet sheet = wb.getSheetAt(i);
					sheetData = ReadSheet(sheet);
					excelData.put(wb.getSheetName(i), sheetData);
				}
				wb.close();
			}
		}
		catch(IOException ex)
		{
			Log.error(ex.getMessage());
		}
		
		return excelData;
	}
	
	public HashMap<String, HashMap<String, String>> ReadExcelFile(String sheetName)
	{
		HashMap<String, HashMap<String, String>> sheetData = null;
		try
		{
			FileInputStream fs = new FileInputStream(FileManager.getInstance().getConfigReader().getExcelPath());
			try (XSSFWorkbook wb = new XSSFWorkbook(fs)) {
				XSSFSheet sheet = wb.getSheet(sheetName);
				sheetData = ReadSheet(sheet);
				wb.close();
			}
		}
		catch(IOException ex)
		{
			Log.error(ex.getMessage());
		}
		
		return sheetData;
	}
	
	private HashMap<String, HashMap<String, String>> ReadSheet(Sheet sheet) 
	{
		Row row;
		Cell cell;

		int rowCount = sheet.getLastRowNum();
		
		HashMap<String, HashMap<String, String>> excelRows = new HashMap<>();
		for(int currentRow = 1; currentRow <= rowCount; currentRow ++)
		{
			row = sheet.getRow(currentRow);
			String rowHeader = sheet.getRow(currentRow).getCell(0).getStringCellValue();
			
			int noOfCols = row.getLastCellNum();
			
			HashMap<String, String> colData = new HashMap<>();
			for(int currentCol = 0; currentCol < noOfCols ; currentCol ++)
			{
				cell = row.getCell(currentCol);
				String columnHeader = sheet.getRow(sheet.getFirstRowNum()).getCell(currentCol).getStringCellValue();
				colData.put(columnHeader, cell.getStringCellValue());
			}
			excelRows.put(rowHeader, colData);
		}

		return excelRows;
	}

}