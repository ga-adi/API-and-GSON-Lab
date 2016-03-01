package com.boloutaredoubeni.pokedex;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private PokeDexArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.pokedex);
        mAdapter = new PokeDexArrayAdapter(this, new ArrayList<PokemonResource>());
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PokedexTask().execute();
    }

    public class PokedexTask extends AsyncTask<Void, Void, ArrayList<PokemonResource>> {

        @Override
        protected ArrayList<PokemonResource> doInBackground(Void... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://pokeapi.co/api/v2/pokemon/").build();
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                Type pokemonResourceType = new TypeToken<ArrayList<PokemonResource>>() {}.getType();
                return gson.fromJson(response.body().string(), pokemonResourceType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<PokemonResource> pokemonResources) {
            super.onPostExecute(pokemonResources);
            mAdapter.clear();
            mAdapter.addAll(pokemonResources);
            mAdapter.notifyDataSetChanged();
        }
    }
}
