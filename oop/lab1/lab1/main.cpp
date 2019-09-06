//
//  main.cpp
//  lab1
//
//  Created by Sergey on 10.09.2018.
//  Copyright © 2018 Sergey. All rights reserved.
//

#include <iostream>
#include <cmath>
using namespace std;

int main() {
    // 8 вариант
    float x, y, a, Y1, Y2, Z1, Z2; // объявляем переменные для значений x, y, a и значений функции
    const float pi = 3.14159; // объявляем константу пи
    
    cout << "Введите x, y, a\n";
    cin >> x >> y >> a; // ввод значений с клавиатуры
    
    Y1 = x/(x*x + y*y) - y*(x-y)*(x-y)/(x*x*x*x - y*y*y*y); // вычисление первой формулы
    Y2 = 1/(x+y); // вычисление второй формулы
    Z1 = pow(cos(3 * pi/8 - a/4), 2) - pow(cos(11*pi/8 + a/4), 2); // вычисление третьей формулы
    Z2 = sqrt(2)/2 * sin(a/2); // вычисление четвертой формулы
    
    // вывод результатов
    cout << "Results\n";
    cout << "y1 = " << Y1 << endl;
    cout << "y2 = " << Y2 << endl;
    cout << "z1 = " << Z1 << endl;
    cout << "z2 = " << Z2 << endl;
    
    return 0;
}
