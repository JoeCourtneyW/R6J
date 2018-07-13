package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.util.HttpUtil;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class Auth {
    private static final String RAINBOW_SIX_APPID = "39baebad-39e5-4552-8c25-2c9b919064e2";
    private static final SimpleDateFormat EXPIRATION_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");

    private String auth_token;


    private String key;
    private String sessionId;
    private Instant expiration;

    private Auth(String auth_token){
        this.auth_token = auth_token;
        ObjectNode post_body = new ObjectMapper().createObjectNode().put("rememberMe", true);
        JsonNode response;
        try {
            response = HttpUtil.readJsonInputStream(HttpUtil.post(HttpUtil.grabConnection("https://connect.ubi.com/ubiservices/v2/profiles/sessions"), post_body.toString(),
                    "Ubi-AppId", RAINBOW_SIX_APPID,
                    "Authorization", "Basic " + auth_token));

            if(response == null)
                throw new IOException();

            System.out.println("Authorized successfully");

            Date expiration_date = EXPIRATION_FORMAT.parse(response.get("expiration").asText(), new ParsePosition(0));

            this.key = response.get("ticket").asText();
            this.sessionId = response.get("sessionId").asText();
            this.expiration = expiration_date.toInstant();
        }catch(IOException e){
            System.out.println("Failed to retrieve session token with the given credentials");
        }
    }

    public Auth(String email, String password) {
        this(getBasicToken(email, password));
    }

    private void updateSession(){
        Auth new_auth = new Auth(auth_token);

        this.key = new_auth.key;
        this.sessionId = new_auth.sessionId;
        this.expiration = new_auth.expiration;
    }

    public JsonNode authorizedGet(String url, String... parameters){

        if(expiration.isBefore(Instant.now())){
            System.out.println("Session expired, attempting to authenticate again");
            updateSession();
        }
        try {
            return HttpUtil.readJsonInputStream(HttpUtil.get(HttpUtil.grabConnection(url, parameters),
                    "Authorization", "Ubi_v1 t=" + key,
                    "Ubi-AppId", RAINBOW_SIX_APPID,
                    "Ubi-SessionId", sessionId,
                    "Connection", "keep-alive"));
        } catch(IOException e){
            System.out.println("Failed to process GET request to " + url);
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode get(String url, String... parameters){
        try {
            return HttpUtil.readJsonInputStream(HttpUtil.get(HttpUtil.grabConnection(url, parameters)));
        }  catch(IOException e){
            System.out.println("Failed to process GET request to " + url);
            e.printStackTrace();
            return null;
        }
    }


    private static String getBasicToken(String email, String password) {
        return Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }
}
