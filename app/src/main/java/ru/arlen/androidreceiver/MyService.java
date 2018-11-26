package ru.arlen.androidreceiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String MY_SERVICE = "MY_SERVICE";
    public static final String MSG = "MSG";
    public static final String INTENT_ACTION  = "ru.arlen.INTENT_ACTION";

    private FSM state = FSM.INSTANCE;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int stateId = intent.getIntExtra(MainActivity.STATE, 0);
        state.changeState(stateId);
        Log.w(MY_SERVICE, "new State: " + state.getCurrentState());

        Intent newIntent = new Intent(INTENT_ACTION);
        newIntent.putExtra(MSG, state.getCurrentState().name());
        sendBroadcast(newIntent);

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(MY_SERVICE, "onDestroy");
        state = null;
    }
}
