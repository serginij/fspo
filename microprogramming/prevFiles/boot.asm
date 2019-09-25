org 0x7C00
  call loadCode
  call setVESA
  cli
  mov sp,0xfffc
  mov ax,0
  mov ss,ax
  mov ds,ax
  mov fs,ax
  mov gs,ax
  mov es,ax
  
  lgdt[globalTable]
  mov eax,cr0
  or eax,1
  mov cr0,eax
  mov eax,[buf+0x28]
  mov dword[0x8000],eax
  jmp Code:0x8004
ret

loadCode:
  mov ax,0x0203
  mov bx,0x8004
  mov cx,0x0002
  mov dx,0x0080
  int 0x13
ret

setVESA:
  mov ax,0x4f01
  mov cx,0x118
  mov di,buf
  int 0x10
  mov ax,0x4f02
  mov bx,0x4118
  int 0x10
ret

buf dd  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0; 26 нулей


dTableStart:
  null_d:
  dd 0
  dd 0
  code_d:
  dw 0xffff
  dw 0
  db 0
  db 0b10011010
  db 0b11001111
  db 0
  data_d:
  dw 0xffff
  dw 0
  db 0
  db 0b10010010
db 0
dTableEnd:
globalTable:
  dw dTableEnd-dTableStart
  dd dTableStart
Code equ code_d-dTableStart
Data equ data_d-dTableStart