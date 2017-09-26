package cn.guoxiang.frescodemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.guoxiang.frescodemo.adapter.ImageRecyclerViewAdapter;
import cn.guoxiang.frescodemo.bean.Img;

public class MainActivity extends AppCompatActivity {

    private List<Img> imgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgList = new ArrayList<>();
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_pic);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ImageRecyclerViewAdapter(imgList));
    }

    private void initData() {
        int[] pics = new int[]{
                R.raw.big_pic, R.raw.clear_pic, R.raw.height_pic,
                R.raw.mid_pic, R.raw.normal_gif, R.raw.normal,
                R.raw.test, R.raw.width_pic
        };
        for (int i = 0; i < 10; i++) {
            for (int j : pics){
                imgList.add(new Img(Uri.parse("res:///" + j)));
            }
        }
    }
}
