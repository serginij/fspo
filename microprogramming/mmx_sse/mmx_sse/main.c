//
//  main.c
//  mmx_sse
//
//  Created by Sergey on 01/11/2019.
//  Copyright © 2019 Sergey. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>

void SSE_Work(int *a,int *b,int size){
    asm("mov edi,[ebp+8]");
    asm("mov esi,[ebp+12]");
    asm("mov ecx,[ebp+16]");
    asm("for:");
    asm("movdqu xmm0,[edi]");
    asm("paddd xmm0,[esi]");
    asm("movdqu [edi],xmm0");
    asm("add esi,16");
    asm("add edi,16");
    asm("sub ecx,4");
    asm("jnz for");
    
}

void C_Work(int *a,int *b,int size){
    int i;
    for(i=0;i<size;i++)
        a[i]+=b[i];
}

int main(){
    int size=52428800,i,
    *a=malloc(size*sizeof(int)),
    *b=malloc(size*sizeof(int));
    
    srand(time(0));
    for(i=0;i<size;i++){
        a[i]=rand()%50;
        b[i]=2;
    }
    
    SSE_Work(a,b,size);
    //C_Work(a,b,size);
    
    // for(i=0;i<size;i++)
    //     printf("%d ",a[i]);
    return 0;
}
