package br.unicamp.ft.e215293.Winding.internet;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReceiveJSON extends AsyncTask<String, Void, String> {
    private JSONReceiver jsonReceiver;

    public ReceiveJSON(JSONReceiver jsonReceiver) {
        this.jsonReceiver = jsonReceiver;
    }

    public void sendJSON(JSONObject jsonObject) {
        if (jsonReceiver != null) {
            jsonReceiver.receiveJSON(jsonObject);
        }
    }

    @Override
    protected String doInBackground(String... args) {
        if (args.length == 0) {
            return "No Parameter";
        }

        try {

            URL url = new URL(args[0]);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream())
            );

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String args) {
        try {
            JSONObject jsonObject = new JSONObject(args);
            sendJSON(jsonObject);
            System.out.println("JSON Received");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
