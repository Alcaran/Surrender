package data.api;

import org.json.JSONObject;
import sun.plugin.javascript.navig.Array;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

class HttpRequest {
    private String developmentApiKey = "RGAPI-956e01fc-fccb-4ce3-b9c1-52376f4d9c34";

    JSONObject sendGet(String apiPath, List<Object[]> parameters, boolean setFullPathRequest) throws Exception {

        StringBuilder url;
        if(setFullPathRequest)
            url = new StringBuilder(apiPath);
        else
            url = new StringBuilder("https://br1.api.riotgames.com" + apiPath + "?api_key=" + this.developmentApiKey);

        for (Object[] parameter : parameters) {
            if (parameter[1] instanceof int[])
            {
                for(int parameterObject : (int[]) parameter[1]) {
                    url.append("&").append(parameter[0]).append("=").append(parameterObject);
                }
            } else url.append("&").append(parameter[0]).append("=").append(parameter[1]);
        }

        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url.toString()).openConnection();

        httpClient.setRequestMethod("GET");

        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            String parsedResponse = response.toString();
            JSONObject obj;
            if(parsedResponse.charAt(0) == '[')
                obj = new JSONObject("{ array:" + parsedResponse + "}");
            else
                obj = new JSONObject(response.toString());

            return obj;
        }
    }

    void sendPost() throws Exception {

        // url is missing?
        //String url = "https://selfsolve.apple.com/wcResults.do";
        String url = "https://httpbin.org/post";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add request header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            System.out.println("{" + response.toString() + "}");

        }

    }

}