package com.song.example.wanandroid.app.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.song.example.wanandroid.Global
import com.song.example.wanandroid.app.BR
import com.song.example.wanandroid.app.R
import com.song.example.wanandroid.app.databinding.ListitemHomeArticleBinding
import com.song.example.wanandroid.app.main.home.ArticlePagedAdapter
import com.song.example.wanandroid.app.main.home.HomeViewModel
import com.song.example.wanandroid.app.main.home.article.ArticleVO
import com.song.example.wanandroid.app.main.home.banner.BannerVO
import com.song.example.wanandroid.app.main.home.banner.HomeBannerAdapter
import com.song.example.wanandroid.app.main.home.homeKodeinModule
import com.song.example.wanandroid.base.ui.BaseActivity
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.instance


/**
 * @author song
 */
class WelcomeActivity : BaseActivity() {

//    override val kodein: Kodein = Kodein.lazy {
////        //extend(_parentKodein, copy = Copy.All)
////        import(homeKodeinModule)
////    }

    override fun activityCustomDiModule() = Kodein.Module("WelcomeActivity") {
        import(homeKodeinModule)
    }

    private lateinit var banner: Banner<*, *>
    private lateinit var refreshSwipe: SwipeRefreshLayout
    private lateinit var articleRecycleView: RecyclerView
    private var articleAdapter: ArticlePagedAdapter? = null

    companion object {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        initRecyclerView()

        val list = listOf(
                BannerVO(
                        title = "",
                        type = 0,
                        imagePath = "https://img.zcool.cn/community/013c7d5e27a174a80121651816e521.jpg",
                        url = ""
                )
        )

        val bannerAdapter = HomeBannerAdapter(list)
        banner = findViewById<View>(R.id.banner) as Banner<*, *>
        banner.run {
            adapter = bannerAdapter
            setOrientation(Banner.HORIZONTAL)
            indicator = CircleIndicator(this@WelcomeActivity)
        }
        banner.setOnBannerListener { data, position ->
            if (data is BannerVO) {
                Toast.makeText(Global.globalContext, "${data.title}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.banners.observe(this, Observer {
            bannerAdapter.setDatas(it)
        })
        viewModel.articles.observe(this, Observer {
            refreshSwipe.isRefreshing = false
            Log.e("HelloWorld", "$it")
        })

        viewModel.pagedArticles.observe(this, Observer {
            refreshSwipe.isRefreshing = false
            if (it.isNotEmpty()) {
                articleAdapter?.submitList(it)
            }
        })

        lifecycleScope.launchWhenResumed {
            viewModel.loadBanner()
        }
    }

    private fun initRecyclerView() {
        articleAdapter = ArticlePagedAdapter(
                ArticleVoDiffCallback(),
                BR.articleVo,
                viewDataBinding = { parent, viewType -> createViewDataBinding(parent, viewType)
                })

        refreshSwipe = findViewById(R.id.srl_refresh)
        refreshSwipe.setOnRefreshListener {
            viewModel.loadArticle()
        }

        articleRecycleView = (findViewById<RecyclerView>(R.id.rv_article)).apply {
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(this@WelcomeActivity, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = articleAdapter
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
        banner.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        banner.stop()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_welcome, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "action_search", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
