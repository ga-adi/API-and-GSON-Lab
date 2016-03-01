package com.boloutaredoubeni.pokedex;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  private ListView mListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    new PokedexTask().execute();

  }

  public class PokedexTask extends AsyncTask<Void, Void, ArrayList<PokemonResource>> {

    @Override
    protected ArrayList<PokemonResource> doInBackground(Void... params) {
      try {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://pokeapi.co/api/v2/pokemon/").build();
        Response response = client.newCall(request).execute();
        return processPokemon(response.body().string());
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PokemonResource> pokemonResources) {
      super.onPostExecute(pokemonResources);
      // TODO: put the returned stuff in the list view
    }

    private ArrayList<PokemonResource> processPokemon(String body) {
      Gson gson = new Gson();
      return gson.fromJson(body, PokeDexJSON.class).getPokemon();
    }
  }
}
