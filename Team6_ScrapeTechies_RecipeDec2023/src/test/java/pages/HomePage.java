package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigReader;
import utilities.Log;

public class HomePage {
	
	WebDriver driver;
	ConfigReader configReader = new ConfigReader();
	
	@FindBy(xpath = "//input[contains(@id,'txtsearch')]") WebElement txtSearch;
	@FindBy(xpath = "//input[@class='searchbtn']") WebElement btnSearch;
	@FindBy(xpath = "//a[@title='Recipea A to Z']") WebElement linkRecipeAtoZ;

	
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void GoToHomePage()
	{
		driver.get(configReader.getWebUrl());
		Log.info("Navigated to Home Page");
	}
	
	public void SearchRecipe(String txtSearchContent)
	{
		txtSearch.sendKeys(txtSearchContent);
		Log.info("Entered search content in Search textbox");
		
		btnSearch.click();
		Log.info("Clicked on Search button");
	}
	
	public void GotoAtoZPage()
	{
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Recipea A to Z']")));
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkRecipeAtoZ);
		Log.info("Navigated to A to Z Recipes Page");
	}

}
