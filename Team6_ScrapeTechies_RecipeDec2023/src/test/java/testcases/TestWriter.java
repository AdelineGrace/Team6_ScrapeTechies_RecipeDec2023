package testcases;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import model.Recipe;
import utilities.ExcelWriter1;

public class TestWriter {
	
    private static Workbook workbook;
    private static Sheet sheet;

	public static void main(String[] args) throws IOException {  
		
		ExcelWriter1 writer = new ExcelWriter1("src/test/resources/Data/Test.xlsx", 
				"Recipe ID,Recipe Name,Recipe Category(Breakfast/lunch/snack/dinner),Food Category(Veg/non-veg/vegan/Jain),Ingredients,Preparation Time,Cooking Time,Preparation method,Nutrient values,Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism),Recipe URL");
		
		Recipe recipe = new Recipe();
		recipe.recipeId = "1";
		recipe.recipeName = "Test";
		recipe.recipeCategory = "Snack";
		recipe.foodCategory = "Vegetarian";
		recipe.ingredients = "Test";
		recipe.preparationMethod = "Test";
		recipe.prepTime = "10 mins";
		recipe.cookingTime = "10 mins";
		recipe.nutritionValue = "test";
		recipe.targetCondition = "PCOS";
		recipe.recipeURL = "test";
		
		writer.WriteRecipeToExcel(recipe);
		writer.WriteRecipeToExcel(recipe);
		
//		try
//		{
//			String filename = "src/test/resources/Data/Test.xlsx"; 
//			HSSFWorkbook workbook = new HSSFWorkbook(); 
//			HSSFSheet sheet = workbook.createSheet("Recipes");
//			
//			HSSFRow rowhead = sheet.createRow((short)0); 
//			List<String> lstHeaders = Arrays.asList("Recipe ID,Recipe Name,Recipe Category(Breakfast/lunch/snack/dinner),Food Category(Veg/non-veg/vegan/Jain),Ingredients,Preparation Time,Cooking Time,Preparation method,Nutrient values,Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism),Recipe URL".split(","));
//			for(int i=0;i<=lstHeaders.size()-1;i++)
//			{
//				rowhead.createCell(i).setCellValue(lstHeaders.get(i));  
//			}
//			
//			FileOutputStream fileOut = new FileOutputStream(filename);  
//			workbook.write(fileOut);  
//			fileOut.close();   
//			workbook.close(); 
//			System.out.println("Excel file has been generated successfully.");  
//			
//		}
//		catch(Exception ex)
//		{
//			
//		}
//		
////		try   
//		{  
//		//declare file name to be create   
//		String filename = "src/test/resources/Data/Test.xlsx";  
//		//creating an instance of HSSFWorkbook class  
//		HSSFWorkbook workbook = new HSSFWorkbook();  
//		//invoking creatSheet() method and passing the name of the sheet to be created   
//		HSSFSheet sheet = workbook.createSheet("January");   
//		//creating the 0th row using the createRow() method  
//		HSSFRow rowhead = sheet.createRow((short)0);  
//		//creating cell by using the createCell() method and setting the values to the cell by using the setCellValue() method  
//		rowhead.createCell(0).setCellValue("S.No.");  
//		rowhead.createCell(1).setCellValue("Customer Name");  
//		rowhead.createCell(2).setCellValue("Account Number");  
//		rowhead.createCell(3).setCellValue("e-mail");  
//		rowhead.createCell(4).setCellValue("Balance");  
//		//creating the 1st row  
//		HSSFRow row = sheet.createRow((short)1);  
//		//inserting data in the first row  
//		row.createCell(0).setCellValue("1");  
//		row.createCell(1).setCellValue("John William");  
//		row.createCell(2).setCellValue("9999999");  
//		row.createCell(3).setCellValue("william.john@gmail.com");  
//		row.createCell(4).setCellValue("700000.00");  
//		//creating the 2nd row  
//		HSSFRow row1 = sheet.createRow((short)2);  
//		//inserting data in the second row  
//		row1.createCell(0).setCellValue("2");  
//		row1.createCell(1).setCellValue("Mathew Parker");  
//		row1.createCell(2).setCellValue("22222222");  
//		row1.createCell(3).setCellValue("parker.mathew@gmail.com");  
//		row1.createCell(4).setCellValue("200000.00");  
//		FileOutputStream fileOut = new FileOutputStream(filename);  
//		workbook.write(fileOut);  
//		//closing the Stream  
//		fileOut.close();  
//		//closing the workbook  
//		workbook.close();  
//		//prints the message on the console  
//		System.out.println("Excel file has been generated successfully.");  
//		}   
//		catch (Exception e)   
//		{  
//		e.printStackTrace();  
//		}  
	}
}
