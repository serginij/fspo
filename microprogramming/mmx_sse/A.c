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
        "por xmm0,[esi];"//pxor  was pand
        "movdqu [edi],xmm0;"
        "add esi,16;"
        "add edi,16;"
        "sub ecx,4;"
    "jnz for;"
        );
}

void MMX_Work(int *a,int *b,int size){
    asm(".intel_syntax;"
        "mov edi,[ebp+8];"
        "mov esi,[ebp+12];"
        "movq mm0,[edi];"
        "paddd mm0,[esi];"
        "movq [edi],mm0;"
        );
}

void C_Work(int *a,int *b,int size){
int i;
for(i=0;i<size;i++)
    a[i]+=b[i];
}

int main(){
    char path[128], newPath[128];
    int type, menu = 1;
    while(menu){
        puts("1 - Усилить синий канал изображения\n0 - Выход");
        scanf("%d", &menu);
        if(menu == 0){
            break;
        }
        puts("Введите название изображения");
        scanf("%s", &path);
        // puts("Выберете цвет: 1 - Красный, 2 - Зеленый, 3 - Синий");
        // scanf("%d", &type);
        // printf("Current path %s\nType %d\n", path, type);

        FILE *f = fopen(path, "rb+");
        int w,h,d,i,j;

        fseek(f, 18, SEEK_SET);
        fread(&w, sizeof(w), 1, f);
        fread(&h, sizeof(h), 1, f);
        fseek(f, 2, SEEK_CUR);
        fread(&d, 2, 1, f);
        fseek(f, 26, SEEK_CUR);
        // printf("width=%d, height=%d depth=%d", w, h, d);

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

        // switch(type) {
        //     case 1:
        //         maskColor = 0x0000ff;
        //         strcat(newPath, "red-");
        //     break;
        //     case 2:
        //         maskColor = 0x00ff00;
        //         strcat(newPath, "green-");
        //     break;
        //     case 3:
                maskColor = 0xff0000;
                strcat(newPath, "blue-");
        //     break;
        // }
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
        // printf("\n%s", newPath);

        FILE *new = fopen(newPath, "w");

        for(j = 0; j < 56; j++) {
            fwrite(&header[j], sizeof(int), 1, new);
        }

        for (i = 0; i < h; i++) {
            for(j = 0; j < w; j++) {
                fwrite(&pixmap[i][j], sizeof(int), 1, new);
            }
        }

        fclose(new);
    }
//     int size=16,i,
//         *a=malloc(size*sizeof(int)),
//         *b=malloc(size*sizeof(int));

//     // srand(time(0));
//     for(i=0;i<size;i++){
//         a[i]=6;
//         b[i]=2+i;
//     }

//     SSE_Work(a,b,size);
//     // MMX_Work(a,b,size);
//     // C_Work(a,b,size);

//    for(i=0;i<size;i++)
//        printf("%d ",a[i]);
//     //    printf("%d ", 1);
    return 0;
}
