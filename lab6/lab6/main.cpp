//
//  main.cpp
//  lab6
//
//  Created by Sergey on 16.10.2018.
//  Copyright © 2018 Sergey. All rights reserved.
//

#include <iostream> //Подключаем библиотеки
#include <cmath> //Математическая библеотека
using namespace std; //Указываем пространство имен

template <typename T>
void dynamicMemory(T **& A, T *& B, T *& C, int n, int m){
    int i, j;
    A = new T*[n]; //Динамические выделение памяти для массивов
    B = new T[n];
    C = new T[n];
    
    for (i = 0; i < n; i++) {
        A[i] = new T[m]; //Динамическое выделение памяти для массива А[i]
    }
    
    cout << "Введите массив А - разрядность " << n << "x" << m << endl;
    for (i = 0; i < n; i++) {
        for (j = 0; j < m; j++) {
            cin >> A[i][j]; //Ввод массива А
        }
    }
    cout << "Введите массив B - разрядность " << n << endl;
    for (i = 0; i < n; i++) {
        cin >> B[i]; //Ввод массива B
    }
}
template <typename H>
void calculateArr(H **A, H *B, H *C, int n, int m){
    int i,j;
    for(i = 0; i < n; i++) {
        if(B[i] == 0) {
            C[i] = 0; //Условие если B[i]=0
        }
        else if(B[i] < 0) {
            C[i] = A[i][0] + A[i][m-1]; //Условие если B[i]<0
        } else {
            for(j = 0; j < m; j++) {
                C[i] += A[i][j]; //Условие если B[i]>0
            }
        }
    }
}

template <typename Y>
void printArray(Y*C, int n) {
    int i;
    cout << "массив C" << endl;
    for (i = 0; i < n; i++) {
        cout << C[i] << " ";
    }
    cout << endl;
}

int main() {
    float **A, *B, *C;// Объявление указателей типа float
    int n, m, i; //Объявляем переменные типа int

    cout << "Введите размерность массивов ( n, m )\n";
    cin >> n >> m; //Ввод размера массивов
    
    dynamicMemory(A, B, C, n, m);
    calculateArr(A, B, C, n, m);
    printArray(C, n);
    for (i = 0; i < n; i++) {
        delete[]A[i];
    }
    delete[]A;
    delete[]B;
    delete[]C;
    return 0;
}
