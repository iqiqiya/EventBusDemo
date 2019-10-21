package iqiqiya.lanlana.eventbusdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class StickyActivity extends AppCompatActivity {

    // 发布后在目标Activity中注册

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true)
    public void onStickyMessageEvent(StickyMessageEvent event){
        setTitle(event.message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
    }
}
