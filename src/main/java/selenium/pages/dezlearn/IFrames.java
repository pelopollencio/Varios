package selenium.pages.dezlearn;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import selenium.utils.UtilsSelenium;

public class IFrames {

	private UtilsSelenium driverUtils;

	public IFrames(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	private void goPage() {
		driverUtils.goToUrl(Urls.IFRAME.getUrl());
	}

	By locatorIFrames = By.id("iframe1");

	public void clickInIframe() {
		goPage();
		List<WebElement> frames = driverUtils.getElements(locatorIFrames);
		for (int i = 0; i < frames.size(); i++) {
			By locatorButton = By.xpath("//h4[contains(text(), '" + (i + 1) + "')]/..//button");
			By locatorMsg = By.xpath("//h4[contains(text(), '" + (i + 1) + "')]/..//p");
			driverUtils.switchToIFrame(frames.get(i));
			driverUtils.click(locatorButton);

			driverUtils.waitForVisibility(locatorMsg, 3);
			String msgiframe = driverUtils.getText(locatorMsg);
			driverUtils.setLogInfo("Message inside iframe => " + msgiframe);
			driverUtils.assertTrue(msgiframe.contains("button from iframe " + (i + 1)), "Correct expected message");
			driverUtils.switchToDefaultContent();
			driverUtils.scrollDown();
			driverUtils.sleepSeconds(1);
		}
	}
}
