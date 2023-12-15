package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelWriter {
	private String fileName;
    private Workbook workbook;
    private Sheet sheet;
    private Set<String> uniqueRecipeIds;
	public ExcelWriter(String fileName) {
        this.fileName = fileName;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Recipe Data");
        this.uniqueRecipeIds = new HashSet<>();
    }

    public void writeHeader(List<String> headers) {
    	 for (int i = 0; i < headers.size(); i++) {
    	        Row row = sheet.createRow(i);
    	        Cell cell = row.createCell(0);
    	        cell.setCellValue(headers.get(i));
    	    }
    }

    public void writeData(List<String> rowData) {
        String recipeId = rowData.get(0);

        if (!uniqueRecipeIds.contains(recipeId)) {
            int colNum = sheet.getRow(0).getPhysicalNumberOfCells(); 
            Row headerRow = sheet.getRow(0);
            Cell cell = headerRow.createCell(colNum);
            cell.setCellValue(recipeId);

            for (int i = 0; i < rowData.size(); i++) {
                Row row = sheet.createRow(i + 1); // Start from row 1 to avoid overwriting headers
                Cell dataCell = row.createCell(colNum);
                dataCell.setCellValue(rowData.get(i));
            }

            uniqueRecipeIds.add(recipeId);
        }
    }
    public void writeMorbidConditionToSheet(String recipeId, String recipeName, String recipeCategory, String foodCategory,
            String ingredients, String preparationTime, String cookingTime,
            String preparationMethod, String nutrientValues, String morbidConditions,
            String recipeUrl, Sheet recipeDataSheet) {
int rowNum = recipeDataSheet.getPhysicalNumberOfRows();

Row dataRow = recipeDataSheet.createRow(rowNum);

Cell cellId = dataRow.createCell(0);
cellId.setCellValue("Recipe ID");  
Cell cellIdValue = dataRow.createCell(1);
cellIdValue.setCellValue(recipeId);

Cell cellName = dataRow.createCell(2);
cellName.setCellValue("Recipe Name");
Cell cellNameValue = dataRow.createCell(3);
cellNameValue.setCellValue(recipeName);

Cell cellCategory = dataRow.createCell(4);
cellCategory.setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
Cell cellCategoryValue = dataRow.createCell(5);
cellCategoryValue.setCellValue(recipeCategory);

Cell cellFoodCategory = dataRow.createCell(6);
cellFoodCategory.setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
Cell cellFoodCategoryValue = dataRow.createCell(7);
cellFoodCategoryValue.setCellValue(foodCategory);

Cell cellIngredients = dataRow.createCell(8);
cellIngredients.setCellValue("Ingredients");
Cell cellIngredientsValue = dataRow.createCell(9);
cellIngredientsValue.setCellValue(ingredients);

Cell cellPreparationTime = dataRow.createCell(10);
cellPreparationTime.setCellValue("Preparation Time");
Cell cellPreparationTimeValue = dataRow.createCell(11);
cellPreparationTimeValue.setCellValue(preparationTime);

Cell cellCookingTime = dataRow.createCell(12);
cellCookingTime.setCellValue("Cooking Time");
Cell cellCookingTimeValue = dataRow.createCell(13);
cellCookingTimeValue.setCellValue(cookingTime);

Cell cellPreparationMethod = dataRow.createCell(14);
cellPreparationMethod.setCellValue("Preparation method");
Cell cellPreparationMethodValue = dataRow.createCell(15);
cellPreparationMethodValue.setCellValue(preparationMethod);

Cell cellNutrientValues = dataRow.createCell(16);
cellNutrientValues.setCellValue("Nutrient values");
Cell cellNutrientValuesValue = dataRow.createCell(17);
cellNutrientValuesValue.setCellValue(nutrientValues);

Cell cellMorbidConditions = dataRow.createCell(18);
cellMorbidConditions.setCellValue("Targetted morbid conditions");
Cell cellMorbidConditionsValue = dataRow.createCell(19);
cellMorbidConditionsValue.setCellValue(morbidConditions);

Cell cellRecipeUrl = dataRow.createCell(20);
cellRecipeUrl.setCellValue("Recipe URL");
Cell cellRecipeUrlValue = dataRow.createCell(21);
cellRecipeUrlValue.setCellValue(recipeUrl);
}

   
    public void saveFile() throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}