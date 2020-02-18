package com.itmo.testclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int count = 0;
    int time = 10;
    SharedPreferences pref;
    SQLiteDatabase db;
    Timer tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button add = findViewById(R.id.add);
        final Button reset = findViewById(R.id.reset);
        TextView last = findViewById(R.id.last);
        final TextView counter = findViewById(R.id.counter);
        final Button save = findViewById(R.id.save);
        final Button show = findViewById(R.id.show);
        final ListView list = findViewById(R.id.list);

        db = openOrCreateDatabase("clicker", MODE_PRIVATE, null);
        db.execSQL("create table if not exists results(res int(1000))");

        pref = getSharedPreferences("clicker", MODE_PRIVATE);
        last.setText("Последний " + pref.getInt("last",0));

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetApp();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0) {
                    startTimer();
                }
                if(time > 0) {
                    count++;
                    counter.setText(count + "");
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.isOpen()) {
                    db.execSQL("insert into results values("+ count +")");
                    Toast.makeText(MainActivity.this, "Результат сохранен", Toast.LENGTH_SHORT).show();
                    list.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Не удалось сохранить результат", Toast.LENGTH_SHORT).show();
                }
                resetApp();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setVisibility(View.VISIBLE);
                if(db.isOpen()) {
                    Cursor c = db.rawQuery("select res from results", null);
                    ArrayList<String> arr = new ArrayList<String>();

                    if(c.moveToFirst()) {
                       do{
                           arr.add(c.getString(0));
                       } while(c.moveToNext());

                       String[] array = new String[arr.size()];
                       array = arr.toArray(array);
                       initList(array);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Не удалось загрузить результаты", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startTimer() {
        final TextView timer = findViewById(R.id.timer);
        tm = new Timer();

        tm.schedule(new TimerTask() {
            @Override
            public void run() {
                time--;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.setText(time + ".0");
                    }
                });

                if(time == 0) {
                    tm.cancel();
                }
            }
        }, 1000, 1000);
    }

    public void resetApp() {
        TextView counter = findViewById(R.id.counter);
        TextView timer = findViewById(R.id.timer);
        TextView last = findViewById(R.id.last);
        pref = getSharedPreferences("clicker", MODE_PRIVATE);

        SharedPreferences.Editor ed = pref.edit();
        ed.putInt("last", count);
        ed.apply();

        last.setText("Последний " + count);
        count = 0;
        time = 10;
        counter.setText("0");
        timer.setText("10.0");
        tm.cancel();
    }

    public void initList(String arr[]){
        ListView list = findViewById(R.id.list);
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arr));
    }
}
