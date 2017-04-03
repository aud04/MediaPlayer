package com.example.austepha.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import static com.example.austepha.mediaplayer.R.id.sbmusique;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar sbmusique = (SeekBar) findViewById(R.id.sbmusique);
        sbmusique.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
// bouton start
        Button start = (Button) findViewById(R.id.start);
        final MusiqueAsyncTask[] mt = new MusiqueAsyncTask[1];
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.amy);
                mediaPlayer.setLooping(false);
                sbmusique.setMax(mediaPlayer.getDuration());
                mt[0] = new MusiqueAsyncTask(getApplicationContext(), sbmusique, mediaPlayer);
                mt[0].execute();
            }
        });
// bouton stop, le cancel fait référence au onCancelled dans MusiqueAsyncTask
        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mt[0] != null) {
                    mt[0].cancel(true);
                    mt[0] = null;
                }
                sbmusique.setProgress(0);
            }
        });

// audioManager gère le son du player
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        SeekBar sbvolume = (SeekBar) findViewById(R.id.sbvolume);
// Par défaut on met le son au milieu
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC/2), AudioManager.FLAG_SHOW_UI);
        sbvolume.setProgress(audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC)/2); //permet de bien placer le curseur sur la barrre
// Le maximum de ma barre corrrespond au volume du son maximum
        sbvolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        sbvolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}




