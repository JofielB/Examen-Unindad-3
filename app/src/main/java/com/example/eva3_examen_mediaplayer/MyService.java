package com.example.eva3_examen_mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    MediaPlayer mediaPlayer = null;

    IBinder mBinder = new LocalBinder();

    int songNumber;

    int listOfSongs[] = {R.raw.that_is_life,
            R.raw.baile_del_tambor,
            R.raw.cancion_columpio,
            R.raw.el_ansioso,
            R.raw.el_inicio,
            R.raw.el_triangulo,
            R.raw.gerudo_valley,
            R.raw.la_chona,
            R.raw.musica_de_elevador,
            R.raw.never_gonna_give_you_up,
            R.raw.palmas_al_aire,
            R.raw.the_final_countdown
    };

    boolean aleatorio = false;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public MyService getServerInstance() {
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null) {
            mediaPlayer.release();
        }
    }

    public void startMusic(){
            mediaPlayer = MediaPlayer.create(this,listOfSongs[songNumber]);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
            mediaPlayer.start();
    }

    public void pauseMusic(){
        if(mediaPlayer!=null) {
            mediaPlayer.pause();
        }
    }

    public void resumeMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }

    public void setSong(int s){
        songNumber = s;
        startMusic();
    }

    public void previousSong(){
        if(aleatorio){
            songNumber = randomNumber();
        }else {
            if (songNumber == 0) {
                songNumber = listOfSongs.length - 1;
            } else {
                songNumber--;
            }
        }
        changeSong();
    }

    public void nextSong(){
        if(aleatorio){
            int number = randomNumber();
            songNumber = number==songNumber ? randomNumber():number;
        }else {
            if (songNumber + 1 == listOfSongs.length) {
                songNumber = 0;
            } else {
                songNumber++;
            }
        }
        changeSong();
    }

    private void changeSong(){
        mediaPlayer.release();
        sendCurrentSong();
        startMusic();
    }

    private void sendCurrentSong(){
        Intent intMensaje = new Intent("MI_SERVICIO");
        intMensaje.putExtra("MENSAJE",songNumber);
        sendBroadcast(intMensaje);
    }

    public void reproduccionAleatoria(){
        aleatorio = true;
    }

    private int randomNumber(){
        double randomDouble = Math.random();
        randomDouble = randomDouble * listOfSongs.length;
        int randomInt = (int) randomDouble;
        return randomInt;
    }
}