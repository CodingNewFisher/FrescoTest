package cn.guoxiang.glideuse.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.guoxiang.glideuse.ImageShowActivity;
import cn.guoxiang.glideuse.MainActivity;
import cn.guoxiang.glideuse.R;
import cn.guoxiang.glideuse.bean.Img;

/**
 * Created by guoxiang on 2017/9/22.
 */

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>{
    public static final String INTENT_EXTRA_ID_TAG = "img_id";
    private List<Img> mImgList;
    private Context mContext;

    public ImageRecyclerViewAdapter(List<Img> imgList){
        this.mImgList = imgList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ImageViewHolder viewHolder;
        this.mContext = parent.getContext();
        view = View.inflate(this.mContext, R.layout.pic_item, null);
        parent.setTag(view);
        viewHolder = new ImageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Img img = mImgList.get(position);
        Glide.with(mContext).load(R.raw.normal)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ImageShowActivity.class);
                intent.putExtra(INTENT_EXTRA_ID_TAG, img.getImageUri());
                ((MainActivity)view.getContext())
                        .startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImgList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
