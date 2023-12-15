package testcases;

import org.testng.annotations.Test;

public class TestClass extends BaseClass {


	@Test
	public void Test1() 
	{
		homePage.GoToHomePage();
		homePage.GotoAtoZPage();
		
		atoZPage.SelectRecipe();
	}

}
