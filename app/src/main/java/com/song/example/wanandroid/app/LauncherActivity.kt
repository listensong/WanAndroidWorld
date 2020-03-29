package com.song.example.wanandroid.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.song.example.wanandroid.app.main.WelcomeActivity
import com.song.example.wanandroid.extend.setFullScreen

/**
 * @author song
 */
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setFullScreen()
        setContentView(R.layout.activity_launcher)

        val mainIntent = Intent(this, WelcomeActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(mainIntent)
            finish()
        }, 100)
    }
}
