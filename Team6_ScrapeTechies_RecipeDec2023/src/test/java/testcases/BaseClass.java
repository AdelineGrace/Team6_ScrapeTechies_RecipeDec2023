package testcases;

import context.TestContext;
import pages.*;
import utilities.ConfigReader;
import utilities.ExcelData;
import utilities.ExcelReader;
import utilities.ExcelWriter;

public class BaseClass {
	
	static TestContext testContext;
	
	static HomePage homePage;
	static RecipePage recipePage;
	static AtoZPage atoZPage;
	
	static ConfigReader configReader;
	static ExcelWriter excelWriter;
	static ExcelReader eliminateAddExcelReader;
	
	public BaseClass()
	{
		ConfigReader.loadProperty();
		
		testContext = new TestContext();
		
		homePage = testContext.getPageObjectManager().getHomePage();
		recipePage = testContext.getPageObjectManager().getRecipePage();
		atoZPage = testContext.getPageObjectManager().getAtoZPage();
	
		ExcelData.LoadEliminationData();
		ExcelData.LoadToAddData();
		ExcelData.LoadAllergiesData();
	}
	

}
