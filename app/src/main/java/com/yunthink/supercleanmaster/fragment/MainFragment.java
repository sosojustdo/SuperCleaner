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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private int coreSize = Runtime.getRuntime().availableProcessors();
    private ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(coreSize);
    private ScheduledThreadPoolExecutor timer2 = new ScheduledThreadPoolExecutor(coreSize);
    private ScheduledThreadPoolExecutor timer3 = new ScheduledThreadPoolExecutor(coreSize);

    @InjectView(R.id.card1)
    RelativeLayout c1_js;
    @InjectView(R.id.card2)
    RelativeLayout c2_ql;
    @InjectView(R.id.card3)
    RelativeLayout c3_cpu;
    @InjectView(R.id.card4)
    RelativeLayout c4_dc;


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
        EventBus.getDefault().register(this);
    }

    private void fillData() {
        // TODO Auto-generated method stub
        long l = AppUtil.getAvailMemory(mContext);
        long y = AppUtil.getTotalMemory(mContext);
        final double x = (((y - l) / (double) y) * 100);

        arcProcess.setProgress(0);
        timer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null != arcProcess && null != arcProcess.getProgress()){
                            if (arcProcess.getProgress() >= (int) x) {
                                //timer.cancel();
                            } else {
                                arcProcess.setProgress(arcProcess.getProgress() + 1);
                            }
                        }
                    }
                });
            }
        },50, 20, TimeUnit.MILLISECONDS);

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

        timer2.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null != arcStore && null != arcStore.getProgress()){
                            if (arcStore.getProgress() >= (int) percentStore) {
                                Log.e("kevin", "" + percentStore);
                                //timer2.cancel();
                            } else {
                                arcStore.setProgress(arcStore.getProgress() + 1);
                            }
                        }
                    }
                });
            }
        }, 50, 20, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        timer.shutdown();
        timer2.shutdown();
        timer3.shutdown();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BusEntity entity) {
        //cpuProcess.setProgress((int) entity.temp);
        final int cpu_Final = (int) entity.temp;
        timer3.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null != cpuProcess && null != cpuProcess.getProgress()){
                            if (cpuProcess.getProgress() >= cpu_Final) {
                                //timer3.shutdown();
                            } else {
                                cpuProcess.setProgress(cpuProcess.getProgress() + 1);
                            }
                        }
                    }
                });
            }
        }, 50, 20, TimeUnit.MILLISECONDS);
    }
}