package com.example.sampletvfocustest.launcher

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.sampletvfocustest.R
import com.example.sampletvfocustest.leanback.BasePageActivity
import com.example.sampletvfocustest.recycler.RecyclerApproachActivity
import kotlinx.android.synthetic.main.activity_launcher.*

class LauncherActivity : Activity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        iniUi()
    }

    private fun iniUi() {
        btLeanBackApproach.setOnClickListener(this)
        btRecyclerViewApproach.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btLeanBackApproach -> startActivity(BasePageActivity.getIntent(this))
            R.id.btRecyclerViewApproach -> startActivity(RecyclerApproachActivity.getIntent(this))
        }
    }
}