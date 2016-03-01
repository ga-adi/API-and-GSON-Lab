package com.boloutaredoubeni.pokedex;

import java.util.ArrayList;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class PokeDexJSON {
  private ArrayList<PokemonResource> mPokemon;

  public PokeDexJSON(ArrayList<PokemonResource> pokemon) {
    mPokemon = pokemon;
  }

  public ArrayList<PokemonResource> getPokemon() {
    return mPokemon;
  }

  public void setPokemon(ArrayList<PokemonResource> mPokemon) {
    this.mPokemon = mPokemon;
  }
}
