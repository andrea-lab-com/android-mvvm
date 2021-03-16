package com.csosa.healiostest.activities

import android.os.Bundle
import com.csosa.healiostest.base.BaseActivity
import com.csosa.healiostest.commons.startActivity
import com.csosa.healiostest.databinding.ActivitySettingsBinding

internal class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openAboutActivity() = startActivity<AboutActivity>()

}
