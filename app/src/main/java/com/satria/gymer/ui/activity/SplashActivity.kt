package com.satria.gymer.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R
import com.satria.gymer.utils.SharedPrefUtils

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,
                if(SharedPrefUtils.getAuthToken(this@SplashActivity)!="")
                    MainActivity::class.java
                else
                    LoginActivity::class.java
            )
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 5000) // 5000ms = 5 detik
    }
}
