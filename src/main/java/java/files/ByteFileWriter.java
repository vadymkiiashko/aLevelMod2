package files;


import utilities.ByteUtils;

import java.io.*;
import java.nio.file.Path;

public class ByteFileWriter {

    private Path pathToFile;
    private int symbolSize;
    private OutputStream outputStream;

    public ByteFileWriter(SymbolType symbolType, Path pathToFile) throws FileNotFoundException {
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
        this.outputStream = new BufferedOutputStream(new FileOutputStream(pathToFile.toFile()));
        this.pathToFile = pathToFile;
    }

    // TODO: 28.12.2019 Add supporting for dword and qword symbols
    public void writeNextSymbol(int symbol) throws IOException {
        byte [] bytes = ByteUtils.intToOneByteArray(symbol);
        writeNextBytes(bytes);
    }

    public void writeNextBytes(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

    public void flushAndClose() throws IOException {
        outputStream.flush();
        outputStream.close();
    }
}
