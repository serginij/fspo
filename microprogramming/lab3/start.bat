javac lab.java
javah lab
gcc -Wl,--add-stdcall-alias -shared -I "C:\Program Files (x86)\Java\jdk1.8.0_231\include" -I "C:\Program Files (x86)\Java\jdk1.8.0_231\include\win32" -masm=intel lab.c -o lab.dll
java lab