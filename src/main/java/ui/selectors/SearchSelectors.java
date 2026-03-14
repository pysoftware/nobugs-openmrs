package ui.selectors;

public class SearchSelectors {
    public static final String SEARCH_QUERY = "**/search?query=";
    public static final String RESULT_HEADER = "h2[class*='__resultsHeader___']";
    public static final String SEARCH_RESULT_TO_INT = "el => parseInt(el.textContent.match(/\\d+/)[0])";
    public static final String PATIENT_BANNER = "div[role='banner']:has(a[href*='/patient/'])";
}
