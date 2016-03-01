package com.boloutaredoubeni.pokedex;

import java.util.ArrayList;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class PokemonJSONObject {

  private int mID;
  private String mName;
  private ArrayList<PokeType> mTypes;

  public PokemonJSONObject(int id, String name, ArrayList<PokeType> pokeTypes) {
    mID = id;
    mName = name;
    mTypes = pokeTypes;
  }

  public ArrayList<PokeType> getTypes() {
    return mTypes;
  }

  public void setTypes(ArrayList<PokeType> mTypes) {
    this.mTypes = mTypes;
  }

  public int getID() {
    return mID;
  }

  public void setID(int mID) {
    this.mID = mID;
  }

  public String getName() {
    return mName;
  }

  public void setName(String mName) {
    this.mName = mName;
  }
}
