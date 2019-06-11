//
//  main.cpp
//  lab11
//
//  Created by Sergey on 23/04/2019.
//  Copyright © 2019 Sergey. All rights reserved.
//

#include <iostream>
#include <math.h>
using namespace std;

class Square {
    float side;
public:
    Square(){
        side = 1;
    }
    Square(float side){
        try{
            if(side == 0) {
                throw 12;
            }
            this -> side = side;
        }
        catch (int code) {
            cout << "Error! Code " << code << endl;
//            Square();
            this -> side = 1;
        }
        
    }
    float getSide(){
        return side;
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
void tryCatch(Square *B) {
    try{
        cin>>*B;
        if(B -> getSide() == 0)
            throw 0;
    }
    catch(int code) {
        cout << "Error! Code " << code << endl;
        tryCatch(B);
    }

};

int main(int argc, const char * argv[]) {
    Square *A = new Square(5), *B = new Square();
//    tryCatch(B);
//    cin>>*B;
    Square T = *A+*B;
    T++;
    
    cout << "Side A " << A -> getSide() << "\nSide B " << B -> getSide() << endl;
    cout << "Perimeter A " << A -> getPerimeter() << "\nPerimeter B " << B -> getPerimeter() << endl;
    cout << "Diagonal A " << A -> getDiagonal() << "\nDiagonal B " << B -> getDiagonal() << endl;
    cout << "Square A " << A -> getSquare() << "\nSquare B " << B -> getSquare() << endl;
    cout << "T side " << T.getSide() << endl;
    cout<<*A;
    
    delete A;
    delete B;
    return 0;
}

