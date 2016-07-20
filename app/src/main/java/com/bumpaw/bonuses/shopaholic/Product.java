package com.bumpaw.bonuses.shopaholic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 19/07/2016.
 */
public class Product implements Parcelable {
    private long id;
    private String name;
    private String price;
    private String imageUri;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.imageUri);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.price = in.readString();
        this.imageUri = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
