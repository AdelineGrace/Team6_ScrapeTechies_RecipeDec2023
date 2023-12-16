package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.Recipe;
import utilities.ExcelReader;
import utilities.ExcelWriter;
import utilities.Log;

public class RecipePage {

	WebDriver driver;
	static ExcelWriter excelWriter;
	static ExcelReader excelReader;
	  List<String> diabetesEliminate = new ArrayList<String>();
	    List<String> hypothyroidismEliminate = new ArrayList<String>();
	    List<String> hypertensionEliminate = new ArrayList<String>();
	    List<String> PCOSEliminate = new ArrayList<String>();
	@FindBy(xpath = "//time[@itemprop='prepTime']")
	WebElement recipePrepTime;
	@FindBy(xpath = "//time[@itemprop='cookTime']")
	WebElement recipeCookingTime;
	@FindBy(id = "rcpnuts")
	WebElement nutrientsLst;
	@FindBy(xpath = "//span[@itemprop='recipeIngredient']/a")
	List<WebElement> lstIngredients;
	@FindBy(id = "recipe_tags")
	WebElement recipeTags;

	public RecipePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Recipe GetRecipeDetails(Recipe recipe) {
		try {
			// Get recipe URL, prep time, cook time
			recipe.recipeURL = driver.getCurrentUrl();
			Log.info(recipe.recipeURL);

			recipe.prepTime = GetRecipePrepTime();
			Log.info(recipe.prepTime);

			recipe.cookingTime = GetRecipeCookingTime();
			Log.info(recipe.cookingTime);

			// Get recipe nutrient values
			recipe.nutritionValue = GetNutrientValues();
			Log.info(recipe.nutritionValue);
			// Get recipe tags to calculate recipe category and food category
						String recipeTags = GetRecipeTags();

						// Calculate recipe category based on recipe name and tags
						recipe.recipeCategory = GetRecipeCategory(recipe.recipeName, recipeTags);
						Log.info(recipe.recipeCategory);
			// Get recipe ingredients
			recipe.ingredients = GetRecipeIngredients();
			Log.info(recipe.ingredients);
		    //LoadEliminationLists();
			excelReader = new ExcelReader(
					"C:/Users/shaun/git/Team6_ScrapeTechies_RecipeDecem2023/Team6_ScrapeTechies_RecipeDec2023/src/test/resources/Data/IngredientsAndComorbidities-ScrapperHackathon.xlsx");

			diabetesEliminate = excelReader.readColumnsFromExcel(new int[]{0}, 2).get(0);
			hypothyroidismEliminate = excelReader.readColumnsFromExcel(new int[]{2}, 2).get(0);
			hypertensionEliminate = excelReader.readColumnsFromExcel(new int[]{4}, 2).get(0);
			PCOSEliminate = excelReader.readColumnsFromExcel(new int[]{6}, 2).get(0);
			
		    if (!containsEliminatedIngredient(recipe.ingredients, diabetesEliminate)) {
		        recipe.targetCondition = "Diabetes";
		    } else if (!containsEliminatedIngredient(recipe.ingredients, hypothyroidismEliminate)) {
		        recipe.targetCondition = "Hypothyroidism";
		    } else if (!containsEliminatedIngredient(recipe.ingredients, hypertensionEliminate)) {
		        recipe.targetCondition = "Hypertension";
		    } else if (!containsEliminatedIngredient(recipe.ingredients, PCOSEliminate)) {
		        recipe.targetCondition = "PCOS";
		    }

		    Log.info(recipe.targetCondition);

			if (recipe.targetCondition != null &&!recipe.targetCondition.isEmpty())
			{
				 BatchWriteRecipesToExcel(recipe);
			}
	
		
	}
		catch (Exception ex) 
		{
			Log.info(ex.getMessage());
			
			driver.navigate().back();
			
		}

		return recipe;
	}
//	public void LoadEliminationLists() {
//		excelReader = new ExcelReader(
//				"C:/Users/shaun/git/Team6_ScrapeTechies_RecipeDecem2023/Team6_ScrapeTechies_RecipeDec2023/src/test/resources/Data/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
////		diabetesEliminate = excelReader.readColumnsFromExcel(0);
////		hypothyroidismEliminate = excelReader.readColumnsFromExcel(2);
////		hypertensionEliminate = excelReader.readColumnFromExcel(4);
////		PCOSEliminate = excelReader.readColumnFromExcel(6);
////	}
//		diabetesEliminate = excelReader.readColumnsFromExcel(new int[]{0}, 2).get(0);
//		hypothyroidismEliminate = excelReader.readColumnsFromExcel(new int[]{2}, 2).get(0);
//		hypertensionEliminate = excelReader.readColumnsFromExcel(new int[]{4}, 2).get(0);
//		PCOSEliminate = excelReader.readColumnsFromExcel(new int[]{6}, 2).get(0);
//	}
	
	private boolean containsEliminatedIngredient(String recipeIngredients, List<String> eliminatedIngredients) {
		return eliminatedIngredients.stream().anyMatch(recipeIngredients::contains);
	}

public String GetRecipeIngredients() {
		String strIngredients = "";
		for (WebElement ingredient : lstIngredients) {
			if (strIngredients.isEmpty())
				strIngredients = ingredient.getText();
			else
				strIngredients = strIngredients + ", " + ingredient.getText();
		}
		return strIngredients;
	}


private void BatchWriteRecipesToExcel(Recipe recipe) {
    int batchSize = 10;

    //if (excelWriter == null) {
        excelWriter = new ExcelWriter("C:/Users/shaun/git/Team6_ScrapeTechies_RecipeDecem2023/Team6_ScrapeTechies_RecipeDec2023/src/test/resources/Data/Recipe-filters-ScrapperHackathon.xlsx", false);
   // }

    excelWriter.writeToExcel(recipe);

//    if (excelWriter.getCurrentBatchSize() >= batchSize) {
//        excelWriter.saveBatch();
    }


	 
	public String GetRecipePrepTime() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//time[@itemprop='prepTime']")));

		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipePrepTime);
		return recipePrepTime.getText();
	}

	public String GetRecipeCookingTime() {
		return recipeCookingTime.getText();
	}

	public String GetNutrientValues() {
		try {
			return driver.findElement(By.xpath("//span[@itemprop='calories']")).getText();
		} catch (Exception ex) {
			return "NA";
		}
	}

	
	public String GetRecipeTags() {
		return recipeTags.getText();
	}

	public String GetRecipeCategory(String recipeName, String recipeTags) {
		String recipeCategory = "";

		if (recipeName.contains("Vegan") || recipeTags.contains("Vegan"))
			recipeCategory = "Vegan";
		else if (recipeName.contains("Jain") || recipeTags.contains("Jain"))
			recipeCategory = "Jain";
		else if (recipeName.contains("Egg") || recipeTags.contains("Egg"))
			recipeCategory = "Eggitarian";
		else if (recipeName.contains("NonVeg") || recipeTags.contains("NonVeg"))
			recipeCategory = "Non-veg";
		else
			recipeCategory = "Vegetarian";

		return recipeCategory;
	}
}
