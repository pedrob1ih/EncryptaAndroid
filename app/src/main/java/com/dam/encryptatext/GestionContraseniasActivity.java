package com.dam.encryptatext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dam.Encripta.Encryptacion;

public class GestionContraseniasActivity extends AppCompatActivity {

    private TextView tVCajaDeTexto;
    private Button bGuardar,bVerPatron;
    private EditText eTEntradaUsuario;
    private Encryptacion e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_contrasenias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tVCajaDeTexto= (TextView) findViewById(R.id.tVCajaDeTexto);
        bGuardar = (Button) findViewById(R.id.bGuardar);
        bVerPatron= (Button) findViewById(R.id.bVerPatron);
        eTEntradaUsuario= (EditText) findViewById(R.id.eTEntradaUsuario);


        bGuardar.setOnClickListener(listenetGuardar);
        bVerPatron.setOnClickListener(listenetVerPatron);
        Bundle b= this.getIntent().getExtras();
        if(b!=null){
            e= new Encryptacion(b.getString("nombreDelFichero"));
        }
        else
            e= new Encryptacion("FicheroClaves");


    }

    private String formaListado(){
//        e.ge
        int lista[][]=e.getaPatrones();
        String salida="";
        for(int i=0;i<lista.length;i++){
            for(int j=0;j<lista[0].length;j++){
                salida+=lista[i][j]+":";
            }
            salida+="\n";
        }
        return salida;
    }
    View.OnClickListener listenetGuardar= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                e.generaClaves(eTEntradaUsuario.getText().toString());
                e.guardarClavePublica();
                tVCajaDeTexto.setText(formaListado());
            } catch (Exception e1) {
                tVCajaDeTexto.setText(e1.getMessage());
            }
        }
    };
    View.OnClickListener listenetVerPatron= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tVCajaDeTexto.setText(formaListado());
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestioncontrasenias, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mainActivity) {
            Intent i = new Intent(GestionContraseniasActivity.this,MainActivity.class);
//            i.putExtra("nombreDelFichero", e.getNombreFichero());
            setResult(Activity.RESULT_OK, i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
