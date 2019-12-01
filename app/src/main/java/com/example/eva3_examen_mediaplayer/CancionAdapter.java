package com.example.eva3_examen_mediaplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CancionAdapter extends ArrayAdapter<Cancion> {
    Context contexto;
    int recurso;
    Cancion[] lista_canciones;


    //contexto de la app, resource es el layout, el objeto clima
    public CancionAdapter(Context context, int resource, Cancion[] objects) {
        super(context, resource, objects);
        contexto = context;
        recurso = resource;
        lista_canciones = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgCancion;
        TextView txtTitulo, txtDescripcion;

        //CONVERTVIEW ES UNA FILA DE LA LISTA
        if(convertView == null){
            //CREAR NUESTRO LAYOUT
            //INFLATER
            LayoutInflater lInflater = ((Activity)contexto).getLayoutInflater();
            convertView = lInflater.inflate(recurso,parent,false);
        }
        //SACAMOS DE LA CONVERVIEW LOS DATOS QUE NECESITAMOS
        imgCancion = convertView.findViewById(R.id.imgCancion);
        txtTitulo= convertView.findViewById(R.id.txtTituloMedia);
        txtDescripcion = convertView.findViewById(R.id.txtDescripcion);
        //LO LLENAMOS
        imgCancion.setImageResource(lista_canciones[position].getImagen());
        txtTitulo.setText(lista_canciones[position].getTitulo());
        txtDescripcion.setText(lista_canciones[position].getDescripcion());

        return convertView;
    }
}
