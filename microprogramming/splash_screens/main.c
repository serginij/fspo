void putPixel(int x,int y,int color);
void drawRhombus(int x, int y, int size, int color);
void circle(int x, int y, int r,int color);
void square(int x, int y, int r, int color);
void rectangle(int x, int y, int a, int b, int color);
void wait(int time);
void drawHorizontal(int x, int y, int size, int color);

int start(){
  char card;
	int i,j;
	for (i=0; i<1024;i++) {
		for(j=0; j<768;j++){
			putPixel(i,j,0x35800f);
			
		}
			// Загрузочный экран - Большой туз по центру, карта переворачиватся, появляется надпись  Card os
	}
		square(0, 0, 100, 0xff00ff);
		drawRhombus(100, 200, 50, 0xff0000);
    for(i=600; i<1000;i+=2) {
      circle(i, i, 70, 0x00ff00);
      wait(1000);
    }
		
    for(i=300; i<420;i+=2) {
      circle(i, i, 100, 0x00ff00);
      wait(1000);
    }
    for(i=420; i<800;i+=2) {
      circle(i, 420, 50, 0x00ff00);
      wait(1000);
    }
    // rectangle(300, 300, 200, 300, 0x000000);
    for (i=301; i < 600; i+=2) {
      drawHorizontal(301, i, 199, 0xffffff);
    }
    drawRhombus(400, 400, 10, 0xff0000);
		while(1);
	return 0;
}

void putPixel(int x,int y,int color){
	int *p = 0x8000;
	char *c=*p;
	c+=((y*1024)+x)*3;
	*(int*)c=color;
}

void drawRhombus(int x, int y, int size, int color) {
	int i, j;
	for(i = 0;i < size; i++) {
		for(j = 0; j < size; j++) {
				putPixel(j+x+i, j+y-i, color);
				putPixel(j+x+i, j+y-i+1, color);
        wait(1000);
			}
	}
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

void square(int x, int y, int r, int color){ // X, Y - координаты с левого верхнего угла, R - длина стороны квадрата, Color - цвет
  int i, j;
  for(i=0; i <= r; i++){
    for(j=0; j <= r; j++){
      putPixel(i+x, j+y, color);
    }
  }
}

void wait(int time){  
  for(int i=0; i<time*100; i++);
}

void drawHorizontal(int x, int y, int size, int color){
  int i;
  for(i=x; i<x+size;i++) {
      putPixel(i, y, color);
  }
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