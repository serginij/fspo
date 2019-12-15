import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

class lab {
    public static native void SSE(int[] m , int size, int[] arr);
    static {System.loadLibrary("lab");}
    public static void main(String[] arguments) {
		int[] arr = new int[] {0xff000000,0xff000000,0xff000000,0xff000000};
		int checkfile=0;
    final Scanner in = new Scanner(System.in);
		String filename="abcde";
		int menu = 1, i;
		while(menu == 1) {
			menu = checkMenu(menu); 
			if( menu == 0 ) {
				return;
			}
		while (checkfile==0){
			System.out.println("Enter picture name");
      		filename= in.nextLine();
			try {
				checkfile=1;
				BufferedReader reader = new BufferedReader(new FileReader(filename));
			}
			catch (FileNotFoundException e) {
				checkfile=0;
				System.out.println("Error! No such file! Please, try again.");
			}
		}	
		try (DataInputStream file = new DataInputStream(new FileInputStream(filename))) {
			final String suffix = "_blue";
			int width, height;
			byte[] header = new byte[0x36];
			file.read(header);
			width = byteArrayToInt(header, 0x12);
			height = byteArrayToInt(header, 0x16);
			int[][] pixmap = new int[height][width];
			for (i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					pixmap[i][j] = file.readInt();
				}
			}
			file.close();
			int newW=width/4;
			if (width%4!=0) {
				newW=(newW*4)+4;
			}
			else {
				newW=(newW*4);
			}
			filename = filename.substring(0x0, filename.length() - 4) + suffix + ".bmp";
			for(i = 0; i < height; i++) {
				SSE(pixmap[i], newW, arr);
			}
			System.out.println("New file saved with name " + filename);
			try(DataOutputStream outFile = new DataOutputStream(new FileOutputStream(filename))) {
					outFile.write(header);
					for (i = 0; i < height; i++){
						for (int j = 0; j < width; j++){
							outFile.writeInt(pixmap[i][j]);
						}
					}
			}
			catch (IOException ex) {
				System.out.println("Error! File not found!");
			}
		}
		catch (IOException ex) {
            System.out.println(ex.getMessage());
    }
		}
  }

	public static int byteArrayToInt(byte[] array, int index) {
		int value;
		byte[] bufferInt = new byte[0x4];
		System.arraycopy(array, index, bufferInt, 0x0, 0x4);
		ByteBuffer buffer = ByteBuffer.wrap(bufferInt);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		value = buffer.getInt();
		return value;
	}

	public static int checkMenu(int menu) {
		Scanner in = new Scanner(System.in);
		System.out.println("1 - Enhance blue channel\n0 - Exit");
		menu = Integer.parseInt(in.nextLine());

		if( menu == 0 ) {
			return menu;
		}

		if( menu != 1 ) {
			System.out.println("Error! Incorrect value, try again.");
			checkMenu(menu);
		}
		return menu;
	}
}