package com.itmo.testasyncsort;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Integer arr[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button sort = findViewById(R.id.sort);
        final EditText len = findViewById(R.id.len);

//        int length = 10;
//        arr = new Integer[length];
//        for(int i = 0; i < length; i++) {
//            arr[i] = new Random().nextInt(10);
//        }
//
//        bubbleSort(arr);
//        for(int i = 0; i < length; i++) {
//            Log.d("sorted", arr[i] + "");
//        }

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = len.getText().toString();

                if(value.equals("")) {
                    Toast.makeText(MainActivity.this, "Ошибка: введите длину массива", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(value) < 2 || Integer.parseInt(value) > 1000000) {
                    Toast.makeText(MainActivity.this, "Ошибка: допустимый интервал значений (2, 1000000)", Toast.LENGTH_LONG).show();
                } else {
                    int length = Integer.parseInt(value);
                    arr = new Integer[length];

                    AsyncWorker a = new AsyncWorker();
                    a.execute(arr);
                }

                Log.d("click", "clicked!");
            }
        });
    }

    class AsyncWorker extends AsyncTask <Integer[], Void, Integer[]> {
        ProgressDialog p;
        TextView bubble = findViewById(R.id.bubble);
        TextView qsort = findViewById(R.id.qsort);
        double time1, time2;

        protected void onPreExecute(){
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setTitle("Сортировка");
            p.setMessage("Выполнение...");
            p.show();
        }

        @Override
        protected Integer[] doInBackground(Integer[]... a) {
            Integer[] arr = new Integer[a[0].length];
            for(int i=0; i < a[0].length; i++) {
                int num = new Random().nextInt(100);
                a[0][i] = num;
                arr[i] = num;
            }

            long start1 = SystemClock.currentThreadTimeMillis();
            bubbleSort(a[0]);
            long end1 = SystemClock.currentThreadTimeMillis() - start1;
            time1 = end1 * 0.001;

            long start2 = SystemClock.currentThreadTimeMillis();
            qsort(arr);
            long end2 = SystemClock.currentThreadTimeMillis() - start2;
            time2 = end2 * 0.001;

            for(int i = 0; i < 100; i++) {
                Log.d("sorted with qsort", arr[i] + "");
            }

            return a[0];
        }

        @Override
        protected void onPostExecute(Integer[] arr){
            super.onPostExecute(arr);

            bubble.setText("Сортировка пузырьком: " + time1 +" с.");
            qsort.setText("Быстрая сортировка: " + time2 +" с.");

            p.dismiss();
            Toast.makeText(MainActivity.this, "Сортировка завершена успешно", Toast.LENGTH_LONG).show();
            Log.d("sort", "END");
        }

    }

    public static void bubbleSort(Integer[] arr) {
        int buf;

        for(int i = 0; i < arr.length - 1; i++) {
            for(int j = i; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    buf = arr[i];
                    arr[i] = arr[j];
                    arr[j] = buf;
                }
            }
        }
    }

    public static void qsort(Integer arr[]) {
        if(arr.length < 2) {
            return;
        }

        final int pivor = arr[Math.round(arr.length/2)];
        final ArrayList less = new ArrayList<Integer>();
        final ArrayList greater = new ArrayList<Integer>();

        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < pivor) {
                less.add(arr[i]);
            }
            if(arr[i] > pivor) {
                greater.add(arr[i]);
            }
        }
        less.add(pivor);
        Collections.addAll(less, greater);
        Integer[] res = new Integer[less.size()];

        Log.d("test arr", res[0] + " " + less.size());
//        System.arraycopy(less.toArray(), 0, res, 0, less.size());


//        qsort(res);
        Arrays.sort(arr);
    }
}
