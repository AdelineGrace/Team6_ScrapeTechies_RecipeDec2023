package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Recipe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelWriter {
	private String fileName;
    private Workbook workbook;
    private Sheet sheet;
    Set<String> uniqueRecipeIds;
    Recipe recipe = new Recipe();
    boolean append;  
    private int batchSize = 10;  // Set your desired batch size
    private int currentBatchSize = 0;
    private List<Recipe> batchRecipes = new ArrayList<>();
    public ExcelWriter(String fileName, boolean append) { 
    	 this.fileName = fileName;
         this.append = append;

         if (!append) {
             this.workbook = new XSSFWorkbook();
             this.sheet = workbook.createSheet("Recipe Data");
             this.uniqueRecipeIds = new HashSet<>();
             writeHeaders();
         } else {
             try {
                 this.workbook = WorkbookFactory.create(new FileInputStream(fileName));
             } catch (IOException e) {
                 e.printStackTrace();
             }
             this.sheet = workbook.getSheet("Recipe Data");
         }
     }

    
    public int getCurrentBatchSize() {
        return currentBatchSize;
    }
    public void writeToExcel(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
        	writeToExcel(recipe);
        }
    }

    public void writeToExcel(Recipe recipe) {
        int rowNum = sheet.getPhysicalNumberOfRows();

        String[] properties = {
                "Recipe ID", "Recipe Name", "Recipe Category(Breakfast/lunch/snack/dinner)",
                "Food Category(Veg/non-veg/vegan/Jain)", "Ingredients", "Preparation Time", "Cooking Time",
                "Preparation method", "Nutrient values", "Targetted morbid conditions", "Recipe URL"
        };

        for (int i = 0; i < properties.length; i++) {
            Row dataRow = sheet.createRow(rowNum + i);

            Cell cellLabel = dataRow.createCell(0);
            cellLabel.setCellValue(properties[i]);

            Cell cellValue = dataRow.createCell(1);
            switch (i) {
                case 0:
                    cellValue.setCellValue(recipe.recipeId);
                    break;
                case 1:
                    cellValue.setCellValue(recipe.recipeName);
                    break;
                case 2:
                    cellValue.setCellValue(recipe.recipeCategory);
                    break;
                case 3:
                    cellValue.setCellValue(recipe.foodCategory);
                    break;
                case 4:
                    cellValue.setCellValue(recipe.ingredients);
                    break;
                case 5:
                    cellValue.setCellValue(recipe.prepTime);
                    break;
                case 6:
                    cellValue.setCellValue(recipe.cookingTime);
                    break;
                case 7:
                    cellValue.setCellValue(recipe.preparationMethod);
                    break;
                case 8:
                    cellValue.setCellValue(recipe.nutritionValue);
                    break;
                case 9:
                    cellValue.setCellValue(recipe.targetCondition);
                    break;
                case 10:
                    cellValue.setCellValue(recipe.recipeURL);
                    break;
            }
        }
    
            currentBatchSize++;
            batchRecipes.add(recipe);

            // Check if the batch size is reached and save the file
            if (currentBatchSize >= batchSize) {
                saveBatch();
            }
        }
    

    public void saveBatch() {
        try (FileOutputStream fileOut = new FileOutputStream(fileName, true)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWorkbook();
        }

        currentBatchSize = 0;
        batchRecipes.clear();
    }

    public void closeWorkbook() {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHeaders() {
        int rowNum = sheet.getLastRowNum() + 1;

        String[] properties = {
                "Recipe ID", "Recipe Name", "Recipe Category(Breakfast/lunch/snack/dinner)",
                "Food Category(Veg/non-veg/vegan/Jain)", "Ingredients", "Preparation Time", "Cooking Time",
                "Preparation method", "Nutrient values", "Targetted morbid conditions", "Recipe URL"
        };

        for (int i = 0; i < properties.length; i++) {
            Row headerRow = sheet.createRow(rowNum + i);

            Cell cellLabel = headerRow.createCell(0);
            cellLabel.setCellValue(properties[i]);
        }
    }

}










