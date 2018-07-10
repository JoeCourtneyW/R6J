package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import util.HttpUtil;

import java.time.Instant;
import java.util.Base64;

public class Auth {
    private static final String RAINBOW_SIX_APPID = "39baebad-39e5-4552-8c25-2c9b919064e2";
    private String key;
    private String sessionId;
    private Instant expiration; //TODO: Don't let the session expire, and don't let the user make a request on an expired token

    private Auth(String key, String sessionId, Instant expiration) {
        this.key = key;
        this.sessionId = sessionId;
        this.expiration = expiration;
    }

    public Auth(String email, String password) {
        String auth_token = getBasicToken(email, password);
        ObjectNode post_body = new ObjectMapper().createObjectNode().put("rememberMe", true);
        JsonNode response = HttpUtil.readJsonInputStream(HttpUtil.post(HttpUtil.grabConnection("https://connect.ubi.com/ubiservices/v2/profiles/sessions"), post_body.toString(),
                "Ubi-AppId", RAINBOW_SIX_APPID,
                "Authorization", "Basic " + auth_token));
        if(response.isNull()) {
            System.out.println("Authorization failed. Exiting.");
            System.exit(0);
        }
        System.out.println("Authorized successfully: Continuing");
        new Auth(response.get("content").get("ticket").asText(),
                response.get("content").get("sessionId").asText(),
                Instant.parse(response.get("content").get("expiration").asText()));
    }

    public void updateSession(){ //TODO: Run this if the session is expired when attempting to authenticate

    }

    public JsonNode authorizedGet(String url, String... parameters){
        if(expiration.isBefore(Instant.now())){
            new Auth("", "");
        }
        return HttpUtil.readJsonInputStream(HttpUtil.get(HttpUtil.grabConnection(url, parameters),
                "Ubi-AppId", RAINBOW_SIX_APPID,
                "Ubi-SessionId", sessionId,
                "Authorization", "Ubi_v1 t= " + key,
                "Connection", "keep-alive"));
    }
    public JsonNode get(String url, String... parameters){
        return HttpUtil.readJsonInputStream(HttpUtil.get(HttpUtil.grabConnection(url, parameters)));
    }
    private static String getBasicToken(String email, String password) {
        return Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }
}
