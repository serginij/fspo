void putPixel(int x,int y,int color);
void circle(int x, int y, int r,int color);

void rectangle(int x, int y, int a, int b, int color);
void wait(int time);

int start(){
	int i,j;
  // x = 512 y = 384 a = 20 b = 30 startX = 502 startY = 369
  for(i=0; i < 3000;i+=40) {
      circle(0, i, i, 0xff0000);
      wait(10000);
  }
  for(i=0; i < 3000;i+=40) {
      circle(0, i, i, 0x000000);
      wait(9000);
    }
  int size = 15;
    for(i=0; i < 400; i+=size) {
      rectangle(502-2*i/3, 369-i, 20+5*i/4, 30+2*i, 0xffffff);
      if(i > 40) {
        rectangle(502-2*(i-size*5)/3, 369-(i-size*5), 20+5*(i-size*5)/4, 30+2*(i-size*5), 0x000000);
      }
      wait(50000);
    }

    for(i=0; i<25; i+=5) circle(400+i, 394, 100, 0x00ff00);

    for(i=0; i<100;i+=5) {
      
      circle(550+i, 300, 20, 0x00ff00);
      circle(550+i, 400, 20, 0x00ff00);
      circle(550+i, 500, 20, 0x00ff00);

      circle(550, 300+i, 20, 0x00ff00);
      circle(650, 400+i, 20, 0x00ff00);
      wait(10000);
      
    }
    
		while(1);
	return 0;
}

void putPixel(int x,int y,int color){
	int *p = 0x8000;
	char *c=*p;
	c+=((y*1024)+x)*3;
	*(int*)c=color;
}

void circle(int x, int y, int r,int color){ //X,Y - координаты центра, R - радиус, color-цвет
  int xd = 0;
  int yd = r;
  int delta = 1 - 2 * r;
  int error = 0;
  while (yd >= 0){
       putPixel(x + xd, y + yd, color);
       putPixel(x + xd, y - yd, color);
       putPixel(x - xd, y + yd, color);
       putPixel(x - xd, y - yd, color);
       error = 2 * (delta + yd) - 1;
       if ((delta < 0) && (error <= 0)){
           delta += 2 * ++xd + 1;
           continue;
       }
       if ((delta > 0) && (error > 0)){
           delta -= 2 * --yd + 1;
           continue;
       }
      //  wait(1000);
       delta += 2 * (++xd - yd--);
   }
}

void wait(int time){  
  for(int i=0; i<time*100; i++);
}

void rectangle(int x, int y, int a, int b, int color) {
  int i;
  for(i=x; i<x+a;i++) {
      putPixel(i, y, color);
      putPixel(i, y+b, color);
  }
  for(i=y; i<y+b;i++) {
    putPixel(x, i, color);
    putPixel(x+a, i, color);
  }
}