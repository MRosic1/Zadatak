package com.example.zadatak;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    List<Rezervacija> rezervacije;
    EditText pin,naz_restorana,broj_osoba,na_ime;
    TextView datum,vrijeme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pin = findViewById(R.id.editText);
        naz_restorana = findViewById(R.id.editText2);
        broj_osoba = findViewById(R.id.editText4);
        na_ime = findViewById(R.id.editText5);
        datum = findViewById(R.id.textView7);
        vrijeme = findViewById(R.id.textView8);
    }

    public void setDatum(View view) {
        DialogFragment datum = new DatePickerFragment();
        datum.show(getSupportFragmentManager(), "date picker");
    }

    public void setVrijeme(View view) {
        DialogFragment vrijeme = new TimePickerFragment();
        vrijeme.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String current = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView t = findViewById(R.id.textView7);
        t.setText(current);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView t2 = findViewById(R.id.textView8);
        t2.setText(hourOfDay + ":" + minute);
    }

    public void spremanjePodataka() {
        SharedPreferences save = getSharedPreferences("save", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        Gson g = new Gson();
        String spremanjeJson = g.toJson(rezervacije);
        editor.putString("podaci", spremanjeJson);
        editor.apply();
    }

    public void citanjePodataka() {
        SharedPreferences save = getSharedPreferences("save", Context.MODE_PRIVATE);
        Gson g = new Gson();
        String json = save.getString("podaci", null);
        Type tip = new TypeToken<ArrayList<Rezervacija>>() {
        }.getType();
        rezervacije = g.fromJson(json, tip);

        if (rezervacije == null) {
            rezervacije = new ArrayList<Rezervacija>();

        }
    }

    public void dodajRezervaciju(View view) {
        Intent i = new Intent(this, Unos.class);
        startActivityForResult(i, 1);
    }


    public void urediRezervaciju(View view){
        citanjePodataka();
        int trenutnipin = 0;
        try {
            trenutnipin = Integer.parseInt(pin.getText().toString());
        }
        catch (Exception e ){
            Toast.makeText(this,"Niste unijeli broj",Toast.LENGTH_SHORT).show();
            return;
        }
        for (Rezervacija rez: rezervacije ){
            if(rez.getPIN()==trenutnipin){
                rez.setIme(na_ime.getText().toString());
                rez.setRestoran(naz_restorana.getText().toString());
                rez.setBr_osoba(broj_osoba.getText().toString());
                rez.setVrijeme(vrijeme.getText().toString());
                rez.setDatum(datum.getText().toString());
                spremanjePodataka();
                Toast.makeText(this,"Rezervacija uspješno uređena",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void citajRezervaciju(View view){
        citanjePodataka();
        int trenutnipin = 0;
        try {
            trenutnipin = Integer.parseInt(pin.getText().toString());
        }
        catch (Exception e ){
            Toast.makeText(this,"Niste unijeli broj",Toast.LENGTH_SHORT).show();
            return;
        }
        for (Rezervacija rez: rezervacije ){
            if(rez.getPIN()==trenutnipin) {
                na_ime.setText(rez.getIme());
                vrijeme.setText(rez.getVrijeme());
                datum.setText(rez.getDatum());
                naz_restorana.setText(rez.getRestoran());
                broj_osoba.setText(rez.getBr_osoba());
                Toast.makeText(this, "Rezervacija uspješno učitana", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this,"Ne postojeći PIN",Toast.LENGTH_SHORT).show();
    }
    public void izbrisiRezervaciju(View view){
        citanjePodataka();
        int trenutnipin = 0;
        try {
            trenutnipin = Integer.parseInt(pin.getText().toString());
        }
        catch (Exception e ){
            Toast.makeText(this,"Niste unijeli broj",Toast.LENGTH_SHORT).show();
            return;
        }
        for (Rezervacija rez: rezervacije ){
            if(rez.getPIN()==trenutnipin){
                rezervacije.remove(rez);
                Toast.makeText(this,"Rezervacija uspješno obrisana",Toast.LENGTH_SHORT).show();
                pin.setText("");
                na_ime.setText("");
                naz_restorana.setText("");
                vrijeme.setText("");
                datum.setText("");
                broj_osoba.setText("");
                spremanjePodataka();
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                citanjePodataka();
                Rezervacija nova = new Rezervacija(
                        data.getStringExtra("naziv"),
                        data.getStringExtra("datum"),
                        data.getStringExtra("vrijeme"),
                        data.getStringExtra("broj_osoba"),
                        data.getStringExtra("na_ime")
                );
                do {
                    nova.setPIN();
                } while (provjera(rezervacije, nova));
                rezervacije.add(nova);
                pin.setText((nova.getPIN())+"");
                naz_restorana.setText((nova.getRestoran())+"");
                datum.setText(nova.getDatum()+"");
                vrijeme.setText(nova.getVrijeme()+"");
                broj_osoba.setText(nova.getBr_osoba()+"");
                na_ime.setText(nova.getIme()+"");
                spremanjePodataka();
                Toast.makeText(this,"Rezervacija uspješno dodana",Toast.LENGTH_SHORT).show();
            }
            }
        }
        public boolean provjera (List <Rezervacija> reze, Rezervacija nova){
            for (Rezervacija rezervacija : reze) {
                if (rezervacija.getPIN() == nova.getPIN()) {
                    return true;
                }
            }
            return false;
        }
    }

