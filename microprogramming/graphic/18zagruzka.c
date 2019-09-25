asm(".code16gcc\n");

void setpixel(int, int, char);
int set(int, int, char);
int perenos(int, int);
void zaderzhka();
void zachistka();
int drawone();
int start()
{
	int i, mesto=10, b, g, j, z;
	zachistka();
	for (z = 0; z < 45; z++)
	{
		zachistka();
		mesto = drawone(mesto);
		zaderzhka();
		zachistka();
		mesto = mesto + 60;
		if (mesto > 127999)
			{
				mesto = 0;
			}
		zaderzhka();
	}
	while (1);
	return 0;
}
void setpixel(int x, int y, char color)
{
	char *p = (char*)0xA0000;
	p += (320 * y) + 2*x;
	*p = color;
}
int set(int razmer, int mesto, char color)
{
	int i;
	for (i = 0; i < razmer; i++)
	{
		char *p = (char*)0xA0000;
		p += mesto;
		*p = color;
		mesto = mesto + 2;
	}
	return(mesto);
}
int perenos(int kolvo, int mesto)
{
	int i;
	for (i = 0; i < kolvo; i++)
	{
		int y, x;
		y = mesto / 640;
		x = mesto % 640;
		y++;
		mesto = y * 640 + x-10;
	}
	return mesto;
}
void zaderzhka()
{
	int i, j, m;
	for (j = 0; j < 200; j++)
	{
		for (i = -9999; i < 9999; i = i + 2)
		{
			m = m + 1;
			m = m - 1;
			i--;
		}
		m=m + 1;
	}
}
void zachistka()
{
	int i;
	char color = 0;
	for (i = 0; i < 128000; i=i+2)
	{
		char *p = (char*)0xA0000;
		p += i;
		*p = color;
	}
}
int drawone(mesto)
{
	mesto = set(2, mesto, 0);//1
	mesto = set(1, mesto, 4);
	mesto = set(2, mesto, 0);
	mesto = perenos(1, mesto);
	mesto = set(2, mesto, 0);//2
	mesto = set(1, mesto, 4);
	mesto = set(2, mesto, 0);
	mesto = perenos(1, mesto);
	mesto = set(1, mesto, 0);//3
	mesto = set(2, mesto, 4);
	mesto = set(2, mesto, 0);
	mesto = perenos(1, mesto);
	mesto = set(2, mesto, 0);//4
	mesto = set(1, mesto, 4);
	mesto = set(2, mesto, 0);
	mesto = perenos(1, mesto);
	mesto = set(2, mesto, 0);//5
	mesto = set(1, mesto, 4);
	mesto = set(2, mesto, 0);
	mesto = perenos(1, mesto);
	mesto = set(2, mesto, 0);//6
	mesto = set(1, mesto, 4);
	mesto = set(2, mesto, 0);
	mesto = perenos(1, mesto);
	mesto = set(1, mesto, 0);//4
	mesto = set(3, mesto, 4);
	mesto = set(1, mesto, 0);
	mesto = perenos(1, mesto);
	return mesto;
}