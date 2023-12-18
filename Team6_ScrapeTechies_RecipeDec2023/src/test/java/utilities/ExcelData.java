package utilities;

import java.util.ArrayList;
import java.util.List;

public class ExcelData {

	public static List<String> DiabetesEliminate = new ArrayList<String>();
	public static List<String> HypothyroidismEliminate = new ArrayList<String>();
	public static List<String> HypertensionEliminate = new ArrayList<String>();
	public static List<String> PCOSEliminate = new ArrayList<String>();
	
	public static List<String> DiabetesToAdd = new ArrayList<String>();
	public static List<String> HypothyroidismToAdd = new ArrayList<String>();
	public static List<String> HypertensionToAdd = new ArrayList<String>();
	public static List<String> PCOSToAdd = new ArrayList<String>();
	
	public static List<String> AllergiesToFilter = new ArrayList<String>();
	
	static ExcelReader excelReader;
	
	public static void LoadEliminationData()
	{
		excelReader = new ExcelReader("src/test/resources/Data/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		DiabetesEliminate = excelReader.readColumnFromExcel(0);
		HypothyroidismEliminate = excelReader.readColumnFromExcel(2);
		HypertensionEliminate = excelReader.readColumnFromExcel(4);
		PCOSEliminate = excelReader.readColumnFromExcel(6);
	}
	
	public static void LoadToAddData()
	{
		excelReader = new ExcelReader("src/test/resources/Data/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		DiabetesToAdd = excelReader.readColumnFromExcel(1);
		HypothyroidismToAdd = excelReader.readColumnFromExcel(3);
		HypertensionToAdd = excelReader.readColumnFromExcel(5);
		PCOSToAdd = excelReader.readColumnFromExcel(7);
	}
	
	public static void LoadAllergiesData()
	{
		excelReader = new ExcelReader("src/test/resources/Data/Allergies.xlsx");
		AllergiesToFilter = excelReader.readColumnFromExcel(1);
	}
}
