package iqiqiya.lanlana.eventbusdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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
                        break;
                    case 1:
                        // failure
                        break;
                }
            }
        });
        return builder.create();
    }
}
