package com.itmo.testgenpwd;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    boolean isChecked = false;
    String symbols = "ABC$DEF#GH%IJKL&MNO*PQ-RSTUVWX_YZ";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch switchType = findViewById(R.id.switchType);
        final Button generate = findViewById(R.id.generate);
        final EditText len = findViewById(R.id.len);
        final TextView password = findViewById(R.id.password);

        db = openOrCreateDatabase("pwd", MODE_PRIVATE,null);
        db.execSQL("create table if not exists passwords (password text)");

        switchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = b;
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = len.getText().toString();
                if(value.equals("")) {
                    Toast.makeText(MainActivity.this, "Ошибка: введите длину пароля", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(value) < 4 || Integer.parseInt(value) > 100) {
                    Toast.makeText(MainActivity.this, "Ошибка: длина пароля должна соответсвовать интервалу (4, 100)", Toast.LENGTH_LONG).show();
                } else {
                    int length = Integer.parseInt(value);
                    String pwd = generatePassword(length, isChecked);

                    password.setText(pwd);
                }
            }
        });
    }

    String generatePassword(int length, boolean isChecked) {
        String pwd = "";

        if(isChecked) {
            for(int i = 0; i < length; i++) {
                pwd += new Random().nextInt(10);

            }
        } else {
            for(int i = 0; i < length; i++) {
                pwd += symbols.charAt(new Random().nextInt(symbols.length()));
            }
        }

        if(db.isOpen()) {
            Log.d("db", "database is open");
            Cursor c = db.rawQuery("select password from passwords", null);

            int flag = 0;
            if(c.moveToFirst()) {
                Log.d("db", "raw is not empty");
                do {
                    Log.d("pwd from db", c.getString(0));
                    if(c.getString(0).equals(pwd)) {
                        flag = 1;
                        generatePassword(length, isChecked);
                    }

                } while(c.moveToNext());
            }
            if(flag == 0) {
                Log.d("db", "insert");
                db.execSQL("insert into passwords values('"+ pwd +"')");
            }
        }

        return pwd;
    }
}
