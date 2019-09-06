//
//  main.cpp
//  lab8
//
//  Created by Sergey on 28.02.2019.
//  Copyright © 2019 Sergey. All rights reserved.
//

#include <iostream>
#include <math.h>
using namespace std;

class Square {
protected:
    float side;
public:
    Square(){
        side = 1;
    }
    Square(float side){
        this -> side = side;
    }
    float getDiagonal(){
        return sqrt(2 * side * side);
    }
    float getSquare(){
        return side * side;
    }
    float getPerimeter(){
        return side * 4;
    }
    Square&operator++(int){
        side++;
        return *this;
    }
    Square&operator+(Square&C) {
        Square T(0);
        T.side = this -> side + C.side;
        return T;
    }
    friend istream&operator>>(istream&i, Square&C);
    friend ostream&operator<<(ostream&o, Square&C);
};

istream&operator>>(istream&i, Square&C) {
    cout << "Введите длину стороны" << endl;
    i >> C.side;
    return i;
}
ostream&operator<<(ostream&o, Square&C) {
    o << "side = " << C.side << endl;
    return o;
}

class Square2: public Square{
protected:
    float r;
public:
    Square2(){
        side = 1;
        r = side/2;
    }
    Square2(float side) {
        this -> side = side;
        this -> r = side/2;
    }
    float getR(){
        return r*sqrt(2);
    }
    float getr(){
        return r;
    }
};

int main(int argc, const char * argv[]) {
    Square2 *A = new Square2(5), *B = new Square2();
    cin>>*B;
    Square T = *A+*B;
    cout<<*A;
    cout<<*B;
    cout << "Perimeter A " << A -> getPerimeter() << "\nPerimeter B " << B -> getPerimeter() << endl;
    cout << "Diagonal A " << A -> getDiagonal() << "\nDiagonal B " << B -> getDiagonal() << endl;
    cout << "Square A " << A -> getSquare() << "\nSquare B " << B -> getSquare() << endl;
    cout << "r A " << A -> getr() << "\nr B " << B -> getr() << endl;
    cout << "R A " << A -> getR() << "\nR B " << B -> getR() << endl;
    cout << "T ";
    cout<<T;
    cout << "A ";
    cout<<*A;
    
    delete A;
    delete B;
    return 0;
}

