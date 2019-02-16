package com.yunthink.supercleanmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superbooster.cleaning.R;
import com.yunthink.supercleanmaster.base.BaseFragment;
import com.yunthink.supercleanmaster.model.SDCardInfo;
import com.yunthink.supercleanmaster.ui.BatteryActivity;
import com.yunthink.supercleanmaster.ui.CpucoolingActivity;
import com.yunthink.supercleanmaster.ui.MemoryCleanActivity;
import com.yunthink.supercleanmaster.ui.RubbishCleanActivity;
import com.yunthink.supercleanmaster.utils.AppUtil;
import com.yunthink.supercleanmaster.utils.BusEntity;
import com.yunthink.supercleanmaster.utils.StorageUtil;
import com.yunthink.supercleanmaster.utils.Utils;
import com.yunthink.supercleanmaster.widget.circleprogress.ArcProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends BaseFragment {

    @InjectView(R.id.arc_store)
    ArcProgress arcStore;

    @InjectView(R.id.arc_process)
    ArcProgress arcProcess;

    @InjectView(R.id.cpu_process)
    ArcProgress cpuProcess;

    @InjectView(R.id.capacity)
    TextView capacity;

    Context mContext;

    private Timer timer;
    private Timer timer2;
    private Timer timer3;
    @InjectView(R.id.card1)
    RelativeLayout c1_js;
    @InjectView(R.id.card2)
    RelativeLayout c2_ql;
    @InjectView(R.id.card3)
    RelativeLayout c3_cpu;
    @InjectView(R.id.card4)
    RelativeLayout c4_dc;

    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.inject(this, view);
        mContext = getActivity();

        initClick();
        return view;
    }

    private void initClick() {
        c1_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isFastDoubleClick()) {
                    return;
                }else{
//                    String xh = PhoneBrand.getSystem();
                    startActivity(MemoryCleanActivity.class);
                }
            }
        });
        c2_ql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isFastDoubleClick()) {
                    return;
                }else{
//                    String xh = PhoneBrand.getSystem();
                    startActivity(RubbishCleanActivity.class);
                }
            }
        });
        c3_cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isFastDoubleClick()) {
                    return;
                }else{
//                    String xh = PhoneBrand.getSystem();
                    startActivity(CpucoolingActivity.class);
                }
            }
        });
        c4_dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isFastDoubleClick()) {
                    return;
                }else{
//                    String xh = PhoneBrand.getSystem();
                    startActivity(BatteryActivity.class);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fillData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        //UmengUpdateAgent.update(getActivity());
        EventBus.getDefault().register(this);
    }

    private void fillData() {
        // TODO Auto-generated method stub
        timer = null;
        timer2 = null;
        timer3 = null;
        timer = new Timer();
        timer2 = new Timer();
        timer3 = new Timer();

        long l = AppUtil.getAvailMemory(mContext);
        long y = AppUtil.getTotalMemory(mContext);
        final double x = (((y - l) / (double) y) * 100);

        arcProcess.setProgress(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arcProcess.getProgress() >= (int) x) {
                            timer.cancel();
                        } else {
                            arcProcess.setProgress(arcProcess.getProgress() + 1);
                        }

                    }
                });
            }
        }, 50, 20);


        SDCardInfo mSDCardInfo = StorageUtil.getSDCardInfo();
        SDCardInfo mSystemInfo = StorageUtil.getSystemSpaceInfo(mContext);

        long nAvailaBlock;
        long TotalBlocks;
        if (mSDCardInfo != null) {
            nAvailaBlock = mSDCardInfo.free + mSystemInfo.free;
            TotalBlocks = mSDCardInfo.total + mSystemInfo.total;
        } else {
            nAvailaBlock = mSystemInfo.free;
            TotalBlocks = mSystemInfo.total;
        }

        final double percentStore = (((TotalBlocks - nAvailaBlock) / (double) TotalBlocks) * 100);

        capacity.setText(StorageUtil.convertStorage(TotalBlocks - nAvailaBlock) + "/" + StorageUtil.convertStorage(TotalBlocks));
        arcStore.setProgress(0);
        cpuProcess.setProgress(0);


        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arcStore.getProgress() >= (int) percentStore) {
                            Log.e("kevin", "" + percentStore);
                            timer2.cancel();
                        } else {
                            arcStore.setProgress(arcStore.getProgress() + 1);
                        }

                    }
                });
            }
        }, 50, 20);
    }

/*    @OnClick(R.id.card1)
    void speedUp() {


//        Toast
        String xh = PhoneBrand.getSystem();
//        Toast.makeText(AboutActivity.this,""+xh,Toast.LENGTH_LONG).show();
//        if(xh!=SYS_MIUI ){
        startActivity(MemoryCleanActivity.class);


    }*/

//
//    @OnClick(R.id.card2)
//    void rubbishClean() {
//        startActivity(RubbishCleanActivity.class);
//    }
//
//
//    @OnClick(R.id.card3)
//    void AutoStartManage() {
//        startActivity(CpucoolingActivity.class);
//    }
//
//    @OnClick(R.id.card4)
//    void SoftwareManage() {
//        startActivity(BatteryActivity.class);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        timer.cancel();
        timer2.cancel();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BusEntity entity) {
//        cpuProcess.setProgress((int) entity.temp);
        final int cpu_Final = (int) entity.temp;
        try {
            timer3.schedule(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (cpuProcess.getProgress() >= cpu_Final) {
                                timer3.cancel();
                            } else {
                                cpuProcess.setProgress(cpuProcess.getProgress() + 1);
                            }

                        }
                    });
                }
            }, 50, 20);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}