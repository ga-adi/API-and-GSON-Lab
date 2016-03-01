package com.example.perrycooperman.walmartsearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

    Button mButton;
    EditText mEditText;
    ListView mListView;
    ArrayList<MarvelObject> mArray;
    ArrayAdapter<MarvelObject> mAdapter;

    public String SEARCH_TEXT = "http://gateway.marvel.com/v1/public/characters?apikey=3451abb6bc184b8c6c910978be00baed&ts=999&hash=4fc0ce256a72a6a95f39eb4d95b64f99&nameStartsWith=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button)findViewById(R.id.button);
        mEditText = (EditText)findViewById(R.id.editText);
        mListView= (ListView)findViewById(R.id.listView);
        mArray = new ArrayList<MarvelObject>();

        mAdapter = new ArrayAdapter<MarvelObject>(MainActivity.this,android.R.layout.simple_list_item_1,mArray);
        mListView.setAdapter(mAdapter);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarvelAsyncClass asyncClass = new MarvelAsyncClass();
                asyncClass.execute(SEARCH_TEXT + mEditText.getText().toString());
            }
        });
    }

    public class MarvelAsyncClass extends AsyncTask<String,Void,MarvelParent>{

        @Override
        protected void onPreExecute() {
            mArray.clear();
            super.onPreExecute();
        }

        @Override
        protected MarvelParent doInBackground(String... params) {
            String data = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                data = getInputData(inStream);
            } catch(Throwable e){
                e.printStackTrace();
            }

            Gson gson = new Gson();
            MarvelParent parent = gson.fromJson(data, MarvelParent.class);
            return parent;
        }
        @Override
        protected void onPostExecute(MarvelParent marvelParent) {
            MarvelSearchArray marvelSearchArray = marvelParent.getData();
            mArray.addAll(marvelSearchArray.getResults());
            mAdapter.notifyDataSetChanged();
            super.onPostExecute(marvelParent);
        }
    }


    public String getInputData(InputStream inStream) throws IOException{
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));

        String data = null;

            while ((data = bufferedReader.readLine()) != null){
                builder.append(data);
            }
            bufferedReader.close();

        return builder.toString();
    }
}
