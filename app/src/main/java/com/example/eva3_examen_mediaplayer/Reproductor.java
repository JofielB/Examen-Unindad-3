package com.example.eva3_examen_mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Reproductor extends AppCompatActivity {

    ImageView portada, playButton, previousSong, nextSong;

    TextView tituio,descripcion;

    AnimationDrawable soundAnimation;

    String[] songsName = {
            "Frank Sinatra - That´s Life",
            "El Tambor - Mi baile",
            "Sr. Columpio - El Tempo del Columpio",
            "Grupo Marrano - El ansioso",
            "Instrumento - El inicio",
            "Triangulo - La cancion del triangulo",
            "Legend of Zelda - Gerudo Valley",
            "Los Tucanes de Tijuana - La Chona",
            "Elevador - Elevandome a lo alto",
            "Rick Astley - Never Gona Give You Up",
            "Las Manos - Manitas Locas",
            "Europe - The Final Countdown"
    };
    int currenSong;

    Intent music;
    boolean estado;

    Intent intent;

    //Conectar con el servicio
    boolean mBounded;
    MyService mServer;

    //Broadcast receiver
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goingFullScreen();
        setContentView(R.layout.activity_reproductor);

        prepareShow();
        //Broadcast Receiver
        broadcastReceiver = new MiReceptorDifusion();
        IntentFilter filtro = new IntentFilter("MI_SERVICIO");
        registerReceiver(broadcastReceiver,filtro);

        //Play Button
        playButton = findViewById(R.id.imgPlayButton);
        playButton.setImageResource(android.R.drawable.ic_media_pause);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estado = !estado;
                if (estado){
                    playButton.setImageResource(android.R.drawable.ic_media_play);
                    soundAnimation.stop();

                    //stopService(music);
                    mServer.pauseMusic();
                }else{
                    playButton.setImageResource(android.R.drawable.ic_media_pause);
                    soundAnimation.start();

                    //startService(music);
                    mServer.resumeMusic();

                }
            }
        });

        //Previous Button
        previousSong = findViewById(R.id.btnPreviusSong);
        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mServer.previousSong();
                if(estado){
                    mServer.pauseMusic();
                }
            }
        });

        //Next Song
        nextSong = findViewById(R.id.btnNextSong);
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mServer.nextSong();
                if(estado){
                    mServer.pauseMusic();
                }
            }
        });
    }

    private void goingFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
    }

    private void prepareShow(){
        //Obtener intento con el cual se lanzo
        intent = getIntent();
        currenSong = intent.getIntExtra("NUMBER",1);
        setCoverAnimation();
        setTitleSong();
        startMusicService();
    }

    private void setTitleSong(){
        String intentTitulo = intent.getStringExtra("SONG");
        tituio = findViewById(R.id.txtTituloMedia);
        descripcion = findViewById(R.id.txtDescriptionMedia);
        tituio.setText(intentTitulo);
    }

    private void setCoverAnimation(){
        portada = findViewById(R.id.imageView4);
        portada.setBackgroundResource(R.drawable.animation);
        soundAnimation = (AnimationDrawable) portada.getBackground();
        soundAnimation.start();
    }

    private void startMusicService(){
        //CREAR EL SERVICIO AL ELEGIR UNA CANCION
        music = new Intent(getApplicationContext(),MyService.class);

        //DETENER EL SERVICIO SI ES QUE YA EXISTE UNO
        stopService(music);

        //INICIAR EL SERVICIO CON LA NUEVA CANCION
        startService(music);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent mIntent = new Intent(this, MyService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //Toast.makeText(Reproductor.this, "Service is disconnected", 1000).show();
            mBounded = false;
            mServer = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //Toast.makeText(Reproductor.this, "Service is connected", 1000).show();
            mBounded = true;
            MyService.LocalBinder mLocalBinder = (MyService.LocalBinder)service;
            mServer = mLocalBinder.getServerInstance();

            //SELECCIONAR CANCION
            mServer.setSong(currenSong);

            //MANDAR REPRODUCCION ALEATORIA
            Intent a = getIntent();
            boolean aleatorio = a.getBooleanExtra("RANDOM",false);
            if(aleatorio){
                mServer.reproduccionAleatoria();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }



    class MiReceptorDifusion extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int nextSong = intent.getIntExtra("MENSAJE",0);
            tituio.setText(songsName[nextSong]);
        }
    }
}
