package com.example.heath.broadcastbestpractice;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by heath on 15-11-15.
 */
public class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Waring");
        dialogBuilder.setMessage("强制下线");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
                Intent intent1 = new Intent(context, LoginActivity.class);
                //广播接收器不是Activity，在Activity外启动Activity必须要加这个标志
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        });
        //test
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams
                .TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}
