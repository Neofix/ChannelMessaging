package com.fernandes.damien.channelmessaging;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by damien on 20/01/17.
 */

public class Downloader extends AsyncTask<Long,Integer,String> {
    Context context;
    HashMap<String, String> param = new HashMap<>();
    String url;
    private int requestcode;
    ArrayList<OnDownloadListener> listeners = new ArrayList<>();


    public Downloader(Context aContext, String aUrl, HashMap<String, String> aParam, int requestcode) {
        this.param = aParam;
        this.url=aUrl;
        this.context = aContext;
        this.requestcode=requestcode;
    }

    @Override
    protected String doInBackground(Long... params) {
        return performPostCall(url, param);
    }

    @Override
    protected void onPostExecute(String result) {
        for(OnDownloadListener listener : listeners)
        {
            listener.OnDownloadComplete(result, requestcode);
        }

    }

    public String performPostCall(String requestURL, HashMap< String, String > postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getPostDataString(HashMap < String, String > params) throws
            UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry < String, String > entry: params.entrySet()) {
            if (first) first = false;
            else result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public void setOnDownloadComplete(OnDownloadListener listener)
    {
        listeners.add(listener);
    }

}
