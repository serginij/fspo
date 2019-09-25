void setPixel(int, int, int );

int start (){
	int i;
	for (i=0; i<500; i++) {
		setPixel(i, i, 0xffffff);
	}

	while(1);
	return 0;
}

void setPixel(int x, int y, int color) {
	// char *a = (char*)0xa000;
	// char *p = a;
	// p += ((y*1024) + x)*3;
	// *(int *)p=color;
	int *p = 0x8000;
	char *c = *p;
	c += ((y * 1024) + x) * 3;
	*(int*)c = color;
}
