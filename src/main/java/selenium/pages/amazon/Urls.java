package selenium.pages.amazon;

public enum Urls {

	HOME("https://www.amazon.es")
	;

	private final String url;

	private Urls(String url) {
		this.url = url;
	}

	protected String getUrl() {
		return this.url;
	}
}
