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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    int counter = 0;
    String user;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        db = this.openOrCreateDatabase("data", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE if not exists users(name text, secName text, thName text, login text, password text, phone text, counter integer);");

        final LinearLayout lay = findViewById(R.id.main);
        final LinearLayout auth = findViewById(R.id.auth);
        final LinearLayout reg = findViewById(R.id.reg);

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = 0;
                String login = ((EditText)findViewById(R.id.login)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();

                if(db.isOpen() == true) {
                    Cursor c = db.rawQuery("select * from users;", null);
                    if(c.moveToFirst()) {

                        do {
                            Log.d("user", c.getString(3));

                            if (c.getString(3).equals(login) && c.getString(4).equals(password)) {
                                lay.removeView(auth);
                                lay.removeView(reg);
                                flag = 1;
                                counter = c.getInt(6);

                                user = c.getString(3);
                                Intent i = new Intent(MainActivity.this, List.class);

                                i.putExtra("user", user);
                                i.putExtra("db", "data");

                                startActivity(i);
                            }

                        } while (c.moveToNext());

                    }
                    if (flag == 0) {
                        Toast.makeText(MainActivity.this, "Error in login / password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        findViewById(R.id.regButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay.removeView(auth);
            }
        });

        findViewById(R.id.regOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText)findViewById(R.id.regName)).getText().toString();
                String secName = ((EditText)findViewById(R.id.regSecName)).getText().toString();
                String thName = ((EditText)findViewById(R.id.regThName)).getText().toString();
                String login = ((EditText)findViewById(R.id.regLogin)).getText().toString();
                String password = ((EditText)findViewById(R.id.regPassword)).getText().toString();
                String phone = ((EditText)findViewById(R.id.regPhone)).getText().toString();


                if(false) {
                    Toast.makeText(MainActivity.this, "Ошибка в регистрации", Toast.LENGTH_LONG).show();
                }

                if(name.equals("") || secName.equals("") || thName.equals("") || login.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "Не все поля заполнены\nПоля Имя, Фамилия, Отчество, Логин, Пароль обязательны для ввода", Toast.LENGTH_LONG).show();
                }
                else {
                    insert(name, secName, thName, login, password, phone, counter);
                    Toast.makeText(MainActivity.this, "Регистрация произошла успешно", Toast.LENGTH_LONG).show();
                    lay.removeView(reg);
                    lay.addView(auth);
                }

            }
        });

    }

    void insert(String name, String secName, String thName, String login, String password, String phone, int counter) {

        db.execSQL("insert into users values('"+ name +"','"+ secName +"','"+ thName +"','"+ login +"','"+ password +"','"+ phone +"',"+ counter +")");
    }

}
