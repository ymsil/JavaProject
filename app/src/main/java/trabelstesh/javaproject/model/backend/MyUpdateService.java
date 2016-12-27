package trabelstesh.javaproject.model.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyUpdateService extends Service
{

    final  String TAG = "myUpdateService";

    public MyUpdateService()
    {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG,"onStartCommand");
        Toast.makeText(this,"run service",Toast.LENGTH_LONG).show();

        Thread t = new Thread() {
            @Override
            public void run() {

                while (true)
                {
                    try
                    {
                        Thread.sleep(10000);
                        Log.d(TAG,"thread run ..");
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(DBManagerFactory.getManager().isUpdated())
                    {
                        Log.d(TAG,"isUpdated run ..");
                        Intent updateIntent = new Intent("trabelstesh.javaproject.myreciever");
                        sendBroadcast(updateIntent);
                    }
                }
            }
        };
        t.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG,"onBind");
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}
