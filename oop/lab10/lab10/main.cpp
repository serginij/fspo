//
//  main.cpp
//  lab10
//
//  Created by Sergey on 26.03.2019.
//  Copyright © 2019 Sergey. All rights reserved.
//

#include <iostream>
#include <math.h>
using namespace std;

template <class D>
class Square {
    D side;
public:
    Square(){
        side = 1;
    }
    Square(D side){
        this -> side = side;
    }
    D getDiagonal(){
        return sqrt(2 * side * side);
    }
    D getSquare(){
        return side * side;
    }
    D getPerimeter(){
        return side * 4;
    }
    Square<D>&operator++(int){
        side++;
        return *this;
    }
    Square<D>&operator+=(Square<D>&C) {
        this -> side += C.side;
        return *this;
    }
    template <class D1> friend istream&operator>>(istream&i, Square<D1>&C);
    template <class D1> friend ostream&operator<<(ostream&o, Square<D1>&C);
};
template <class D>
    istream&operator>>(istream&i, Square<D>&C) {
    cout << "Введите длину стороны" << endl;
    i >> C.side;
    return i;
}
template <class D>
    ostream&operator<<(ostream&o, Square<D>&C) {
    o << "side = " << C.side << endl;
    return o;
}

int main(int argc, const char * argv[]) {
    Square<float> *A = new Square<float>(5), *B = new Square<float>();
    cin>>*B;
    *A+=*B;
    
    cout << "A ";
    cout<<*A;
    cout << "B ";
    cout<<*B;
    cout << "Perimeter A " << A -> getPerimeter() << "\nPerimeter B " << B -> getPerimeter() << endl;
    cout << "Diagonal A " << A -> getDiagonal() << "\nDiagonal B " << B -> getDiagonal() << endl;
    cout << "Square A " << A -> getSquare() << "\nSquare B " << B -> getSquare() << endl;
    
    delete A;
    delete B;
    return 0;
}

