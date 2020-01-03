package utilities;


import java.nio.ByteBuffer;

public class ByteUtils {
    private static int sizeOfSymbol;

    static {
        switch (Constants.READING_SYMBOL_TYPE) {
            case BYTE:
                sizeOfSymbol = 1;
                break;
            case WORD:
                sizeOfSymbol = 2;
                break;
            case DOUBLEWORD:
                sizeOfSymbol = 4;
                break;
        }
    }


    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static byte[] intToOneByteArray(int value) {
        if (value < 0 && value >= Byte.MIN_VALUE) {
            value = value << Integer.SIZE - Byte.SIZE;
            value = value >>> Integer.SIZE - Byte.SIZE;
            return ByteBuffer.allocate(sizeOfSymbol).put((byte) value).array();

        } else if (value >= 0) {
            return ByteBuffer.allocate(sizeOfSymbol).put((byte) value).array();
        } else {
            throw new IllegalArgumentException("Int is less than Byte.MIN_VALUE, buffer overflow");
        }
    }

    public static int byteArrayToInt(byte[] array) {
        if (array.length == 3) {
            System.out.println();
        }
        if (array.length < 5) {
            ByteBuffer buffer = ByteBuffer.wrap(array);
            return buffer.getInt();
        } else
            throw new IllegalArgumentException("Array has incorrect length:" + array.length);
    }
}
