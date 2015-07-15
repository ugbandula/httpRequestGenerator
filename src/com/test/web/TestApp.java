package com.test.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created with IntelliJ IDEA.
 * User: Bandula2
 * Date: 9/12/14
 * Time: 7:07 PM
 */
public class TestApp {
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) {
        try {
            TestApp testApp = new TestApp();

            // Generate HTTP POST Request
            testApp.sendHTTPPostRequest();

            // Generate HTTP GET Request
            testApp.sendGetRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendHTTPPostRequest() {
        try {
            HttpPost httppost = new HttpPost("http://localhost:8081/UserServices/user-service/logout");

            List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            parameters.add(new BasicNameValuePair("username", "demouser"));
            parameters.add(new BasicNameValuePair("session", "69ad76c5-b0a7-4490-bb16-be69ee46cc5f"));

            httppost.setEntity(new UrlEncodedFormEntity(parameters));

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(httppost);
            HttpEntity resEntity = httpResponse.getEntity();

            // Get the HTTP Status Code
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            // Get the contents of the response
            InputStream input = resEntity.getContent();
            String responseBody = IOUtils.toString(input);
            input.close();

            // Print the response code and message body
            System.out.println("HTTP Status Code: " + statusCode);
            System.out.println(">> " + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HTTP GET request
    private void sendGetRequest() throws Exception {
        String url = "http://localhost:8081/UserServices/user-service/users/";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", "token=f47cdba8-d066-48e8-ad29-8c4222906bc7");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }
}
