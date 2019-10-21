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

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String HANDLE_EVENT_ACTION = "handle_event_action";
    public static final String STATUS_KEY = "status";
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收到广播之后
            String action = intent.getAction();
            if (HANDLE_EVENT_ACTION.equals(action)){

                boolean status = intent.getBooleanExtra(STATUS_KEY,false);
                if (status) {
                    setImageSrc(R.drawable.ic_happy);
                } else {
                    setImageSrc(R.drawable.ic_sad);
                }
            }
        }
    };


    protected void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter(HANDLE_EVENT_ACTION);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver, filter);
    }

    protected void onStop(){
        super.onStop();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);
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
