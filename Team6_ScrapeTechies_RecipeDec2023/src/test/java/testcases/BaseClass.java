package testcases;

import java.util.ArrayList;
import java.util.List;

import context.TestContext;
import managers.FileManager;
import pages.AtoZPage;
import pages.HomePage;
import pages.RecipePage;
import pages.SearchResultsPage;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.ExcelWriter;
import model.Recipe;

public class BaseClass {
	
	static TestContext testContext;
	
	static HomePage homePage;
	static RecipePage recipePage;
	static SearchResultsPage searchResultsPage;
	static AtoZPage atoZPage;
	
	static ConfigReader configReader;
	static ExcelWriter excelWriter;
	static ExcelReader excelReader;
	
	static List<Recipe> lstRecipe = new ArrayList<>();
	
	
	public BaseClass()
	{
		testContext = new TestContext();
		
		homePage = testContext.getPageObjectManager().getHomePage();
		recipePage = testContext.getPageObjectManager().getRecipePage();
		searchResultsPage = testContext.getPageObjectManager().getSearchResultsPage();
		atoZPage = testContext.getPageObjectManager().getAtoZPage();
		
		//configReader = FileManager.getInstance().getConfigReader();
		//excelWriter = FileManager.getInstance().getExcelWriter();
		//excelReader = FileManager.getInstance().getExcelReader();
	}
	

}
