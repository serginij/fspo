org 0x7c00
call loadKernel
call VESA
cli                  ;clear interrupt (чтобы не было прерываний)
mov ax,0
mov ds,ax
mov es,ax
mov fs,ax
mov gs,ax
mov ss,ax

mov sp,0xfffc
lgdt[gdt_descriptor] ;load global descriptor table

mov eax,cr0          ;command register 0
or eax,1
mov cr0,eax

jmp CODE_D:goToProtected
ret

loadKernel:
    mov al,4
    mov cl,2
    mov bx,0x8000
    call read_proc

    mov al,1
    mov cl,6
    mov bx,0x9000
    call read_proc
ret

read_proc:
    mov ah,2
    mov ch,0
    mov dh,0
    mov dl,0x80
    int 0x13
ret

[bits 32]
goToProtected:
    mov esi,[lfb_address]
    jmp CODE_D:0x8000
ret

[bits 16]
VESA:
mov ax,0x4f01
mov cx, 0x118       ;1024*768
mov di, infobuf
int 0x10

mov ax,0x4f02
mov bx,0x4118
int 0x10

mov eax, dword[infobuf+0x28]
mov dword[lfb_address],eax

ret

gdt_start:
null_descriptor:
    dd 0
    dd 0
code_descriptor:
    dw 0xffff       ;нижний предел памяти
    dw 0            ;младшие 2 байта адреса
    db 0            ;средний байт адреса
    db 0b10011010   ;флаги дескриптора P DPL S E ED/C RW A
    db 0b11001111   ;старший предел
    db 0            ;старшийбайт адреса
data_descriptor:
    dw 0xffff
    dw 0
    db 0
    db 0b10010010
    db 0b11001111
    db 0
gdt_end:

gdt_descriptor:
    dw gdt_end-gdt_start
    dd gdt_start

CODE_D equ code_descriptor-gdt_start
DATA_D equ data_descriptor-gdt_start

infobuf dd 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
lfb_address dd 0
bps dd 0
