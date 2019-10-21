package iqiqiya.lanlana.eventbusdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Author: iqiqiya
 * Date: 2019/10/21
 * Time: 11:44
 * Blog: blog.77sec.cn
 * Github: github.com/iqiqiya
 */
public class PublisherDialogFragment extends DialogFragment {

    //发布方操作
    private static final String TAG = "PublisherDialogFragment";

    //定义弹出框
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        String[] items = {"Success", "Failure", "Posting", "Main", "MainOrdered"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        // success
                        EventBus.getDefault().post(new SuccessEvent());
                        break;
                    case 1:
                        // failure
                        EventBus.getDefault().post(new FailureEvent());
                        break;
                    case 2:
                        // posting mode
                        if (Math.random() > .5) {
                            new Thread("posting-002") {
                                @Override
                                public void run() {
                                    super.run();
                                    EventBus.getDefault().post(new PostingEvent(Thread.currentThread().toString()));
                                }
                            }.start();
                        } else {
                            EventBus.getDefault().post(new PostingEvent(Thread.currentThread().toString()));
                        }
                    case 3:
                        // main thread mode
                        if (Math.random() > .5) {
                            new Thread("working-thread"){
                                @Override
                                public void run() {
                                    super.run();
                                    EventBus.getDefault().post(new MainEvent(Thread.currentThread().toString()));
                                }
                            }.start();
                        } else {
                            EventBus.getDefault().post(new MainEvent(Thread.currentThread().toString()));
                        }
                        break;
                    case 4:
                        // main ordered thread mode
                        Log.d(TAG, "onClick: before @" + SystemClock.uptimeMillis());
                        EventBus.getDefault().post(new MainOrderedEvent(Thread.currentThread().toString()));
                        Log.d(TAG, "onClick: after @" + SystemClock.uptimeMillis());
                        break;
                }
            }
        });
        return builder.create();
    }
}
