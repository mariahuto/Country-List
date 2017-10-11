package com.eet.pma.maria.countrylist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> country_list; //contenedor secuencial: podemos poner, borrar, mover,...!= tabla java

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("llista",country_list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        /**per si girem la pantalla, o sortim de l'app: es guardaràn els canvis que hem fet**/
        if(savedInstanceState == null){
            inicialitza();
        }
        else{
            Bundle state = savedInstanceState;
            country_list = state.getStringArrayList("llista");
        }

        ListView list = (ListView) findViewById(R.id.id_cl); //mostrar per pantalla

        /** Adaptador: totes les listview en tenen un. Fa de intermediari i va creant els escalons per anar baixant la llista**/
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,country_list);
        list.setAdapter(adapter); //posem l'adaptador a la llista


        /**quan clickem a un objecte de la llista**/
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {
                //parent = adaptador; item = quin s'ha clickat segons l'adaptador; pos = posició de l'objecte; id = la fila
                Toast.makeText(CountryListActivity.this,
                        getResources().getString(R.string.choose) + country_list.get(pos),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        /**quan clickem una estona sobre un objecte (per borrar i refrescar llista)**/
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                builder.setTitle(R.string.confirm);
                String missatge = getResources().getString(R.string.confirm_message); //missatge d'esborrar1
                builder.setMessage(missatge + country_list.get(pos) + " ?");

                /**boto per esborrar**/
                builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        country_list.remove(pos); //elimina país
                        adapter.notifyDataSetChanged(); //refresca; "notifica que han canviat les dades"
                    }
                });

                /**boto cancelar l'esborrat**/
                builder.setNegativeButton(android.R.string.cancel,null); //no hi ha 'listener'; no ha de fer res
                builder.create().show(); //mostra-ho per pantalla
                return true;
            }
        });
    }


    private void inicialitza() {
        String [] countries = getResources().getStringArray(R.array.countries);
        country_list = new ArrayList<>(Arrays.asList(countries)); //hem de canviar l'string[] a array abans de guardar
    }
}
