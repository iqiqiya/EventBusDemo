package iqiqiya.lanlana.eventbusdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

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

    private static final String TAG = "PublisherDialogFragment";

    //定义弹出框
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        String[] items = {"Success", "Failure"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        // success
                    {
                        // 发送广播
                        final Intent intent = new Intent();
                        // 通过Action来确定是哪个广播
                        intent.setAction(MainActivity.HANDLE_EVENT_ACTION);
                        intent.putExtra(MainActivity.STATUS_KEY, true);
                        LocalBroadcastManager.getInstance(getActivity())
                                .sendBroadcast(intent);
                    }
                        break;
                    case 1:
                        // failure
                    {
                        final Intent intent = new Intent();
                        intent.setAction(MainActivity.HANDLE_EVENT_ACTION);
                        intent.putExtra(MainActivity.STATUS_KEY, false);
                        LocalBroadcastManager.getInstance(getActivity())
                                .sendBroadcast(intent);
                    }
                        break;
                }
            }
        });
        return builder.create();
    }
}
