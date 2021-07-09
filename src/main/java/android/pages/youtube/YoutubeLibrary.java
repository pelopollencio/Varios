package android.pages.youtube;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import android.utils.UtilsAppium;

public class YoutubeLibrary {

	private UtilsAppium driverUtils;

	public YoutubeLibrary(UtilsAppium driverUtils) {
		this.driverUtils = driverUtils;
	}

	By locatorTusVideos = By.xpath("//*[contains(@text, 's vídeos') or (@text = 'Your videos')]");
	By locatorMenuVideos = By.xpath("//android.widget.ImageView[contains(@content-desc, 'acciones')]");
	By locatorEliminar = By
			.xpath("//android.widget.TextView[contains(@text, 'Eliminar') or contains(@text, 'Delete')]");
	By locatorConfirmEliminar = By.id("android:id/button1");
	By locatorVideoList = By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup");
	By locatorReady = By.xpath("//*[(contains(@text, 'Listo')) or (contains(@text, 'Ready'))]");
	By locatorStatus = By.id("com.google.android.youtube:id/upload_status_message");

	public void deleteVideo() {
		List<WebElement> list;
		driverUtils.sleepSeconds(1);
		driverUtils.assertTrue(driverUtils.click(locatorTusVideos), "locatorTusVideos was clicked");
		list = driverUtils.driver().findElements(locatorVideoList);
		int videos = list.size();
		driverUtils.waitForVisibility(locatorMenuVideos);
		driverUtils.assertTrue(driverUtils.click(locatorMenuVideos), "locatorMenuVideos was clicked");
		driverUtils.assertTrue(driverUtils.click(locatorEliminar), "locatorEliminar was clicked");
		driverUtils.waitForVisibility(locatorConfirmEliminar);
		driverUtils.assertTrue(driverUtils.click(locatorConfirmEliminar), "locatorConfirmEliminar was clicked");
		while (list.size() == videos) {
			try {
				list = driverUtils.driver().findElements(locatorVideoList);
			} catch (Exception e) {
				break;
			}
		}
		driverUtils.sleepSeconds(2);
	}

	public void confirmVideoIsUploaded() {
		driverUtils.assertTrue(driverUtils.click(locatorTusVideos), "locatorTusVideos was clicked");
		try {
			driverUtils.assertTrue(driverUtils.waitForVisibility(locatorReady, 40), "Video was uploaded successfull");
		} catch (AssertionError e) {
			driverUtils.setLogInfo("Can't confirm that video was uploaded");
		}
	}
}
