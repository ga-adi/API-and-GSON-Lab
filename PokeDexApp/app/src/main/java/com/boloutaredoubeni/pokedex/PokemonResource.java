package com.boloutaredoubeni.pokedex;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class PokemonResource {
  private String mName;
  private String mURL;

  public PokemonResource(String name, String url) {
    mName = name;
    mURL = url;
  }

  public String getName() {
    return mName;
  }

  public void setName(String mName) {
    this.mName = mName;
  }

  public String getURL() {
    return mURL;
  }

  public void setURL(String mURL) {
    this.mURL = mURL;
  }
}
