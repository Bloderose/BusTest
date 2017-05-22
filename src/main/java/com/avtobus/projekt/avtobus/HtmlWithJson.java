package com.avtobus.projekt.avtobus;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




public class HtmlWithJson {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private static final String TAG = "HttpUrl";

    public JSONObject getJSONFromUrl(String url) {

        HttpURLConnection urlcon = null;
        BufferedReader reader = null;
        JSONObject jObj = null;
        Log.i(TAG,url);

        try{
            //url = url + "/"+"bavarski"; // pripnemo k urlju se ime postaje
            URL u = new URL(url);


            urlcon = (HttpURLConnection) u.openConnection();
            urlcon.setReadTimeout(READ_TIMEOUT);
            urlcon.setConnectTimeout(CONNECTION_TIMEOUT);
            urlcon.setRequestMethod("GET"); // pripni k urlju
            urlcon.setRequestProperty("Accept", "application/json");
            urlcon.connect();

            int status = urlcon.getResponseCode();
            Log.i(TAG,"response code: "+status);
            switch (status) {
                case 201:
                    break;
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    jObj = new JSONObject(sb.toString());
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlcon != null) {
                urlcon.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jObj;
    }

}
