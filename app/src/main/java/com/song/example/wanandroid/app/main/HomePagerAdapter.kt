package com.song.example.wanandroid.app.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.song.example.wanandroid.app.R
import java.lang.IndexOutOfBoundsException

/**
 * @package com.song.example.wanandroid.app.main
 * @fileName HomePagerAdapter
 * @date on 4/19/2020 6:12 PM
 * @author Listensong
 * @desc
 * @email No
 */
class HomePagerAdapter : FragmentPagerAdapter {

    private val fragmentList = arrayListOf<Fragment>()
    private val fragmentTitleList = arrayListOf<String>()

    constructor(fm: FragmentManager): this(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    }

    constructor(fm: FragmentManager, behavior: Int): super(fm, behavior) {
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun release() {
        fragmentList.clear()
        fragmentTitleList.clear()
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return try {
             fragmentTitleList[position]
        } catch (e: IndexOutOfBoundsException) {
            ""
        }
    }

    fun getTabView(context: Context, position: Int): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_home_tab, null, false)
        val textView: TextView = view.findViewById(R.id.tv_tab_title)
        textView.text = getPageTitle(position)
        textView.setTextColor(context.resources.getColor(R.color.colorGray))
        textView.textSize = 18f
        return view
    }
}