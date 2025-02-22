package com.depromeet.whatnow.ui.archive

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.depromeet.whatnow.base.BaseActivity
import com.depromeet.whatnow.ui.archive.detail.DetailActivity
import com.depromeet.whatnow.ui.model.Promise
import com.depromeet.whatnow.ui.promiseAdd.PromiseAddActivity
import com.depromeet.whatnow.ui.theme.WhatNowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchiveActivity : BaseActivity() {

    private val viewModel: ArchiveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WhatNowTheme {
                ArchiveScreen(
                    viewModel = viewModel,
                    onBack = ::finish,
                    navigateToDetail = ::startDetailActivity,
                    navigateToPromiseAdd = ::startPromiseAddActivity
                )
            }
        }
    }

    private fun startPromiseAddActivity() {
        val intent = Intent(this, PromiseAddActivity::class.java)
        startActivity(intent)
    }

    private fun startDetailActivity(promises: List<Promise>, selectedIndex: Int) =
        DetailActivity.startActivity(this, promises, selectedIndex)

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ArchiveActivity::class.java)
            context.startActivity(intent)
        }
    }
}
