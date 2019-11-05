#include <stdio.h>
#include <stdlib.h>

void SSE_Work(int *a,int *b,int size){
    asm(".intel_syntax;"
        "mov edi,[ebp+8];"
        "mov esi,[ebp+12];"
    "mov ecx,[ebp+16];"
    "for:;"
        "movdqu xmm0,[edi];"
        "paddd xmm0,[esi];"
        "movdqu [edi],xmm0;"
        "add esi,16;"
        "add edi,16;"
        "sub ecx,4;"
    "jnz for;"
        );
    // asm("mov esi,[ebp+12]");
    // asm("mov ecx,[ebp+16]");
    // asm("for:");
    //     asm("movdqu xmm0,[edi]");
    //     asm("paddd xmm0,[esi]");
    //     asm("movdqu [edi],xmm0");
    //     asm("add esi,16");
    //     asm("add edi,16");
    //     asm("sub ecx,4");
    // asm("jnz for");

}

void MMX_Work(int *a,int *b,int size){
    asm(".intel_syntax;"
        "mov edi,[ebp+8];"
        "mov esi,[ebp+12];"
        "movq mm0,[edi];"
        "paddd mm0,[esi];"
        "movq [edi],mm0;"
        );
    // asm("mov esi,[ebp+12]");
    // asm("mov ecx,[ebp+16]");
    // asm("for:");
    //     asm("movdqu xmm0,[edi]");
    //     asm("paddd xmm0,[esi]");
    //     asm("movdqu [edi],xmm0");
    //     asm("add esi,16");
    //     asm("add edi,16");
    //     asm("sub ecx,4");
    // asm("jnz for");

}

void C_Work(int *a,int *b,int size){
int i;
for(i=0;i<size;i++)
    a[i]+=b[i];
}

int main(){
    int size=16,i,
        *a=malloc(size*sizeof(int)),
        *b=malloc(size*sizeof(int));

    // srand(time(0));
    for(i=0;i<size;i++){
        a[i]=6;
        b[i]=2;
    }

    // SSE_Work(a,b,size);
    MMX_Work(a,b,size);
    // C_Work(a,b,size);

   for(i=0;i<size;i++)
       printf("%d ",a[i]);
    //    printf("%d ", 1);
    return 0;
}
