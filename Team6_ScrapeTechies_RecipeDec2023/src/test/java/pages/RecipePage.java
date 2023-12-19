package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import model.Recipe;
import utilities.ExcelData;
import utilities.ExcelReader;
import utilities.ExcelWriter1;
import utilities.Log;
import org.apache.commons.lang3.StringUtils;

public class RecipePage {

	WebDriver driver;
	ExcelWriter1 excelWriter;
	ExcelWriter1 excelWriterEliminate;
	ExcelWriter1 excelWriterToAdd;
	ExcelWriter1 excelWriterAllergies;
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

			// calculate target condition based on ingredients and input list of
			// eliminations
			recipe.targetCondition = GetTargetCondition(recipe.ingredients);
			Log.info(recipe.targetCondition);

			// calculate toadd status based on ingredients and input list of
			// eliminations and to adds
			recipe.toAdd = GetToAddStatus(recipe.ingredients, recipe.targetCondition);
			Log.info("To add - " + recipe.toAdd);

			// calculate no allergies status based on ingredients
			recipe.NoAllergies = GetNoAllergiesStatus(recipe.ingredients, recipe.targetCondition);
			Log.info("No Allergy - " + recipe.NoAllergies);

			WriteRecipeToExcels(recipe);

		} catch (Exception ex) {
			Log.info(ex.getMessage());

		} finally {
			Log.info("In finally");

			driver.navigate().back();

			if (!driver.getCurrentUrl().contains("AtoZ"))
				driver.navigate().back();
		}

		return recipe;
	}

	private boolean containsIngredient(String recipeIngredients, List<String> ingredientsToCheck) {
		List<String> recipeIng = Arrays.asList(recipeIngredients.split(", "));
		return recipeIng.stream().anyMatch(recipeIngredient -> ingredientsToCheck.stream()
				.anyMatch(ingToCheck -> StringUtils.containsIgnoreCase(ingToCheck, recipeIngredient)));
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

//	private void BatchWriteRecipesToExcel(Recipe recipe) {
//		int batchSize = 10;
//
//		// if (excelWriter == null) {
//		excelWriter = new ExcelWriter("src/test/resources/Data/Recipe-filters-ScrapperHackathon.xlsx", false);
//		// }
//
//		excelWriter.writeToExcel(recipe);
//
//		if (excelWriter.getCurrentBatchSize() >= batchSize) {
//			excelWriter.saveBatch();
//		}
//	}

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

	public String GetTargetCondition(String ingredients) {
		List<String> targetConditions = new ArrayList<>();
		String targetCondition = "";

		if (!containsIngredient(ingredients, ExcelData.DiabetesEliminate))
			targetConditions.add("Diabetes");
		if (!containsIngredient(ingredients, ExcelData.HypothyroidismEliminate))
			targetConditions.add("Hypothyroidism");
		if (!containsIngredient(ingredients, ExcelData.HypertensionEliminate))
			targetConditions.add("Hypertension");
		if (!containsIngredient(ingredients, ExcelData.PCOSEliminate))
			targetConditions.add("PCOS");

		for (int i = 0; i < targetConditions.size(); i++) {
			if (i == 0)
				targetCondition = targetConditions.get(i);
			else
				targetCondition = targetCondition + ", " + targetConditions.get(i);
		}

		return targetCondition;
	}

	public Boolean GetToAddStatus(String ingredients, String targetCondition) {
		Boolean toAddStatus = false;

		if ((targetCondition.contains("Diabetes") && containsIngredient(ingredients, ExcelData.DiabetesToAdd))
				|| (targetCondition.contains("Hypothyroidism")
						&& containsIngredient(ingredients, ExcelData.HypothyroidismToAdd))
				|| (targetCondition.contains("Hypertension")
						&& containsIngredient(ingredients, ExcelData.HypertensionToAdd))
				|| (targetCondition.contains("PCOS") && containsIngredient(ingredients, ExcelData.PCOSToAdd)))
			toAddStatus = true;

		return toAddStatus;
	}

	public Boolean GetNoAllergiesStatus(String ingredients, String targetCondition) {
		Boolean NoAllergiesStatus = false;

		if (!containsIngredient(ingredients, ExcelData.AllergiesToFilter) && !targetCondition.isEmpty()
				&& !targetCondition.isBlank())
			NoAllergiesStatus = true;

		return NoAllergiesStatus;
	}

	public void WriteRecipeToExcels(Recipe recipe) {
		// Write all recipes to excel
		if (excelWriter == null)
			excelWriter = new ExcelWriter1("src/test/resources/Output/AllRecipes.xlsx",
					"Recipe ID,Recipe Name,Recipe Category(Breakfast/lunch/snack/dinner),Food Category(Veg/non-veg/vegan/Jain),Ingredients,Preparation Time,Cooking Time,Preparation method,Nutrient values,Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism),Recipe URL");

		excelWriter.WriteRecipeToExcel(recipe);
		Log.info("Recipe added to excel");

		// Write Elimination excel
		if (excelWriterEliminate == null)
			excelWriterEliminate = new ExcelWriter1("src/test/resources/Output/Recipe-filters-Elimination.xlsx",
					"Recipe ID,Recipe Name,Recipe Category(Breakfast/lunch/snack/dinner),Food Category(Veg/non-veg/vegan/Jain),Ingredients,Preparation Time,Cooking Time,Preparation method,Nutrient values,Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism),Recipe URL");
		if (recipe.targetCondition != null && !recipe.targetCondition.isEmpty()) {
			excelWriterEliminate.WriteRecipeToExcel(recipe);
			Log.info("Recipe added to excel");
		}

		// Write To Add excel
		if (excelWriterToAdd == null)
			excelWriterToAdd = new ExcelWriter1("src/test/resources/Output/Recipe-filters-ToAdd.xlsx",
					"Recipe ID,Recipe Name,Recipe Category(Breakfast/lunch/snack/dinner),Food Category(Veg/non-veg/vegan/Jain),Ingredients,Preparation Time,Cooking Time,Preparation method,Nutrient values,Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism),Recipe URL");
		if (recipe.toAdd) {
			excelWriterToAdd.WriteRecipeToExcel(recipe);
			Log.info("Recipe added to to add excel");
		}

		// Write To Add excel
		if (excelWriterAllergies == null)
			excelWriterAllergies = new ExcelWriter1("src/test/resources/Output/Recipe-filters-Allergies.xlsx",
					"Recipe ID,Recipe Name,Recipe Category(Breakfast/lunch/snack/dinner),Food Category(Veg/non-veg/vegan/Jain),Ingredients,Preparation Time,Cooking Time,Preparation method,Nutrient values,Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism),Recipe URL");
		if (recipe.NoAllergies) {
			excelWriterAllergies.WriteRecipeToExcel(recipe);
			Log.info("Recipe added to to allergies excel");
		}
	}
}