//package testcases;
//
//import java.util.List;
//
//import org.openqa.selenium.WebElement;
//
//import utilities.ExcelReader;
//import utilities.ExcelWriter;
//
//public class Eliminate {
//	
//    List<WebElement> recipeElements = driver.findElements(By.xpath("//div[@class='recipe']"));
//
//  //Twinkle Code has to be added here
//    for (WebElement recipeElement : recipeElements) {
//
//    	String recipeId = extractRecipeId(recipeElement);
//        String recipeName = extractRecipeName(recipeElement);
//        String ingredients = extractIngredients(recipeElement);
//        
//    ExcelReader excelReader = new ExcelReader("src/test/resources/Data/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
//
//   //Extracting "To Eliminate" Ingredients from excel 
//    List<String> diabetesEliminate = excelReader.readColumnFromExcel(0);
//    List<String> hypothyroidismEliminate = excelReader.readColumnFromExcel(2);
//    List<String> hypertensionEliminate = excelReader.readColumnFromExcel(4);
//    List<String> PCOSEliminate = excelReader.readColumnFromExcel(6);
//
//	//Comparing Recipe Ingredients with To Eliminate Ingredients
//        boolean EliminateRecipe = containsEliminatedIngredient(ingredients, diabetesEliminate)
//                || containsEliminatedIngredient(ingredients, hypothyroidismEliminate)
//                || containsEliminatedIngredient(ingredients, hypertensionEliminate)
//                || containsEliminatedIngredient(ingredients, PCOSEliminate);
//      
//      
//        if (!EliminateRecipe) {
//            writeRecipeDetailsToSheet(recipeId, recipeName, category, foodCategory, ingredients,
//                    preparationTime, cookingTime, preparationMethod, nutrientValues, morbidConditions, recipeUrl, recipeDataSheet);
//  
//        
//        //Adding Morbid Condition to the Sheet
//            if (!containsEliminatedIngredient(ingredients, diabetesEliminate)) {
//                writeMorbidConditionToSheet(recipeId, recipeName, recipeCategory, foodCategory, ingredients,
//                        preparationTime, cookingTime, preparationMethod, nutrientValues, "Diabetes", recipeUrl, recipeDataSheet);
//            }
//            if (!containsEliminatedIngredient(ingredients, hypothyroidismEliminate)) {
//                writeMorbidConditionToSheet(recipeId, recipeName, recipeCategory, foodCategory, ingredients,
//                        preparationTime, cookingTime, preparationMethod, nutrientValues, "Hypothyroidism", recipeUrl, recipeDataSheet);
//            }
//            if (!containsEliminatedIngredient(ingredients, hypertensionEliminate)) {
//                writeMorbidConditionToSheet(recipeId, recipeName, recipeCategory, foodCategory, ingredients,
//                        preparationTime, cookingTime, preparationMethod, nutrientValues, "Hypertension", recipeUrl, recipeDataSheet);
//            }
//            if (!containsEliminatedIngredient(ingredients, PCOSEliminate)) {
//                writeMorbidConditionToSheet(recipeId, recipeName, recipeCategory, foodCategory, ingredients,
//                        preparationTime, cookingTime, preparationMethod, nutrientValues, "PCOS", recipeUrl, recipeDataSheet);
//            }
//        }
//               
//        
//    }
//        private static boolean containsEliminatedIngredient(WebElement recipeElement, List<String> eliminatedIngredients) {
//            WebElement ingredientsElement = recipeElement.findElement(By.xpath(".//div[@class='ingredients']"));
//            String ingredientsText = ingredientsElement.getText();
//            return eliminatedIngredients.stream().anyMatch(ingredientsText::contains);
//        }
//	
//	
//	
//	}