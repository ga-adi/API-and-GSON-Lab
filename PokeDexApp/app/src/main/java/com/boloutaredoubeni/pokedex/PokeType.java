package com.boloutaredoubeni.pokedex;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class PokeType {

  private int mSlot;
  private String mType;

  public PokeType(int slot, String type) {
    mSlot = slot;
    mType = type;
  }

  public String getType() {
    return mType;
  }

  public void setType(String mType) {
    this.mType = mType;
  }

  public int getSlot() {
    return mSlot;
  }

  public void setSlot(int mSlot) {
    this.mSlot = mSlot;
  }
}
