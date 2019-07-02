package testcases.revolut;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;
import pages.revolut.AdminCommunityPage;
import pages.revolut.AdminHomePage;
import util.TestUtil;

public class ChangeCountryFlagTest extends TestBase {
	AdminHomePage homePage;
	AdminCommunityPage adminLoginObj;
	Select s;
	Actions action;
	String searchvalue = "We got a banking licence";

	public ChangeCountryFlagTest() {
		super();
	}

	@BeforeClass
	public void setUp() throws IOException, AWTException {
		TestUtil.className = this.getClass().getName();
		initialization();
		homePage = new AdminHomePage();
		adminLoginObj = new AdminCommunityPage();
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 30);
	}

	@Test(description = "Switch to admin URL")
	public void Home() throws IOException, AWTException, InterruptedException {
		homePage.AdminURL();

		action.moveToElement(driver
				.findElement(By.xpath("//*[@id=\"___gatsby\"]/div/div[2]/div/div/div[2]/div[2]/div[4]/div[1]/span")))
				.build().perform();

		Thread.sleep(3000);

		driver.findElement(By.linkText("Community")).click();

		// Store all currently open tabs in tabs
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// Switch newly open Tab
		driver.switchTo().window(tabs.get(0));

		// Perform some operation on Newly open tab
		// Close newly open tab after performing some operations.
		driver.close();

		// Switch to old(Parent) tab.
		driver.switchTo().window(tabs.get(1));
		adminLoginObj.adminLoginLink();

	}

	@Test(description = "Switch to admin URL", dataProvider = "getRevoltadminloginData", dependsOnMethods = "Home")
	public void adminLogin(String Username, String pass) throws IOException, InterruptedException, AWTException {
		// driver.get(super.getProperties().getProperty("RevolutCommunityUrl"));
		adminLoginObj.adminUserPass(Username, pass);
		adminLoginObj.adminLoginButton();

	}

	@Test(description = "Verify Banking Licence", dependsOnMethods = "adminLogin")
	public void searchContent() throws IOException, AWTException, InterruptedException {
		driver.get(super.getProperties().getProperty("RevolutURL"));
		adminLoginObj.clickCountryFlagLink();
		Thread.sleep(3000);
		adminLoginObj.ClickUnitedStatesLink();
		String URL = driver.getCurrentUrl();
		Assert.assertEquals(URL, "https://www.revolut.com/change-country");

	}

}
