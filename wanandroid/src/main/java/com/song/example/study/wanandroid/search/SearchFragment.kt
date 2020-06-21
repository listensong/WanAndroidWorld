package com.song.example.study.wanandroid.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.song.example.study.base.ui.BaseFragment
import com.song.example.study.common.router.LinkSwitch
import com.song.example.study.common.widget.LinearItemSpaceDecoration
import com.song.example.study.extension.observer
import com.song.example.study.extension.setVisible
import com.song.example.study.util.DeviceUtil
import com.song.example.study.wanandroid.BR
import com.song.example.study.wanandroid.R
import com.song.example.study.wanandroid.databinding.WanFragmentSearchBinding
import com.song.example.study.wanandroid.databinding.WanListitemCommonArticleBinding
import com.song.example.study.wanandroid.main.home.article.ArticleVO
import com.song.example.study.wanandroid.search.word.HotWordVO
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * @package com.song.example.study.wanandroid.search
 * @fileName SearchFragment
 * @date on 6/7/2020 4:55 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class SearchFragment : BaseFragment() {

    override fun fragmentCustomDiModule() = Kodein.Module(TAG) {
        import(wanSearchKodeinModule)
    }

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: WanFragmentSearchBinding
    private var articleAdapter: BaseArticlePagedAdapter? = null
    private val viewModel: SearchViewModel by instance()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = WanFragmentSearchBinding.inflate(inflater, container, false)
        initToolbar()
        initViewModelBinding()
        initRecyclerView()
        return binding.root
    }

    private fun initToolbar() {
        binding.ivBack.setOnClickListener {
            finishActivity()
        }
        binding.ivSearch.setOnClickListener {
            handleSearchWord(binding.etSearchInput.text?.toString())
        }
        binding.etSearchInput.setOnEditorActionListener { _, actionId, _ ->
            if (isActionSearch(actionId)) {
                handleSearchWord(binding.etSearchInput.text?.toString())
                //返回true，保留软键盘。false，隐藏软键盘
                return@setOnEditorActionListener false
            }
            return@setOnEditorActionListener false
        }
    }

    private fun isActionSearch(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE) {
            return true
        }
        return false
    }

    private fun handleSearchWord(keyword: String?) {
        if (keyword == null || keyword.isEmpty()) {
            return
        }
        observeAndStartQueryKeyWord(keyword)
    }

    private fun initViewModelBinding() {
        observer(viewModel.getHotWord(), Observer {
            updatePopularHotWord(it)
        })
    }

    private fun initRecyclerView() {
        articleAdapter = createAdapter()
        binding.rvSearchArticle.apply {
            hasFixedSize()
            addItemDecoration(
                    LinearItemSpaceDecoration(DeviceUtil.dimensionPixelSizeInt(safeContext, R.dimen.wan_item_space_decoration))
            )
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = articleAdapter
        }
        binding.srlRefresh.setOnRefreshListener {
            viewModel.refreshSearchResult()
        }
    }

    private fun observeAndStartQueryKeyWord(keyword: String) {
        DeviceUtil.hideKeyboard(binding.etSearchInput)
        if (viewModel.livePagedList(keyword).hasObservers()) {
            viewModel.refreshSearchKeyword(keyword)
            return
        }

        observer(viewModel.livePagedList(keyword), Observer {
            binding.srlRefresh.isRefreshing = false
            articleAdapter?.submitList(it)
        })
    }

    private fun updatePopularHotWord(list: List<HotWordVO>) {
        if (list.isEmpty()) {
            return
        }
        binding.fblHotWord.run {
            removeAllViews()
            list.forEach { vo ->
                addView(getKeywordView(vo.name))
            }
        }
        binding.tvSearchTitle.setVisible()
    }


    private fun getKeywordView(keyword: String): View {
        return LayoutInflater.from(context).inflate(
                R.layout.wan_layout_item_hot_word, null, false
        ).apply {
            findViewById<TextView>(R.id.tv_hot_word_tag).text = keyword
            setOnClickListener {
                observeAndStartQueryKeyWord(keyword)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshHotWord()
    }

    private fun createAdapter(): BaseArticlePagedAdapter {
        return BaseArticlePagedAdapter(
                SearchArticleVoDiffCallback(),
                BR.articleVo,
                viewDataBinding = { parent, _ -> createArticleViewDataBinding(parent) }
        )
    }

    private class SearchArticleVoDiffCallback: DiffUtil.ItemCallback<ArticleVO>() {
        override fun areItemsTheSame(oldItem: ArticleVO, newItem: ArticleVO): Boolean {
            return oldItem.link == newItem.link || oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleVO, newItem: ArticleVO): Boolean {
            return oldItem == newItem
        }
    }

    private fun createArticleViewDataBinding(parent: ViewGroup): WanListitemCommonArticleBinding {
        return WanListitemCommonArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        ).also { binding ->
            binding.itemClickedListener = View.OnClickListener {
                LinkSwitch.goWebView(requireActivity(), binding.articleVo?.link)
            }
            binding.itemArticle.setPinnedVisible(false)
        }
    }
}