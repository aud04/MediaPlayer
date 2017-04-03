package com.example.austepha.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by austepha on 28/03/2017.
 */

public class MusiqueAsyncTask extends AsyncTask<Void, Integer, Void> {

    private Context mContext;
    private SeekBar mProgressBar;
    private MediaPlayer mediaplayer;

    MusiqueAsyncTask(Context c, SeekBar p, MediaPlayer mediaplayer) {
        this.mContext = c;
        this.mProgressBar = p;
        this.mediaplayer = mediaplayer;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(mediaplayer.getCurrentPosition() != mediaplayer.getDuration() && !this.isCancelled()) {

            publishProgress(mediaplayer.getCurrentPosition());
        }
        return null;
    }

    //lance la musique
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mediaplayer.start();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    //progression de la bar seekbar
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setProgress(values[0]);
    }

    //permet de stopper la musique
    @Override
    protected void onCancelled() {
        super.onCancelled();
        mediaplayer.stop();
    }
}
