package com.example.heath.hw_4;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by heath on 15-11-13.
 */
public class MyWidgetProvider extends AppWidgetProvider {
    //onUpdate在创建widget时会被调用，因此在这里注册了点击事件，
    // 而不是一开始所理解的点击事件会调用onUpdate
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //用于Activity跳转
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R
                .layout.widget_layout);
        remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}
