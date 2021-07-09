package selenium.test.amazon;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.SeleniumCore;
import selenium.pages.amazon.AmazonHome;
import selenium.pages.amazon.AmazonProductPage;
import selenium.utils.UtilsSelenium;

public class Test_amazon extends SeleniumCore {

	private AmazonHome home;
	private AmazonProductPage productPage;
	private UtilsSelenium driverUtils;

	@BeforeClass
	@Parameters({ "browser" })
	public void setUp(@Optional("chrome") String browser, ITestContext context) {
		driverUtils = SeleniumCore.openDriver(browser, context);
		home = new AmazonHome(driverUtils);
		productPage = new AmazonProductPage(driverUtils);
	}

	@Test(priority = 0, description = "Search a product, enter in the first result and select quantity", testName = "Search product")
	public void test_1_searchProduct() {

		home.searchProduct("raton");
		productPage.browseProduct("2");
	}

}
