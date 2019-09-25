org 0x7C00
call loadProgram
jmp 0:0x8000
ret
loadProgram:
	mov ah, 2
	mov al, 2
	mov bx, 0x8000
	mov ch, 0 
	mov cl, 2
	mov dh, 0 
	mov dl, 0x80
	int 0x13
ret