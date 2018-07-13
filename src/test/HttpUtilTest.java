package test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.util.HttpUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

public class HttpUtilTest {

    @Test
    public void testHttpGet() throws IOException{
        // http://httpbin.org returns all of the information we send to it
        HttpURLConnection httpURLConnection = HttpUtil.grabConnection("http://httpbin.org/get",
                "param1", "value1",
                            "param2", "value2");

        InputStream inputStream = HttpUtil.get(httpURLConnection);

        // The response object should contain the http response of the server
        JsonNode response = HttpUtil.readJsonInputStream(inputStream);
        assertNotNull(response);

        // The json object's data should include the args we sent to the server
        assertAll("httpArgs",
                () -> assertEquals(response.get("args").get("param1").asText(), "value1"),
                () -> assertEquals(response.get("args").get("param2").asText(), "value2"));
    }

    @Test
    public void testHttpPost() throws IOException {
        // http://httpbin.org returns all of the information we send to it
        HttpURLConnection httpURLConnection = HttpUtil.grabConnection("http://httpbin.org/post",
                "param1", "value1",
                "param2", "value2");

        InputStream inputStream = HttpUtil.post(httpURLConnection, "{\"testObject\":{\"key1\":\"value1\",\"key2\":\"value2\"}}");

        // The response object should contain the http response of the server
        JsonNode response = HttpUtil.readJsonInputStream(inputStream);
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
}
