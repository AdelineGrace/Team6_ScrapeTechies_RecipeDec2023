package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import model.Recipe;
import utilities.Log;

public class RecipePage {

	WebDriver driver;

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

			// Get recipe ingredients
			recipe.ingredients = GetRecipeIngredients();

			// Get recipe tags to calculate recipe category and food category
			String recipeTags = GetRecipeTags();

			// Calculate recipe category based on recipe name and tags
			recipe.recipeCategory = GetRecipeCategory(recipe.recipeName, recipeTags);
			Log.info(recipe.recipeCategory);

			// Go back to list of recipes
			driver.navigate().back();
			driver.navigate().back();
		} 
		catch (Exception ex) 
		{
			Log.info(ex.getMessage());
			
			// Go back to list of recipes
			driver.navigate().back();
			driver.navigate().back();
		}

		return recipe;
	}

	public String GetRecipePrepTime() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipePrepTime);
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

	public List<String> GetRecipeIngredients() {
		List<String> lstIng = new ArrayList<>();

		for (WebElement ingredient : lstIngredients) {
			lstIng.add(ingredient.getText());
		}

		return lstIng;
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
