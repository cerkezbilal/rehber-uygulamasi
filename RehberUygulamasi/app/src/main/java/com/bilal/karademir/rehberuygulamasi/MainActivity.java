package com.bilal.karademir.rehberuygulamasi;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import  android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private FloatingActionButton fb;
    RecyclerView rv;
    ArrayList<Kisiler> kisilerArrayList;
    KisilerAdapter adapter;
    Context context = this;
    Veritabani vt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        fb = findViewById(R.id.fb);
        rv = findViewById(R.id.rv);
        vt = new Veritabani(context);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        toolbar.setTitle("Rehber");
        setSupportActionBar(toolbar);

        kisilerArrayList = new KisilerDao().tumKisler(vt);

        adapter = new KisilerAdapter(context,kisilerArrayList,vt);
        rv.setAdapter(adapter);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertGoster();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem item = menu.findItem(R.id.action_ara);
       SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
       searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //Log.e("onQueryTextSubmit",query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        //Log.e("onQueryTextChange",newText);

        kisilerArrayList = new KisilerDao().kisiAra(vt,newText);

        adapter = new KisilerAdapter(context,kisilerArrayList,vt);
        rv.setAdapter(adapter);
        return false;
    }

    public void alertGoster(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim,null);
        final EditText editTextAd = tasarim.findViewById(R.id.editTextAd);
        final EditText editTextTel = tasarim.findViewById(R.id.editText2Tel);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("Kişi Ekle");
        ad.setView(tasarim);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String ad = editTextAd.getText().toString().trim();
                String tel = editTextTel.getText().toString().trim();

                new KisilerDao().kisiEkle(vt,ad,tel);
                kisilerArrayList = new KisilerDao().tumKisler(vt);
                adapter = new KisilerAdapter(context,kisilerArrayList,vt);
                rv.setAdapter(adapter);

                //Toast.makeText(context, "Adı: "+ad+" Tel: "+tel, Toast.LENGTH_SHORT).show();

            }
        });

        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.create().show();


    }
}
