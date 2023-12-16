package managers;

import org.openqa.selenium.WebDriver;

import pages.AtoZPage;
import pages.HomePage;
import pages.RecipePage;
import pages.SearchResultsPage;


public class PageObjectManager {
	
	private WebDriver driver;
	private HomePage homePage;
	private RecipePage recipePage;
	private SearchResultsPage searchResultsPage;
	private AtoZPage atoZPage;
	
	
	public PageObjectManager(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	public HomePage getHomePage()
	{
		return (homePage == null) ? homePage = new HomePage(driver) : homePage;
	}
	
	public RecipePage getRecipePage()
	{
		return (recipePage == null) ? recipePage = new RecipePage(driver) : recipePage;
	}
	
	public SearchResultsPage getSearchResultsPage()
	{
		return (searchResultsPage == null) ? searchResultsPage = new SearchResultsPage(driver) : searchResultsPage;
	}
	
	public AtoZPage getAtoZPage()
	{
		return (atoZPage == null) ? atoZPage = new AtoZPage(driver) : atoZPage;
	}
	
}
