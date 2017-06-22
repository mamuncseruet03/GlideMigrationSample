package com.ronnnnn.glidemigrationsample_glidev4.components.image_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.ronnnnn.glidemigrationsample_glidev4.R
import com.ronnnnn.glidemigrationsample_glidev4.extentions.bindView
import com.ronnnnn.glidemigrationsample_glidev4.models.*

/**
 * Created by kokushiseiya on 2017/06/17.
 */
class ImageListActivity : AppCompatActivity(), ImageListPresenter.ImageListView {

    companion object {
        private const val KEY_USAGE_TYPE = "key_usage_type"

        fun createIntent(context: Context, usageType: UsageType): Intent =
                Intent(context, ImageListActivity::class.java).apply {
                    putExtra(KEY_USAGE_TYPE, usageType)
                }
    }

    private var usageType: UsageType? = null

    private lateinit var presenter: ImageListPresenter
    private lateinit var adapter: ImageListRecyclerAdapter<Content>
    private lateinit var loadingProgress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)

        presenter = ImageListPresenter(this)

        usageType = intent.getSerializableExtra(KEY_USAGE_TYPE) as? UsageType

        var presenterAction: () -> Unit = {}
        usageType?.let {
            when (it.contentType) {
                ContentType.Photo -> {
                    adapter = ImageListRecyclerAdapter(this, it)
                    presenterAction = { presenter.getRecentPhotos() }
                }

                ContentType.Gif -> {
                    adapter = ImageListRecyclerAdapter(this, it)
                    presenterAction = { presenter.getTrendingGifs() }
                }
            }
        }

        bindView<RecyclerView>(R.id.image_recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ImageListActivity.adapter
        }
        loadingProgress = bindView(R.id.loading_progress_bar)

        presenterAction()
    }

    override fun toggleProgressVisibility(isVisible: Boolean) {
        loadingProgress.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun setPhotos(photoList: List<Photo>) {
        adapter.setItems(photoList)
    }

    override fun setGifs(gifList: List<Gif>) {
        adapter.setItems(gifList)
    }
}