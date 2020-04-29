package com.horizontal.java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.horizontal.java.adapter.HorizontalRecyclerAdapter;
import com.horizontal.java.widget.HorizontalScrollMoreLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HorizontalScrollMoreLayout.OnStartActivityListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        HorizontalScrollMoreLayout horizontalMoreLayout = findViewById(R.id.horizontal_scroll_more_layout);
        horizontalMoreLayout.setOnStartActivity(this);

        List<String> data = new ArrayList<>(5);
        data.add("http://a.hiphotos.baidu.com/zhidao/pic/item/5366d0160924ab1857f1cbae35fae6cd7a890b47.jpg");
        data.add("http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
        data.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=372372667,1126179944&fm=26&gp=0.jpg");
        data.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3984473917,238095211&fm=26&gp=0.jpg");
        data.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1091579268,2185400060&fm=26&gp=0.jpg");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(new HorizontalRecyclerAdapter(data, Glide.with(this)));

    }

    @Override
    public void onStartActivity() {
        startActivity(new Intent(this,SecondActivity.class));
    }
}
