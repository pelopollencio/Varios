package selenium.pages.dezlearn;

import org.openqa.selenium.By;
import selenium.utils.UtilsSelenium;

public class JavaScriptAlerts {

	private UtilsSelenium driverUtils;

	public JavaScriptAlerts(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	private void goPage() {
		driverUtils.goToUrl(Urls.JS_ALERTS.getUrl());
	}

	By locatorSimpleAlert = By.id("s_alert1");
	By locatorConfirmationAlert = By.id("c_alert2");
	By locatorConfirmationMsg = By.xpath("//p[contains(@id, 'conf') and contains(@style, 'display: block')]");
	By locatorPromptAlert = By.id("p_alert3");

	public void simpleAlert() {
		goPage();

		String expectedMsg = "A test alert message from Dezlearn";
		driverUtils.click(locatorSimpleAlert);
		String alertText = driverUtils.alertGetText();
		driverUtils.setLogInfo("Alert text => " + alertText);
		driverUtils.alertAccept();
		driverUtils.assertTrue(expectedMsg.equals(alertText), "Alert message it's the expected");
		driverUtils.sleepSeconds(1);
	}

	/**
	 * @param option accept or cancel
	 */
	public void confirmationManagement(String option) {
		boolean originalAlert = driverUtils.isAlert();
		if (!originalAlert) {
			goPage();
			driverUtils.click(locatorConfirmationAlert);
		}

		String alertText = driverUtils.alertGetText();
		driverUtils.setLogInfo("Alert text => " + alertText);
		if ("accept".equals(option))
			driverUtils.alertAccept();
		else
			driverUtils.alertDismiss();

		driverUtils.assertTrue(driverUtils.waitForVisibility(locatorConfirmationMsg, 3),
				"Confirmation message was showed");
		String actionMsg = driverUtils.getText(locatorConfirmationMsg);
		driverUtils.setLogInfo("Action message => " + actionMsg);

		driverUtils.sleepSeconds(2);
	}

	public void promptAlert(String keys, String option) {
		goPage();

		driverUtils.click(locatorPromptAlert);
		driverUtils.alertSendKeys(keys);
		confirmationManagement(option);
	}

}
