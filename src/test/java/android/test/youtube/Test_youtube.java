package android.test.youtube;

import java.util.HashMap;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import android.core.AppiumNode;
import android.pages.youtube.YoutubeCamera;
import android.pages.youtube.YoutubeHome;
import android.pages.youtube.YoutubeLibrary;
import android.utils.UtilsAppium;

public class Test_youtube extends AppiumNode {

	private YoutubeHome home;
	private YoutubeCamera camera;
	private YoutubeLibrary library;
	private UtilsAppium driverUtils;
	private HashMap<String, String> capabilities;

	public Test_youtube() {
		capabilities = new HashMap<String, String>();
		capabilities.put("appPackage", "com.google.android.youtube");
		capabilities.put("appActivity", "com.google.android.apps.youtube.app.WatchWhileActivity");
		capabilities.put("noReset", "false");
//		capabilities.put("appWaitActivity", "");
	}

	@BeforeClass
	public void setUp(ITestContext context) {
		driverUtils = AppiumNode.startDriver(capabilities, context);
		home = new YoutubeHome(driverUtils);
		camera = new YoutubeCamera(driverUtils);
		library = new YoutubeLibrary(driverUtils);
	}

	@Test(priority = 0, description = "Upload a video", testName = "Upload video", enabled = true)
	public void test_1_uploadVideo() {
		boolean storageVideo = true;
		home.goCamera();
		camera.uploadVideo(storageVideo);
		home.goLibrary();
		library.confirmVideoIsUploaded();
	}

	@Test(priority = 1, description = "Delete the uploaded video", testName = "Delete video", enabled = true)
	public void test_2_deleteVideo() {
		home.goLibrary();
		library.deleteVideo();
	}

	@Test(priority = 2, description = "Search and play a video", testName = "Search video")
	public void test_3_searchVideo() {
		String toSearch = "fear of the dark";
		home.searchAndPlay(toSearch);
	}

}
