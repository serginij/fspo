//
//  main.cpp
//  lab5
//
//  Created by Sergey on 16.10.2018.
//  Copyright © 2018 Sergey. All rights reserved.
//

#include <iostream>//Подключаем библиотеки
using namespace std;//Объявляем пространство имен

//Функция для динамического выделения памяти и инициализации массивов
void dynamicMemory(int*& A, int*& B, int size){
    int i;
    A = new int[size]; //Динамическое выделение памяти
    B = new int[size];
    //Ввод массивов с клавиатуры
    cout << "Введите массив А размер " << size << endl;
    for(i = 0; i < size; i++) {
        cin >> A[i];
    }
    cout << "Введите массив B размер " << size << endl;
    for(i = 0; i < size; i++) {
        cin >> B[i];
    }
}
//Функция для вычисления значений в массивах
void calculateArr(int A[], int B[], int R, int F, int size) {
    int i, maxA = A[0], maxB = B[0];
    int flagA = 0, flagB = 0;
    //Поиск максимальных элементов массивов
    for(i = 0; i < size; i++) {
        if(maxA < A[i]) maxA = A[i];
        if(maxB < B[i]) maxB = B[i];
    }
    //Замена значений в массивах по условию
    for(i = 0; i < size; i++) {
        if(A[i] == maxA) flagA = 1;
        if(!flagA) A[i] = R;
        if(B[i] == maxB) flagB = 1;
        if(!flagB) B[i] = F;
    }
}
//Функция для вывода массивов на экран
void printArrays(int A[], int B[], int size) {
    int i;
    cout << "Массив А" << endl;
    for(i = 0; i < size; i++) {
        cout << A[i] << " ";
    }
    
    cout << endl << "Массив B" << endl;
    for(i = 0; i < size; i++) {
        cout << B[i] << " ";
    }
    cout << endl;
}

int main(int argc, const char * argv[]) {
    int *A, *B, R = 11, F = 22;//Объявлем массивы и значения R, F
    //Вызов функций
    dynamicMemory(A, B, 10);
    calculateArr(A, B, R, F, 10);
    printArrays(A, B, 10);
    //Очистка памяти
    delete[]A;
    delete[]B;
    return 0;
}
