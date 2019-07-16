package com.example.todo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MTodo implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("order")
    @Expose
    private String order;

    public MTodo(String title, String order) {
        this.title = title;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOrder() {
        return order;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.order);
    }

    protected MTodo(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.order = in.readString();
    }

    public static final Parcelable.Creator<MTodo> CREATOR = new Parcelable.Creator<MTodo>() {
        @Override
        public MTodo createFromParcel(Parcel source) {
            return new MTodo(source);
        }

        @Override
        public MTodo[] newArray(int size) {
            return new MTodo[size];
        }
    };
}
