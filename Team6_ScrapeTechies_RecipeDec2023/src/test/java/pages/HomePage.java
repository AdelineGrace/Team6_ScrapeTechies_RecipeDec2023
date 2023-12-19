package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.ConfigReader;
import utilities.Log;
import utilities.Screenshots;

public class HomePage {
	
	WebDriver driver;

	public HomePage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	///
	// Method to go to Home Page
	///
	public void GoToHomePage()
	{
		try
		{
			driver.get(ConfigReader.getWebUrl());
			Log.info("Navigated to Home Page");
		}
		catch(Exception ex)
		{
			Log.info(ex.getMessage());
			Screenshots.CaptureScreenshot(driver);
		}
	}
	
	///
	// Method to Search a recipe
	///
	public void SearchRecipe(String txtSearchContent)
	{
		try
		{
			driver.findElement(By.xpath("//input[contains(@id,'txtsearch')]")).sendKeys(txtSearchContent);
			Log.info("Entered search content in Search textbox");
			
			driver.findElement(By.xpath("//input[@class='searchbtn']")).click();
			Log.info("Clicked on Search button");
		}
		catch(Exception ex)
		{
			Log.info(ex.getMessage());
			Screenshots.CaptureScreenshot(driver);
		}
	}
	
	///
	// Method to go to A to Z recipes Page
	///
	public void GotoAtoZPage()
	{
		try
		{
			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Recipea A to Z']")));
			
			WebElement linkRecipeAtoZ = driver.findElement(By.xpath("//a[@title='Recipea A to Z']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkRecipeAtoZ);
			Log.info("Navigated to A to Z Recipes Page");
		}
		catch(Exception ex)
		{
			Log.info(ex.getMessage());
			Screenshots.CaptureScreenshot(driver);
		}
	}

}
