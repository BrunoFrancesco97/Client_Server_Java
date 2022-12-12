
import sys 

def parity():
    if len(sys.argv) == 2:
        if int(sys.argv[1]) % 2 == 0:
            print("1")
        else:
            print("0")

if __name__ == '__main__':
    parity()