import sys
#r = open('boot.bin','r')

s = []
z = b'\x00'
z2 = b'\x55'
z3 = b'\xaa'
for i in range(510):
    s.append(z)
s.append(z2)
s.append(z3)

i = 0
with open("boot.bin", "rb") as r:
    byte = r.read(1)
    while byte:
        s[i] = byte
        i += 1
        byte = r.read(1)

with open("a.exe", "rb") as r:
    byte = r.read(1)
    while byte:
        s.append(byte)
        byte = r.read(1)

w = open("disk.img","wb")

for x in s:
    w.write(x)

newArr = []
with open("font.bin","rb") as r:
    byte = r.read(1)
    while byte:
        newArr.append(byte)
        byte = r.read(1)

for x in newArr:
    w.write(x)

w.close()
r.close()
