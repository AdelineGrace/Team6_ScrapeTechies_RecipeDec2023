package testcases;
import org.testng.annotations.Test;

import utilities.Log;

public class TestClass extends BaseClass {


	@Test
 	public void Test1() 
	{
		Log.info("Started");
		homePage.GoToHomePage();
		homePage.GotoAtoZPage();
		
		try
		{
			lstRecipe = atoZPage.PagesLogic();
		}
		catch(Exception ex)
		{
			Log.info(ex.getMessage());
			Log.info("Total recipe scraped - " + lstRecipe.size());
		}
	}

}
