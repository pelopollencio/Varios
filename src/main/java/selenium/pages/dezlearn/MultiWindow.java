package selenium.pages.dezlearn;

import org.openqa.selenium.By;

import selenium.utils.UtilsSelenium;

public class MultiWindow {

	private UtilsSelenium driverUtils;

	public MultiWindow(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	private void goPage() {
		driverUtils.goToUrl(Urls.MULTIPLE_TABS.getUrl());
	}

	By locatorLaunchWindows = By.id("u_7_8");

	public void checkAndCloseNewWindows() {
		goPage();

		driverUtils.waitForVisibility(locatorLaunchWindows, 5);
		driverUtils.click(locatorLaunchWindows);

		driverUtils.sleepSeconds(2);

		while (driverUtils.getWindows().size() > 1) {
			driverUtils.switchWindowFromMain();
			driverUtils.setLogInfo("Switched to window => " + driverUtils.driver().getWindowHandle());
			String currentUrl = driverUtils.driver().getCurrentUrl();
			driverUtils.setLogInfo("New tab in url '" + currentUrl + "' will be closed in 1 seconds");
			driverUtils.sleepSeconds(1);
			driverUtils.driver().close();
			driverUtils.switchToMainWindow();
		}

	}

}
