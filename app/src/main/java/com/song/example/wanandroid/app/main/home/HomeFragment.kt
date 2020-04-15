package com.song.example.wanandroid.app.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.song.example.wanandroid.Global
import com.song.example.wanandroid.app.BR

import com.song.example.wanandroid.app.R
import com.song.example.wanandroid.app.databinding.FragmentHomeBinding
import com.song.example.wanandroid.app.databinding.ListitemHomeArticleBinding
import com.song.example.wanandroid.app.main.home.article.ArticleVO
import com.song.example.wanandroid.app.main.home.banner.BannerVO
import com.song.example.wanandroid.app.main.home.banner.HomeBannerAdapter
import com.song.example.wanandroid.base.ui.BaseFragment
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
    private var articleAdapter: ArticlePagedAdapter? = null

    companion object {
        fun newInstance() = HomeFragment()

        private class ArticleVoDiffCallback: DiffUtil.ItemCallback<ArticleVO>() {
            override fun areItemsTheSame(oldItem: ArticleVO, newItem: ArticleVO): Boolean {
                return oldItem.link == newItem.link || oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleVO, newItem: ArticleVO): Boolean {
                return oldItem == newItem
            }
        }
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
        articleAdapter = ArticlePagedAdapter(
                ArticleVoDiffCallback(),
                BR.articleVo,
                viewDataBinding = { parent, viewType -> createViewDataBinding(parent, viewType)
                })

        binding.srlRefresh.setOnRefreshListener {
            viewModel.loadArticle()
        }

        binding.rvArticle.apply {
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = articleAdapter
        }
    }

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
            WanLog.e("HelloWorld", "$it")
        })

        viewModel.pagedArticles.observe(viewLifecycleOwner, Observer {
            binding.srlRefresh.isRefreshing = false
            if (it.isNotEmpty()) {
                articleAdapter?.submitList(it)
            }
        })

        lifecycleScope.launchWhenResumed {
            viewModel.loadBanner()
        }
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
