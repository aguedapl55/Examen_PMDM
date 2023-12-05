package com.example.examen_aguedapl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cronometro extends AppCompatActivity {

    TextView texto;
    public static List<String> l;
    String s;
    public static int horas, minutos, segundos;
    public static boolean pausado;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        //recycler view
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        l = new ArrayList<>();
        l.add("0:0:0");
        rv.setAdapter(new TimeAdapter(l));

        //inicializar variables
        texto = (TextView) findViewById(R.id.texto);
        horas = minutos = segundos = 0;
        s = "";
        pausado = true;
        generarHilo();
    }

    // METODOS ////////////////////////////////////////////////////////////////////////////////////

    public String tiempo() {
        if (segundos > 59) { //si hubiese más de 59 segundos, es decir, hubiese un minuto
            segundos = 0;
            minutos++;
        }
        if (minutos > 59) { //si hubiese más de 59 minutos, es decir, hubiese una hora
            minutos = 0;
            horas++;
        }
        String s = "" + horas + ":" + minutos + ":" + segundos;
        return s;
    }

    public void record(View view) { //boton RECORD //guarda los segundos en el recycler view cuando se presione el boton
        String tiempo = texto.getText().toString();
        l.add(tiempo);
        rv.setAdapter(new TimeAdapter(l)); //actualización rv, sin este comando no saldrian los tiempos en el rv
    }

    // HILO ///////////////////////////////////////////////////////////////////////////////////////

    public void generarHilo() { //genera un hilo que añade segundos al contador
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) { //sin este while, el contador finalizaria tras ser pausado
                    while (!pausado) { //cuando el contador NO este pausado
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {}
                        segundos++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                texto.setText(tiempo());
                            }
                        });
                    }
                }
            }
        });
    }

    // GETTERS SETTERS PAUSADO ////////////////////////////////////////////////////////////////////
        //estos metodos existen para que sea posible pausar y reanudar el contador

    public void setPausadoFalse(View view) { //boton PLAY //el cronometro NO esta pausado
        pausado = false;
    }

    public void setPausadoTrue(View view) { //boton PAUSE //el cronometro SI esta pausado
        pausado = true;
    }

}