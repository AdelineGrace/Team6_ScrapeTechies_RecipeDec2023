package testcases;


import java.io.IOException;
import java.util.List;
import org.testng.annotations.*;
import managers.DriverManager;
import utilities.ConfigReader;
import utilities.ExcelWriter;

public class Recipe1 {
	static DriverManager driverManager = new DriverManager();
    static ExcelWriter excelWriter;  

    @BeforeMethod
    public static void setUp() {
        driverManager.getDriver();
        driverManager.getDriver().get(ConfigReader.getWebUrl());
        excelWriter = new ExcelWriter("src/test/resources/Data/Recipe-filters-ScrapperHackathon.xlsx");
        List<String> headers = List.of(
                "Recipe ID",
                "Recipe Name",
                "Recipe Category",
                "Food Category",
                "Ingredients",
                "Preparation Time",
                "Cooking Time",
                "Preparation method",
                "Nutrient values",
                "Targeted morbid conditions",
                "Recipe URL"
        );
        excelWriter.writeHeader(headers);
    }
    
    @Test
    public static void recipes(){
    	System.out.println("working");
    }
    
    @AfterMethod
    public static void tearDown() throws IOException {
        driverManager.getDriver().quit();
        excelWriter.saveFile();  
    }

   
    
}
