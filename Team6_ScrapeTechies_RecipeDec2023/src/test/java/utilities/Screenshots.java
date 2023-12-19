package utilities;

import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Screenshots {

	public static void CaptureScreenshot(WebDriver driver)
	{
		try
		{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(ConfigReader.getScreenshotPath() + new Date() + ".jpeg"));
	
		}
		catch(IOException ex)
		{
			Log.info(ex.getMessage());
		}
	}
}