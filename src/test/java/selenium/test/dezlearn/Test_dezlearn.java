package selenium.test.dezlearn;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.SeleniumCore;
import selenium.pages.dezlearn.IFrames;
import selenium.pages.dezlearn.IFramesNested;
import selenium.pages.dezlearn.JavaScriptAlerts;
import selenium.pages.dezlearn.MultiWindow;
import selenium.pages.dezlearn.TestSync;
import selenium.pages.dezlearn.WebTable;
import selenium.utils.UtilsSelenium;

public class Test_dezlearn extends SeleniumCore {

	private TestSync testSync;
	private MultiWindow multiWindows;
	private WebTable webTable;
	private JavaScriptAlerts alerts;
	private IFrames iframes;
	private IFramesNested iframesNested;
	private UtilsSelenium driverUtils;

	@BeforeClass
	@Parameters({ "browser" })
	public void setUp(@Optional("chrome") String browser, ITestContext context) {
		driverUtils = SeleniumCore.openDriver(browser, context);
		testSync = new TestSync(driverUtils);
		multiWindows = new MultiWindow(driverUtils);
		webTable = new WebTable(driverUtils);
		alerts = new JavaScriptAlerts(driverUtils);
		iframes = new IFrames(driverUtils);
		iframesNested = new IFramesNested(driverUtils);
	}

	@DataProvider(name = "users")
	public Object[][] users() {
		return new Object[][] { { "First Name Ej", "Last Name Ej", "correo@email.com" } };
	}

	@Test(description = "Fill all data and wait for order number", testName = "Fill data", dataProvider = "users")
	public void test01_fillDataAndWait(Object data[]) {
		String firstName = data[0].toString();
		String lastName = data[1].toString();
		String email = data[2].toString();
		testSync.fillOrder(firstName, lastName, email);
	}

	@Test(description = "Open and close multiple windows", testName = "Multi windows")
	public void test02_openAndCloseMultiWindows() {
		multiWindows.checkAndCloseNewWindows();
	}

	@Test(description = "Fill all web table data and update", testName = "Web table example")
	public void test03_fillWebTableAndUpdate() {
		webTable.fillAllDataTableAndUpdate();
	}

	@Test(description = "Manage all alerts types", testName = "Alerts")
	public void test04_workingWithAlerts() {
		driverUtils.setLogInfo("<mark>Simple alert</mark>");
		alerts.simpleAlert();

		driverUtils.setLogInfo("<mark>Confirm alert accept</mark>");
		alerts.confirmationManagement("accept");

		driverUtils.setLogInfo("<mark>Confirm alert cancel</mark>");
		alerts.confirmationManagement("cancel");

		driverUtils.setLogInfo("<mark>Promt alert accept</mark>");
		alerts.promptAlert("Mensaje de alerta", "accept");

		driverUtils.setLogInfo("<mark>Promt alert cancel</mark>");
		alerts.promptAlert("Mensaje de alerta", "cancel");
	}

	@Test(description = "Click on button inside iframes", testName = "Iframes example")
	public void test05_iframesExamples() {
		iframes.clickInIframe();
	}

	@Test(description = "Click on button inside nested iframes", testName = "NestedIframes example")
	public void test06_nestedIFramesExamples() {
		iframesNested.clickInNestedIframes();
	}

}
