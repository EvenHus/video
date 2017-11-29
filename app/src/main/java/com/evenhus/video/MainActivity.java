package com.evenhus.video;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    boolean videoComplete = false;
    MediaPlayer mediaPlayer = new MediaPlayer();
    AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.hah);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeCtrl = (SeekBar)findViewById(R.id.seekBar);
        volumeCtrl.setMax(maxVolume);
        volumeCtrl.setProgress(currentVolume);

        volumeCtrl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final SeekBar playCtrl = (SeekBar)findViewById(R.id.seekBar2);
        playCtrl.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                playCtrl.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 100);


        playCtrl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();

            }
        });

    }

    public void video(View view){
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        Button buttonShow = (Button)findViewById(R.id.showButton);
        videoView.setVisibility(view.VISIBLE);
        buttonShow.setVisibility(view.INVISIBLE);

        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.samplevideo);

        MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);

        videoView.start();




        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            Button buttonHide = (Button)findViewById(R.id.hideButton);
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoComplete = true;
                buttonHide.setVisibility(buttonHide.VISIBLE);
            }

        });
    }

    public void hide(View view){

        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setVisibility(view.INVISIBLE);

        Button buttonShow = (Button)findViewById(R.id.showButton);
        buttonShow.setVisibility(buttonShow.VISIBLE);

        view.setVisibility(view.INVISIBLE);


    }

    public void sound(View view){
        mediaPlayer.start();

    }

    public void pauseSound(View view) {
        mediaPlayer.pause();
        int currentTrackPosition = mediaPlayer.getCurrentPosition();

        Log.i("Track position", Integer.toString(currentTrackPosition));
    }
}
