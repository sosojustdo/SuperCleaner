package com.yunthink.supercleanmaster.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.superbooster.cleaning.R;
import com.yunthink.supercleanmaster.adapter.RublishMemoryAdapter;
import com.yunthink.supercleanmaster.base.BaseSwipeBackActivity;
import com.yunthink.supercleanmaster.model.CacheListItem;
import com.yunthink.supercleanmaster.model.StorageSize;
import com.yunthink.supercleanmaster.service.CleanerService;
import com.yunthink.supercleanmaster.utils.StorageUtil;
import com.yunthink.supercleanmaster.utils.SystemBarTintManager;
import com.yunthink.supercleanmaster.utils.UIElementsHelper;
import com.yunthink.supercleanmaster.widget.textcounter.CounterView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public class RubbishCleanActivity extends BaseSwipeBackActivity implements OnDismissCallback, CleanerService.OnActionListener {

    ActionBar ab;
    Resources res;
    private CleanerService mCleanerService;
    private boolean mAlreadyScanned = false;
    private boolean mAlreadyCleaned = false;
    @InjectView(R.id.listview)
    ListView mListView;

    @InjectView(R.id.header)
    RelativeLayout header;


    @InjectView(R.id.textCounter)
    CounterView textCounter;
    @InjectView(R.id.sufix)
    TextView sufix;

    @InjectView(R.id.pb_progressbar)
    ProgressBar pb_view;
    @InjectView(R.id.pb_text)
    TextView pb_text;
    @InjectView(R.id.back_img)
    ImageView back_img;
    @InjectView(R.id.back_img2)
    ImageView back_img2;
    @InjectView(R.id.no_cache_rl)
    RelativeLayout noCache;
// 飞机组件
@InjectView(R.id.clean_plan)
ImageView clean_Plan;

    @InjectView(R.id.clean_success)
    ImageView clean_Success;
    @InjectView(R.id.clean_star2)
    ImageView clean_Star2;

    RublishMemoryAdapter rublishMemoryAdapter;

    List<CacheListItem> mCacheListItem = new ArrayList<>();

    @InjectView(R.id.bottom_lin)
    LinearLayout bottom_lin;

    @InjectView(R.id.clear_button)
    Button clearButton;

    private boolean  pb_status ;
//    spin_kit
    @InjectView(R.id.spin_kit)
    ProgressBar spin_Kit;

    private long cache_Nub;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCleanerService = ((CleanerService.CleanerServiceBinder) service).getService();
            mCleanerService.setOnActionListener(RubbishCleanActivity.this);

            //  updateStorageUsage();

            if (!mCleanerService.isScanning() && !mAlreadyScanned) {
                mCleanerService.scanCache();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCleanerService.setOnActionListener(null);
            mCleanerService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rublish_clean);
        ab=getActionBar();
        ab.hide();
        res = getResources();

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RubbishCleanActivity.this.finish();
            }
        });
        back_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RubbishCleanActivity.this.finish();
            }
        });

        rublishMemoryAdapter = new RublishMemoryAdapter(mContext, mCacheListItem);
        mListView.setAdapter(rublishMemoryAdapter);
        //点击后可以进入应用详情界面（目前不使用）
        mListView.setOnItemClickListener(rublishMemoryAdapter);
