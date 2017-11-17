package com.example.wangqian.happinesshunter.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.wangqian.happinesshunter.R;

public class AlarmReceiver extends BroadcastReceiver {
	
	@Override
    public void onReceive(Context context, Intent intent) {
 Log.e("xxxxxxxxxxxxx","xxxxxxxxxxxxxx");

        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.bigwhite_head)
                .setContentTitle("今天发生什么开心的事情了嘛~")
                .setContentText("分享快乐，快乐会加倍哦23333")
                .setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE);

        notifyManager.notify(1, builder.build());
    }
}
