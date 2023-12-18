package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import model.Recipe;
import utilities.Log;

public class AtoZPage {

	WebDriver driver;
	RecipePage recipePage;

	@FindBy(xpath = "//div[contains(@class,'recipecard')]")
	List<WebElement> recipeCards;

	public AtoZPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public List<Recipe> GetAllRecipes(List<Recipe> lstRecipe) {
		// Get recipes for each recipe card on page
		for (int i = 1; i <= recipeCards.size(); i++) {
			
			// New recipe object
			Recipe recipe = new Recipe();

			try {
				// Scroll to the card
				WebElement recipeCard = driver.findElement(By.xpath("//div[contains(@class,'recipecard')][" + i + "]"));
				//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipeCard);

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

				recipePage = new RecipePage(driver);
				recipe = recipePage.GetRecipeDetails(recipe);

				lstRecipe.add(recipe);
				Log.info("No. of recipes found so far " + lstRecipe.size());
			} catch (Exception ex) {
				Log.info(ex.getMessage());
			}
		}

		return lstRecipe;
	}

	public List<Recipe> PagesLogic() {
		List<Recipe> lstRecipe = new ArrayList<Recipe>();
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='A']")));

		for (char letter = 'B'; letter <= 'B'; letter++) {
			try {
				if (letter != 'A') {
					Log.info("looking for " + letter);

					WebElement letterElement = driver
							.findElement(By.xpath("//a[text()='" + String.valueOf(letter) + "']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", letterElement);

					Log.info("clicked on " + letter);
				}

				List<WebElement> pagination = driver.findElements(By.xpath("//div[contains(text(),'Goto Page')][1]/a"));

				// for (int i = 2; i <= pagination.size(); i++) {
				for (int i = 4; i <= 4; i++) {
					try {
						if (i != 1) {
							Log.info("looking for page " + i);

							//wait.until(ExpectedConditions.visibilityOfElementLocated(
									//By.xpath("//*[@id='maincontent']/div[1]/div[2]/a[" + i + "]")));
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
