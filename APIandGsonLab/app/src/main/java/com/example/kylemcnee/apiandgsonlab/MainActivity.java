package com.example.kylemcnee.apiandgsonlab;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MarvelCharacter> mCharacters;
    private CharacterAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCharacters = new ArrayList<>();
        mAdapter = new CharacterAdapter(this, mCharacters);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        final EditText input = (EditText) findViewById(R.id.input);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = input.getText().toString();
                if (query.isEmpty()) {
                    input.setError("Please enter a query");
                    input.requestFocus();
                } else {
                    SearchAsyncTask task = new SearchAsyncTask();
                    task.execute(query);
                }
            }
        });
    }

    public class SearchAsyncTask extends AsyncTask<String, Void, MarvelSearchResult>{

        @Override
        protected MarvelSearchResult doInBackground(String... params) {
            String search;
            try {
                URL url = new URL(getUrlString(params[0]));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");

                connection.connect();
                InputStream inputStream = connection.getInputStream();
                search = getInputData(inputStream);

                Gson gson = new Gson();
                return gson.fromJson(search, MarvelSearchResult.class);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MarvelSearchResult marvelSearchResult) {
            super.onPostExecute(marvelSearchResult);

            if (marvelSearchResult != null) {
                mCharacters.clear();
                mCharacters.addAll(marvelSearchResult.getData().getResults());
                mAdapter.notifyDataSetChanged();
            }
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
            final String URL = "http://gateway.marvel.com/v1/public/characters?nameStartsWith=%s&ts=%d&apikey=%s&hash=%s";

            query = URLEncoder.encode(query, "UTF-8");
            long ts = System.currentTimeMillis();
            String publicKey = getString(R.string.marvel_public_key);
            String privateKey = getString(R.string.marvel_private_key);

            return String.format(URL, query, ts, publicKey, getMarvelMd5Digest(ts, privateKey, publicKey));
        }

        private String getMarvelMd5Digest(long timeStamp, String privateKey, String publicKey)
                throws UnsupportedEncodingException, NoSuchAlgorithmException {

            String inputString = timeStamp + privateKey + publicKey;
            byte[] inputBytes = inputString.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(inputBytes);

            // see http://stackoverflow.com/a/15485672
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String conversion = Integer.toString(b & 0xFF,16);
                while (conversion.length() < 2) {
                    conversion = "0" + conversion;
                }
                sb.append(conversion);
            }
            return sb.toString();
        }
    }
}
