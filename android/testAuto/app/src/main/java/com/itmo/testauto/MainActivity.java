package com.itmo.testauto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Paint p;
    int primary, secondary;
    int x1 = 0, y1 = 0, x2 = 5, y2 = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Graphic G = new Graphic(this);
        G.initPaint(0xff0000ff, 0xffff0000, 45);
        setContentView(G);
    }

    class Graphic extends View{
        public Graphic(Context context) {
            super(context);
        }

        public void initPaint(int primaryColor, int background, int width) {
            primary = primaryColor;
            secondary = background;
            p = new Paint();
            p.setStrokeWidth(width);
            p.setColor(background);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int size = 78, offset = 2;

            for(int i = 0; i < 9; i++) {
                for(int j = 0; j < 6; j++) {
                    if(j == x1 && i == y1 || j == x2 && i == y2) {
                        if(Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1) {
                            p.setColor(primary);
                        } else {
                            generateCords();
                            if(j == x1 && i == y1 || j == x2 && i == y2) {
                                p.setColor(primary);
                            }
                        }
                    } else {
                        p.setColor(secondary);
                    }
                    canvas.drawRect(j*size + offset*j, i*size + offset*i, j*size + size + j*offset, i*size + size + offset*i, p);
                }
            }

            generateCords();

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            invalidate();
        }

        protected void generateCords() {
//            Log.d("(x, y)", x1 + " " + y1);

            int randX1, randY1, randX2, randY2;

            randX1 = new Random().nextInt(100);
            randY1 = new Random().nextInt(100);

            if(randX1 > 50) {
                if(x1 == 5) {
                    x1 = 0;
                } else {
                    x1++;
                }
            } else if(randY1 < 50){
                if(x1 == 0) {
                    x1 = 5;
                } else {
                    x1--;
                }
            }

            if(randY1 > 50 && randX1 < 51) {
                if(y1 == 8) {
                    y1 = 0;
                } else {
                    y1++;
                }
            } else if(randX1 < 51 ){
                if(y1 == 0) {
                    y1 = 8;
                } else {
                    y1--;
                }
            }

//            Log.d("new (x, y)", x1 + " " + y1);

            randX2 = new Random().nextInt(100);
            randY2 = new Random().nextInt(100);

            if(randX2 > 50) {
                if(x2 == 5) {
                    x2 = 0;
                } else {
                    x2++;
                }
            } else if(randY2 < 50){
                if(x2 == 0) {
                    x2 = 5;
                } else {
                    x2--;
                }
            }

            if(randY2 > 50 && randX2 < 51) {
                if(y2 == 8) {
                    y2 = 0;
                } else {
                    y2++;
                }
            } else if(randX2 < 51 ){
                if(y2 == 0) {
                    y2 = 8;
                } else {
                    y2--;
                }
            }
        }
    }
}
