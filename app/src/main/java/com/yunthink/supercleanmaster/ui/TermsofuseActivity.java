package com.yunthink.supercleanmaster.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superbooster.cleanup.R;
import com.yunthink.supercleanmaster.base.BaseSwipeBackActivity;
import com.yunthink.supercleanmaster.utils.AppUtil;

import butterknife.InjectView;

public class TermsofuseActivity extends BaseSwipeBackActivity {


    @InjectView(R.id.t_subVersion)
    TextView subVersion;
    ActionBar ab;
    @InjectView(R.id.back_img2)
    ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsofuse);ab=getActionBar();
        ab.hide();

//        TextView tv = (TextView) findViewById(R.id.app_information);
//        Linkify.addLinks(tv, Linkify.ALL);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermsofuseActivity.this.finish();
            }
        });
        subVersion.setText("V"+ AppUtil.getVersion(mContext));

    }

}
