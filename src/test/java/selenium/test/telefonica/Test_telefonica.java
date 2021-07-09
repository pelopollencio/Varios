package selenium.test.telefonica;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.SeleniumCore;
import selenium.pages.telefonica.TelefonicaHome;
import selenium.utils.UtilsSelenium;

public class Test_telefonica extends SeleniumCore {

	private UtilsSelenium driverUtils;
	private TelefonicaHome home;

	@BeforeClass
	@Parameters({ "browser" })
	public void setUp(@Optional("chrome") String browser, ITestContext context) {
		driverUtils = SeleniumCore.openDriver(browser, context);
		home = new TelefonicaHome(driverUtils);
	}

	@Test(description = "Go to home page and get all tags 'a' with href in domain", testName = "Get hrefs on domain")
	public void test01_getHrefs() {
		home.getAllTagsOnDomain();
	}

}
