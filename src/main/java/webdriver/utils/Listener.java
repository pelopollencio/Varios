package webdriver.utils;

import static webdriver.utils.extentreport.ExtentManager.getExtentReports;
import static webdriver.utils.extentreport.ExtentTestManager.getTest;
import static webdriver.utils.extentreport.ExtentTestManager.startTest;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;

public class Listener implements ITestListener {

	private final Logger LOG = LoggerFactory.getLogger(Listener.class);
	private final boolean OPEN_REPORT = false;

	@Override
	public void onStart(ITestContext arg0) {
		String suiteName = arg0.getCurrentXmlTest().getSuite().getName();
		CommonUtils.suiteName = suiteName;

		LOG.info("*****************************************");
		LOG.info("SuiteName: " + suiteName);
		LOG.info("Start of execution suite => " + arg0.getName());
		LOG.info("List of tests involed:");
		List<ITestNGMethod> list = arg0.getSuite().getAllMethods();
		for (ITestNGMethod it : list)
			LOG.info("Test involved => " + it.getMethodName());
		LOG.info("Browser: " + arg0.getCurrentXmlTest().getParameter("browser"));
		LOG.info("*****************************************");
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		String testClass = arg0.getMethod().getRealClass().getSimpleName();
		String testName = arg0.getName();
		String reportTestName;

		LOG.info("*****************************************");
		LOG.info("Test Started => " + arg0.getName());
		LOG.info("*****************************************");
		if (CommonUtils.suiteName == null || CommonUtils.suiteName.isEmpty()
				|| CommonUtils.suiteName.equals("Default suite"))
			CommonUtils.suiteName = testClass;

		String browser = "Browser: " + (String) arg0.getAttribute("browser");
		reportTestName = "Selenium";
		if (browser.contains("ANDROID")) {
			reportTestName = "Appium";
			browser = browser.replace("Browser", "Device") + "<br>";
			browser += "Manufacturer: " + (String) arg0.getAttribute("manufacturer") + "<br>";
			browser += "Model: " + (String) arg0.getAttribute("model") + "<br>";
			browser += "Api lvl: " + (String) arg0.getAttribute("apiLvl") + "<br>";
		}
		reportTestName += ". Class: " + testClass + ". Test: " + testName;

		startTest(reportTestName, arg0.getMethod().getDescription());
		getTest().log(Status.INFO, "<h3 style='text-align:center; background-color: green;'>" + browser + "</h3>");
	}

	@Override
	public void onFinish(ITestContext context) {
		LOG.info("*****************************************");
		LOG.info("Test '" + context.getName() + "' finished");
		LOG.info("Test OK:\t\t" + context.getPassedTests().size());
		LOG.info("Test Skipped:\t" + context.getSkippedTests().size());
		LOG.info("Test Failed:\t" + context.getFailedTests().size());
		LOG.info("*****************************************");

		getExtentReports().flush();
		if (OPEN_REPORT) {
			try {
				Desktop desk = Desktop.getDesktop();
				File report = new File(CommonUtils.reportPath + "/extent-report.html");
				desk.open(report);
			} catch (IOException e) {
				LOG.error("Can't open report: " + e.getMessage());
			}
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		getTest().log(Status.PASS, "Test passed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getTest().log(Status.SKIP, "Test skipped");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		getTest().log(Status.FAIL, "Test failed");
	}

}
