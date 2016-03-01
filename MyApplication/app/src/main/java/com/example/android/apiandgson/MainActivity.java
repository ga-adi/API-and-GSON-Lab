package com.example.android.apiandgson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String mPublicKey;
    String mHash;
    String mQuery;
    String mURL;
    ListView mListView;
    EditText mEditText;
    Button mButton;
    private MarvelAsyncTask mTask;
    ArrayAdapter<Item> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPublicKey = getString(R.string.public_key);
        mHash = getString(R.string.hash);
        mURL = "http://gateway.marvel.com/v1/public/characters?name=" + mQuery + "&ts=12345&apikey=" + mPublicKey + "&hash=" + mHash;
        mListView = (ListView) findViewById(R.id.listView);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuery = mEditText.getText().toString();
                mURL = "http://gateway.marvel.com/v1/public/characters?name=" + mQuery + "&ts=12345&apikey=" + mPublicKey + "&hash=" + mHash;

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (mTask != null && (mTask.getStatus() != AsyncTask.Status.FINISHED)) {
                        mTask.cancel(true);
                    }
                    mTask = new MarvelAsyncTask();
                    mTask.execute(mURL);
                } else {
                    Toast.makeText(MainActivity.this, "No network connection detected", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public class MarvelAsyncTask extends AsyncTask<String, Void, MarvelSearchResults> {

        //
        @Override
        protected MarvelSearchResults doInBackground(String... params) {
            String data = "";
            Log.d("text", params[0] + " ");
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                data = getInputData(inStream);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            MarvelSearchResults result = gson.fromJson(data, MarvelSearchResults.class);

            return result;
        }


        @Override
        protected void onPostExecute(MarvelSearchResults result) {
            super.onPostExecute(result);
            ArrayList<Item> blah = new ArrayList<Item>();
            if (result == null) {
                Toast.makeText(MainActivity.this, "Blah", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<MarvelDataResult> marvelDataResults = result.getData().getResults();
                for (MarvelDataResult r : marvelDataResults) {
//                    Log.d("COMIC RESULT", "name " + r.getName());
                    Comics c = r.getComics();
                    ArrayList<Item> items = c.getItems();
                    for (Item ind : items) {
//                        Log.d("COMIC", ind.getName());
                        blah.add(ind);
                    }

                }


            }
            mArrayAdapter = new ArrayAdapter<Item>(MainActivity.this, android.R.layout.simple_list_item_1, blah);
            mListView.setAdapter(mArrayAdapter);
            mArrayAdapter.notifyDataSetChanged();
        }

        private String getInputData(InputStream inStream) throws IOException {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

            String data = null;

            while ((data = reader.readLine()) != null) {
                builder.append(data);
            }
            reader.close();
            return builder.toString();
        }
    }
}