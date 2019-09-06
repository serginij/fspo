//
//  main.cpp
//  lab12
//
//  Created by Sergey on 14/05/2019.
//  Copyright © 2019 Sergey. All rights reserved.
//

#include <iostream>
#include <string>
using namespace std;

string reduceString(string str) {
    string newStr;
    int i,j, count=0;
    for(i = 0; i <= str.length()-1; i++) {
//        cout << "i " << str[i] << endl;
        for(j = i; j < str.length(); j++) {
            if(str[i] != str[j]) {
                newStr.push_back(str[i]);
                newStr.push_back(to_string(count)[0]);
                if(count > 1) {
                    i += count-1;
                }
                count = 0;
                j = str.length();// Needs in fix
            }
            if (i == str.length()-1) {
                newStr.push_back(str[i]);
                newStr.push_back(to_string(++count)[0]);
            }
            if (str[i] == str[j]) {
                count++;
            }
        }
    }
    return newStr;
}

int main(int argc, const char * argv[]) {
    string str;
    cout << "Enter string" << endl;
    cin >> str;
    str = reduceString(str);
    cout << "Reducer string: " << str << endl;
    return 0;
}
