package com.example.eva3_examen_mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    Cancion[] canciones = {
            new Cancion(R.drawable.music,"Frank Sinatra - That´s Life","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"El Tambor - Mi baile","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Sr. Columpio - El Tempo del Columpio","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Grupo Marrano - El ansioso","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Instrumento - El inicio","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Triangulo - La cancion del triangulo","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Legend of Zelda - Gerudo Valley","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Los Tucanes de Tijuana - La Chona","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Elevador - Elevandome a lo alto","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Rick Astley - Never Gona Give You Up","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Las Manos - Manitas Locas","Descripcion de la cancion"),
            new Cancion(R.drawable.music,"Europe - The Final Countdown","Descripcion de la cancion")
    };
    ListView listaClima;
    LinearLayout linearLayout;

    LinearLayout repAleatoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goingFullScreen();
        setContentView(R.layout.activity_main);

        listaClima = findViewById(R.id.listMusic);
                listaClima.setAdapter(new CancionAdapter(this,
                        R.layout.layout_cancion,canciones));
        listaClima.setOnItemClickListener(this);

        linearLayout = findViewById(R.id.repAleatoria);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cancion = randomNumber();
                Intent intent = new Intent(getApplicationContext(),Reproductor.class);
                intent.putExtra("SONG", canciones[cancion].getTitulo());
                intent.putExtra("NUMBER", cancion);
                intent.putExtra("RANDOM",true);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,Reproductor.class);
        intent.putExtra("SONG", canciones[i].getTitulo());
        intent.putExtra("NUMBER", i);
        startActivity(intent);

    }
    private void goingFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent music = new Intent(this,MyService.class);
        stopService(music);
    }

    private int randomNumber(){
        double randomDouble = Math.random();
        randomDouble = randomDouble * canciones.length;
        int randomInt = (int) randomDouble;
        return randomInt;
    }
}
