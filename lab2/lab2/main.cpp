//
//  main.cpp
//  lab2
//
//  Created by Sergey on 18.09.2018.
//  Copyright © 2018 Sergey. All rights reserved.
//

#include <iostream> //Подключаем библиотеки
#include <cmath> //Математическая библеотека
using namespace std; //Указываем пространство имен

int main() {
    float *A, *W, maxA, maxW, G1 = 1, G2 = 1; //Объявляем переменные типа float
    int size, i, countA = 0, countW = 0, flagA = 1, flagW = 1; //Объявляем переменные типа int
    
    cout << "Введите размерность массивов\n";
    cin >> size; //Ввод размера массивов
    
    A = new float[size]; //Динамические выделение памяти для массивов
    W = new float[size];
    
    cout << "Введите массив А - разрядность " << size << endl;
    for (i = 0; i < size; i++) {
        cin >> A[i]; //Ввод массива А
    }
    
    cout << "Введите массив W - разрядность " << size << endl;
    for (i = 0; i < size; i++) {
        cin >> W[i]; //Ввод массива W
    }
    //Присвоение начального значения переменным
    maxA = A[0];
    maxW = W[0];
    
    for (i = 0; i < size; i++) { //Поиск максимальных значений массивов
        if (maxA < A[i]) {
            maxA = A[i];
        }
        if (maxW < W[i]) {
            maxW = W[i];
        }
    }
    //Нахождение суммы и произведения элементов до максимального
    for (i = 0; i < size; i++) {
        if ( A[i] < maxA && flagA ) {
            countA++;
            G1 *= A[i];
        }
        if ( A[i] == maxA ) {
            flagA = 0;
        }
        if ( W[i] < maxW && flagW ) {
            countW++;
            G2 *= W[i];
        }
        if ( W[i] == maxW ) {
            flagW = 0;
        }
    }
    //Вычисление среднего геометрического
    G1 == 1 ? G1 = 0 : G1;
    G2 == 1 ? G2 = 0 : G2;
    G1 = pow(G1, 1./countA);
    G2 = pow(G2, 1./countW);
    
    //Вывод значений на экран
    cout << "кол-во элементов в A " << countA << endl;
    cout << "кол-во элементов в W " << countW << endl;
    cout << "Среднее геом. A = " << G1 << endl;
    cout << "Среднее геом. W = " << G2 << endl;
    return 0;
}
