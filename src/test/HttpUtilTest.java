package test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.com.github.joecourtneyw.util.HttpUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

public class HttpUtilTest {

    @Test
    public void testHttpGet() throws IOException {
        // http://httpbin.org returns all of the information we send to it
        HttpURLConnection httpURLConnection = HttpUtil.connect("http://httpbin.org/get",
                                                               "param1", "value1",
                                                               "param2", "value2");

        assertNull(HttpUtil.get(null)); //Should return null with null connection object
        assertNull(HttpUtil.get(httpURLConnection, "odd")); //Should return null with odd number of headers


        InputStream inputStream = HttpUtil.get(httpURLConnection);

        // The response object should contain the http response of the server in json format
        JsonNode response = HttpUtil.parse(inputStream);
        assertNotNull(response);

        // The json object's data should include the args we sent to the server
        assertAll("httpArgs",
                  () -> assertEquals(response.get("args").get("param1").asText(), "value1"),
                  () -> assertEquals(response.get("args").get("param2").asText(), "value2"));
    }

    @Test
    public void testHttpPost() throws IOException {
        // http://httpbin.org returns all of the information we send to it
        HttpURLConnection httpURLConnection = HttpUtil.connect("http://httpbin.org/post",
                                                               "param1", "value1",
                                                               "param2", "value2");

        assertNull(HttpUtil.post(null, "payload")); //Should return null with null connection object
        assertNull(HttpUtil.post(httpURLConnection, null)); //Should return null with null payload
        assertNull(HttpUtil.post(httpURLConnection, "payload", "odd")); //Should return null with odd number of headers


        InputStream inputStream = HttpUtil.post(httpURLConnection, "{\"testObject\":{\"key1\":\"value1\",\"key2\":\"value2\"}}");

        // The response object should contain the http response of the server in json format
        JsonNode response = HttpUtil.parse(inputStream);
        assertNotNull(response);

        // The json object's data should include the args we sent to the server
        assertAll("httpArgs",
                  () -> assertEquals(response.get("args").get("param1").asText(), "value1"),
                  () -> assertEquals(response.get("args").get("param2").asText(), "value2"));

        // Build an object mapper object to read the json data we sent to the server
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode testPayload = objectMapper.readTree(response.get("data").asText());

        // The JsonNode object should contain all the data we sent to it
        assertAll("httpPayload",
                  () -> assertTrue(testPayload.has("testObject")),
                  () -> assertEquals(testPayload.get("testObject").get("key1").asText(), "value1"),
                  () -> assertEquals(testPayload.get("testObject").get("key2").asText(), "value2"));
    }

    @Test
    public void testParse() {
        assertNull(HttpUtil.parse(null)); //Should return null if we pass null input stream

    }

    @Test
    public void testConnect() {
        assertNull(HttpUtil.connect("http://httpbin.org", "odd")); //Should return null with odd number of parameters
        assertNull(HttpUtil.connect(null)); //Should return null with null url
        assertNull(HttpUtil.connect("httsp:\\asd..")); //Should return null with malformed URL
        assertNull(HttpUtil.connect("")); //Should return null with empty url

        assertNotNull(HttpUtil.connect("http://httpbin.org", "key", "value")); //Shouldn't be null with proper arguments
        assertNotNull(HttpUtil.connect("http://httpbin.org", "key needs encoding",
                                       "value needs encoding")); //Shouldn't be null even with arguments requiring encoding
    }
}
