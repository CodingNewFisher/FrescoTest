package cn.guoxiang.frescotest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.DrawableUtils;
import com.facebook.drawee.drawable.RoundedColorDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.IOException;
import java.io.InputStream;

import me.relex.photodraweeview.PhotoDraweeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_forward;
    private Button btn_next;
    private ImageView hugeImage;
    private InputStream inputStream;
    private PhotoDraweeView photoDraweeView;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        handlePhotoView();
        handleHugeImage();
    }

    private void handlePhotoView() {
        Uri uriImage = Uri.parse("http://img5.imgtn.bdimg.com/it/u=2107516106,1049442633&fm=27&gp=0.jpg");
 //       handleSimpleDraweeView();
        photoDraweeView.setPhotoUri(uriImage);
    }

    private void handleSimpleDraweeView() {
        //        simpleDraweeView.setLayoutParams(new WindowManager.LayoutParams());
//        if (simpleDraweeView != null)
//        simpleDraweeView.setImageURI(uriImage);
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder
                .newInstance(getResources())
                .setRetryImage(new RoundedColorDrawable(Color.RED))
                .setPlaceholderImage(R.mipmap.ic_launcher_round)
                .setFailureImage(R.drawable.failed)
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .setFadeDuration(5000)
                .build();
        ControllerListener<?> listener = new BaseControllerListener<>();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequestBuilder.newBuilderWithResourceId(R.drawable.failed).build())
                .setOldController(photoDraweeView.getController())
                .build();
        photoDraweeView.setController(controller);
        photoDraweeView.setHierarchy(hierarchy);
    }

    private void handleHugeImage() {
        try {
            inputStream = getResources().openRawResource(R.raw.qingming);
            showHugeImage(hugeImage, inputStream, new Rect(256*i, 256*i, 256*i+256, 256*i+256));
            Debug.getNativeHeapAllocatedSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        btn_forward = (Button) findViewById(R.id.btn_toleft);
        btn_next = (Button) findViewById(R.id.btn_toright);
        btn_forward.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        photoDraweeView = (PhotoDraweeView) findViewById(R.id.simple_view);
        hugeImage = (ImageView) findViewById(R.id.image_huge);
    }

    private void showHugeImage(ImageView hugeImage, InputStream inputStream, Rect rect) throws IOException {
        //获取图片的宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        int width = options.outWidth;
        int height = options.outHeight;
        //设置图片中心区域
        BitmapFactory.Options tmp_options = new BitmapFactory.Options();
        tmp_options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
        //图片真实大小Rect
        Rect realRect = new Rect(0, 0, width, height);
        //如果范围在图片内则加载
        if (realRect.contains(rect)){
            Bitmap bitmap_part = bitmapRegionDecoder.decodeRegion(
                    rect, tmp_options
            );
            hugeImage.setImageBitmap(bitmap_part);
        }

    }

    private void ScrollViewMatch() {
        ImageView hugeImage = (ImageView) findViewById(R.id.image_huge);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.failed);
        int bmp_W = bitmap.getWidth();
        int bmp_H = bitmap.getHeight();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int scr_w = displayMetrics.widthPixels;
//        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        ViewGroup.LayoutParams layoutParams = hugeImage.getLayoutParams();
        layoutParams.height = scr_w / bmp_W * bmp_H;
        hugeImage.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_toleft:
                forwardShow();
                break;
            case R.id.btn_toright:
                nextShow();
                break;

        }
    }
    void forwardShow(){
        i++;
        int tmp = i << 8;
        try {
            showHugeImage(hugeImage, inputStream, new Rect(tmp, tmp, 256 + tmp, 256 + tmp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void nextShow(){
        i--;
        int tmp = i << 8;
        try {
            showHugeImage(hugeImage, inputStream, new Rect(tmp - 256, tmp - 256, tmp, tmp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
