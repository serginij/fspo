#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void SSE_Work(int *a,int *b,int size){
    asm(".intel_syntax;"
        "mov edi,[ebp+8];"
        "mov esi,[ebp+12];"
    "mov ecx,[ebp+16];"
    "for:;"
        "movdqu xmm0,[edi];"
        "por xmm0,[esi];"
        "movdqu [edi],xmm0;"
        "add esi,16;"
        "add edi,16;"
        "sub ecx,4;"
    "jnz for;"
        );
}

void checkMenu(int a) {
    if(a != 1) {
        puts("Введено неверное значение");    
        puts("1 - Усилить синий канал изображения\n0 - Выход");
        scanf("%d", &a);
        if(a != 1) {
            checkMenu(a);
        }
    }
}

void checkFile(FILE *f, char path[]) {
    if(!f) {
        puts("Неправильное название изображения");
        puts("Введите название изображения");
        scanf("%s", path);
        f = fopen(path, "rb+");
        if(!f) {
            checkFile(f, path);
        }
    }
}

int main(){
    char path[128], newPath[128];
    int type, menu = 1;
    while(menu){
        puts("1 - Усилить синий канал изображения\n0 - Выход");
        scanf("%d", &menu);
        fflush(stdin);
        if(menu == 0){
            break;
        }
        checkMenu(menu);
        puts("Введите название изображения");
        scanf("%s", &path);

        FILE *f = fopen(path, "rb+");
        checkFile(f, path);
        f = fopen(path, "rb");
        int w,h,d,i,j;

        fseek(f, 18, SEEK_SET);
        fread(&w, sizeof(w), 1, f);
        fread(&h, sizeof(h), 1, f);
        fseek(f, 2, SEEK_CUR);
        fread(&d, 2, 1, f);
        fseek(f, 26, SEEK_CUR);

        int *header = (int*)malloc(56 * sizeof(int*));

        int **pixmap = (int**)malloc(h * sizeof(int**));
        for (i = 0; i < h; i++) {
            pixmap[i] = (int*)malloc(w * sizeof(int*));
            for(j = 0; j < w; j++) {
                fread(&pixmap[i][j], sizeof(int), 1, f);
            }
        }

        unsigned int *mask=malloc(w*sizeof(int*));
        int maskColor;
        maskColor = 0xff0000;
        strcat(newPath, "blue-");

        for(i = 0; i < w; i++) {
            mask[i] = maskColor;
        }
        for(i = 0; i < h; i++) {
            SSE_Work(pixmap[i], mask, 1024);
        }

        rewind(f);
        fseek(f, 0, SEEK_SET);

        for(j = 0; j < 56; j++) {
            fread(&header[j], sizeof(int), 1, f);
        }

        fseek(f, 56, SEEK_SET);
        fclose(f);
        strcat(newPath, path);
        
        FILE *new = fopen(newPath, "wb");

        for(j = 0; j < 56; j++) {
            fwrite(&header[j], sizeof(int), 1, new);
        }
        for (i = 0; i < h; i++) {
            for(j = 0; j < w; j++) {
                fwrite(&pixmap[i][j], sizeof(int), 1, new);
            }
        }
        printf("Новый файл сохранен с названием %s\n", newPath);
        fclose(new);
    }
    
    return 0;
}
