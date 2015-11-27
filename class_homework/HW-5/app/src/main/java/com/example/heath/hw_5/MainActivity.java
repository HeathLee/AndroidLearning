package com.example.heath.hw_5;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private Button playOrPause, pause, stop;
    private MusicService musicService;
    private SeekBar seekBar;
    private TextView textView;
    private SimpleDateFormat time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binServiceConnection();
        handler.post(runnable);
        playOrPause = (Button) findViewById(R.id.play_or_pause);
        pause = (Button) findViewById(R.id.quiz);
        stop = (Button) findViewById(R.id.stop);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        textView = (TextView) findViewById(R.id.time);
        time = new SimpleDateFormat("m:ss");
        playOrPause.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_or_pause:
                if (!musicService.mediaPlayer.isPlaying()) {
                    musicService.mediaPlayer.start();
                    playOrPause.setText("Pause");
                } else {
                    musicService.mediaPlayer.pause();
                    playOrPause.setText("Play");
                }
                textView.setText(time.format(musicService.mediaPlayer
                        .getCurrentPosition()) + "/" + time.format(musicService
                        .mediaPlayer.getDuration()));
                seekBar.setProgress(musicService.mediaPlayer
                        .getCurrentPosition());
                seekBar.setMax(musicService.mediaPlayer.getDuration());
                break;
            case R.id.quiz:
                handler.removeCallbacks(runnable);
                unbindService(serviceConnection);
                try {
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.stop:
                if (musicService.mediaPlayer != null) {
                    musicService.mediaPlayer.reset();
                    try {
                        File file = new File("/data/The Soundrops - " +
                                "Stay Beside.mp3");
                        musicService.mediaPlayer.setDataSource(file.getPath());
                        musicService.mediaPlayer.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (musicService.mediaPlayer != null) {
            musicService.mediaPlayer.stop();
            musicService.mediaPlayer.release();
        }
        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    private void binServiceConnection() {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    Handler handler = new android.os.Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView.setText(time.format(musicService.mediaPlayer
                    .getCurrentPosition()) + "/" + time.format(musicService
                    .mediaPlayer.getDuration()));
            seekBar.setProgress(musicService.mediaPlayer
                    .getCurrentPosition());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicService.mediaPlayer.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            handler.postDelayed(runnable, 100);
        }
    };
}
