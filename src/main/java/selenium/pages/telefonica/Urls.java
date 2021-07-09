package selenium.pages.telefonica;

public enum Urls {

	HOME("https://www.telefonica.es")
	;

	private final String url;

	private Urls(String url) {
		this.url = url;
	}

	protected String getUrl() {
		return this.url;
	}
}
