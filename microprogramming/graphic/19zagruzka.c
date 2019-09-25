void printChar(int, int, char, char);
int start()
{
	char text[] = "Protected Mode =)";
	int i;
	for (i = 0; text[i]; i++)
		printChar(i, 0, text[i], 4);
	while (1);
	return 0;
}

void printChar(int x, int y, char c, char color)
{
	char *p = (char*)0xB8000;
	p += ((y * 80) + x) * 2;
	*p = c;
	p++;
	*p = color;
}