package android.pages.youtube;

import org.openqa.selenium.By;

import android.utils.UtilsAppium;

public class YoutubeHome {

	private UtilsAppium driverUtils;

	public YoutubeHome(UtilsAppium driverUtils) {
		this.driverUtils = driverUtils;
	}

	By locatorCamera = By.xpath("//android.widget.Button[contains(@content-desc, 'Crear')]");
	By locatorVideoUpload = By.xpath("//android.view.ViewGroup[contains(@content-desc, 'Subir un v')]");
	By locatorBiblio = By
			.xpath("//android.widget.Button[(@content-desc = 'Biblioteca') or (@content-desc = 'Library')]");
	By locatorSearchIcon = By.id("com.google.android.youtube:id/menu_item_1");
	By locatorSearchBar = By.id("com.google.android.youtube:id/search_edit_text");
	By locatorSearchResult = By
			.xpath("//android.widget.ImageView[@resource-id = 'com.google.android.youtube:id/search_type_icon']");
	By locatorFirstVideoResult = By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[2]");
	By locatorVideoWindow = By.id("com.google.android.youtube:id/player_view");

	public void goCamera() {
		driverUtils.waitForVisibility(locatorCamera);
		driverUtils.assertTrue(driverUtils.click(locatorCamera), "locatorCamera was clicked");
		driverUtils.assertTrue(driverUtils.click(locatorVideoUpload), "locatorVideoUpload  was clicked");
	}

	public void goLibrary() {
		driverUtils.waitForVisibility(locatorBiblio);
		driverUtils.assertTrue(driverUtils.click(locatorBiblio), "locatorBiblio was clicked");
	}

	public void searchAndPlay(String toSearch) {

		driverUtils.assertTrue(driverUtils.click(locatorSearchIcon), "locatorSearchIcon was clicked");
		driverUtils.assertTrue(driverUtils.sendKeys(locatorSearchBar, toSearch),
				toSearch + " was sended to locatorSearchBar");
		driverUtils.assertTrue(driverUtils.click(locatorSearchResult), "locatorSearchResult  was clicked");
		try {
			driverUtils.assertTrue(driverUtils.click(locatorFirstVideoResult));
			driverUtils.sleepSeconds(15);
			driverUtils.setLogInfo("Video is playing");
		} catch (AssertionError e) {
			driverUtils.scrollTo(locatorFirstVideoResult);
			driverUtils.assertTrue(driverUtils.click(locatorFirstVideoResult), "locatorFirstVideoResult was clicked");
		}
	}

}
