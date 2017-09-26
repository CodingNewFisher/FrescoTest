package cn.guoxiang.glideuse.bean;

import android.net.Uri;

/**
 * Created by guoxiang on 2017/9/22.
 */

public class Img {
    private Uri imageUri;

    public Img(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
