#include <jni.h>

void SSE(jint *m,jint size, jint *arr);
JNIEXPORT void JNICALL Java_lab_SSE (JNIEnv *env, jclass class, jintArray arr1, jint width, jintArray arr2) {
	jint *arrPointer1 = (*env) -> GetIntArrayElements(env, arr1, 0);
	jint *arrPointer2 = (*env) -> GetIntArrayElements(env, arr2, 0);
	SSE(arrPointer1,width, arrPointer2);
	(*env) -> ReleaseIntArrayElements(env, arr1, arrPointer1, 0);
	(*env) -> ReleaseIntArrayElements(env, arr2, arrPointer2, 0);
}

void SSE(jint *m,jint size, jint *arr){
    asm("mov edi,[ebp+8]");
	asm("mov ecx,[ebp+12]");
	asm("mov esi,[ebp+16]");
	asm("for:");
		asm("movdqu xmm0,[edi]");
		asm("movdqu xmm1,[esi]");
		asm("por xmm0,xmm1");
		asm("movdqu [edi],xmm0");
		asm("add edi,16");
		asm("sub ecx,4");
	asm("jnz for");
}