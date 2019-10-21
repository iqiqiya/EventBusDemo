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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        String[] items = {"Success", "Failure", "Posting", "Main", "MainOrdered", "Background"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        // success
                        postSuccessEvent();
                        break;
                    case 1:
                        // failure
                        postFailureEvent();
                        break;
                    case 2:
                        // posting mode
                        postPostingEvent();
                    case 3:
                        // main thread mode
                        postMainEvent();
                        break;
                    case 4:
                        // main ordered thread mode
                        postMainOrderedEvent();
                        break;
                    case 5:
                        // background thread mode
                        postBackgroundEvent();
                        break;
                }
            }
        });
        return builder.create();
    }

    private void postBackgroundEvent() {
        if (Math.random() > .5) {
            ExecutorService pool = Executors.newFixedThreadPool(1);
                pool.submit(new Runnable() {
                    @Override
                    public void run() {
                        // 非UI线程
                        EventBus.getDefault().post(new BackgroundEvent(Thread.currentThread().toString()));
                    }
                });
                pool.shutdown();
        } else {
            EventBus.getDefault().post(new BackgroundEvent(Thread.currentThread().toString()));
        }
    }

    private void postMainOrderedEvent() {
        Log.d(TAG, "onClick: before @" + SystemClock.uptimeMillis());
        EventBus.getDefault().post(new MainOrderedEvent(Thread.currentThread().toString()));
        Log.d(TAG, "onClick: after @" + SystemClock.uptimeMillis());
    }

    private void postMainEvent() {
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
    }

    private void postPostingEvent() {
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
    }

    private void postSuccessEvent() {
        EventBus.getDefault().post(new SuccessEvent());
    }

    private void postFailureEvent() {
        EventBus.getDefault().post(new FailureEvent());
    }
}
