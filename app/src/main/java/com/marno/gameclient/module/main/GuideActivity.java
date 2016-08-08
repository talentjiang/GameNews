package com.marno.gameclient.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.marno.gameclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    public static final int WHAT = 0x11;
    @BindView(R.id.tv_count)
    TextView tvCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_count)
    public void onClick(View v) {
        finishActivity();
    }

    private void finishActivity() {
        this.finish();
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
    }
}
