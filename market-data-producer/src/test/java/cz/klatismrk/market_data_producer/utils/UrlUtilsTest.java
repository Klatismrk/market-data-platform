package cz.klatismrk.market_data_producer.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlUtilsTest {

  private final static String API_URL = "https://api.example.com";
  private final static String ENDPOINT = "/data";
  private final static String URL_WITHOUT_PARAMS = API_URL + ENDPOINT;
  private final static String PARAM_1 = "param1=value1";
  private final static String PARAM_2 = "param2=value2";
  private final static char QUESTION_MARK = '?';
  private final static char AMPERSAND = '&';

  @Test
  void testBuildUrlWithoutParams() {
    String expectedUrl = URL_WITHOUT_PARAMS;
    String actualUrl = UrlUtils.buildUrl(API_URL, ENDPOINT);
    assertUrlsEqueals(expectedUrl, actualUrl);
  }

  @Test
  void testBuildUrlWithOneParam() {
    String expectedUrl = URL_WITHOUT_PARAMS + QUESTION_MARK + PARAM_1;
    String actualUrl = UrlUtils.buildUrl(API_URL, ENDPOINT, PARAM_1);
    assertUrlsEqueals(expectedUrl, actualUrl);
  }

  @Test
  void testBuildUrlWithTwoParams() {
    String expectedUrl = URL_WITHOUT_PARAMS + QUESTION_MARK + PARAM_1 + AMPERSAND + PARAM_2;
    String actualUrl = UrlUtils.buildUrl(API_URL, ENDPOINT, PARAM_1, PARAM_2);
    assertUrlsEqueals(expectedUrl, actualUrl);
  }

  private static void assertUrlsEqueals(String expectedUrl, String actualUrl) {
    assertEquals(expectedUrl, actualUrl, "Expected URL: " + expectedUrl + ", but got: " + actualUrl);
  }

}
