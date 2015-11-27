package com.example.heath.hw_5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by heath on 15-11-24.
 */
public class MusicService extends Service {
    private MyBinder mBinder = new MyBinder();
    public static MediaPlayer mediaPlayer = new MediaPlayer();

    class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public MusicService() {
        try {
            File file = new File("/data/The Soundrops - Stay Beside.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
