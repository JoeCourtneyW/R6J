package main.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringJoiner;

public class HttpUtil {

    //Used to parse InputStream into a JsonNode, can just use one global instance for all parsing
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param connection A connection object from the grabConnection() function
     * @param headers An alternating key value list of the connection headers and their values
     * @return An InputStream object that contains the data returned from the GET request
     */
    public static InputStream get(HttpURLConnection connection, String... headers) throws IOException{
        if(connection == null){
            return null;
        }

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            for(int i = 0; i < headers.length; i+=2){
                connection.setRequestProperty(headers[i], headers[i+1]);
            }

            connection.setDoOutput(true);
            connection.setUseCaches(false);


            return connection.getInputStream();
    }

    /**
     *
     * @param connection A connection object from the grabConnection() function
     * @param payload A string object representing the payload to POST to the open connection
     * @param headers An alternating key value list of the connection headers and their values
     * @return An InputStream object that contains the data returned from the POST request
     */
    public static InputStream post(HttpURLConnection connection, String payload, String... headers) throws IOException{
        if(connection == null)
            return null;

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            for(int i = 0; i < headers.length; i+=2){
                connection.setRequestProperty(headers[i], headers[i+1]);
            }

            connection.setDoOutput(true);
            connection.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(payload);
            wr.close();

            return connection.getInputStream();
    }

    /**
     *
     * @param url The url you want to connect to
     * @param parameterList An alternating key value list of the parameters for the connection
     * @return The final url string used in connections
     */
    private static String buildUrl(String url, String... parameterList){
        StringJoiner parametersStringJoiner = new StringJoiner("&");
        for(int i = 0; i < parameterList.length; i+=2){
            parametersStringJoiner.add(parameterList[i] + "=" + parameterList[i+1]);
        }
        return url + "?" + parametersStringJoiner.toString();
    }


    /**
     *
     * @param url URL String to be passed into buildUrl
     * @param parameters Parameter list to be passed into buildUrl
     * @return The HttpURLConnection object, or null if openConnection() fails
     */
    public static HttpURLConnection grabConnection(String url, String... parameters){
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(buildUrl(url, parameters)).openConnection();
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
        return connection;

    }

    /**
     *
     * @param input The InputStream object returned from a http request function
     * @return A JsonNode object with the inputStream data
     */
    public static JsonNode readJsonInputStream(InputStream input){
        if(input == null)
            return null;
        try {
            //Parses the JSON from the given input stream
            return objectMapper.readTree(input);
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
