package com.example.api_gson_lab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText mEditText;
    ListView mListView;
    ArrayList<String> mSearchList;
    ArrayAdapter<String> mAdapter;
    FloatingActionButton mFab;
    YelpAsyncTask mTask;
    String mSearchString = "https://api.yelp.com/v2/search/?location=New york, ny&term=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditText = (EditText)findViewById(R.id.searchQueryEditText);
        mListView = (ListView)findViewById(R.id.listview);

        mSearchList = new ArrayList<>();
        mAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mSearchList);
        mListView.setAdapter(mAdapter);

        YelpAPI credentials = new YelpAPI(YelpAPI.CONSUMER_KEY,YelpAPI.CONSUMER_SECRET,YelpAPI.TOKEN,YelpAPI.TOKEN_SECRET);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (mTask != null && (mTask.getStatus() != AsyncTask.Status.FINISHED)) {mTask.cancel(true);}
                    mTask = new YelpAsyncTask();
                    String search = mEditText.getText().toString();
                    String mUrl = mSearchString + search;
                    mTask.execute(mUrl);
                } else {Toast.makeText(MainActivity.this, "No network connection detected", Toast.LENGTH_SHORT).show();}

            }
        });
    }

    public class YelpAsyncTask extends AsyncTask<String, Void, YelpSearchResults> {

        @Override
        protected YelpSearchResults doInBackground(String... params) {
            String data ="";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                data = getInputData(inStream);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            //Gson object to convert JSON data into java class
            Gson gson = new Gson();
            YelpSearchResults result = gson.fromJson(data,YelpSearchResults.class);
            return result;
        }

        @Override
        protected void onPostExecute(YelpSearchResults result) {
            super.onPostExecute(result);

            mSearchList.clear();
            if(result==null){
                Toast.makeText(MainActivity.this, "No results!", Toast.LENGTH_SHORT).show();
            } else {
                for (YelpLocations item : result.getBusinesses()) {
                    mSearchList.add(item.getName());
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        String data = null;
        while ((data = reader.readLine()) != null){builder.append(data);}
        reader.close();
        return builder.toString();
    }
}
