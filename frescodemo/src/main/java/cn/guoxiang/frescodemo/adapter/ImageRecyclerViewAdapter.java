package cn.guoxiang.frescodemo.adapter;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import cn.guoxiang.frescodemo.ImageShowActivity;
import cn.guoxiang.frescodemo.MainActivity;
import cn.guoxiang.frescodemo.R;
import cn.guoxiang.frescodemo.bean.Img;
import cn.guoxiang.frescodemo.utility.ImageUtil;

/**
 * Created by guoxiang on 2017/9/22.
 */

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>{
    public static final String INTENT_EXTRA_ID_TAG = "img_uri";
    private List<Img> mImgList;

    public ImageRecyclerViewAdapter(List<Img> imgList){
        this.mImgList = imgList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ImageViewHolder viewHolder;
        view = View.inflate(parent.getContext(), R.layout.pic_item, null);
        parent.setTag(view);
        viewHolder = new ImageViewHolder(view);
        return viewHolder;
    }
    void showThumbImage(SimpleDraweeView view, Uri imageUrl, int width, int height){
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(imageUrl)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        view.setController(controller);
    }
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Img img = mImgList.get(position);
        showThumbImage(holder.imageView, img.getImageUri(), 200, 200);
//        holder.imageView.setImageURI(img.getImageUri());

//        ControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>() {
//            @Override
//            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                super.onFinalImageSet(id, imageInfo, animatable);
//                int width = imageInfo.getWidth();
//                int height = imageInfo.getHeight();
//                if (ImageUtil.isBigBitmap(width, height)){
//                    holder.imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                }else{
//                    holder.imageView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                }
//            }
//        };

//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setAutoPlayAnimations(true)
//                .setControllerListener(listener)
//                .build();
//
//        holder.imageView.setController(controller);

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
        SimpleDraweeView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
