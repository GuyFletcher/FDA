package com.fletcherhart.fdaapp;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Fletcher on 7/10/2017.
 */

public class Drug {

    private UUID mId;
    private String mGeneric;
    private String mBrand;
    private String mManufacturer;
    private String mUseage;
    private String mEPC;


    public Drug() {
        this(UUID.randomUUID());
    }

    public Drug(UUID id) {
        mId = id;

    }
    public UUID getId() {
        return mId;
    }

    public String getGenericName() {
        return mGeneric;
    }

    public void setGenericName(String generic) {
        mGeneric = generic;
    }

    public String getmBrand() {
        return mBrand;
    }

    public void setmBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public String getmManufacturer() {
        return mManufacturer;
    }

    public void setmManufacturer(String mManufacturer) {
        this.mManufacturer = mManufacturer;
    }

    public String getmUseage() {
        return mUseage;
    }

    public void setmUseage(String mUseage) {
        this.mUseage = mUseage;
    }

    public String getmEPC() {
        return mEPC;
    }

    public void setmEPC(String mEPC) {
        this.mEPC = mEPC;
    }
}
