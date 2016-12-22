package trabelstesh.javaproject.model.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast

        Toast.makeText(context,"onReceive to "+ intent.getAction(),Toast.LENGTH_LONG).show();

        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
