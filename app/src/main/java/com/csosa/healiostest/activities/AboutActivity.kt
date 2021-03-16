package com.csosa.healiostest.activities

import android.os.Bundle
import com.csosa.healiostest.R
import com.csosa.healiostest.base.BaseActivity
import com.csosa.healiostest.databinding.ActivityAboutBinding
import com.mikepenz.aboutlibraries.LibsBuilder

internal class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.aboutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = LibsBuilder()
            .withAboutIconShown(true)
            .supportFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment).commit()
    }
}
