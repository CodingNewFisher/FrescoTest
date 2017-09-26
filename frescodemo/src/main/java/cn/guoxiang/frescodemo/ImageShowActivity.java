package cn.guoxiang.frescodemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;

import java.io.InputStream;
import java.io.InputStreamReader;

import cn.guoxiang.frescodemo.adapter.ImageRecyclerViewAdapter;
import me.relex.photodraweeview.Attacher;
import me.relex.photodraweeview.PhotoDraweeView;

import static cn.guoxiang.frescodemo.utility.ImageUtil.isBigBitmap;

public class ImageShowActivity extends AppCompatActivity {
    private Uri mImageUri = null;
    private PhotoDraweeView mSelectedImage;
    private int height = 0;
    private int width = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        mSelectedImage = (PhotoDraweeView)findViewById(R.id.selected_image);
        mImageUri = getIntent().getParcelableExtra(ImageRecyclerViewAdapter.INTENT_EXTRA_ID_TAG);
        showGif(mSelectedImage, mImageUri);
//        mSelectedImage.setPhotoUri(mImageUri);
        //解决超大图显示问题
//        if (isBigBitmap(width, height)){
            mSelectedImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            Attacher attacher = mSelectedImage.getAttacher();
            attacher.setMaximumScale(50.f);
//        }

    }

    private void showGif(PhotoDraweeView photoDrawView, Uri uri) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .setOldController(photoDrawView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>(){
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        height = imageInfo.getHeight();
                        width = imageInfo.getWidth();
                    }
                })
                .build();

        photoDrawView.setController(controller);
    }

}
