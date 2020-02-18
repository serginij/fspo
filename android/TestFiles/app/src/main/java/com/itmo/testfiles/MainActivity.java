package com.itmo.testfiles;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends Activity {

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button find = findViewById(R.id.find);
        final Button replace = findViewById(R.id.replace);
        final Button open = findViewById(R.id.open);
        final Button save = findViewById(R.id.save);
        final Button saveSel = findViewById(R.id.saveSelected);

        final EditText text = findViewById(R.id.text);
        pref = getSharedPreferences("files", MODE_PRIVATE);

        text.setText(pref.getString("text", ""));

        final SharedPreferences.Editor ed = pref.edit();

        TextWatcher w = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed.putString("text", text.getText().toString());
                ed.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        text.addTextChangedListener(w);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile();
            }
        });

        saveSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSelected();
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find();
            }
        });

        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replace();
            }
        });
    }

    public void saveFile() {
        final EditText text = findViewById(R.id.text);
        final EditText path = findViewById(R.id.savepathfile);

        String file = path.getText().toString();

        if(!checkEmpty(R.id.savepathfile)) {

            File f = new File(Environment.getExternalStorageDirectory() + "/" + file);

            try {
                FileWriter fw = new FileWriter(f);
                fw.write(text.getText().toString());
                fw.flush();
                fw.close();

                Toast.makeText(MainActivity.this, "Файл успешно сохранен", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Не удалось сохранить файл", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public void saveSelected() {
        final EditText text = findViewById(R.id.text);
        final EditText path = findViewById(R.id.savepathfile);

        String file = path.getText().toString();

        if(!checkEmpty(R.id.savepathfile)) {

            File f = new File(Environment.getExternalStorageDirectory() + "/" + file);

            String textToSave = text.getText().toString().subSequence(text.getSelectionStart(), text.getSelectionEnd()).toString();

            try {
                FileWriter fw = new FileWriter(f);
                fw.write(textToSave);
                fw.flush();
                fw.close();

                Toast.makeText(MainActivity.this, "Файл успешно сохранен", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Не удалось сохранить файл", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public void openFile() {
        final EditText text = findViewById(R.id.text);
        final EditText path = findViewById(R.id.openfilename);

        String file = path.getText().toString();

        if(!checkEmpty(R.id.openfilename)) {

            File f = new File(Environment.getExternalStorageDirectory() + "/" + file);
            char buf[] = new char[(int) f.length()];

            try {
                FileReader fr = new FileReader(f);
                fr.read(buf);
                fr.close();
                Toast.makeText(MainActivity.this, "Файл успешно загружен", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(MainActivity.this, "Не удалось открыть файл", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Не удалось открыть файл", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            String newText = "";
            for (int i = 0; i < f.length(); i++) {
                newText += buf[i];
            }
            text.setText(newText);
        }
    }

    public void find() {
        final EditText text = findViewById(R.id.text);
        final EditText find = findViewById(R.id.findtext);

        String fileText = text.getText().toString();
        String findText = find.getText().toString();

        if(!checkEmpty(R.id.findtext)) {

            int c = 0;
            int i = fileText.indexOf(findText);
            while (i < fileText.lastIndexOf(findText)) {
                i += fileText.indexOf(findText, i + 1);
                c++;
            }

            if (i > 0) {
                c++;
            }

            Toast.makeText(MainActivity.this, "Найдено совпадений: " + c, Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkEmpty(int id) {
        EditText text = findViewById(id);
        boolean isEmpty = text.getText().toString().equals("");

        if(isEmpty) {
            Toast.makeText(MainActivity.this, "Ошибка: введите название файла", Toast.LENGTH_LONG).show();
        }
        return isEmpty;
    }

    public void replace() {
        final EditText text = findViewById(R.id.text);
        final EditText find = findViewById(R.id.findtext);
        final EditText replace = findViewById(R.id.replacetext);

        String fileText = text.getText().toString();
        String findText = find.getText().toString();
        String replaceText = replace.getText().toString();

        String textBefore = fileText;
        Log.d("textBefore", textBefore);

        text.requestFocus();
        text.setSelection(fileText.indexOf(findText));
        text.setText(fileText.replaceFirst(findText, replaceText));

        Log.d("textBefore", textBefore);
        Log.d("text", text.getText().toString());

        if(textBefore.equals(text.getText().toString())) {
            Toast.makeText(MainActivity.this, "Совпадений не найдено", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Текст успешно заменен", Toast.LENGTH_LONG).show();
        }
    }
}
