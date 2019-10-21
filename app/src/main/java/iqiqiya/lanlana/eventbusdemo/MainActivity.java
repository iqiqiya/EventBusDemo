package iqiqiya.lanlana.eventbusdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // 订阅方操作
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSuccessEvent(SuccessEvent event){
        setImageSrc(R.drawable.ic_happy);
    }

    @Subscribe
    public void onFailureEvent(FailureEvent event){
        setImageSrc(R.drawable.ic_sad);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(final PostingEvent event){
        final String threadInfo = Thread.currentThread().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setpublisherThreadInfo(event.threadInfo);
                setSubcriberThreadInfo(threadInfo);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent event){
        setpublisherThreadInfo(event.threadInfo);
        setSubcriberThreadInfo(Thread.currentThread().toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMainOrderedEvent(MainOrderedEvent event){
        Log.d(TAG, "onMainOrderedEvent: enter @ " + SystemClock.uptimeMillis());
        setpublisherThreadInfo(event.threadInfo);
        setSubcriberThreadInfo(Thread.currentThread().toString());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onMainOrderedEvent: exit @ " + SystemClock.uptimeMillis());
    }

    private void setpublisherThreadInfo(String threadInfo){
        setTextView(R.id.publisherThreadTextView,threadInfo);
    }

    private void setSubcriberThreadInfo(String threadInfo){
        setTextView(R.id.subscriberThreadTextView,threadInfo);
    }

    /**
     * 应该放在UI线程中
     * @param resId
     * @param text
     */
    private void setTextView(int resId, String text){
        TextView textView = findViewById(resId);
        textView.setText(text);
        // 透明度渐变得动画
        textView.setAlpha(.5f);
        textView.animate().alpha(1).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Subscriber");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 展示dialog fragment(publisher)
                PublisherDialogFragment fragment = new PublisherDialogFragment();

                fragment.show(getSupportFragmentManager(),"publisher");
            }
        });
    }

    // 设置图片
    private void setImageSrc(int resId){
            ImageView imageView = findViewById(R.id.emotionImageView);
            imageView.setImageResource(resId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
