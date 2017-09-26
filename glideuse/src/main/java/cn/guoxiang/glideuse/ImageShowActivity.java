package cn.guoxiang.glideuse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.InputStream;

import cn.guoxiang.glideuse.adapter.ImageRecyclerViewAdapter;
import me.relex.photodraweeview.Attacher;
import me.relex.photodraweeview.PhotoDraweeView;
import uk.co.senab.photoview.PhotoView;

public class ImageShowActivity extends AppCompatActivity {
    private PhotoView mPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        initView();
        Uri uri = getIntent().getParcelableExtra(ImageRecyclerViewAdapter.INTENT_EXTRA_ID_TAG);
        Log.e("ImageError", uri.toString());
//        InputStream inputStream  = getResources().openRawResource(R.raw.width_pic);
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//        Glide.with(this).load(R.raw.width_pic)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .override(bitmap.getWidth(), bitmap.getHeight())
//                .into(mPhotoView);
        Glide.with(this).load(uri)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mPhotoView);
    }

    private void initView() {
        mPhotoView = (PhotoView) findViewById(R.id.photo_view);
        mPhotoView.setMaximumScale(50);
        mPhotoView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


}
