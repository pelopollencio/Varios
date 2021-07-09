package selenium.pages.dezlearn;

public enum Urls {

	HOME("https://www.dezlearn.com/"),
	TEST_SYNC("https://www.dezlearn.com/test-sync-example/"),
	MULTIPLE_TABS("https://www.dezlearn.com/multiple-browser-windows/"),
	WEB_TABLE("https://www.dezlearn.com/webtable-example/"),
	JS_ALERTS("https://www.dezlearn.com/javascript-alerts/"),
	IFRAME("https://www.dezlearn.com/iframe-example/"),
	NESTED_IFRAMES("https://www.dezlearn.com/nested-iframes-example/")
	;

	private final String url;

	private Urls(String url) {
		this.url = url;
	}

	protected String getUrl() {
		return this.url;
	}
}
