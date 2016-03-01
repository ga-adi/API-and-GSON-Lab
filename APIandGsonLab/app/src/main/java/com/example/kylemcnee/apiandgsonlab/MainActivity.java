package com.example.kylemcnee.apiandgsonlab;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    String mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class SearchAsyncTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(getUrlString(params[0]));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                connection.connect();
                InputStream inputStream = connection.getInputStream();
                String data = getInputData(inputStream);

                //call Gson.fromJson(data)

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getInputData(InputStream inStream) throws IOException{
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

            String character;

            while ((character = reader.readLine()) != null){
                builder.append(character);
            }

            return builder.toString();
        }

        private String getUrlString(String query)
                throws UnsupportedEncodingException, NoSuchAlgorithmException {
            String mUrl = "http://gateway.marvel.com/v1/public/characters?nameStartsWith=%s&ts=%d&apikey=%s&hash=%s";
            long ts = System.currentTimeMillis();
            String publicKey = getString(R.string.marvel_public_key);
            String privateKey = getString(R.string.marvel_private_key);
            return String.format(mUrl, query, ts, publicKey, getMarvelMd5Digest(ts, privateKey, publicKey));
        }

        private String getMarvelMd5Digest(long timeStamp, String privateKey, String publicKey)
                throws UnsupportedEncodingException, NoSuchAlgorithmException {
            String inputString = timeStamp + privateKey + publicKey;
            byte[] inputBytes = inputString.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(inputBytes);
            return digest.toString();
        }

    }
}
