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
import utilities.Log;

public class AtoZPage {

	WebDriver driver;
	RecipePage recipePage;

	@FindBy(xpath = "//div[contains(text(),'Goto Page')][1]/a[text()='2']")
	WebElement page2;

	@FindBy(xpath = "//div[contains(@class,'recipecard')]")
	List<WebElement> recipeCards;

	public AtoZPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public List<Recipe> GetAllRecipes(List<Recipe> lstRecipe) {
	    try {
	        List<WebElement> recipeCards = driver.findElements(By.xpath("//div[contains(@class,'recipecard')]"));

	        for (int i = 1; i <= recipeCards.size(); i++) {
	            Recipe recipe = new Recipe();

	            try {
	                WebElement recipeCard = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/form[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[" + i + "]"));

//	                // Your code for extracting details from the recipe card
		
//
	                // Get the recipe id
	                WebElement recipeNo = recipeCard.findElement(By.xpath(".//span[contains(text(),'Recipe#')]"));
	                recipe.recipeId = recipeNo.getText().split(" ")[1].split(System.lineSeparator())[0];
	                Log.info(recipe.recipeId);

	                // Get the recipe name
	                WebElement recipeLink = recipeCard.findElement(By.xpath(".//div[@class='rcc_rcpcore']//a"));
	                recipe.recipeName = recipeLink.getText();
	                Log.info(recipe.recipeName);

	                // Click on the recipe link
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", recipeLink);
	                recipePage = new RecipePage(driver);
	                recipe = recipePage.GetRecipeDetails(recipe);

	                lstRecipe.add(recipe);
	                Log.info("No. of recipes found so far " + lstRecipe.size());

	                // Navigate back
	                driver.navigate().back();

	            } catch (NoSuchElementException e2) {
	                // Log the exception
	                Log.info("Recipe card not found for index: " + i);
	                continue; // Continue with the next iteration of the loop
	            }
	        }
	    } catch (Exception ex) {
	        Log.info(ex.getMessage());
	    }

	    return lstRecipe;
	}
//
//

//		public List<Recipe> GetAllRecipes(List<Recipe> lstRecipe) {
//		    try {
//		        // Define the two XPaths
//		        String recipeCardXPath1 = "//div[contains(@class,'recipecard')][%d]";
//		        String recipeCardXPath2 = "/html[1]/body[1]/div[2]/form[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[%d]/div[3]/span[1]/a[1]";
//		        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
//		        // Iterate through each recipe card
//		        List<WebElement> recipeCards = driver.findElements(By.xpath("//div[contains(@class,'recipecard')]"));
//		        for (int i = 1; i <= recipeCards.size(); i++) {
//		            try {
//		                Recipe recipe = new Recipe();
//		                WebElement recipeCard = null;  // Declare recipeCard here
//
//		                // Try the first XPath
//		                recipeCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(recipeCardXPath1, i))));
//		                Log.info("Using XPath 1 for Recipe Card");
//
//		                // Your code for extracting details from the recipe card
//
//		                // Get the recipe id
//		                WebElement recipeNo = recipeCard.findElement(By.xpath(".//span[contains(text(),'Recipe#')]"));
//		                recipe.recipeId = recipeNo.getText().split(" ")[1].split(System.lineSeparator())[0];
//		                Log.info(recipe.recipeId);
//
//		                // Get the recipe name
//		                WebElement recipeLink = recipeCard.findElement(By.xpath(".//div[@class='rcc_rcpcore']//a"));
//		                recipe.recipeName = recipeLink.getText();
//		                Log.info(recipe.recipeName);
//
//		                // Click on the recipe link
//		                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", recipeLink);
//		                recipePage = new RecipePage(driver);
//		                recipe = recipePage.GetRecipeDetails(recipe);
//
//		                lstRecipe.add(recipe);
//		                Log.info("No. of recipes found so far " + lstRecipe.size());
//
//		                // Navigate back
//		                driver.navigate().back();
//		            } catch (NoSuchElementException e1) {
//		                try {
//		                    // If the first XPath fails, try the second XPath
//		                  WebElement  recipeCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(recipeCardXPath2,i))));
//		                    Log.info("Using XPath 2 for Recipe Card");
//
//		                    // Your code for extracting details from the recipe card
//			                Recipe recipe = new Recipe();
//
//		                    // Get the recipe id
//		                    WebElement recipeNo = recipeCard.findElement(By.xpath(".//span[contains(text(),'Recipe#')]"));
//		                    recipe.recipeId = recipeNo.getText().split(" ")[1].split(System.lineSeparator())[0];
//		                    Log.info(recipe.recipeId);
//
//		                    // Get the recipe name
//		                    WebElement recipeLink = recipeCard.findElement(By.xpath(".//div[@class='rcc_rcpcore']//a"));
//		                    recipe.recipeName = recipeLink.getText();
//		                    Log.info(recipe.recipeName);
//
//		                    // Click on the recipe link
//		                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", recipeLink);
//		                    recipePage = new RecipePage(driver);
//		                    recipe = recipePage.GetRecipeDetails(recipe);
//
//		                    lstRecipe.add(recipe);
//		                    Log.info("No. of recipes found so far " + lstRecipe.size());
//
//		                    // Navigate back
//		                    driver.navigate().back();
//		                } catch (NoSuchElementException e2) {
//		                    // Log the exception
//		                    Log.info("Recipe card not found for index: " + i);
//		                    continue; // Continue with the next iteration of the loop
//		                }
//		            }
//		        }
//		    } catch (Exception ex) {
//		        Log.info(ex.getMessage());
//		    }
//
//		    return lstRecipe;
//		}
//
//	
	
	public List<Recipe> PagesLogic() {
		List<Recipe> lstRecipe = new ArrayList<Recipe>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

		for (char letter = 'A'; letter <= 'A'; letter++) {
			try {
				if (letter != 'A') {
					Log.info("looking for " + letter);

					WebElement letterElement = driver
							.findElement(By.xpath("//a[text()='" + String.valueOf(letter) + "']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", letterElement);

					Log.info("clicked on " + letter);
				}

				List<WebElement> pagination = driver.findElements(By.xpath("//div[contains(text(),'Goto Page')][1]/a"));

				//for (int i = 2; i <= pagination.size(); i++) {
				for (int i = 5; i <= 10; i++) {
					try {
						if (i != 1) {
							Log.info("looking for page " + i);

							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id='maincontent']/div[1]/div[2]/a[" + i + "]")));
							WebElement pagei = driver
									.findElement(By.xpath("//*[@id='maincontent']/div[1]/div[2]/a[" + i + "]"));

							((JavascriptExecutor) driver).executeScript("arguments[0].click();", pagei);
							Log.info("clicked on page " + i);

						}
					} catch (Exception ex) {
						Log.info(ex.getMessage());
					}
					 lstRecipe = GetAllRecipes(lstRecipe);
				}

			} catch (Exception ex) {
				Log.info(ex.getMessage());
			}
		}

		return lstRecipe;
	}
}
