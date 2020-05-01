package com.song.example.wanandroid.app.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.song.example.wanandroid.app.BR
import com.song.example.wanandroid.app.databinding.FragmentHomeBinding
import com.song.example.wanandroid.app.databinding.ListitemHomeArticleBinding
import com.song.example.wanandroid.app.databinding.ListitemHomeBannerBinding
import com.song.example.wanandroid.app.main.WelcomeViewModel
import com.song.example.wanandroid.app.main.home.article.ArticleVO
import com.song.example.wanandroid.app.main.home.banner.BannerVO
import com.song.example.wanandroid.app.main.home.banner.HomeBannerAdapter
import com.song.example.wanandroid.app.main.mainWelcomeKodeinModule
import com.song.example.wanandroid.base.ui.BaseFragment
import com.song.example.wanandroid.common.network.RequestStatus
import com.song.example.wanandroid.common.router.LinkSwitch
import com.song.example.wanandroid.common.widget.LoadMoreScrollListener
import com.song.example.wanandroid.common.widget.MixedTypeAdapter
import com.song.example.wanandroid.util.DeviceUtil
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
        import(mainWelcomeKodeinModule)
    }

    private lateinit var binding: FragmentHomeBinding
    private var bannerAdapter: HomeBannerAdapter? = null
    private var articleAdapter: MixedTypeAdapter<ArticleVO>? = null
    private var banner: Banner<*, *>? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by instance()
    private val attachViewModel: WelcomeViewModel by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initBanner()
        initRecyclerView()
        initViewModelBinding()
        return binding.root
    }

    private fun initRecyclerView() {
        articleAdapter = createAdapter(binding.rvArticle)
        binding.srlRefresh.setOnRefreshListener {
            refreshHomeContent()
        }
    }

    private fun refreshHomeContent() {
        viewModel.loadBanner()
        viewModel.loadArticle()
    }

    private fun createAdapter(recyclerView: RecyclerView?): MixedTypeAdapter<ArticleVO> {
        return MixedTypeAdapter<ArticleVO>(
                mutableListOf(),
                BR.articleVo,
                viewType = { it.itemType },
                spanSize = { 2 },
                viewDataBinding = { parent, viewType ->
                    createItemViewDataBinding(parent, viewType)
                }
        ).also { mixedAdapter ->
            recyclerView?.apply {
                hasFixedSize()
                //addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
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

    private fun createItemViewDataBinding(parent: ViewGroup,
                                          viewType: Int): ViewDataBinding {
        return when (viewType) {
            HomeConst.ITEM_TYPE_BANNER -> {
                createBannerViewDataBinding(parent, viewType)
            }
            HomeConst.ITEM_TYPE_ARTICLE -> {
                createArticleViewDataBinding(parent, viewType)
            }
            else -> {
                createArticleViewDataBinding(parent, viewType)
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
            WanLog.i(TAG, "requestState " + it)
            if (it is RequestStatus.Complete && it.err != null) {
                WanLog.d(TAG, "RequestStatus " + it.err)
            }
        })

        lifecycleScope.launchWhenResumed {
            refreshHomeContent()
        }

        attachViewModel.isDrawerOpen().observe(viewLifecycleOwner, Observer { opened ->
            if (opened) {
                banner?.stop()
            } else {
                banner?.start()
            }
        })
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
    private fun createArticleViewDataBinding(parent: ViewGroup, viewType: Int): ListitemHomeArticleBinding {
        return ListitemHomeArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        ).also { binding ->
            binding.itemClickedListener = View.OnClickListener {
                LinkSwitch.goWebView(requireActivity(), binding.articleVo?.link)
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun createBannerViewDataBinding(parent: ViewGroup, viewType: Int): ListitemHomeBannerBinding {
        return ListitemHomeBannerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        ).also { binding ->
            banner = binding.banner.run {
                setBannerRound(DeviceUtil.dp2PxFloat(15f))
                adapter = bannerAdapter!!
                setOrientation(Banner.HORIZONTAL)
                indicator = CircleIndicator(requireContext())
                setOnBannerListener { data, _ ->
                    if (data is BannerVO) {
                        LinkSwitch.goWebView(requireActivity(), data.url)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        banner?.start()
    }

    override fun onPause() {
        super.onPause()
        banner?.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        banner = null
    }
}
