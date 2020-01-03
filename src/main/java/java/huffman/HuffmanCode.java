package huffman;


public class HuffmanCode {
    public int code;
    public int original;
    public int length;

    public void appendBit(int bit) {
        if (length <= Integer.SIZE) {
            code = code << 1;
            code = code | bit;
            length++;
        } else
            throw new IllegalArgumentException("HaffmanCode overflow in int32 container");
    }

    @Override
    public HuffmanCode clone() {
        HuffmanCode cloneCode = new HuffmanCode();
        cloneCode.length = this.length;
        cloneCode.code = this.code;
        cloneCode.original = this.original;
        return cloneCode;
    }
}
