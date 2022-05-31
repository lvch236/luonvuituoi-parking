package com.example.luonvuituoi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.luonvuituoi.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animations

        // Animations
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        binding.logo.animation = topAnim
        binding.circle.animation = topAnim
        binding.clBackground.animation = scaleAnim

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2900)
    }
}