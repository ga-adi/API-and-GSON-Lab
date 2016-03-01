package com.example.wasabi.gsonapilab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    public static String mFrontUrl = "https://api.edmunds.com/api/vehicle/v2/";
    public static String mRearUrl = "/models?api_key=v2dfeb767u3zngchr3jprdtn&view=basic&fmt=json&category=";

    CarAsyncTask mTask;

    EditText makeEdit, bodyEdit;
    Button searchButton;
    ListView listView;
    ArrayAdapter<Car> adapter;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeEdit = (EditText)findViewById(R.id.manufacturer_edit);
        bodyEdit = (EditText)findViewById(R.id.body_type_edit);
        searchButton = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.listview);
        progressbar = (ProgressBar)findViewById(R.id.progress_bar);
        progressbar.setVisibility(View.GONE);
        adapter = new ArrayAdapter<Car>(MainActivity.this, android.R.layout.simple_list_item_1, new ArrayList<Car>());
        listView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makeInput = makeEdit.getText().toString();
                String bodyInput = bodyEdit.getText().toString();
                String url = mFrontUrl + makeInput + mRearUrl + bodyInput;

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (mTask != null && (mTask.getStatus() != AsyncTask.Status.FINISHED)) {
                        mTask.cancel(true);
                    }
                    mTask = new CarAsyncTask();
                    mTask.execute(url);
                } else {
                    Toast.makeText(MainActivity.this, "No network connection detected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class CarAsyncTask extends AsyncTask<String, Void, CarSearchResult>{
        @Override
        protected CarSearchResult doInBackground(String... params) {

            String data = "";

            try{
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                data = getInputData(inStream);
            }catch (Throwable e){
                e.printStackTrace();
            }

            Gson gson = new Gson();
            CarSearchResult result = gson.fromJson(data, CarSearchResult.class);

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(CarSearchResult carSearchResult) {
            super.onPostExecute(carSearchResult);
            progressbar.setVisibility(View.GONE);

            if(carSearchResult == null){
                Toast.makeText(MainActivity.this, "Results not found", Toast.LENGTH_SHORT).show();
            }else {
                adapter.clear();
                adapter.addAll(carSearchResult.getModels());
            }
        }
    }

    private String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

        String data = null;

        while ((data = reader.readLine()) != null){
            builder.append(data);
        }
        reader.close();
        return builder.toString();
    }
}
