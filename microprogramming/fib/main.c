#include <stdlib.h>
#include <stdio.h>

void sort(int *arr, int length) {
  asm(".intel_syntax;"
      "mov ebx,[ebp+8];"    
      "mov ecx,[ebp+12];"
    "for:;"
      "mov edi, [ebp+12];"
      "mov ebx, [ebp+8];"
      "mov eax, 0;"
      "mov edx, 0;"
        "while:;"
          "mov eax, [ebx];"
          "mov edx, [ebx+4];"
          "cmp eax, edx;"
          "jle skip;"
            "mov [ebx], edx;"
            "mov [ebx+4], eax;"
            // "add eax, 1;"
            // "mov [ebx], eax;"
          "skip:;"
          "add ebx, 4;"
          "sub edi, 1;"
          "jnz while;"
        "sub ecx, 1;"
    "jnz for;"
        );
}

void fibb(int *m, int fibb_count, int n) {
	int *fibb_array = (int*)malloc(fibb_count * sizeof(int));
	int buf, counter;

	for(int i=0; i<fibb_count; i++) {
		if(i - 2 >= 0) {
			fibb_array[i] = fibb_array[i-2] + fibb_array[i-1];
		}
		else{
			fibb_array[i] = 1;
		}
	}

	counter = 0;
	for(int i=0; i<fibb_count; i++) {
		while(counter < n) {
			for(int j=0; j<fibb_count - 1; j++) {
				if(counter+fibb_array[j+1] <= n-1) {
					buf = m[counter+fibb_array[j]];
					m[counter+fibb_array[j]] = m[counter+fibb_array[j+1]];
					m[counter+fibb_array[j+1]] = buf;
				}
				else break;
					
			}
			counter += fibb_array[fibb_count-1];
		}
	}
}


int main () {
  int size = 1000;
  int out = 30;
  int *arr = (int*) malloc(size * sizeof(int));
  int fib[] = {1, 2, 3, 5, 8, 13, 21};
  
  for(int i = 0; i < size; i++) arr[i] = i;
  fibb(arr, 7, size);
  for(int i = 0; i < out; i++) {
    printf("%d\n", arr[i]);
  }
  puts("\nSORTING...\n");
  sort(arr, size);
  for(int i = 0; i < out; i++) {
    printf("%d\n", arr[i]);
  }
  
  return 0;
}