package files;

import huffman.HuffmanCode;
import utilities.ByteUtils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class BitFileWriter {

    private int dwordVal = 0;
    private int bitsFree = Byte.SIZE;
    private BufferedOutputStream bufferedOutputStream;

    public BitFileWriter(Path pathToFile, boolean append) throws FileNotFoundException {
        this.bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(pathToFile.toFile(), append));
    }

    public void appendHuffmanCodeToFile(HuffmanCode code) throws IOException {
        appendValueToFile(code.code, code.length, code.length);
    }

    public void appendValueToFile(int valueToAppend, int codeLength, int remainingLength) throws IOException {
        if ((bitsFree - remainingLength) >= 0) {
            //buffer can eat valueToAppend
            dwordVal = dwordVal | valueToAppend;
            bitsFree -= remainingLength;
            if (bitsFree == 0) {
                byte[] bytes = ByteUtils.intToOneByteArray(dwordVal);
                bufferedOutputStream.write(bytes);
                dwordVal = 0;
                bitsFree = Byte.SIZE;
            } else
                dwordVal = dwordVal << bitsFree;
        } else {
            // buffer is overflowed
            int valThatCanBePlaced = valueToAppend >>> (codeLength - bitsFree);

            dwordVal = dwordVal | valThatCanBePlaced;

            byte[] bytes = ByteUtils.intToOneByteArray(dwordVal);

            valueToAppend = valueToAppend << (Integer.SIZE - codeLength + bitsFree);
            valueToAppend = valueToAppend >>> (Integer.SIZE - codeLength + bitsFree);

            remainingLength -= bitsFree;
            bufferedOutputStream.write(bytes);
            bitsFree = Byte.SIZE;
            dwordVal = 0;
            if (remainingLength != 0)
                appendValueToFile(valueToAppend, codeLength, remainingLength);
        }
    }

    public void flushAndClose() throws IOException {
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
}
