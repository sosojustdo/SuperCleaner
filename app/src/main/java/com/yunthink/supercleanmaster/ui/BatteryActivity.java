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
import com.superbooster.cleaning.R;
import com.yunthink.supercleanmaster.adapter.ClearMemoryAdapter;
import com.yunthink.supercleanmaster.base.BaseSwipeBackActivity;
import com.yunthink.supercleanmaster.bean.AppProcessInfo;
import com.yunthink.supercleanmaster.model.StorageSize;
import com.yunthink.supercleanmaster.service.CoreService;
import com.yunthink.supercleanmaster.utils.StorageUtil;
import com.yunthink.supercleanmaster.utils.SystemBarTintManager;
import com.yunthink.supercleanmaster.utils.UIElementsHelper;
import com.yunthink.widget.BoomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class BatteryActivity extends BaseSwipeBackActivity implements OnDismissCallback, CoreService.OnPeocessActionListener {

    private ImageView[] pp_Img = new ImageView[5];
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

    // 扫把动画
    @InjectView(R.id.animationIV)
    ImageView besom_img;
    //    扫把展示界面
    @InjectView(R.id.besom_am_rl)
    RelativeLayout besom_Am_Rl;

    //    清理数据显示
    @InjectView(R.id.clean_text)
    TextView clean_Text;

    @InjectView(R.id.back_img2)
    ImageView back_Img;
    @InjectView(R.id.sd_text)
    TextView sd_Text;
    private static final int INITIAL_DELAY_MILLIS = 300;
    SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;

    private CoreService mCoreService;
    //    资源文件
    private String scan_success,scan_success2,clean_up,memory;

    BoomView surface_main;
     int b_size;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCoreService = ((CoreService.ProcessServiceBinder) service).getService();
            mCoreService.setOnActionListener(BatteryActivity.this);
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
        setContentView(R.layout.activity_battery);
        initView();
        ab=getActionBar();
        ab.hide();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initString();
        Log.e("kevin","---:"+mAppProcessInfos.size());
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

    private void initView() {

        pp_Img[0] = (ImageView) findViewById(R.id.item_appicon1);
        pp_Img[1] = (ImageView) findViewById(R.id.item_appicon2);
        pp_Img[2] = (ImageView) findViewById(R.id.item_appicon3);
        pp_Img[3] = (ImageView) findViewById(R.id.item_appicon4);
        pp_Img[4] = (ImageView) findViewById(R.id.item_appicon5);

    }

    private void initString() {
//        scan_success,scan_success2,clean_up,memory;
        scan_success = this.getString(R.string.scan_success);
        scan_success2 = this.getString(R.string.scan_success2);
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


    private void initPaoPao(List<AppProcessInfo> mAppProcessInfos) {

        for (int i =0;i<mAppProcessInfos.size();i++) {
            if(i<=4) {
                Animation animation_yd = AnimationUtils.loadAnimation(BatteryActivity.this, R.anim.on_anim);
                AppProcessInfo appInfo = mAppProcessInfos.get(i);
                pp_Img[i].setVisibility(View.VISIBLE);
                pp_Img[i].setImageDrawable(appInfo.icon);
                pp_Img[i].startAnimation(animation_yd);
            }
//
        }
       b_size = mAppProcessInfos.size();
        new Handler().postDelayed(new Runnable() {
            public void run() {

                onClickClear();

            }
        }, 6000); //延迟2秒跳转




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

        pb_Text.setText(getString(R.string.scanning_m_of_n, current, max));
        pb_View .setProgress(current);
        if(current >= max){
            pb_Text.setText(scan_success+max+scan_success2);
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
            initPaoPao(mAppProcessInfos);

        } else {
            header.setVisibility(View.GONE);
            bottom_lin.setVisibility(View.GONE);
        }

    }

    private void refeshTextCounter() {
        mwaveView.setProgress(20);
        StorageSize mStorageSize = StorageUtil.convertStorageSize(Allmemory);
        sufix.setText(mStorageSize.suffix);
        textCounter.setText((int)mStorageSize.value+"");
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
            Log.e("kevins",""+StorageUtil.convertStorage(killAppmemory));
            refeshTextCounter();
        }
        besom_Am_Rl .setVisibility(View.VISIBLE);
        besom_img.setImageResource(R.drawable.am_besom);
        animationDrawable = (AnimationDrawable) besom_img.getDrawable();
        clean_Text.setText( clean_up+ StorageUtil.convertStorage(killAppmemory) + memory );
      if (b_size>4){
          b_size=4;
      }

        switch (b_size) {
            case 1:
                pp_Img[1].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[1]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[0].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[0]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                break;
            case 2:
                pp_Img[1].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[1]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);

                pp_Img[0].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[0]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[2].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[2]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                break;
            case 3:
                pp_Img[1].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[1]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[0].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[0]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[2].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[2]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[3].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[3]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                break;
            case 4:
                pp_Img[1].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[1]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[0].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[0]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[2].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[2]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[3].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[3]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                pp_Img[4].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[4]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                break;
            case 0:
                pp_Img[0].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        surface_main= BoomView.add2RootView(BatteryActivity.this);
                        surface_main.setPaintView(pp_Img[0]);
                        //surface_main.setEnableViewShakeAnim(false);
                        surface_main.startAnimation();
                    }
                }, 1600);
                break;
            default:
                break;

        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //你需要跳转的地方的代码
//                finish();
                sd_Text.setText(BatteryActivity.this.getString(R.string.over_save));
                Intent intent = new Intent();
                intent.putExtra("title", BatteryActivity.this.getString(R.string.phone_save_electricity));
                intent.putExtra("content", BatteryActivity.this.getString(R.string.over_save));
                intent.setClass(BatteryActivity.this, AdvertisementActivity4.class);
                startActivity(intent);
                BatteryActivity.this.finish();
            }
        }, 8000); //延迟8秒跳转

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