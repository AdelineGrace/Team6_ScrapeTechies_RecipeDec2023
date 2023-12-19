package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import model.Recipe;
import utilities.Log;
import utilities.Screenshots;

public class AtoZPage {

	WebDriver driver;
	RecipePage recipePage;

	public AtoZPage(WebDriver driver) 
	{
		this.driver = driver;
	}

	///
	// This method gets all the recipe details and writes to excel based on the required filters
	///
	public List<Recipe> GetRecipesOnPage(List<Recipe> lstRecipe) 
	{
		// Get all recipes on the page 
		List<WebElement> recipeCards = driver.findElements(By.xpath("//div[contains(@class,'recipecard')]"));
		
		// Get recipes for each recipe card on page
		for (int i = 1; i <= recipeCards.size(); i++) 
		{
			// New recipe object
			Recipe recipe = new Recipe();

			try {
				// Scroll to the card
				WebElement recipeCard = driver.findElement(By.xpath("//div[contains(@class,'recipecard')][" + i + "]"));

				// Get the recipe id
				WebElement recipeNo = recipeCard.findElement(By.xpath(".//span[contains(text(),'Recipe#')]"));
				recipe.recipeId = recipeNo.getText().split(" ")[1].split(System.lineSeparator())[0];
				Log.info(recipe.recipeId);

				// Get the recipe name
				WebElement recipeLink = recipeCard.findElement(By.xpath(".//div[@class='rcc_rcpcore']//a"));
				recipe.recipeName = recipeLink.getText();
				Log.info(recipe.recipeName);

				// Click on the recipe name/link
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", recipeLink);

				// Fetch other recipe details and write to excel based on the filter
				if(recipePage == null)
					recipePage = new RecipePage(driver);
				recipe = recipePage.GetRecipeDetailsAndWriteToExcel(recipe);

				// Add recipe to the list
				lstRecipe.add(recipe);
				
				// Get count of recipes scraped from website without filters
				Log.info("No. of recipes found so far " + lstRecipe.size());
			} 
			catch (Exception ex) 
			{
				Log.info(ex.getMessage());
				Log.info("page " + i + "catch block - " + driver.getTitle());
				Screenshots.CaptureScreenshot(driver);
			}
		}
		return lstRecipe;
	}

	///
	// Gets all the recipes from all the pages on A to Z page 
	///
	public void GetAllRecipes() 
	{
		List<Recipe> lstRecipe = new ArrayList<>();
		
		// Traverse recipe pages from A to Z
		for (char letter = 'O'; letter <= 'Q'; letter++) 
		{
			try {
				// Dont need to change page for A
				if (letter != 'A') 
				{
					Log.info("looking for " + letter);

					WebElement letterElement = driver
							.findElement(By.xpath("//a[text()='" + String.valueOf(letter) + "']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", letterElement);

					Log.info("clicked on " + letter);
				}

				// Numerical Pagination on each Alphabetical page
				List<WebElement> pagination = driver.findElements(By.xpath("//div[contains(text(),'Goto Page')][1]/a"));

				 for (int i = 1; i <= pagination.size(); i++) 
				 {
					try {
						// Dont need to change for page 1
						if (i != 1) {
							Log.info("looking for page " + i);

							WebElement pagei = driver
									.findElement(By.xpath("//*[@id='maincontent']/div[1]/div[2]/a[" + i + "]"));

							((JavascriptExecutor) driver).executeScript("arguments[0].click();", pagei);
							Log.info("clicked on page " + i);

						}
					} 
					catch (Exception ex) 
					{
						Log.info(ex.getMessage());
						Screenshots.CaptureScreenshot(driver);
					}
					
					// Gets all the recipe details
					lstRecipe = GetRecipesOnPage(lstRecipe);
				}

			} 
			catch (Exception ex) 
			{
				Log.info(ex.getMessage());
				Screenshots.CaptureScreenshot(driver);
			}
		}
	}

}
