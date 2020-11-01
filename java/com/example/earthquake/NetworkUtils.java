package com.example.earthquake;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static ArrayList<Row> fetchEarthquakeData(String requestURL)
    {
        URL url= createURL(requestURL);
        String jsonResponse=null;
        try
        {
            jsonResponse=makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        QueryUtils.setSampleJsonResponse(jsonResponse);
        ArrayList<Row> result=QueryUtils.extractEarthquakes();
        return result;
    }

    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse="";
        if(url==null) return jsonResponse;
        HttpURLConnection urlConnection=null;
        InputStream inputstream=null;
        try
        {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputstream=urlConnection.getInputStream();
                jsonResponse=readInputStream(inputstream);
            }
            else
            {
                Log.e(LOG_TAG,"Error response code :" + urlConnection.getResponseCode());
            }
        }
        catch(IOException e)
        {
            Log.e(LOG_TAG,"Error retriveing response", e);
        }
        finally
        {
            if(urlConnection!=null) urlConnection.disconnect();
            if (inputstream!=null) inputstream.close();
        }
        return jsonResponse;
    }
    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}

