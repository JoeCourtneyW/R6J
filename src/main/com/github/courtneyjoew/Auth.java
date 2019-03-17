package main.com.github.courtneyjoew;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.com.github.courtneyjoew.util.HttpUtil;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class Auth {

    private static final String RAINBOW_SIX_APPID = "39baebad-39e5-4552-8c25-2c9b919064e2";

    //The format returned by the server detailing the expiration time
    private static final SimpleDateFormat EXPIRATION_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");

    private String auth_token;


    private String key; //The ticket given by the server
    private String sessionId; //Our session id
    private Instant expiration; //When our session id expires

    private Auth(String auth_token){
        this.auth_token = auth_token;
        ObjectNode post_body = new ObjectMapper().createObjectNode().put("rememberMe", true);
        JsonNode response;
        try {
            response = HttpUtil.parse(
                    HttpUtil.post(HttpUtil.connect("https://connect.ubi.com/ubiservices/v2/profiles/sessions"),
                                  post_body.toString(),
                                  "Ubi-AppId", RAINBOW_SIX_APPID,
                                  "Authorization", "Basic " + auth_token));

            if (response == null)
                throw new IOException("Null response from Ubisoft authentication endpoint");

            Date expiration_date = EXPIRATION_FORMAT.parse(response.get("expiration").asText(), new ParsePosition(0));

            this.key = response.get("ticket").asText();
            this.sessionId = response.get("sessionId").asText();
            this.expiration = expiration_date.toInstant();
        } catch (IOException e) {
            System.out.println("Failed to retrieve session token with the given credentials");
        }
    }

    public Auth(String email, String password) {
        this(getBasicToken(email, password));
    }

    /**
     * Called when the session expires, creates new auth object and grabs session id from it
     */
    public void updateSession(int attempt) {
        if(attempt > 3) {
            //Ubisoft servers are obviously being stupid, so wait a little bit before sending another request
            return;
        }
        Auth new_auth = new Auth(auth_token);
        if(new_auth.key == null) {
            updateSession(attempt + 1);
            return;
        }
        this.key = new_auth.key;
        this.sessionId = new_auth.sessionId;
        this.expiration = new_auth.expiration;
    }

    /**
     * A simple HTTP get request with proper authentication headers attached
     *
     * @param url        The url to query
     * @param parameters The parameters to append to the url
     *
     * @return The JsonNode object returned by the url
     */
    JsonNode authorizedGet(String url, String... parameters) {

        if (expiration.isBefore(Instant.now())) {
            System.out.println("Session expired, attempting to reauthenticate");
            updateSession(1);
            return authorizedGet(url, parameters);
        }
        try {
            JsonNode response = HttpUtil.parse(HttpUtil.get(HttpUtil.connect(url, parameters),
                                               "Authorization", "Ubi_v1 t=" + key,
                                               "Ubi-AppId", RAINBOW_SIX_APPID,
                                               "Ubi-SessionId", sessionId,
                                               "Connection", "keep-alive"));
            if(response.has("httpCode") && response.get("httpCode").asInt() == 401) {
                System.out.println("Unauthorized request, attempting to reauthenticate");
                updateSession(1);
                return authorizedGet(url, parameters);
            }
            return response;
        } catch (IOException e) {
            System.out.println("Failed to process GET request to " + url);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * A simple HTTP get request, see HttpUtil get request
     *
     * @param url        The url to query
     * @param parameters The parameters to append to the url
     *
     * @return The JsonNode object returned by the url
     */
    JsonNode get(String url, String... parameters) {
        try {
            return HttpUtil.parse(HttpUtil.get(HttpUtil.connect(url, parameters)));
        } catch (IOException e) {
            System.out.println("Failed to process GET request to " + url);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Builds a basic auth token from the given email and password
     *
     * @param email    Your Ubisoft email
     * @param password Your Ubisoft password
     *
     * @return The encoded auth token
     */
    private static String getBasicToken(String email, String password) {
        return Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }
}
