package com.song.example.wanandroid.app.main.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.song.example.wanandroid.app.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeBannerAdapter
 * @date on 3/29/2020 1:12 PM
 * @desc: TODO
 * @email No
 */
public class HomeBannerAdapter extends BannerAdapter<BannerVO, HomeBannerAdapter.BannerViewHolder> {

    public HomeBannerAdapter(List<BannerVO> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = (ImageView) getView(parent, R.layout.banner_image);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerVO data, int position, int size) {
        Glide.with(holder.itemView)
                .load(data.getImageUrl())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.imageView);
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view;
        }
    }

    /**
     * 将布局文件转成view，这里为了适配viewpager2中高宽必须为match_parent
     * @param parent
     * @param layoutId
     * @return
     */
    public static View getView(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        //这里判断高度和宽带是否都是match_parent
        if (params.height != -1 || params.width != -1) {
            params.height = -1;
            params.width = -1;
            view.setLayoutParams(params);
        }
        return view;
    }
}
