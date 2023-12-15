package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import model.Recipe;

public class AtoZPage {

	WebDriver driver;
	RecipePage recipePage;

	@FindBy(xpath = "//div[contains(@class,'recipecard')]") WebElement recipeCards;

	
	public AtoZPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		recipePage = new RecipePage(driver);
	}
	
	public List<Recipe> SelectRecipe()
	{
		List<Recipe> lstRecipe = new ArrayList<Recipe>();
		
		// go to 2nd page
		driver.findElement(By.xpath("//div[contains(text(),'Goto Page')][1]/a[text()='2']")).click();
		
		// Get count of recipes on the page
		List<WebElement> lstRecipeCards = driver.findElements(By.xpath("//div[contains(@class,'recipecard')]"));
		int recipeCountOnPage = lstRecipeCards.size();
		
		for(int i = 1 ; i <= recipeCountOnPage ; i++)
		{
			// New recipe object
			Recipe recipe = new Recipe();
			
			// Scroll to the card
			WebElement recipeCard = driver.findElement(By.xpath("//div[contains(@class,'recipecard')][" + i + "]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipeCard);
			
			// Get the recipe id
			WebElement recipeNo = driver.findElement(By.xpath("//div[contains(@class,'recipecard')][" + i + "]//span[contains(text(),'Recipe#')]"));
			recipe.recipeId = recipeNo.getText().split(" ")[1].split(System.lineSeparator())[0];
			System.out.println(recipe.recipeId);
			
			// Get the recipe name
			WebElement recipeLink = driver.findElement(By.xpath("//div[contains(@class,'recipecard')][" + i + "]//div[@class='rcc_rcpcore']//a"));
			recipe.recipeName = recipeLink.getText();
			System.out.println(recipe.recipeName);
			
			// Click on the recipe name/link
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", recipeLink);
			
			recipe = recipePage.GetRecipeDetails(recipe);
			
			lstRecipe.add(recipe);
		}
		
		return lstRecipe;
	}
}
