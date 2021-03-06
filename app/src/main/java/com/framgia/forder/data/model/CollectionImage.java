package com.framgia.forder.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.io.InputStream;

public class CollectionImage extends InputStream implements Parcelable {
    public static final Creator<CollectionImage> CREATOR = new Creator<CollectionImage>() {
        @Override
        public CollectionImage createFromParcel(Parcel in) {
            return new CollectionImage(in);
        }

        @Override
        public CollectionImage[] newArray(int size) {
            return new CollectionImage[size];
        }
    };

    @Expose
    @SerializedName(value = "image", alternate = { "standard" })
    private Image mImage;

    public CollectionImage(Image image) {
        mImage = image;
    }

    private CollectionImage(Parcel in) {
        mImage = in.readParcelable(Image.class.getClassLoader());
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mImage, flags);
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
