package com.horizontal.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.horizontal.view.adapter.HorizontalRecyclerAdapter
import com.horizontal.view.widget.HorizontalScrollMoreLayout

class MainActivity : AppCompatActivity(), HorizontalScrollMoreLayout.OnStartActivityListener{

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLookMoreLayout: HorizontalScrollMoreLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayListOf(
                "http://a.hiphotos.baidu.com/zhidao/pic/item/5366d0160924ab1857f1cbae35fae6cd7a890b47.jpg",
                "http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg",
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=372372667,1126179944&fm=26&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3984473917,238095211&fm=26&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1091579268,2185400060&fm=26&gp=0.jpg"
        )


        mLookMoreLayout = findViewById(R.id.horizontal_scroll_more_layout)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = HorizontalRecyclerAdapter(this, data, Glide.with(this))

        mLookMoreLayout.setOnStartActivity(this)
    }

    override fun onStartActivity() {
        startActivity(Intent(this,SecondActivity::class.java))
    }

}