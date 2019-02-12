package com.yunthink.supercleanmaster.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnListViewOnScrollListener;
import com.john.waveview.WaveView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.superbooster.cleanup.R;
import com.yunthink.supercleanmaster.adapter.ClearMemoryAdapter;
import com.yunthink.supercleanmaster.base.BaseSwipeBackActivity;
import com.yunthink.supercleanmaster.bean.AppProcessInfo;
import com.yunthink.supercleanmaster.model.StorageSize;
import com.yunthink.supercleanmaster.service.CoreService;
import com.yunthink.supercleanmaster.utils.StorageUtil;
import com.yunthink.supercleanmaster.utils.SystemBarTintManager;
import com.yunthink.supercleanmaster.utils.UIElementsHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public class MemoryCleanActivity extends BaseSwipeBackActivity implements OnDismissCallback, CoreService.OnPeocessActionListener {

    @InjectView(R.id.listview)
    ListView mListView;

    @InjectView(R.id.wave_view)
    WaveView mwaveView;

    @InjectView(R.id.header)
    RelativeLayout header;

    List<AppProcessInfo> mAppProcessInfos = new ArrayList<>();

    ClearMemoryAdapter mClearMemoryAdapter;

    @InjectView(R.id.textCounter)
    TextView textCounter;
    @InjectView(R.id.sufix)
    TextView sufix;
    public long Allmemory;

    @InjectView(R.id.bottom_lin)
    LinearLayout bottom_lin;

    @InjectView(R.id.progressBar)
    LinearLayout mProgressBar;
    @InjectView(R.id.progressBarText)
    TextView mProgressBarText;

    @InjectView(R.id.pb_progressbar)
    ProgressBar pb_View;
    @InjectView(R.id.pb_text)
    TextView pb_Text;

    @InjectView(R.id.clear_button)
    Button clearButton;
    private boolean  pb_status ;
    ActionBar ab;
    private AnimationDrawable animationDrawable;

    // 扫把saomiao动画
    @InjectView(R.id.cpu_scan)
    ImageView cpu_Scan;
    // 扫把动画
    @InjectView(R.id.cpu_besom)
    ImageView cpu_Besom;// 扫把动画
    @InjectView(R.id.cpu_success)
    ImageView cpu_Success;
    //    扫把展示界面
    @InjectView(R.id.besom_am_rl)
    RelativeLayout besom_Am_Rl;

    @InjectView(R.id.back_img2)
    ImageView back_Img;
    private static final int INITIAL_DELAY_MILLIS = 300;
    SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;

    private CoreService mCoreService;
//    资源文件
    private Animation  operatingAnim;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCoreService = ((CoreService.ProcessServiceBinder) service).getService();
            mCoreService.setOnActionListener(MemoryCleanActivity.this);
            mCoreService.scanRunProcess();
//            //  updateStorageUsage();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCoreService.setOnActionListener(null);
            mCoreService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_clean);
        ab=getActionBar();
        ab.hide();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initString();

        mClearMemoryAdapter = new ClearMemoryAdapter(mContext, mAppProcessInfos);
        mListView.setAdapter(mClearMemoryAdapter);
        bindService(new Intent(mContext, CoreService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);
        int footerHeight = mContext.getResources().getDimensionPixelSize(R.dimen.footer_height);
        mListView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnType.FOOTER, null, 0, bottom_lin, footerHeight));
        back_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initString() {
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

    @Override
    public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {

    }

    @Override
    public void onScanStarted(Context context) {
//        mProgressBarText.setText(R.string.scanning);
        pb_Text.setText(R.string.scanning);
        showProgressBar(true);
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max,long mNub) {
        if(!pb_status){
            pb_View.setMax( max);
            pb_status = true;
        }
        StorageSize mStorageSize = StorageUtil.convertStorageSize(mNub);
        float ft   =  mStorageSize.value;
        int   scale  =   2;//设置位数
        int   roundingMode  =  4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd  =   new BigDecimal((double)ft);
        bd   =  bd.setScale(scale,roundingMode);
        ft   =  bd.floatValue();

//        StorageSize mStorageSize = StorageUtil.convertStorageSize(mCacheSize);

        sufix.setText(mStorageSize.suffix);
        textCounter.setText(""+ft+"");


        pb_Text.setText(getString(R.string.scanning_m_of_n, current, max));
        pb_View .setProgress(current);
        if(current >= max){
            pb_Text.setText(this.getString(R.string.scan_success)+max+ this.getString(R.string.scan_success2));
        }
    }

    @Override
    public void onScanCompleted(Context context, List<AppProcessInfo> apps) {
        mAppProcessInfos.clear();


        Allmemory = 0;
        for (AppProcessInfo appInfo : apps) {
            if (!appInfo.isSystem) {
                mAppProcessInfos.add(appInfo);
                Allmemory += appInfo.memory;
            }
        }

        refeshTextCounter();
        Log.e("kevin","---:"+mAppProcessInfos.size());
        mClearMemoryAdapter.notifyDataSetChanged();
        showProgressBar(false);


        if (apps.size() > 0) {
            header.setVisibility(View.VISIBLE);
            bottom_lin.setVisibility(View.VISIBLE);


        } else {
            header.setVisibility(View.GONE);
            bottom_lin.setVisibility(View.GONE);
        }

    }

    private void refeshTextCounter() {
        mwaveView.setProgress(20);

    }

    @Override
    public void onCleanStarted(Context context) {

    }

    @Override
    public void onCleanCompleted(Context context, long cacheSize) {

    }


    @OnClick(R.id.clear_button)
    public void onClickClear() {
        long killAppmemory = 0;
        for (int i = mAppProcessInfos.size() - 1; i >= 0; i--) {
            if (mAppProcessInfos.get(i).checked) {
                killAppmemory += mAppProcessInfos.get(i).memory;
                mCoreService.killBackgroundProcesses(mAppProcessInfos.get(i).processName);
                mAppProcessInfos.remove(mAppProcessInfos.get(i));
                mClearMemoryAdapter.notifyDataSetChanged();
            }
        }
        Allmemory = Allmemory - killAppmemory;
//        T.showLong(mContext, clean_up + StorageUtil.convertStorage(killAppmemory) + memory);

        if (Allmemory > 0) {
//            sufix.setText(""+Allmemory );
            Log.e("kevins",""+StorageUtil.convertStorage(killAppmemory));
            refeshTextCounter();
        }
        besom_Am_Rl .setVisibility(View.VISIBLE);
        /*besom_img.setImageResource(R.drawable.am_besom);
        animationDrawable = (AnimationDrawable) besom_img.getDrawable();
            animationDrawable.start();*/
//        扫描动画
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.besom_scan);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        cpu_Scan.startAnimation(operatingAnim);

//        缩小动画
        //初始化
//        Animation scaleAnimation = new ScaleAnimation(0.1f, 1.0f,0.1f,1.0f);
////设置动画时间

//        scaleAnimation.setDuration(3000);
//        cpu_Besom.startAnimation(scaleAnimation);


//        clean_Text.setText( this.getString(R.string.clean_up)+ StorageUtil.convertStorage(killAppmemory) + this.getString(R.string.memory) );
        new Handler().postDelayed(new Runnable() {
            public void run() {
                cpu_Scan.clearAnimation();
                cpu_Scan.setVisibility(View.GONE);
                cpu_Besom.setVisibility(View.GONE);
                cpu_Success.setVisibility(View.VISIBLE);

            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("title", MemoryCleanActivity.this.getString(R.string.app_memory_clean));
                intent.putExtra("content", MemoryCleanActivity.this.getString(R.string.complete_acceleration));
                intent.setClass(MemoryCleanActivity.this, AdvertisementActivity2.class);
                startActivity(intent);
                MemoryCleanActivity.this.finish();
            }
        },10000);
    }


    private void showProgressBar(boolean show) {
        if (show) {
            pb_View.setVisibility(View.VISIBLE);
            pb_Text.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.startAnimation(AnimationUtils.loadAnimation(
                    mContext, android.R.anim.fade_out));
            mProgressBar.setVisibility(View.GONE);
            pb_View.setVisibility(View.GONE);
            pb_Text.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
