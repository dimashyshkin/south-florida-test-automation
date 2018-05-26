package com.herokuapp.theinternet.base;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

	protected WebDriver driver;
	protected HashMap<String, String> testConfig = new HashMap<String, String>();
	protected Logger log;


	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser", "environment" })
	protected void setUp(@Optional("chrome") String browser, @Optional("local") String environment, ITestContext ctx) {
		String testName = ctx.getCurrentXmlTest().getName();
		BrowserDriverFactory factory = new BrowserDriverFactory(browser);
		if (environment.equals("grid")) {
			driver = factory.createDriverGrid();
		} else {
			driver = factory.createDriver();
		}
		testConfig.put("browser", browser);

		// maximize browser window
		driver.manage().window().maximize();

		log = LogManager.getLogger(testName);
	}


	@AfterMethod(alwaysRun = true)
	protected void tearDown() {
		// Closing driver
		log.info("[Closing driver]");
		driver.quit();
	}

}
