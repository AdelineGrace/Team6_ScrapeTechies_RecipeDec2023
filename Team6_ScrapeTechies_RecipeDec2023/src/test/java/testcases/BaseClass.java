package testcases;

import context.TestContext;
import managers.FileManager;
import pages.HomePage;
import pages.RecipePage;
import pages.SearchResultsPage;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.ExcelWriter;

public class BaseClass {
	
	static TestContext testContext;
	
	static HomePage homePage;
	static RecipePage recipePage;
	static SearchResultsPage searchResultsPage;
	
	static ConfigReader configReader;
	static ExcelWriter excelWriter;
	static ExcelReader excelReader;
	
	public BaseClass()
	{
		testContext = new TestContext();
		
		homePage = testContext.getPageObjectManager().getHomePage();
		recipePage = testContext.getPageObjectManager().getRecipePage();
		searchResultsPage = testContext.getPageObjectManager().getSearchResultsPage();
		
		configReader = FileManager.getInstance().getConfigReader();
		excelWriter = FileManager.getInstance().getExcelWriter();
		excelReader = FileManager.getInstance().getExcelReader();
	}
	

}
