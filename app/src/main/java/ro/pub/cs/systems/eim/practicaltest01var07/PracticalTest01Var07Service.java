package ro.pub.cs.systems.eim.practicaltest01var07;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.Random;

// D.1:
public class PracticalTest01Var07Service extends Service {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();

    private final Runnable broadcastTask = new Runnable() {
        @Override
        public void run() {
            int[] values = new int[4];
            for (int i = 0; i < values.length; i++) {
                values[i] = random.nextInt(100);
            }
            Intent intent = new Intent(Constants.ACTION_RANDOM_VALUES);
            intent.setPackage(getPackageName()); // explicit to this app only
            intent.putExtra(Constants.EXTRA_RANDOM_INTS, values);
            sendBroadcast(intent);
            Log.d(Constants.TAG, "Service broadcast: " + values[0] + " " + values[1] + " "
                    + values[2] + " " + values[3]);
            handler.postDelayed(this, Constants.SERVICE_PERIOD_MS);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // restart the schedule so repeated startService() calls do not stack timers
        handler.removeCallbacks(broadcastTask);
        handler.postDelayed(broadcastTask, Constants.SERVICE_PERIOD_MS);
        Log.d(Constants.TAG, "Service started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(broadcastTask);
        Log.d(Constants.TAG, "Service destroyed");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
