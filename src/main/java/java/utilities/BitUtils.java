package utilities;

public class BitUtils {
    public static int merge(byte b1, byte b2) {
        int container = b1;
        return merge(container, b2);
    }

    public static int merge(byte[] byteArray) {
        int container = byteArray[0];
        for (int i = 1; i < byteArray.length; i++) {
            container = merge(container, byteArray[i]);
        }
        return container;
    }

    public static int merge(int container, byte b) {
        container = container << 8;
        container = container | b;
        return container;
    }

    public static int merge(int container, int b) {
        if (
                Integer.numberOfLeadingZeros(container) +
                        (Integer.SIZE - Integer.numberOfLeadingZeros(b)) > Integer.SIZE)
            throw new IllegalArgumentException("Merge error, container can't poll value");

        container = container << (Integer.SIZE - Integer.numberOfLeadingZeros(b));
        container = container | b;
        return container;
    }

    public static int getBit(byte value, int position) {
        return (byte) (value >> position) & 1;
    }

}
