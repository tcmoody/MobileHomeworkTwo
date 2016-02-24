package com.lclark.homeworktwo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pokemon implements Parcelable{

    private String mName, mId, mSpeciesId, mHeight, mWeight;
    private String mBaseExp;
    private String mAttack;
    private String mDefense;
    private String mHp;
    private String mSpeed;
    private String mSpecAttack;
    private String mSpecDef;
    private boolean isUpdated;


    public Pokemon(String csvStr) {
        String[] split = csvStr.trim().split(",");

        mId = split[0];
        mName = split[1];
        mSpeciesId = split[2];
        mHeight = split[3];
        mWeight = split[4];
        isUpdated = false;
    }

    public String getImageUrl() {
        return "http://img.pokemondb.net/artwork/" + getName() + ".jpg";
    }


    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public String getSpeciesId() {
        return mSpeciesId;
    }

    public String getHeight() {
        return mHeight;
    }

    public String getWeight() {
        return mWeight;
    }

    public String getmBaseExp() {
        return mBaseExp;
    }

    public String getmAttack() {
        return mAttack;
    }

    public String getmDefense() {
        return mDefense;
    }

    public String getmHp() {
        return mHp;
    }

    public String getmSpeed() {
        return mSpeed;
    }

    public String getmSpecAttack() {
        return mSpecAttack;
    }

    public String getmSpecDef() {
        return mSpecDef;
    }

    public boolean isUpdated() {
        return getmHp() != null;
    }

    public void setmBaseExp(String mBaseExp) {
        this.mBaseExp = mBaseExp;
    }

    public void setmAttack(String mAttack) {
        this.mAttack = mAttack;
    }

    public void setmDefense(String mDefense) {
        this.mDefense = mDefense;
    }

    public void setmHp(String mHp) {
        this.mHp = mHp;
    }

    public void setmSpeed(String mSpeed) {
        this.mSpeed = mSpeed;
    }

    public void setmSpecAttack(String mSpecAttack) {
        this.mSpecAttack = mSpecAttack;
    }

    public void setmSpecDef(String mSpecDef) {
        this.mSpecDef = mSpecDef;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Pokemon && ((Pokemon) o).getId().equals(getId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mId);
        parcel.writeString(mSpeciesId);
        parcel.writeString(mHeight);
        parcel.writeString(mWeight);
        parcel.writeString(mHp);
        parcel.writeString(mAttack);
        parcel.writeString(mDefense);
        parcel.writeString(mSpeed);
        parcel.writeString(mSpecAttack);
        parcel.writeString(mSpecDef);
        parcel.writeString(mBaseExp);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel parcel) {
            return new Pokemon(parcel);
        }

        @Override
        public Pokemon[] newArray(int i) {
            return new Pokemon[i];
        }
    };

    public Pokemon(Parcel parcel){
        mName = parcel.readString();
        mId = parcel.readString();
        mSpeciesId = parcel.readString();
        mHeight = parcel.readString();
        mWeight = parcel.readString();
        mHp = parcel.readString();
        mAttack = parcel.readString();
        mDefense = parcel.readString();
        mSpeed = parcel.readString();
        mSpecAttack = parcel.readString();
        mSpecDef = parcel.readString();
        mBaseExp = parcel.readString();
    }
}
