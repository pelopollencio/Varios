package selenium.pages.dezlearn;

import org.openqa.selenium.By;
import selenium.utils.UtilsSelenium;

public class IFramesNested {

	private UtilsSelenium driverUtils;

	public IFramesNested(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	private void goPage() {
		driverUtils.goToUrl(Urls.NESTED_IFRAMES.getUrl());
	}

	By locatorParentIframe = By.id("parent_iframe");
	By locatorParentButton = By.id("u_5_5");
	By locatorParentMsg = By.xpath("//h4[contains(text(), 'Parent')]/..//p[@id = 'processing']");
	By locatorNestedIframe = By.id("iframe1");
	By locatorNestedButton = By.id("u_5_6");
	By locatorNestedMsg = By.xpath("//h4[contains(text(), '2')]/..//p[@id = 'processing']");

	public void clickInNestedIframes() {
		goPage();

		driverUtils.switchToIFrame(locatorParentIframe);
		driverUtils.click(locatorParentButton);
		String parentMsg = driverUtils.getText(locatorParentMsg);
		driverUtils.setLogInfo("Message inside parent iframe => " + parentMsg);
		driverUtils.assertTrue(parentMsg.contains("iframe 1"), "Correct expected message");
		driverUtils.sleepSeconds(2);

		driverUtils.switchToIFrame(locatorNestedIframe);
		driverUtils.click(locatorNestedButton);
		String nestedtMsg = driverUtils.getText(locatorNestedMsg);
		driverUtils.setLogInfo("Message inside nested iframe => " + nestedtMsg);
		driverUtils.assertTrue(nestedtMsg.contains("iframe 2"), "Correct expected message");
		driverUtils.sleepSeconds(2);
	}
}
