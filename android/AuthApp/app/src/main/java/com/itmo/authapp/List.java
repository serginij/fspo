package com.itmo.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends Activity {
    String arr[] = {};
//    int counter = 0;
    String user;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        Intent i = getIntent();

        user = i.getStringExtra("user") + "1";

        final LinearLayout lay = findViewById(R.id.lay);
        final LinearLayout listForm = findViewById(R.id.listForm);
        final LinearLayout form = findViewById(R.id.form);

        form.setVisibility(View.GONE);

        final EditText text = findViewById(R.id.text);
        final EditText number = findViewById(R.id.number);

        db = this.openOrCreateDatabase(i.getStringExtra("db"), MODE_PRIVATE, null);

        db.execSQL("create table if not exists "+user+" (id int, name text, value float)");

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form.setVisibility(View.VISIBLE);
                initList(R.id.list, new String[]{});
            }
        });

        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
                form.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.form_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = text.getText().toString();
                String num = number.getText().toString();

                if(a.equals("") || num.equals("")) {
                    Toast.makeText(List.this, "Ошибка: поля не должны быть пустыми", Toast.LENGTH_LONG).show();
                } else {
                    float b = Float.parseFloat(num);
                    Log.d("insert", a + " " + b);
                    insert(a, b);
                    form.setVisibility(View.GONE);
                }


            }
        });
    }

    void initList(int id, String [] text) {
        ListView list = findViewById(id);
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, text));
    }

    void insert(String text, float value) {
        Log.d("user", user);
        db.execSQL("insert into "+user+" (name, value) values ('"+text+"',"+ value +")");
        Toast.makeText(List.this, "Запись добавлена успешно", Toast.LENGTH_LONG).show();
    }

    void select() {
        Cursor c = db.rawQuery("select * from "+user+";", null);
        ArrayList<String> a = new ArrayList<String>();
        int i=0;

        if(c.moveToFirst()) {
            do {
                Log.d("select from user table", c.getString(1));
                a.add(c.getString(1) +"\n"+ c.getFloat(2));
                Log.d("from array list", a.get(i));
                i++;
            } while (c.moveToNext());
        }
        String [] array = new String[a.size()];
        array = a.toArray(array);
        initList(R.id.list,  array);
    }
}
