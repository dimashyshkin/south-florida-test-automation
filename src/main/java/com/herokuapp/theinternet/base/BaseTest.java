package com.herokuapp.theinternet.base;

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
	protected Logger log;


	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser", "environment" })
	protected void setUp(@Optional("chrome") String browser, @Optional("local") String environment, ITestContext ctx) {
		// Create Driver
		BrowserDriverFactory factory = new BrowserDriverFactory(browser);
		if (environment.equals("grid")) {
			driver = factory.createDriverGrid();
		} else {
			driver = factory.createDriver();
		}

		// maximize browser window
		driver.manage().window().maximize();

		// Set up test name and Logger
		setCurrentThreadName();
		String testName = ctx.getCurrentXmlTest().getName();
		log = LogManager.getLogger(testName);
	}


	@AfterMethod(alwaysRun = true)
	protected void tearDown() {
		// Closing driver
		log.info("[Closing driver]");
		driver.quit();
	}


	/** Sets thread name which includes thread id */
	private void setCurrentThreadName() {
		Thread thread = Thread.currentThread();
		String threadName = thread.getName();
		String threadId = String.valueOf(thread.getId());
		if (!threadName.contains(threadId)) {
			thread.setName(threadName + " " + threadId);
		}
	}

}
