package com.boloutaredoubeni.pokedex;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class PokemonResource {
  private String name;
  private String url;

  public PokemonResource(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String mName) {
    this.name = mName;
  }

  public String getURL() {
    return url;
  }

  public void setURL(String mURL) {
    this.url = mURL;
  }
}
