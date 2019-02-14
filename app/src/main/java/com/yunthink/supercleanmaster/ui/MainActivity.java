package com.yunthink.supercleanmaster.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.superbooster.cleanup.R;
import com.yunthink.supercleanmaster.base.ActivityTack;
import com.yunthink.supercleanmaster.base.BaseActivity;
import com.yunthink.supercleanmaster.fragment.MainFragment;
import com.yunthink.supercleanmaster.fragment.NavigationDrawerFragment;
import com.yunthink.supercleanmaster.fragment.SettingsFragment;
import com.yunthink.supercleanmaster.service.PeripheralService;
import com.yunthink.supercleanmaster.utils.BusEntity;
import com.yunthink.supercleanmaster.utils.SystemBarTintManager;
import com.yunthink.supercleanmaster.utils.T;
import com.yunthink.supercleanmaster.utils.UIElementsHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.InjectView;


public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @InjectView(R.id.container)
    FrameLayout container;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    ActionBar ab;
    private CharSequence mTitle;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;
    NavigationDrawerFragment mNavigationDrawerFragment;
    private View mFragmentContainerView;

    MainFragment mMainFragment;
    public static final long TWO_SECOND = 2 * 1000;
    long preTime;

    private PeripheralService mService;
    private Intent bindServiceIntent;

    boolean is_Run = false;
    private Intent startServiceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentContainerView = (View) findViewById(R.id.navigation_drawer);
        mTitle = getTitle();
        applyKitKatTranslucency();

        onNavigationDrawerItemSelected(0);
        initDrawer();


    }



    private void initCpu() {
       if (!PeripheralService.isServiceRunning(this)|| !is_Run ) {
//           Log.e("dianji","1");
            startServiceIntent = new Intent(this, PeripheralService.class);
            startServiceIntent.setAction(PeripheralService.ACTION_START_SERVER);
            startService(startServiceIntent);
        }
//        Log.e("dianji","2");
        bindServiceIntent = new Intent(this, PeripheralService.class);
        bindService(bindServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mUartStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PeripheralService.BROADCAST_CONNECTION_STATUS);
        intentFilter.addAction(PeripheralService.BROADCAST_CPU_TEMPERATURE_UPDATE);
        intentFilter.addAction(PeripheralService.BROADCAST_DEVICES_CONNECTED);
        intentFilter.addAction(PeripheralService.BROADCAST_SERVICE_CLOSED);
        return intentFilter;
    }

    private final BroadcastReceiver mUartStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
            //*********************//
            if (PeripheralService.BROADCAST_CPU_TEMPERATURE_UPDATE.equals(action)) {
                is_Run = true;
//                Log.e("dianji","6");
                double temperature = intent.getDoubleExtra("temperature", 0);
//                labelCpuTemp.setText(String.format("%.1f", temperature));
                String cpu_data = String.format("%.1f", temperature) + "";
                Log.e("kevin778", cpu_data);
                EventBus.getDefault().post(new BusEntity(Float.parseFloat(cpu_data)
                ));

            } else if (PeripheralService.BROADCAST_SERVICE_CLOSED.equals(action)) {
//                Log.e("dianji","5");
                finish();
            }
        }
    };

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
//            Log.e("dianji","3");
            mService = ((PeripheralService.LocalBinder) rawBinder).getService();
        }
        public void onServiceDisconnected(ComponentName classname) {
            mService = null;
        }
    };


    @Override
    public void onPause() {
        super.onPause();

        is_Run = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mUartStatusChangeReceiver);
            unbindService(mServiceConnection);
            stopService(bindServiceIntent);
            is_Run = false;
        } catch (Exception ignore) {
//            Log.e(TAG, ignore.toString());
        }

        mService = null;
    }

    private void initDrawer() {
        // TODO Auto-generated method stub
        ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);// 给home icon的左边加上一个返回的图标
        ab.setHomeButtonEnabled(true);// 需要api level 14 使用home-icon 可点击

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            } else {
                mDrawerLayout.openDrawer(mFragmentContainerView);
            }
        }
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        super.onResume();
//        判断sdk版本，4.4以下
        String sdkInt = android.os.Build.VERSION.SDK ;

        int sdk_bb = Integer.parseInt(sdkInt);
        if(sdk_bb > 20) {
            initCpu();
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
    public void onNavigationDrawerItemSelected(int position) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (position) {
            case 0:
                closeDrawer();
                if (mMainFragment == null) {
                    mMainFragment = new MainFragment();
                    transaction.add(R.id.container, mMainFragment);
                } else {
                    transaction.show(mMainFragment);
                }
                transaction.commit();

                break;
            case 1:
                closeDrawer();
                SettingsFragment.launch(MainActivity.this);
                break;


            // fragment = new SettingsFragment();
            // break;
        }


    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mMainFragment != null) {
            transaction.hide(mMainFragment);
        }
    }


    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 截获后退键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = new Date().getTime();

            // 如果时间间隔大于2秒, 不处理
            if ((currentTime - preTime) > TWO_SECOND) {
                // 显示消息
                T.showShort(mContext, MainActivity.this.getString(R.string.next_down));

                // 更新时间
                preTime = currentTime;

                // 截获事件,不再处理
                return true;
            } else {
                ActivityTack.getInstanse().exit(mContext);
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
