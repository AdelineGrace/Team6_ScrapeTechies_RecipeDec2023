package testcases;

import org.testng.annotations.Test;

public class TestClass extends BaseClass {

	public TestClass() {

	}

	@Test
	public void Test1() 
	{
		homePage.GoToHomePage();
		homePage.SearchRecipe("PCOS");
	}

}
