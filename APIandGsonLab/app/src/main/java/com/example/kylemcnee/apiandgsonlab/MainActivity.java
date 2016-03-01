package com.example.kylemcnee.apiandgsonlab;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MarvelCharacter> mCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCharacters = new ArrayList<>();
    }

    public class SearchAsyncTask extends AsyncTask<String, Void, MarvelSearchResult>{

        @Override
        protected MarvelSearchResult doInBackground(String... params) {
            String search = "";
            try {
                URL url = new URL(getUrlString(params[0]));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");

                connection.connect();
                InputStream inputStream = connection.getInputStream();
                String data = getInputData(inputStream);


            } catch (Exception e) {
                e.printStackTrace();
            }


            Gson gson = new Gson();
            MarvelSearchResult result = gson.fromJson(search, MarvelSearchResult.class);

            return result;
        }

        @Override
        protected void onPostExecute(MarvelSearchResult marvelSearchResult) {
            super.onPostExecute(marvelSearchResult);

            mCharacters.clear();
            mCharacters.addAll(marvelSearchResult.getData().getResults());

            //TODO notify adapter

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
