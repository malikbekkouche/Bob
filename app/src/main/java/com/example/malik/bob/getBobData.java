package com.example.malik.bob;

import android.app.LauncherActivity;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by malik on 05-05-2017.
 */

public class getBobData extends AsyncTask<Void,Void, Chirp> {
    public final static String HOST = "http://emon.itu.dk:8079/api/query";


    @Override
    protected Chirp doInBackground(Void... params) {
        String resource = HOST ;

        InputStream inputStream = null;
        JsonReader jsonReader = null;
        try {
            URL url = new URL(resource);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String encoded = Base64.encodeToString(("percom"+":"+"BaRjY93BeMhfdTwjtGxb4Vf7qeGDnFDV").getBytes("UTF-8"),Base64.NO_WRAP);
            connection.setRequestProperty("Authorization", "Basic "+encoded);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();

            PrintWriter writer = new PrintWriter(outputStream);
            writer.write("select data in (now-10m, now) where Metadata/Extra/Group = \"06\"");
            writer.flush();
            writer.close();

            inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            jsonReader = new JsonReader(reader);
            return readJSON(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (jsonReader != null) {
                    jsonReader.close();
                }
            } catch (Exception e) {
                // Sorry guys
            }
        }
        return null;
    }

    private Chirp readJSON(JsonReader jsonReader) throws IOException {

        double temp = -1;
        int moist = -1;
        double light = -1;
        jsonReader.beginArray();

        //Reading temp
        jsonReader.beginObject();
        jsonReader.skipValue(); //Skip name "UUID"
        jsonReader.skipValue(); //Skip the UUID.
        jsonReader.skipValue(); //Skip name "Readings"
        jsonReader.beginArray();
        while (jsonReader.peek() != JsonToken.END_ARRAY) {
            jsonReader.beginArray();
            jsonReader.skipValue();
            temp = jsonReader.nextDouble();
            jsonReader.endArray();
        }
        jsonReader.endArray();
        jsonReader.endObject();

        //Reading moisture
        jsonReader.beginObject();
        jsonReader.skipValue(); //Skip name "UUID"
        jsonReader.skipValue(); //Skip the UUID.
        jsonReader.skipValue(); //Skip name "Readings"
        jsonReader.beginArray();
        while (jsonReader.peek() != JsonToken.END_ARRAY) {
            jsonReader.beginArray();
            jsonReader.skipValue();
            moist = jsonReader.nextInt();
            jsonReader.endArray();
        }
        jsonReader.endArray();
        jsonReader.endObject();

        //Reading light
        jsonReader.beginObject();
        jsonReader.skipValue(); //Skip name "UUID"
        jsonReader.skipValue(); //Skip the UUID.
        jsonReader.skipValue(); //Skip name "Readings"
        jsonReader.beginArray();
        while (jsonReader.peek() != JsonToken.END_ARRAY) {
            jsonReader.beginArray();
            jsonReader.skipValue();
            light = ((int) (jsonReader.nextDouble()*100)) / 100.0;
            jsonReader.endArray();
        }
        jsonReader.endArray();
        jsonReader.endObject();

        return new Chirp(temp, moist, light);
    }

    @Override
    protected void onPostExecute(Chirp result) {
        Log.d("temp",result.getTemp()+"");
        Log.d("moist", result.getMoist()+"");
        Log.d("light", result.getLight()+"");
    }
}
