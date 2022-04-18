package com.example.desafioscomunsandroidcustoms.ui.activity.bloquear_captura_tela

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioscomunsandroidcustoms.ui.activity.MainActivity
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.ActivityBlockScreenShotsBinding
import com.example.desafioscomunsandroidcustoms.util.preventScreenshotsAndRecentAppThumbnails
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class BlockScreenShotsActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityBlockScreenShotsBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        preventScreenshotsAndRecentAppThumbnails()

        binding.apply {
            supportActionBar?.hide()
            toolbarApp.title = "Block Screenshot"
            toolbarApp.setNavigationIcon(R.drawable.ic_back)
            toolbarApp.setOnClickListener{
                val intent = Intent (this@BlockScreenShotsActivity, MainActivity::class.java)
                this@BlockScreenShotsActivity.startActivity(intent)
            }
        }
    }
}