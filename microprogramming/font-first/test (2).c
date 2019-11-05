const int fontAdrs = 0x8000; //менять если изменять boot.asm
const int fontCount = 58; //менять если изменять font.c
const int fontWidth = 5; //менять если изменять font.c
const int fontHeight = 6; //менять если изменять font.c
const int pixelWidth = 4; //менять если хотите
const int pixelHeight = 4; //менять если хотите
const int width = 1024; //ширина экрана
const int height = 768; //высота экрана

void drawChar (char, int, int); //вывод символа <символ>,<х>,<у>
void drawString (char*, int, int, int); //вывод символа <строка>,<х>,<у>,<расстояние между букв>
void drawRect(int, int, int, int); 
void putPixel(int, int, int);

int start() {
	char str[] = "DJ TAPE";
	drawString(str, width/10, height/2, 4);
	drawChar('A',width/2,height/2+pixelHeight*fontHeight+5);
	while(1);
	return 0;
}

void drawString (char *arr, int x, int y, int space) {
	int i = 0;
		while(*arr) {
			drawChar(*arr, x, y);
			x += space+fontWidth*pixelWidth;
			i++;
			arr++; 
		}
}

void drawChar (char ch, int x, int y) {
	int positionChar;
	char *p = fontAdrs+0x2018;

	//U can delete this and remake switch() as if() below, sorry mne len'
	if( (int)ch == 65) positionChar = 0;
	if( (int)ch == 66) positionChar = 1;
	if( (int)ch == 67) positionChar = 2;
	if( (int)ch == 68) positionChar = 3;
	if( (int)ch == 69) positionChar = 4;
	if( (int)ch == 70) positionChar = 5;
	if( (int)ch == 71) positionChar = 6;
	if( (int)ch == 72) positionChar = 7;
	if( (int)ch == 73) positionChar = 8;
	if( (int)ch == 74) positionChar = 9;
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

	if( (int)ch == 1040) positionChar = 26; //А
	if( (int)ch == 1041) positionChar = 27; //Б
	if( (int)ch == 1042) positionChar = 28; //В
	if( (int)ch == 1043) positionChar = 29; //Г
	if( (int)ch == 1044) positionChar = 30; //Д
	if( (int)ch == 1045) positionChar = 31; //Е
	if( (int)ch == 1046) positionChar = 32; //Ж
	if( (int)ch == 1047) positionChar = 33; //З
	if( (int)ch == 1048) positionChar = 34; //И
	if( (int)ch == 1049) positionChar = 35; //Й
	if( (int)ch == 1050) positionChar = 36; //К
	if( (int)ch == 1051) positionChar = 37; //Л
	if( (int)ch == 1052) positionChar = 38; //М
	if( (int)ch == 1053) positionChar = 39; //Н
	if( (int)ch == 1054) positionChar = 40; //О
	if( (int)ch == 1055) positionChar = 41; //П
	if( (int)ch == 1056) positionChar = 42; //Р
	if( (int)ch == 1057) positionChar = 43; //С
	if( (int)ch == 1058) positionChar = 44; //Т
	if( (int)ch == 1059) positionChar = 45; //У
	if( (int)ch == 1060) positionChar = 46; //Ф
	if( (int)ch == 1061) positionChar = 47; //Х
	if( (int)ch == 1062) positionChar = 48; //Ц
	if( (int)ch == 1063) positionChar = 49; //Ч
	if( (int)ch == 1064) positionChar = 50; //Ш
	if( (int)ch == 1065) positionChar = 51; //Щ
	if( (int)ch == 1066) positionChar = 52; //Ъ
	if( (int)ch == 1067) positionChar = 53; //Ы
	if( (int)ch == 1068) positionChar = 54; //Ь
	if( (int)ch == 1069) positionChar = 55; //Э
	if( (int)ch == 1070) positionChar = 56; //Ю
	if( (int)ch == 1071) positionChar = 57; //Я

	// switch ((int)ch) {
	// 	case 65: 
	// 		positionChar = 0;
	// 		break;
	// 	case 66: 
	// 		positionChar = 1;
	// 		break;
	// 	case 67: 
	// 		positionChar = 2;
	// 		break;
	// 	case 68: 
	// 		positionChar = 3;
	// 		break;
	// 	case 69: 
	// 		positionChar = 4;
	// 		break;
	// 	case 70: 
	// 		positionChar = 5;
	// 		break;
	// 	case 71: 
	// 		positionChar = 6;
	// 		break;
	// 	case 72: 
	// 		positionChar = 7;
	// 		break;
	// 	case 73: 
	// 		positionChar = 8;
	// 		break;
	// 	case 74: 
	// 		positionChar = 9;
	// 		break;
	// 	case 75: 
	// 		positionChar = 10;
	// 		break;
	// 	case 76: 
	// 		positionChar = 11;
	// 		break;
	// 	case 77: 
	// 		positionChar = 12;
	// 		break;
	// 	case 78: 
	// 		positionChar = 13;
	// 		break;
	// 	case 79: 
	// 		positionChar = 14;
	// 		break;
	// 	case 80: 
	// 		positionChar = 15;
	// 		break;
	// 	case 81: 
	// 		positionChar = 16;
	// 		break;
	// 	case 82: 
	// 		positionChar = 17;
	// 		break;
	// 	case 83: 
	// 		positionChar = 18;
	// 		break;
	// 	case 84: 
	// 		positionChar = 19;
	// 		break;
	// 	case 85: 
	// 		positionChar = 20;
	// 		break;
	// 	case 86: 
	// 		positionChar = 21;
	// 		break;
	// 	case 87: 
	// 		positionChar = 22;
	// 		break;
	// 	case 88: 
	// 		positionChar = 23;
	// 		break;
	// 	case 89: 
	// 		positionChar = 24;
	// 		break;
	// 	case 90: 
	// 		positionChar = 25;
	// 		break;
	// 	default:
	// 		positionChar = 0;
	// 		break;
	// }

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
	for(int i=y; i<y+pixelHeight; i++)
		for(int j=x; j<x+pixelWidth; j++)
			putPixel(j,i,0xffffff);
}

void putPixel(int x,int y,int color){
	int *p = 0x8000;
	char *c=*p;
	c+=((y*1024)+x)*3;
	*(int*)c=color;
}
