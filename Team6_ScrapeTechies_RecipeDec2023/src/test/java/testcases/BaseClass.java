package testcases;

import java.util.ArrayList;
import java.util.List;

import context.TestContext;
import pages.*;
import utilities.ConfigReader;
import utilities.ExcelData;
import utilities.ExcelReader;
import utilities.ExcelWriter;
import model.Recipe;

public class BaseClass {
	
	static TestContext testContext;
	
	static HomePage homePage;
	static RecipePage recipePage;
	static AtoZPage atoZPage;
	
	static ConfigReader configReader;
	static ExcelWriter excelWriter;
	static ExcelReader eliminateAddExcelReader;
	
	static List<Recipe> lstRecipe = new ArrayList<>();
	
	
	
	public BaseClass()
	{
		testContext = new TestContext();
		
		homePage = testContext.getPageObjectManager().getHomePage();
		recipePage = testContext.getPageObjectManager().getRecipePage();
		atoZPage = testContext.getPageObjectManager().getAtoZPage();
		
		ExcelData.LoadEliminationData();
		
	}
	

}
