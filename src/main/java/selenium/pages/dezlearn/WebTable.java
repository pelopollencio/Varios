package selenium.pages.dezlearn;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.utils.UtilsSelenium;

public class WebTable {

	private UtilsSelenium driverUtils;

	public WebTable(UtilsSelenium driverUtils) {
		this.driverUtils = driverUtils;
	}

	private void goPage() {
		driverUtils.goToUrl(Urls.WEB_TABLE.getUrl());
	}

	/**
	 * @return random 0 or 1
	 */
	private static int randomDecision(int bound) {
		return new Random().nextInt(bound);
	}

	By locatorRows = By.xpath("//table[@class = 'tg' ]//tr");
	By locatorName = By.xpath("./td[1]");
	By locatorEmail = By.xpath("./td[2]");
	By locatorStandard = By.xpath("./td[3]/input");
	By locatorPremium = By.xpath("./td[4]/input");
	By locatorType = By.xpath("./td[5]/select");
	By locatorComments = By.xpath("./td[6]/input");
	By locatorButtonUpdate = By.id("demo");
	By locatorUpdateSuccess = By.xpath("//p[@id = 'updateDiv' and contains(@style, 'display: block') ]");

	public void fillAllDataTableAndUpdate() {
		goPage();
		List<WebElement> rows = driverUtils.getElements(locatorRows);
		driverUtils.setLogInfo("Table have " + rows.size() + " editables rows");

		for (int i = 1; i < rows.size(); i++)
			fillData(rows.get(i));

		driverUtils.scrollDown();
		driverUtils.click(locatorButtonUpdate);
		driverUtils.assertTrue(driverUtils.waitForVisibility(locatorUpdateSuccess, 5), "Update correct");
		driverUtils.sleepSeconds(2);
	}

	private void fillData(WebElement actualRow) {
		String user = driverUtils.getText(actualRow.findElement(locatorName));
		String email = driverUtils.getText(actualRow.findElement(locatorEmail));
		String comment = "Comentario " + randomDecision(50);
		boolean standard = randomDecision(2) == 1;
		boolean premium = randomDecision(2) == 1;

		if (standard)
			driverUtils.click(actualRow.findElement(locatorStandard));

		if (premium)
			driverUtils.click(actualRow.findElement(locatorPremium));

		String typeSelected = driverUtils.selectInSelectByIndex(actualRow.findElement(locatorType), randomDecision(5));
		driverUtils.sendKeys(actualRow.findElement(locatorComments), comment);

		String rowInfo = "*************** ROW *******************<br>";
		rowInfo += "Usuario: " + user + "<br>";
		rowInfo += "Email: " + email + "<br>";
		rowInfo += "Standard: " + standard + "<br>";
		rowInfo += "Premium: " + premium + "<br>";
		rowInfo += "Type: " + typeSelected + "<br>";
		rowInfo += "Comments: " + comment + "<br>";
		rowInfo += "******************************************";
		driverUtils.setLogInfo(rowInfo);
		driverUtils.sleepSeconds(1);
	}

}
