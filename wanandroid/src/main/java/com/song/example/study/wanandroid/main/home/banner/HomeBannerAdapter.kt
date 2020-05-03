package com.song.example.study.wanandroid.main.home.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.song.example.study.wanandroid.R
import com.song.example.study.wanandroid.main.home.banner.HomeBannerAdapter.BannerViewHolder
import com.youth.banner.adapter.BannerAdapter

/**
 * @author Listensong
 * @package com.song.example.study.wanandroid.main.home
 * @fileName HomeBannerAdapter
 * @date on 3/29/2020 1:12 PM
 * @desc: TODO
 * @email No
 */
class HomeBannerAdapter(
        dataList: List<BannerVO>?
) : BannerAdapter<BannerVO, BannerViewHolder>(dataList) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = getView(parent, R.layout.wan_layout_home_banner) as ImageView
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
            holder: BannerViewHolder, data: BannerVO, position: Int, size: Int) {
        Glide.with(holder.itemView)
                .load(data.imagePath)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(holder.imageView)
    }

    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }

    companion object {
        /**
         * 将布局文件转成view，这里为了适配viewpager2中高宽必须为match_parent
         * @param parent
         * @param layoutId
         * @return
         */
        fun getView(parent: ViewGroup, @LayoutRes layoutId: Int): View {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            val params = view.layoutParams
            //这里判断高度和宽带是否都是match_parent
            if (params.height != -1 || params.width != -1) {
                params.height = -1
                params.width = -1
                view.layoutParams = params
            }
            return view
        }
    }
}