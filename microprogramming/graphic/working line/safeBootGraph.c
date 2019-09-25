void putPixel(int x,int y,int color);
int start(){
	for (int i=0; i<500;i++) putPixel(i,i,0xff0000);
		while(1);
	return 0;
}
void putPixel(int x,int y,int color){
	int *p = 0x8000;
	char *c=*p;
	c+=((y*1024)+x)*3;
	*(int*)c=color;
}