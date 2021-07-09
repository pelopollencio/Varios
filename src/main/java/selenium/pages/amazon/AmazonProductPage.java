package selenium.pages.amazon;

import org.openqa.selenium.By;

import selenium.utils.UtilsSelenium;

public class AmazonProductPage {

	private UtilsSelenium driverUtils;

	public AmazonProductPage(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	By locatorFirstResult = By.xpath("//div[contains(@class, 'results')]//h2/a");
	By locatorBuy = By.xpath("//input[@id='buy-now-button']");
	By locatorQuantity = By.id("quantity");

	public void browseProduct(String quantity) {
		boolean check = false;
		driverUtils.click(locatorFirstResult);
		if (driverUtils.waitForVisibility(locatorBuy)) {
			try {
				driverUtils.selectInSelectByText(locatorQuantity, quantity);
			} catch (Exception e) {
				driverUtils.setLogInfo("Can't select quantity");
			}
			for (int i = 0; i < 8; i++) {
				driverUtils.scrollDown();
				driverUtils.sleepSeconds(1);
			}
			driverUtils.sleepSeconds(2);
			check = true;
		}
		driverUtils.assertTrue(check, "Correct browse content");
	}

}
