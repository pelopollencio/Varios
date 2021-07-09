package selenium.pages.amazon;

import org.openqa.selenium.By;

import selenium.utils.UtilsSelenium;

public class AmazonHome {

	private UtilsSelenium driverUtils;

	public AmazonHome(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	private void firstOpen() {
		driverUtils.goToUrl(Urls.HOME.getUrl());
	}

	By locatorSearchBar = By.id("twotabsearchtextbox");
	By locatorSubmit = By.xpath("//input[@type = 'submit']");
	By locatorCheckBoxPrime = By.xpath("//li[contains(@aria-label, 'Prime')]//a");

	public void searchProduct(String product) {
		firstOpen();

		driverUtils.sendKeys(locatorSearchBar, product);
		driverUtils.click(locatorSubmit);
		driverUtils.sleepMillis(500);
		if (driverUtils.waitForVisibility(locatorSubmit, 1))
			driverUtils.click(locatorSubmit);
		if (driverUtils.waitForVisibility(locatorCheckBoxPrime, 3))
			driverUtils.click(locatorCheckBoxPrime);
		driverUtils.sleepSeconds(2);
	}

}