//        mListView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnType.FOOTER, null, 0, bottom_lin, footerHeight));
        bindService(new Intent(mContext, CleanerService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {

    }

    @Override
    public void onScanStarted(Context context) {
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max,long mSize) {
        if(!pb_status){
            pb_view.setMax( max);
            pb_status = true;
        }
        Log.e("keke",""+mSize);
        StorageSize mStorageSize = StorageUtil.convertStorageSize(mSize);
        float ft   =  mStorageSize.value;
        int   scale  =   2;//设置位数
        int   roundingMode  =  4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal   bd  =   new BigDecimal((double)ft);
        bd   =  bd.setScale(scale,roundingMode);
        ft   =  bd.floatValue();

        sufix.setText(mStorageSize.suffix);
        textCounter.setText(""+ft+"");
        pb_text.setText(getString(R.string.scanning_m_of_n, current, max));
        pb_view .setProgress(current);
        if(current >= max){
            pb_text.setText(RubbishCleanActivity.this.getString(R.string.scan_success)+max+RubbishCleanActivity.this.getString(R.string.scan_success2));
            spin_Kit.setVisibility(View.GONE) ;
            bottom_lin.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onScanCompleted(Context context, List<CacheListItem> apps) {
//        showProgressBar(false);
        mCacheListItem.clear();
        mCacheListItem.addAll(apps);
        rublishMemoryAdapter.notifyDataSetChanged();
        header.setVisibility(View.GONE);
        noCache.setVisibility(View.GONE);
        if (apps.size() > 0) {//当缓存容量大于0时
            header.setVisibility(View.VISIBLE);
            bottom_lin.setVisibility(View.VISIBLE);
/*
            long medMemory = mCleanerService != null ? mCleanerService.getCacheSize() : 0;

            StorageSize mStorageSize = StorageUtil.convertStorageSize(medMemory);
            textCounter.setAutoFormat(false);
            textCounter.setFormatter(new DecimalFormatter());
            textCounter.setAutoStart(false);
            textCounter.setStartValue(0f);
            textCounter.setEndValue(mStorageSize.value);
            textCounter.setIncrement(5f); // the amount the number increments at each time interval
            textCounter.setTimeInterval(50); // the time interval (ms) at which the text changes
            sufix.setText(mStorageSize.suffix);

            textCounter.start();*/
        } else {//无缓存时的操作
            header.setVisibility(View.GONE);
            Intent intent = new Intent();
            intent.putExtra("title", RubbishCleanActivity.this.getString(R.string.cache_clean));
            intent.putExtra("content", RubbishCleanActivity.this.getString(R.string.phone_clean));
            intent.setClass(RubbishCleanActivity.this, MainActivity.class);
            startActivity(intent);
            RubbishCleanActivity.this.finish();
            bottom_lin.setVisibility(View.GONE);
        }

        if (!mAlreadyScanned) {
            mAlreadyScanned = true;

        }


    }

    @Override
    public void onCleanStarted(Context context) {

    }

    @Override
    public void onCleanCompleted(Context context, long cacheSize) {
        dismissDialogLoading();
//        Toast.makeText(context, context.getString(R.string.cleaned, Formatter.formatShortFileSize(
//                mContext, cacheSize)), Toast.LENGTH_LONG).show();
        cache_Nub = cacheSize;
        header.setVisibility(View.GONE);
        bottom_lin.setVisibility(View.GONE);
        mCacheListItem.clear();
        rublishMemoryAdapter.notifyDataSetChanged();

    }


    /**
     * Apply KitKat specific translucency.
     */
    private void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            // mTintManager.setTintColor(0xF00099CC);

            mTintManager.setTintDrawable(UIElementsHelper
                    .getGeneralActionBarBackground(this));

            getActionBar().setBackgroundDrawable(
                    UIElementsHelper.getGeneralActionBarBackground(this));

        }

    }


    @OnClick(R.id.clear_button)
    public void onClickClear() {    //清理缓存的功能

        if (mCleanerService != null && !mCleanerService.isScanning() &&
                !mCleanerService.isCleaning() && mCleanerService.getCacheSize() > 0) {
            mAlreadyCleaned = false;

            mCleanerService.cleanCache();
        }
        noCache.setVisibility(View.VISIBLE);
//        clean_Plan.startAnimation(AnimationUtils.loadAnimation(
//                mContext, android.R.anim.fade_out));
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.on_anim);
        clean_Plan.startAnimation(animation);//开始动画
        new Handler().postDelayed(new Runnable() {
            public void run() {
               /*
                */
                clean_Plan.clearAnimation();
                Animation out_Animation = AnimationUtils.loadAnimation(RubbishCleanActivity.this, R.anim.out_anim);
                clean_Plan.startAnimation(out_Animation);//结束动画
                out_Animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        clean_Plan.clearAnimation();
                        clean_Plan.setVisibility(View.GONE);
                        ScaleAnimation scanl_animation =new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scanl_animation.setDuration(700);//设置动画持续时间
                        clean_Star2.startAnimation(scanl_animation);
/** 常用方法 */
                        clean_Success.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
//

            }
        }, 2000); //延迟2秒跳转

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //你需要跳转的地方的代码
                Intent intent = new Intent();
                intent.putExtra("title", RubbishCleanActivity.this.getString(R.string.cache_clean));
                intent.putExtra("content",RubbishCleanActivity.this.getString(R.string.cleaned, Formatter.formatShortFileSize(
                        mContext, cache_Nub)));
                intent.setClass(RubbishCleanActivity.this, MainActivity.class);
                startActivity(intent);
                RubbishCleanActivity.this.finish();
            }
        },6000);

    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


/*
    private boolean isProgressBarVisible() {
        return mProgressBar.getVisibility() == View.VISIBLE;
    }*/
/*
    private void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.startAnimation(AnimationUtils.loadAnimation(
                    mContext, android.R.anim.fade_out));
            mProgressBar.setVisibility(View.GONE);
        }
    }*/

    public void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

}
