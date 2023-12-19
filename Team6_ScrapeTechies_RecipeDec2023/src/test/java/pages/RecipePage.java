package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import enums.FoodCategory;
import enums.RecipeCategory;
import enums.TargettedMorbidConditions;
import model.Recipe;
import utilities.ConfigReader;
import utilities.ExcelData;
import utilities.ExcelReader;
import utilities.ExcelWriter;
import utilities.Log;
import utilities.Screenshots;

import org.apache.commons.lang3.StringUtils;

public class RecipePage {

	WebDriver driver;
	
	ExcelWriter excelWriter;
	ExcelWriter excelWriterEliminate;
	ExcelWriter excelWriterToAdd;
	ExcelWriter excelWriterAllergies;
	
	ExcelReader excelReader;
	
	WebElement recipeTypeSection;

	public RecipePage(WebDriver driver) 
	{
		this.driver = driver;
	}

	///
	// This method fetches recipe details and writes to excel based on filters
	///
	public Recipe GetRecipeDetailsAndWriteToExcel(Recipe recipe) 
	{
		try 
		{
			// Get recipe URL
			recipe.recipeURL = driver.getCurrentUrl();
			Log.info(recipe.recipeURL);

			// Get Recipe Prep time
			recipe.prepTime = GetRecipePrepTime();
			Log.info(recipe.prepTime);

			// Get recipe cook time
			recipe.cookingTime = GetRecipeCookingTime();
			Log.info(recipe.cookingTime);

			// Get recipe nutrient values
			recipe.nutritionValue = GetNutrientValues();
			Log.info(recipe.nutritionValue);

			// Get recipe tags to calculate recipe category and food category
			String recipeTags = GetRecipeTags();

			// Calculate recipe category based on recipe name and tags
			recipe.recipeCategory = GetRecipeCategory(recipe.recipeName, recipeTags);
			Log.info(recipe.recipeCategory.toString());

			// Calculate food category based on recipe name and tags
			recipe.foodCategory = GetFoodCategory(recipe.recipeName, recipeTags);
			Log.info(recipe.foodCategory.toString());

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

			// Write recipes to excel files based on filters
			WriteRecipeToExcels(recipe);

		} 
		catch (Exception ex) 
		{
			Log.info(ex.getMessage());
			Screenshots.CaptureScreenshot(driver);
		} 
		finally 
		{
			Log.info("In finally");

			driver.navigate().back();

			// If there is an ad
			if (!driver.getCurrentUrl().contains("AtoZ"))
				driver.navigate().back();
		}

		return recipe;
	}

	///
	// This method gets the ingredient list of the recipe
	///
	public String GetRecipeIngredients() 
	{
		List<WebElement> lstIngredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
		
		String strIngredients = "";
		for (WebElement ingredient : lstIngredients) 
		{
			if (strIngredients.isEmpty())
				strIngredients = ingredient.getText();
			else
				strIngredients = strIngredients + ", " + ingredient.getText();
		}
		
		return strIngredients;
	}

	///
	// This recipe gets the recipe steps
	///
	public String GetRecipeSteps() 
	{
		WebElement recipeSteps = driver.findElement(By.id("recipe_small_steps"));
		return recipeSteps.getText();
	}

	///
	// This method gets the recipe preparation time
	///
	public String GetRecipePrepTime() 
	{
		recipeTypeSection = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRecipeScale']/section"));
		return recipeTypeSection.findElement(By.xpath(".//time[@itemprop='prepTime']")).getText();
	}

	///
	// This method gets the recipe cooking time
	///
	public String GetRecipeCookingTime() 
	{
		return recipeTypeSection.findElement(By.xpath(".//time[@itemprop='cookTime']")).getText();
	}

	///
	// This recipe gets the recipe nutrient list
	///
	public String GetNutrientValues() 
	{
		try 
		{
			WebElement nutrientsLst = driver.findElement(By.id("rcpnuts"));
			return nutrientsLst.getText();
		} 
		catch (Exception ex) 
		{
			return "NA";
		}
	}

	///
	// This method gets the recipe tags to calculate Recipe category and food category
	///
	public String GetRecipeTags() 
	{
		return recipeTypeSection.findElement(By.id("recipe_tags")).getText();
	}

	///
	// This method calculates the recipe category based on name and tags
	///
	public RecipeCategory GetRecipeCategory(String recipeName, String recipeTags) 
	{
		if (recipeName.contains("Vegan") || recipeTags.contains("Vegan"))
			return RecipeCategory.Vegan;
		else if (recipeName.contains("Jain") || recipeTags.contains("Jain"))
			return RecipeCategory.Jain;
		else if (recipeName.contains("Egg ") || recipeTags.contains("Egg "))
			return RecipeCategory.Eggitarian;
		else if (recipeName.contains("NonVeg") || recipeTags.contains("NonVeg"))
			return RecipeCategory.NonVeg;
		else
			return RecipeCategory.Vegetarian;
	}

