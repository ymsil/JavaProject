package trabelstesh.javaproject.model.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import trabelstesh.javaproject.controller.BusinessCursorAdapter;

public class CheckerService extends Service {
    final  String TAG = "checkerservice";
    public CheckerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        // TODO: Return the communication channel to the service.
        return  null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");

        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        Toast.makeText(this,"run service",Toast.LENGTH_LONG).show();

        Thread t = new Thread() {
            @Override
            public void run() {

                while (true)
                {
                    try {
                        Thread.sleep(5000);
                        Log.d(TAG,"thread run ..");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(DBManagerFactory.getManager().isUpdated())
                    {
                        Log.d(TAG,"isUpdated run ..");
                        Intent intent1 = new Intent("trabelstesh.javaproject.MyAction");

                        CheckerService.this.sendBroadcast(intent1);


                        //sendBroadcast(intent);
                    }

                }
            }
        };

        t.start();
        return START_STICKY;
    }
}
