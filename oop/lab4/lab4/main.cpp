//
//  main.cpp
//  lab4
//
//  Created by Sergey on 15.10.2018.
//  Copyright © 2018 Sergey. All rights reserved.
//

#include <iostream> //Подключаем библиотеки
#include <iomanip>
#include <string>
using namespace std; //Указываем пространство имен

struct train { //Объявление струкрутры
    string destination;
    int number;
    string time;
}trains[8];

//Функция для поиска поездов по пункту назначения
void searchTrain(struct train *trains, string dest){
    int i = 0;
    cout << "| № |      Пункт назначения       | Номер | Время |" << endl;
    cout << "---------------------------------------------------" << endl;
    for(i = 0; i < 8; i++){
        if(trains[i].destination == dest) {
            cout << left << "|" << setw(3) << i+1;
            cout << left << "|" << setw(32) << trains[i].destination;
            cout << left << "|" << setw(7) << trains[i].number;
            cout << left << "|" << setw(7) << trains[i].time << "|" << endl;
        }
    }
    cout << endl;
}

int main(int argc, const char * argv[]) {
    int i;
    string dest;
    for(i = 0; i < 8; i++) { //Ввод данных в массив структур
        cout << "Поездка №" << i + 1 << endl;
        cout << "Введите пункт назначения" << endl;
        getline(cin, trains[i].destination);
        cout << "Введите время отправления" << endl;
        getline(cin, trains[i].time);
        cout << "Введите номер поезда" << endl;
        cin >> trains[i].number;
        cin.get();
    }
    
    cout << "Введите пункт назначения" << endl;
    cin >> dest;
    //Вызов функции поиска
    searchTrain(trains, dest);
    //Вывод информации о поездах
    cout << "| № |      Пункт назначения       | Номер | Время |" << endl;
    cout << "---------------------------------------------------" << endl;
    for(i = 0; i < 8; i++) {
        cout << left << "|" << setw(3) << i+1;
        cout << left << "|" << setw(32) << trains[i].destination;
        cout << left << "|" << setw(7) << trains[i].number;
        cout << left << "|" << setw(7) << trains[i].time << "|" << endl;
    }
    return 0;
}
