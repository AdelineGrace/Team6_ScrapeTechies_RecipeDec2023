package managers;
import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import enums.BrowserType;
import utilities.ConfigReader;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private BrowserType browserType;

    public DriverManager() {
        browserType = ConfigReader.getBrowserType();
    }

    public WebDriver getDriver() {
        if (driver.get() == null) {
            switch (browserType) {

                case FIREFOX:
                    driver.set(new FirefoxDriver());
                    break;

                case CHROME:
                   // ChromeOptions chromeOptions = new ChromeOptions();
//                    //chromeOptions.addArguments("--headless"); // Run Chrome in headless mode
//                    //chromeOptions.addArguments("--disable-popup-blocking"); // Disable popup blocking
//                    chromeOptions.addArguments("--disable-extensions"); // Disable popup blocking
//                    chromeOptions.addArguments("--blink-settings=imagesEnabled=false");
//                    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//                    jsExecutor.executeScript("document.querySelectorAll('.ad-element').forEach(el => el.style.display = 'none');");
//
//                    driver.set(new ChromeDriver(chromeOptions));
//                    break;
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless"); // Run Chrome in headless mode
<<<<<<< HEAD
                    //chromeOptions.addArguments("--disable-popup-blocking"); // Disable popup blocking
                    chromeOptions.addArguments("--disable-extensions"); // Disable extensions
=======
                    chromeOptions.addArguments("--disable-popup-blocking"); // Disable popup blocking
                    chromeOptions.addArguments("--disable-notifications"); // Disable popup blocking
                    chromeOptions.addArguments("--disable-extensions"); // Disable popup blocking
>>>>>>> main
                    chromeOptions.addArguments("--blink-settings=imagesEnabled=false");

                    // JavaScript to hide ads
                    //
                    String jsScript = "document.querySelectorAll('.ad-element').forEach(el => el.style.display = 'none');";

                    chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);

                    // Create a new ChromeDriver instance with the configured options
                    ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);

                    // Execute JavaScript
                    JavascriptExecutor jsExecutor = (JavascriptExecutor) chromeDriver;
                    jsExecutor.executeScript(jsScript);

                    // Set the driver in the ThreadLocal
                    driver.set(chromeDriver);
                    break;
                case EDGE:
                    driver.set(new EdgeDriver());
                    break;
            }
        }
        driver.get().manage().deleteAllCookies();
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadWait()));
        driver.get().manage().window().maximize();
        
        return driver.get();
    }

   
}
