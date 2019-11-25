package com.haolu.swipeandundo.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.haolu.swipeandundo.R
import com.haolu.swipeandundo.view.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}
