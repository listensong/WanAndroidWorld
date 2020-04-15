package com.song.example.wanandroid.app.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.song.example.wanandroid.Global
import com.song.example.wanandroid.app.BR
import com.song.example.wanandroid.app.databinding.FragmentHomeBinding
import com.song.example.wanandroid.app.databinding.ListitemHomeArticleBinding
import com.song.example.wanandroid.app.main.home.article.ArticleVO
import com.song.example.wanandroid.app.main.home.banner.BannerVO
import com.song.example.wanandroid.app.main.home.banner.HomeBannerAdapter
import com.song.example.wanandroid.base.ui.BaseFragment
import com.song.example.wanandroid.common.network.RequestStatus
import com.song.example.wanandroid.common.widget.LoadMoreScrollListener
import com.song.example.wanandroid.common.widget.MixedTypeAdapter
import com.song.example.wanandroid.util.WanLog
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeFragment
 * @date on 4/15/2020 18:50 PM
 * @author Listensong
 * @desc:
 * @email No
 */
class HomeFragment : BaseFragment() {

    override fun fragmentCustomDiModule() = Kodein.Module("HomeFragment") {
        import(homeKodeinModule)
    }

    private lateinit var binding: FragmentHomeBinding
    private var bannerAdapter: HomeBannerAdapter? = null
    private var articleAdapter: MixedTypeAdapter<ArticleVO>? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRecyclerView()
        initBanner()
        initViewModelBinding()
        return binding.root
    }

    private fun initRecyclerView() {
        articleAdapter = createAdapter(binding.rvArticle)
    }

    private fun createAdapter(recyclerView: RecyclerView?): MixedTypeAdapter<ArticleVO> {
        return MixedTypeAdapter<ArticleVO>(
                mutableListOf(),
                BR.articleVo,
                viewType = { 0 },
                spanSize = { 0 },
                viewDataBinding = { parent, viewType ->
                    createViewDataBinding(parent, viewType)
                }
        ).also { mixedAdapter ->
            recyclerView?.apply {
                hasFixedSize()
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                layoutManager = GridLayoutManager(context, 2).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return 2
                        }
                    }
                }
                itemAnimator = DefaultItemAnimator()
                adapter = mixedAdapter
                addOnScrollListener(loadMoreScrollListener)
            }
        }
    }

    private val loadMoreScrollListener: LoadMoreScrollListener =
            object : LoadMoreScrollListener(callback = { lastPos ->
        viewModel.loadNextPage(viewModel.viewModelScope,
                articleAdapter?.getItem(lastPos)?.curPage ?: -1)
    }) {}

    private fun initBanner() {
        val list = listOf(
                BannerVO(
                        title = "",
                        type = 0,
                        imagePath = "https://img.zcool.cn/community/013c7d5e27a174a80121651816e521.jpg",
                        url = ""
                )
        )

        bannerAdapter = HomeBannerAdapter(list)
        binding.banner.run {
            adapter = bannerAdapter!!
            setOrientation(Banner.HORIZONTAL)
            indicator = CircleIndicator(requireContext())
            setOnBannerListener { data, _ ->
                if (data is BannerVO) {
                    Toast.makeText(Global.globalContext, data.title, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViewModelBinding() {
        viewModel.banners.observe(viewLifecycleOwner, Observer {
            bannerAdapter?.setDatas(it)
        })

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            binding.srlRefresh.isRefreshing = false
            onRefresh(articleAdapter, articleAdapter?.getDataList(), it as MutableList<ArticleVO>?)
        })

        viewModel.requestState.observe(viewLifecycleOwner, Observer {
            binding.srlRefresh.isRefreshing = false
            if (it is RequestStatus.Complete && it.err != null) {
                WanLog.e(TAG, "RequestStatus " + it.err)
            }
        })

        lifecycleScope.launchWhenResumed {
            viewModel.loadBanner()
        }
    }

    private fun onRefresh(adapter: MixedTypeAdapter<ArticleVO>?,
                          oldList: List<ArticleVO>?,
                          newList: MutableList<ArticleVO>?) {
        binding.srlRefresh.isRefreshing = false
        if (adapter == null) {
            return
        }
        adapter.diffUpdate(lifecycleScope, newList, ArticleDiffCallback(oldList, newList))
    }

    @Suppress("UNUSED_PARAMETER")
    private fun createViewDataBinding(parent: ViewGroup, viewType: Int): ListitemHomeArticleBinding {
        return ListitemHomeArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        ).also { binding ->
            binding.itemClickedListener = View.OnClickListener {
                Toast.makeText(Global.globalContext, "${binding.articleVo?.title}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.banner.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.banner.stop()
    }

}
