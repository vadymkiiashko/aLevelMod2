package files;


import utilities.BitUtils;

import java.io.*;
import java.nio.file.Path;

public class ByteFileReader {

    private Path pathToFile;
    private int symbolSize;
    private InputStream inputStream;

    public ByteFileReader(SymbolType symbolType, Path pathToFile) throws FileNotFoundException {
        switch (symbolType) {
            case BYTE:
                symbolSize = 1;
                break;
            case WORD:
                symbolSize = 2;
                break;
            case DOUBLEWORD:
                symbolSize = 4;
                break;
        }
        this.inputStream = new BufferedInputStream(new FileInputStream(pathToFile.toFile()));
        this.pathToFile = pathToFile;
    }

    public int readNextSymbol() throws IOException {
        if (inputStream.available() > 0) {
            byte[] bytes = new byte[symbolSize];
            inputStream.read(bytes);
            return BitUtils.merge(bytes);
        } else
            return Integer.MIN_VALUE;
    }

    public byte[] readNextBytes(int nOfBytes) throws IOException {
        if (inputStream.available() > 0) {
            byte[] bytes = new byte[nOfBytes];
            inputStream.read(bytes);
            return bytes;
        }
        else
            return new byte[nOfBytes-1];
    }

    public void reopenInputStream() throws FileNotFoundException {
        inputStream = new BufferedInputStream(new FileInputStream(pathToFile.toFile()));
    }

}