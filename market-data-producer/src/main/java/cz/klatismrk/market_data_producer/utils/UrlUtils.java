package cz.klatismrk.market_data_producer.utils;

public class UrlUtils {

  private final static char QUESTION_MARK = '?';
  private final static char AMPERSAND = '&';

  public static String buildUrl(String apiUrl, String endpoint, String... queryParams) {
    if (queryParams.length == 0) {
      return apiUrl + endpoint;
    }
    StringBuilder url = new StringBuilder(apiUrl + endpoint + QUESTION_MARK);
    for (String param : queryParams) {
      url.append(param).append(AMPERSAND);
    }
    url.setLength(url.length() - 1); // Remove the trailing "&"
    return url.toString();
  }

}
