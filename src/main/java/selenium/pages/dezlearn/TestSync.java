package selenium.pages.dezlearn;

import org.openqa.selenium.By;

import selenium.utils.UtilsSelenium;

public class TestSync {

	private UtilsSelenium driverUtils;

	public TestSync(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	private void goPage() {
		driverUtils.goToUrl(Urls.TEST_SYNC.getUrl());
	}

	By locatorFirstName = By.name("fname");
	By locatorLastName = By.xpath("//input[@name = 'lname'][1]");
	By locatorEmail = By.xpath("//input[@name = 'lname'][2]");
	By locatorButtonSubmit = By.xpath("//button[text() = 'Submit']");
	By locatorProcessOrder = By.xpath("//p[@id = 'processing' and contains(text(), 'Your order number is')]");

	public void fillOrder(String firstName, String lastName, String email) {

		goPage();

		driverUtils.waitForVisibility(locatorFirstName, 5);
		driverUtils.sendKeys(locatorFirstName, firstName);
		driverUtils.sendKeys(locatorLastName, lastName);
		driverUtils.sendKeys(locatorEmail, email);
		driverUtils.click(locatorButtonSubmit);

		driverUtils.assertTrue(driverUtils.waitForVisibility(locatorProcessOrder, 10), "The order number appears");
		String order = driverUtils.getText(locatorProcessOrder);
		driverUtils.setLogInfo("<mark>" + order + "</mark>");

	}

}
