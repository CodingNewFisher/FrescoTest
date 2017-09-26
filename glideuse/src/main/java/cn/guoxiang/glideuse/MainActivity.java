package cn.guoxiang.glideuse;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.guoxiang.glideuse.adapter.ImageRecyclerViewAdapter;
import cn.guoxiang.glideuse.bean.Img;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List mPicList = new ArrayList<Img>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPic();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new ImageRecyclerViewAdapter(mPicList));
//        test();
//        loadFromNetwork();
//        loadFromLocal();
//          loadFromRes();
    }

    private void test() {
        final int[] pics = new int[]{
                R.raw.big_pic, R.raw.clear_pic, R.raw.height_pic,
                R.raw.mid_pic, R.raw.normal_gif, R.raw.normal,
                R.raw.test, R.raw.width_pic
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final int tmp :
                        pics) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Uri uri = Uri.parse("http://p.nanrenwo.net/uploads/allimg/160819/8448-160Q9154001.gif");
                            ImageView imageView = null;
                            Glide.with(MainActivity.this).load(tmp).
                                    diskCacheStrategy(DiskCacheStrategy.SOURCE).
                                    into(imageView);
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(MainActivity.this, ImageShowActivity.class);
                startActivity(intent);
            }
        }).start();
    }

    private void initPic() {
        final int[] pics = new int[]{
                R.raw.big_pic, R.raw.clear_pic, R.raw.height_pic,
                R.raw.mid_pic, R.raw.normal_gif, R.raw.normal,
                R.raw.test, R.raw.width_pic
        };
        for (int i = 0; i < 10; i++) {
            for (int j : pics){
                mPicList.add(new Img(Uri.parse("res:///" + j)));
            }
        }
    }
//
//    private void loadFromRes() {
//        Glide.with(this).load(Uri.parse("res///"+R.mipmap.ic_launcher)).into(imageView);
//    }
//
//    private void loadFromLocal() {
//        File file = new File(Environment.getDownloadCacheDirectory().getParent()+"meizi.jpg");
//        Glide.with(this).load(file).into(imageView);
//    }
//
//    private void loadFromNetwork() {
//        Uri imageUri = Uri.parse("http://jakewharton.github.io/butterknife/static/logo.png");
//        Glide.with(this).load(imageUri).into(imageView);
//    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pic);
    }
}
