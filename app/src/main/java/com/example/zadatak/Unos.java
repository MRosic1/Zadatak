package com.example.zadatak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class Unos extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText naz_restorana,broj_osoba,na_ime;
    TextView datum,vrijeme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos);
        naz_restorana = findViewById(R.id.editText3);
        broj_osoba = findViewById(R.id.editText7);
        na_ime = findViewById(R.id.editText8);
        datum = findViewById(R.id.textView16);
        vrijeme = findViewById(R.id.textView17);

    }
    public void setDatumInitial(View view){
        DialogFragment datum = new DatePickerFragment();
        datum.show(getSupportFragmentManager(),"date picker");
    }
    public void setVrijemeInitial(View view){
        DialogFragment vrijeme = new TimePickerFragment();
        vrijeme.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String current = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView t = findViewById(R.id.textView16);
        t.setText(current);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView t2 = findViewById(R.id.textView17);
        t2.setText(hourOfDay+":"+minute);
    }
    public void vrati(View view){
        Intent i = getIntent();
        i.putExtra("naziv",naz_restorana.getText().toString());
        i.putExtra("datum",datum.getText().toString());
        i.putExtra("vrijeme",vrijeme.getText().toString());
        i.putExtra("broj_osoba",broj_osoba.getText().toString());
        i.putExtra("na_ime",na_ime.getText().toString());
        setResult(RESULT_OK,i);
        finish();

    }
}
