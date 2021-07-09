package selenium.pages.telefonica;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.utils.UtilsSelenium;

public class TelefonicaHome {
	
	private UtilsSelenium driverUtils;

	public TelefonicaHome(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	By locatorCookies = By.id("onetrust-accept-btn-handler");

	private void firstOpen() {
		driverUtils.goToUrl(Urls.HOME.getUrl());
		driverUtils.click(locatorCookies);
	}

	By locatorTags = By.xpath("//a[contains(@href, '" + Urls.HOME.getUrl() + "')]");

	public void getAllTagsOnDomain() {
		firstOpen();
		List<WebElement> tags = driverUtils.getElements(locatorTags);
		driverUtils.assertTrue(tags.size() > 0, "Se han encontrado tags");
		driverUtils.setLogInfo("Tags list:");
		tags.forEach(tag -> {
			String msg = "href: " + tag.getAttribute("href");
			driverUtils.setLogInfo(msg);
		});
	}

}
