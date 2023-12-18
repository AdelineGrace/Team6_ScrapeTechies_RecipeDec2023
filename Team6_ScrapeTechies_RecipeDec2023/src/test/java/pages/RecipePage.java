package pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import model.Recipe;
import utilities.ExcelData;
import utilities.ExcelReader;
import utilities.ExcelWriter;
import utilities.Log;

public class RecipePage {

	WebDriver driver;
	ExcelWriter excelWriter;
	ExcelReader excelReader;

	@FindBy(id = "rcpnuts")
	WebElement nutrientsLst;
	@FindBy(xpath = "//span[@itemprop='recipeIngredient']/a")
	List<WebElement> lstIngredients;
	@FindBy(id = "recipe_small_steps")
	WebElement recipeSteps;
	
	WebElement recipeTypeSection;

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

			// Calculate food category based on recipe name and tags
			recipe.foodCategory = GetFoodCategory(recipe.recipeName, recipeTags);
			Log.info(recipe.foodCategory);

			// Get recipe ingredients
			recipe.ingredients = GetRecipeIngredients();
			Log.info(recipe.ingredients);

			// Get Recipe steps
			recipe.preparationMethod = GetRecipeSteps();
			Log.info(recipe.preparationMethod);

			// calculate target condition based on ingredients and input list of eliminations
			recipe.targetCondition = GetTargetCondition(recipe.ingredients);
			Log.info(recipe.targetCondition);

		} catch (Exception ex) {
			Log.info(ex.getMessage());

		} finally {
			Log.info("In finally");
			// if (recipe.targetCondition != null && !recipe.targetCondition.isEmpty()) {
			if (excelWriter == null)
				excelWriter = new ExcelWriter("src/test/resources/Data/Recipe-filters-ScrapperHackathon.xlsx", false);
			BatchWriteRecipesToExcel(recipe);
			Log.info("Recipe added to excel");
			// }

			driver.navigate().back();
			//driver.navigate().back();
		}

		return recipe;
	}

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

	public String GetRecipeSteps() {
		return recipeSteps.getText();
	}

	private void BatchWriteRecipesToExcel(Recipe recipe) {
		int batchSize = 10;

		// if (excelWriter == null) {
		excelWriter = new ExcelWriter("src/test/resources/Data/Recipe-filters-ScrapperHackathon.xlsx", false);
		// }

		excelWriter.writeToExcel(recipe);

		if (excelWriter.getCurrentBatchSize() >= batchSize) {
			excelWriter.saveBatch();
		}
	}

	public String GetRecipePrepTime() {
		recipeTypeSection = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRecipeScale']/section"));
		
		return recipeTypeSection.findElement(By.xpath(".//time[@itemprop='prepTime']")).getText();
	}

	public String GetRecipeCookingTime() {
		return recipeTypeSection.findElement(By.xpath(".//time[@itemprop='cookTime']")).getText();
	}

	public String GetNutrientValues() {
		try {
			return nutrientsLst.getText();
		} catch (Exception ex) {
			return "NA";
		}
	}

	public String GetRecipeTags() {
		return recipeTypeSection.findElement(By.id("recipe_tags")).getText();
	}

	public String GetRecipeCategory(String recipeName, String recipeTags) {
		String recipeCategory = "";

		if (recipeName.contains("Vegan") || recipeTags.contains("Vegan"))
			recipeCategory = "Vegan";
		else if (recipeName.contains("Jain") || recipeTags.contains("Jain"))
			recipeCategory = "Jain";
		else if (recipeName.contains("Egg ") || recipeTags.contains("Egg "))
			recipeCategory = "Eggitarian";
		else if (recipeName.contains("NonVeg") || recipeTags.contains("NonVeg"))
			recipeCategory = "Non-veg";
		else
			recipeCategory = "Vegetarian";

		return recipeCategory;
	}

	public String GetFoodCategory(String recipeName, String recipeTags) {
		String foodCategory = "";

		if (recipeName.contains("Breakfast") || recipeTags.contains("Breakfast"))
			foodCategory = "Breakfast";
		else if (recipeName.contains("Lunch") || recipeTags.contains("Lunch"))
			foodCategory = "Lunch";
		else if (recipeName.contains("Dinner") || recipeTags.contains("Dinner"))
			foodCategory = "Dinner";
		else
			foodCategory = "Snacks";

		return foodCategory;
	}
	
	public String GetTargetCondition(String ingredients)
	{
		String targetCondition = "";
		
		if (!containsEliminatedIngredient(ingredients, ExcelData.DiabetesEliminate))
			targetCondition = "Diabetes";
		if (!containsEliminatedIngredient(ingredients, ExcelData.HypothyroidismEliminate))
			targetCondition = "Hypothyroidism";
		if (!containsEliminatedIngredient(ingredients, ExcelData.HypertensionEliminate))
			targetCondition = "Hypertension";
		if (!containsEliminatedIngredient(ingredients, ExcelData.PCOSEliminate))
			targetCondition = "PCOS";
		
		return targetCondition;
	}
}