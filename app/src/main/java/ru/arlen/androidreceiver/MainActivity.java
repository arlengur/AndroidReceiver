package ru.arlen.androidreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import static ru.arlen.androidreceiver.MyService.INTENT_ACTION;
import static ru.arlen.androidreceiver.MyService.MSG;

public class MainActivity extends Activity {
    private static final String STARTED_ACTIVITY = "STARTED_ACTIVITY";
    public static final String STATE = "STATE";
    private Random random;
    private MyReceiver mReceiver;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new MyReceiver();
        random = new Random();

        text = findViewById(R.id.textState);
        View btnStart = findViewById(R.id.button);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                int stateId = random.nextInt(State.values().length);
                intent.putExtra(STATE, stateId);
                Log.w(STARTED_ACTIVITY, "send: " + stateId);
                startService(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(INTENT_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    class MyReceiver extends BroadcastReceiver {
        private static final String MY_RECEIVER = "MY_RECEIVER";

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(MSG);
            Log.w(MY_RECEIVER, "Текущее состояние: " + msg);
            text.setText("Текущее состояние: " + msg);
        }
    }

}
