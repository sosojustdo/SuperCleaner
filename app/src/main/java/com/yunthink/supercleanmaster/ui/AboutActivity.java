package com.yunthink.supercleanmaster.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superbooster.cleaning.R;
import com.yunthink.supercleanmaster.base.BaseSwipeBackActivity;

import butterknife.InjectView;

public class AboutActivity extends BaseSwipeBackActivity {

    @InjectView(R.id.subVersion)
    TextView subVersion;
    ActionBar ab;
    @InjectView(R.id.back_img2)
    ImageView back_img;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ab=getActionBar();
        ab.hide();

        TextView tv = (TextView) findViewById(R.id.app_information);
        Linkify.addLinks(tv, Linkify.ALL);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.finish();
            }
        });
    }

}
