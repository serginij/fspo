package com.itmo.testblocks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

class Graphics extends View {
    protected Paint p;
    protected int colors[] = {0xffF44336, 0xff1976D2, 0xffffffff};

    public Graphics(Context context) {
        super(context);
    }

    public void initPaint(int color, int width) {
        p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(width);
        p.setTextSize(50);
    }

    public void setColor(int color) {
        p.setColor(color);
    }

    @Override
    protected void onDraw(Canvas c) {
        int size = 80, offset = 2;

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 6; j++) {
                setColor(colors[new Random().nextInt(colors.length)]);
                c.drawRect(j*size + (offset*j), i*size + offset*i, j*size + size + offset*j, i*size + size + offset*i, p);
            }
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Graphics G = new Graphics(this);
        G.initPaint(0xffffffff, 80);
        setContentView(G);
    }
}
