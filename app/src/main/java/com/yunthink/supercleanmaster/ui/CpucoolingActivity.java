package com.yunthink.supercleanmaster.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.view.animation.TranslateAnimation;
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
import com.yunthink.supercleanmaster.utils.BusEntity;
import com.yunthink.supercleanmaster.utils.StorageUtil;
import com.yunthink.supercleanmaster.utils.SystemBarTintManager;
import com.yunthink.supercleanmaster.utils.UIElementsHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public class CpucoolingActivity extends BaseSwipeBackActivity implements OnDismissCallback, CoreService.OnPeocessActionListener {

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

    // 风扇view
    @InjectView(R.id.cpu_fun)
    ImageView cpu_Fun;

    // 雪花view
    @InjectView(R.id.cpu_xuehua)
    ImageView cpu_Xuehua;

//    降温状态文字
    @InjectView(R.id.cpu_jw_text)
    TextView cpu_Jw_Text;

    // cpu降温rl
    @InjectView(R.id.cpu_jw_rl)
    RelativeLayout cpu_Jw_rl;

    @InjectView(R.id.back_img2)
    ImageView back_Img;

    @InjectView(R.id.back_img3)
    ImageView back_Img2;
    private static final int INITIAL_DELAY_MILLIS = 300;
    SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;

    private CoreService mCoreService;
    //    资源文件
    private String scan_success,scan_success2,clean_up,memory;
    @InjectView(R.id.cpu_sm)
    ImageView cpu_Sm;
    @InjectView(R.id.cpu_xs_rl)
    RelativeLayout cpu_Xs_Rl;

    @InjectView(R.id.zg_rl)
    RelativeLayout zh_Rl;
    //    雪花动画
    private TranslateAnimation xuehua_Animation;
    //    fun动画
    private Animation fun_Animation;
    @InjectView(R.id.init_fun)
    ImageView init_Fun;


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCoreService = ((CoreService.ProcessServiceBinder) service).getService();
            mCoreService.setOnActionListener(CpucoolingActivity.this);
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
        setContentView(R.layout.activity_cpucooling);
        ab=getActionBar();
        ab.hide();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initString();
        //  applyKitKaslucency();
        Log.e("kevin","---:"+mAppProcessInfos.size());
        mClearMemoryAdapter = new ClearMemoryAdapter(mContext, mAppProcessInfos,true);
        mListView.setAdapter(mClearMemoryAdapter);
        bindService(new Intent(mContext, CoreService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);
        int footerHeight = mContext.getResources().getDimensionPixelSize(R.dimen.footer_height);
        mListView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnType.FOOTER, null, 0, bottom_lin, footerHeight));
      /*  textCounter.setAutoFormat(false);
        textCounter.setFormatter(new DecimalFormatter());
        textCounter.setAutoStart(false);
        textCounter.setIncrement(5f); // the amount the number increments at each time interval
        textCounter.setTimeInterval(50); // the time interval (ms) at which the text changes*/
        back_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(CpucoolingActivity.this,MainActivity.class);
                startActivity(intent);
                CpucoolingActivity.this.finish();
            }
        });
        back_Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CpucoolingActivity.this,MainActivity.class);
                startActivity(intent);
                CpucoolingActivity.this.finish();
            }
        });
        initAnimation();
    }

    private void initAnimation() {

        TranslateAnimation ta = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, -0.2f);

        ta.setDuration(1000);
        ta.setRepeatMode(TranslateAnimation.REVERSE);
        ta.setRepeatCount(-1);

        cpu_Sm.startAnimation(ta);
    }

    private void initString() {
//        scan_success,scan_success2,clean_up,memory;
        scan_success = this.getString(R.string.cpusm1);
        scan_success2 = this.getString(R.string.cpusm2);
        clean_up = this.getString(R.string.clean_up);
        memory = this.getString(R.string.memory);
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

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();

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
//        pb_Text.setText(R.string.scanning);
        showProgressBar(true);
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max,long mNub) {
//        mProgressBarText.setText(getString(R.string.scanning_m_of_n, current, max));
//        pb_Text.setText(getString(R.string.scanning_m_of_n, current, max));
//        pb_Vie
        if(!pb_status){
            pb_View.setMax( max);
            pb_status = true;
        }

//        pb_Text.setText(getString(R.string.scanning_m_of_n, current, max));
        pb_View .setProgress(current);
        if(current >= max){


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
        pb_Text.setText(scan_success+mAppProcessInfos.size()+scan_success2);
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
        StorageSize mStorageSize = StorageUtil.convertStorageSize(Allmemory);
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
        cpu_Jw_rl .setVisibility(View.VISIBLE);
        init_Fun.clearAnimation();
        zh_Rl.setVisibility(View.GONE);

        /*besom_img.setImageResource(R.drawable.am_besom);
        animationDrawable = (AnimationDrawable) besom_img.getDrawable();
        animationDrawable.start();*/
//        雪花动画
        //        雪花
        xuehua_Animation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.down_xuehua);
//        旋转动画
        fun_Animation = AnimationUtils.loadAnimation(this, R.anim.cpu_funanim);
//        LinearInterpolator lin = new LinearInterpolator();
//        AccelerateInterpolator ll = new AccelerateInterpolator();
//        fun_Animation.setInterpolator(ll);
        cpu_Fun.startAnimation(fun_Animation);
        cpu_Xuehua.startAnimation(xuehua_Animation);


        new Handler().postDelayed(new Runnable() {
            public void run() {
                //你需要跳转的地方的代码
                cpu_Jw_Text.setText( CpucoolingActivity.this.getString(R.string.cpu_cooling_over));
                Intent intent = new Intent();
                intent.putExtra("title", CpucoolingActivity.this.getString(R.string.cpucool));
                intent.putExtra("content", CpucoolingActivity.this.getString(R.string.cpu_cooling_over));
                intent.setClass(CpucoolingActivity.this, AdvertisementActivity3.class);
                startActivity(intent);
                CpucoolingActivity.this.finish();
            }
        }, 5000); //延迟5秒跳转

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
            cpu_Xs_Rl .setVisibility(View.GONE);
            zh_Rl.setVisibility(View.VISIBLE);
            fun_Animation = AnimationUtils.loadAnimation(this, R.anim.cpu_funanim);
            init_Fun.startAnimation(fun_Animation);

            //aaa
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onClickClear();
                }
            },2000);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BusEntity entity) {
        final int cpu_Final = (int) entity.temp;
        textCounter.setText(""+cpu_Final);

    }

    @Override
    public void onDestroy() {
        unbindService(mServiceConnection);
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