	///
	// This method calculates the food category based on name and tags
	///
	public FoodCategory GetFoodCategory(String recipeName, String recipeTags) 
	{
		if (recipeName.contains("Breakfast") || recipeTags.contains("Breakfast"))
			return FoodCategory.Breakfast;
		else if (recipeName.contains("Lunch") || recipeTags.contains("Lunch"))
			return FoodCategory.Lunch;
		else if (recipeName.contains("Dinner") || recipeTags.contains("Dinner"))
			return FoodCategory.Dinner;
		else
			return FoodCategory.Snacks;
	}
	
	///
	// This method compares ingredient list and filter list 
	// Returns true if there is a match
	// Returns false if there is no match
	///
	private boolean containsIngredient(String recipeIngredients, List<String> ingredientsToCheck) 
	{
		List<String> recipeIng = Arrays.asList(recipeIngredients.split(", "));
		return recipeIng.stream().anyMatch(recipeIngredient -> ingredientsToCheck.stream()
				.anyMatch(ingToCheck -> StringUtils.containsIgnoreCase(ingToCheck, recipeIngredient)));
	}

	///
	// This method calculates the target morbid condition based on igredients and filters
	///
	public String GetTargetCondition(String ingredients) 
	{
		List<String> targetConditions = new ArrayList<>();
		String targetCondition = "";

		if (!containsIngredient(ingredients, ExcelData.DiabetesEliminate))
			targetConditions.add(TargettedMorbidConditions.Diabetes.toString());
		if (!containsIngredient(ingredients, ExcelData.HypothyroidismEliminate))
			targetConditions.add(TargettedMorbidConditions.Hypothyroidism.toString());
		if (!containsIngredient(ingredients, ExcelData.HypertensionEliminate))
			targetConditions.add(TargettedMorbidConditions.Hypertension.toString());
		if (!containsIngredient(ingredients, ExcelData.PCOSEliminate))
			targetConditions.add(TargettedMorbidConditions.PCOS.toString());

		for (int i = 0; i < targetConditions.size(); i++) 
		{
			if (i == 0)
				targetCondition = targetConditions.get(i);
			else
				targetCondition = targetCondition + ", " + targetConditions.get(i);
		}

		return targetCondition;
	}

	///
	// This method gets the ToAdd status of recipe
	// returns true if the recipe with morbid condition target contains the good to add ingredients
	///
	public Boolean GetToAddStatus(String ingredients, String targetCondition) 
	{
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

	///
	// This method calculates no allery status
	// returns true if the recipe ingredients targetted for morbid condition does not contain any allergens
	///
	public Boolean GetNoAllergiesStatus(String ingredients, String targetCondition) 
	{
		Boolean NoAllergiesStatus = false;

		if (!containsIngredient(ingredients, ExcelData.AllergiesToFilter) && !targetCondition.isEmpty()
				&& !targetCondition.isBlank())
			NoAllergiesStatus = true;

		return NoAllergiesStatus;
	}

	///
	// This method writes all the recipe details to 4 excel files based on filters
	///
	public void WriteRecipeToExcels(Recipe recipe) 
	{
		try
		{
			// Write all recipes to excel with no filters
			if (excelWriter == null)
				excelWriter = new ExcelWriter(ConfigReader.getAllRecipesOutputExcelPath(), ConfigReader.getOutputRowHeaders());
			excelWriter.WriteRecipeToExcel(recipe);
			Log.info("Recipe added to excel");
	
			// Write Elimination excel
			if (excelWriterEliminate == null)
				excelWriterEliminate = new ExcelWriter(ConfigReader.getEliminationOutputExcelPath(), ConfigReader.getOutputRowHeaders());
			if (recipe.targetCondition != null && !recipe.targetCondition.isEmpty()) 
			{
				excelWriterEliminate.WriteRecipeToExcel(recipe);
				Log.info("Recipe added to eliminate excel");
			}
	
			// Write To Add excel
			if (excelWriterToAdd == null)
				excelWriterToAdd = new ExcelWriter(ConfigReader.getToAddOutputExcelPath(), ConfigReader.getOutputRowHeaders());
			if (recipe.toAdd) 
			{
				excelWriterToAdd.WriteRecipeToExcel(recipe);
				Log.info("Recipe added to to add excel");
			}
	
			// Write Allergy excel
			if (excelWriterAllergies == null)
				excelWriterAllergies = new ExcelWriter(ConfigReader.getAllergiesOutputExcelPath(), ConfigReader.getOutputRowHeaders());
			if (recipe.NoAllergies) 
			{
				excelWriterAllergies.WriteRecipeToExcel(recipe);
				Log.info("Recipe added to to allergies excel");
			}
		}
		catch(Exception ex)
		{
			Log.info(ex.getMessage());
			Screenshots.CaptureScreenshot(driver);
		}
	}
}