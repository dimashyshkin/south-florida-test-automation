package com.herokuapp.theinternet.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.herokuapp.theinternet.base.BaseTest;

public class PositiveTests extends BaseTest {

	@Test
	public void logInTest() {
		// open the page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		log.info("Page is opened.");

		// enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

		// enter password
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("SuperSecretPassword!");
		log.info("Entered username and password.");

		// push log in button
		WebElement logInButton = driver.findElement(By.className("radius"));
		logInButton.click();
		log.info("Clicked LogIn button.");

		// Verifications:
		
		// 1 - new url
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl);
		log.info("URL verification is complete.");

		// 2 - log out button is visible
		WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "logOutButton is not visible.");
		log.info("Log Out button is visible.");

		// 3 - Successful log in message
		WebElement successMessage = driver.findElement(By.id("flash"));
		String expectedSuccessMessage = "You logged into a secure area!";
		String actualSuccessMessage = successMessage.getText();
		Assert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
				"actualSuccessMessage does not contain expectedSuccessMessage\nexpectedSuccessMessage: "
						+ expectedSuccessMessage + "\nactualSuccessMessage: " + actualSuccessMessage);
		log.info("Successful log in message is correct.");
	}

}
