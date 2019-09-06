//
//  main.cpp
//  lab7
//
//  Created by Sergey on 03.12.2018.
//  Copyright © 2018 Sergey. All rights reserved.
//

#include <iostream>
#include <istream>
#include <string>

using namespace std;

struct Buses {
    int busId, number;
    string driver;
    string state; //в парке | в пути
    Buses *Next;
};

Buses *Head;

void createList (int n, Buses** Head) {
    if (n > 0) {
        (*Head) = new Buses();
        cout << "Введите номер маршрута" << endl;
        cin >> (*Head) -> number;
        cout << "Введите Фамилия И.О. водителя" << endl;
        cin.ignore();
        getline(cin, (*Head) -> driver);
        cout << "Введите номер автобуса" << endl;
        cin >> (*Head) -> busId;
        cout << "Введите статус автобуса (в пути / в парке)" << endl;
        cin.ignore();
        getline(cin, (*Head) -> state);
        (*Head) -> Next = NULL;
        createList(n-1, &((*Head) -> Next));
    }
}

void busComePark (int id, Buses* Head) {
    Buses *item;
    item = Head;
    while (item != NULL) {
        if (id == item -> busId) {
            item -> state = "в парке";
        }
        item = item -> Next;
    }
}

void busLeftPark (int id, Buses* Head) {
    Buses *item;
    item = Head;
    while (item != NULL) {
        if (id == item -> busId) {
            item -> state = "в пути";
        }
        item = item -> Next;
    }
}

void findBus (string state, Buses* Head) {
    Buses *item;
    item = Head;
    cout << "Список автобусов " << state << ':' << endl;
    while (item != NULL) {
        if (state.compare((item -> state)) == 0) {
            cout << endl << "\t номер автобуса - " << item -> busId << endl;
            cout << "\t номер маршрута - " << item -> number << endl;
            cout << "\t ФИО водителя   - " << item -> driver << endl;
        }
        item = item -> Next;
    }
}

int main(int argc, const char * argv[]) {
    setlocale(LC_ALL, "Russian");
    createList(2, &Head);
    string inDrive = "в пути", inPark = "в парке";
    int flag = 1, busId = 0;
    while (flag != 0) {
        cout << endl << "Выбор действия:\n"
        << "Автобус въехал в парк\t--> 1\n"
        << "Автобус выехал из парка\t--> 2\n"
        << "Сведения об автобусах:\n"
        << " Автобусы в парке\t\t--> 3\n"
        << " Автобусы в пути\t\t--> 4\n"
        << "Выход из программы\t     --> 0" << endl ;
        cin >> flag;
        switch (flag){
            case 0:
                flag = 0;
                break;
            case 1:
                cout << "Введите номер автобуса" << endl;
                cin >> busId;
                busComePark(busId, Head);
                break;
            case 2:
                cout << "Введите номер автобуса" << endl;
                cin >> busId;
                busLeftPark(busId, Head);
                break;
            case 3:
                findBus(inPark, Head);
                break;
            case 4:
                findBus(inDrive, Head);
                break;
        }
    }
    return 0;
}

