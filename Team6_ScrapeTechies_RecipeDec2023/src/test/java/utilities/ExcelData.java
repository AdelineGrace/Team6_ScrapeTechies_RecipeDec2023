package utilities;

import java.util.ArrayList;
import java.util.List;

public class ExcelData {

	public static List<String> DiabetesEliminate = new ArrayList<String>();
	public static List<String> HypothyroidismEliminate = new ArrayList<String>();
	public static List<String> HypertensionEliminate = new ArrayList<String>();
	public static List<String> PCOSEliminate = new ArrayList<String>();
	
	static ExcelReader excelReader;
	
	public static void LoadEliminationData()
	{
		excelReader = new ExcelReader("src/test/resources/Data/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		DiabetesEliminate = excelReader.readColumnFromExcel(0);
		HypothyroidismEliminate = excelReader.readColumnFromExcel(2);
		HypertensionEliminate = excelReader.readColumnFromExcel(4);
		PCOSEliminate = excelReader.readColumnFromExcel(6);
	}
}
