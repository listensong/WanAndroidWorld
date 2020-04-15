package com.song.example.wanandroid.app.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.song.example.wanandroid.app.R
import com.song.example.wanandroid.app.main.home.HomeFragment
import com.song.example.wanandroid.app.main.home.homeKodeinModule
import com.song.example.wanandroid.base.ui.BaseActivity
import org.kodein.di.Kodein


/**
 * @author song
 */
class WelcomeActivity : BaseActivity() {

    override fun activityCustomDiModule() = Kodein.Module("WelcomeActivity") {
        import(homeKodeinModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow()
        }
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
