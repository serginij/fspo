const int fontAdrs = 0x8018; //менять если изменять boot.asm
const int fontCount = 58; //менять если изменять font.c
const int fontWidth = 5; //менять если изменять font.c
const int fontHeight = 6; //менять если изменять font.c
const int pixelWidth = 10; //менять если хотите
const int pixelHeight = 10; //менять если хотите
const int width = 1024; //ширина экрана
const int height = 768; //высота экрана

void drawChar (char, int, int); //вывод символа <символ>,<х>,<у>
void drawString (char*, int, int, int, int); //вывод символа <строка>,<х>,<у>,<расстояние между букв>, <задержка между выводом>
void drawRect(int, int, int, int); 
void putPixel(int, int, int);

void circle(int x, int y, int r,int color);
void rectangle(int x, int y, int a, int b, int color);
void wait(int time);

int start() {
  char str[] = "SHAPE";
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

    drawString(str, 330, 120, 20, 10000);
		
	while(1);
	return 0;
}

void drawString (char *arr, int x, int y, int space, int wait_t) {
	int i = 0;
		while(i<5) {
			drawChar(*arr, x, y);
			x += space+fontWidth*pixelWidth;
			i++;
			arr++; 
      wait(wait_t);
		}
}

void drawChar (char ch, int x, int y) {
	int positionChar;
	char *p = fontAdrs;

	if( (int)ch == 65) positionChar = 0;
	if( (int)ch == 66) positionChar = 1;
	if( (int)ch == 67) positionChar = 2;
	if( (int)ch == 68) positionChar = 3;
	if( (int)ch == 69) positionChar = 4;
	if( (int)ch == 70) positionChar = 5;
	if( (int)ch == 71) positionChar = 6;
	if( (int)ch == 72) positionChar = 7;
	if( (int)ch == 73) positionChar = 8;
	if( (int)ch == 74) positionChar = 8;
	if( (int)ch == 75) positionChar = 10;
	if( (int)ch == 76) positionChar = 11; 
	if( (int)ch == 77) positionChar = 12; 
	if( (int)ch == 78) positionChar = 13; 
	if( (int)ch == 79) positionChar = 14;  
	if( (int)ch == 80) positionChar = 15;    
	if( (int)ch == 81) positionChar = 16;    
	if( (int)ch == 82) positionChar = 17;    
	if( (int)ch == 83) positionChar = 18;     
	if( (int)ch == 84) positionChar = 19;     
	if( (int)ch == 85) positionChar = 20;     
	if( (int)ch == 86) positionChar = 21;    
	if( (int)ch == 87) positionChar = 22;   
	if( (int)ch == 88) positionChar = 23;  
	if( (int)ch == 89) positionChar = 24; 
	if( (int)ch == 90) positionChar = 25;	

	for(int i=0; i<positionChar; i++) p += fontWidth*fontHeight;

	for(int i=y; i<y+fontHeight*pixelHeight; i+=pixelHeight) {
		for(int j=x; j<x+fontWidth*pixelWidth; j+=pixelWidth) {
			if(*p == 1)
				drawRect(j,i,pixelWidth,pixelHeight);
        
			p++;
		}
	}
}

void drawRect(int x, int y, int pixelWidth, int pixelHeight) {
	for(int i=y; i<y+pixelHeight; i+=2)
		for(int j=x; j<x+pixelWidth; j++)
			putPixel(j,i,0xffffff);
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

