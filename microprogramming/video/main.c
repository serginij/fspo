void putPixel(int, int, int );

int start (){
	int i;
	for (i=0; i<500; i++) {
		putPixel(i,i, 0xffff00);
	}
	while(1);
	return 0;
}

void putPixel(int x, int y, int color) {
	int *a = (int* )0x8000;
	char *p = a;
	p += ((y*1024) + x)*3;
	*(int *)p=color;
}