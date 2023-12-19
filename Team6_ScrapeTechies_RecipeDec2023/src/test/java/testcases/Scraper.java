package testcases;
import org.testng.annotations.Test;

import utilities.Log;

public class Scraper extends BaseClass {


	@Test
 	public void GetScraping() 
	{
		Log.info("Started Scraping");
		try
		{
			homePage.GoToHomePage();
			homePage.GotoAtoZPage();
			atoZPage.GetAllRecipes();
		}
		catch(Exception ex)
		{
			Log.info(ex.getMessage());
		}
	}

}
