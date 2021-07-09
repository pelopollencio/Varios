package android.pages.youtube;

import org.openqa.selenium.By;

import android.utils.UtilsAppium;

public class YoutubeCamera {

	private UtilsAppium driverUtils;

	public YoutubeCamera(UtilsAppium driverUtils) {
		this.driverUtils = driverUtils;
	}

	By locatorRec = By.id("com.google.android.youtube:id/camera_overlay");
	By locatorShutter = By.id("com.android.camera2:id/shutter_button");
	By locatorDone = By.id("com.android.camera2:id/btn_done");
	By locatorFirstVideo = By.xpath("//android.support.v7.widget.RecyclerView/android.widget.FrameLayout[1]");
	By locatorSend = By.id("com.google.android.youtube:id/menu_upload_activity_done");
	By locatorUpload = By.xpath("//android.view.ViewGroup[@content-desc=\"Subir\"]");

	public void uploadVideo(boolean storageVideo) {
		if (storageVideo && driverUtils.click(locatorFirstVideo)) {
			driverUtils.assertTrue(storageVideo, "Storaged video was selected");
		} else {
			driverUtils.assertTrue(driverUtils.click(locatorRec), "locatorRec was clicked");
			driverUtils.assertTrue(driverUtils.click(locatorShutter), "locatorShutter couldn't be clicked\n");
			driverUtils.sleepSeconds(10);
			driverUtils.assertTrue(driverUtils.click(locatorShutter), "locatorShutter couldn't be clicked\n");
			driverUtils.assertTrue(driverUtils.click(locatorDone), "locatorDone was clicked");
		}

		driverUtils.sleepSeconds(2);
		driverUtils.assertTrue(driverUtils.click(locatorSend), "locatorSend was clicked");
		driverUtils.assertTrue(driverUtils.click(locatorUpload), "locatorUpload was clicked");
	}

}
