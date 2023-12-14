package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import managers.FileManager;
import utilities.Log;

public class HomePage {
	
	WebDriver driver;
	
	@FindBy(xpath = "//input[contains(@id,'txtsearch')]") WebElement txtSearch;
	@FindBy(xpath = "//input[@class='searchbtn']") WebElement btnSearch;
	@FindBy(xpath = "//a[text()='Recipe A To Z']") WebElement linkRecipeAtoZ;

	
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void GoToHomePage()
	{
		driver.get(FileManager.getInstance().getConfigReader().getWebUrl());
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
		linkRecipeAtoZ.click();
		Log.info("Navigated to A to Z Recipes Page");
	}

}
