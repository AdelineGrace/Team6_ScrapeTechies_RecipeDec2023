package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import model.Recipe;

public class RecipePage {

	WebDriver driver;

	public RecipePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Recipe GetRecipeDetails(Recipe recipe) 
	{
		// Get recipe URL, prep time, cook time
		recipe.recipeURL = driver.getCurrentUrl();
		System.out.println(recipe.recipeURL);
		recipe.prepTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
		System.out.println(recipe.prepTime);
		recipe.cookingTime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
		System.out.println(recipe.cookingTime);

		// Get recipe nutrient values
		if (driver.findElement(By.id("rcpnuts")).isDisplayed()) {
			recipe.nutritionValue = driver.findElement(By.xpath("//span[@itemprop='calories']")).getText();
		} else
			recipe.nutritionValue = "NA";
		System.out.println(recipe.nutritionValue);

		// Get recipe ingredients
		recipe.ingredients = new ArrayList<>();
		List<WebElement> ingredients = driver.findElements(By.xpath("//span[@itemprop='recipeIngredient']/a"));
		for (WebElement ingredient : ingredients) {
			recipe.ingredients.add(ingredient.getText());
		}

		// Get recipe tags to calculate recipe category and food category
		WebElement tags = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
		String recipeTags = tags.getText();

		// Calculate recipe category based on recipe name and tags
		if (recipe.recipeName.contains("Vegan") || recipeTags.contains("Vegan")) 
			recipe.recipeCategory = "Vegan";
		else if (recipe.recipeName.contains("Jain") || recipeTags.contains("Jain")) 
			recipe.recipeCategory = "Jain";
		else if (recipe.recipeName.contains("Egg") || recipeTags.contains("Egg")) 
			recipe.recipeCategory = "Eggitarian";
		else if (recipe.recipeName.contains("NonVeg") || recipeTags.contains("NonVeg")) 
			recipe.recipeCategory = "Non-veg";
		else 
			recipe.recipeCategory = "Vegetarian";
		
		System.out.println(recipe.recipeCategory);

		// Go back to list of recipes
		driver.navigate().back();
		driver.navigate().back();

		return recipe;
	}

}
