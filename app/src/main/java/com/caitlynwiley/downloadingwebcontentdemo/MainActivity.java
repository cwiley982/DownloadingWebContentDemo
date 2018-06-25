package com.caitlynwiley.downloadingwebcontentdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = "";
        try {
            result = task.execute("http://www.blackle.com/").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Contents of URL", result);

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                //create a URL object with the url passed in
                url = new URL(urls[0]);
                //Open a url connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //create an input stream
                InputStream in = urlConnection.getInputStream();
                //create a reader to read from that stream
                InputStreamReader reader = new InputStreamReader(in);
                //read a character from the stream
                int data = reader.read();
                //loop until the end of the html file
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
