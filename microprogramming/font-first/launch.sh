#!/bin/bash
cd /Users/sergey/Documents/Micro/launch/font-first
nasm -f bin boot.asm -o boot.bin
i686-w64-mingw32-gcc -nostdlib -m32 main.c
i686-w64-mingw32-objcopy -O binary a.exe
pythonw pyt.py
qemu-system-i386 disk.img