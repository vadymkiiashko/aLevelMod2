package files;

import huffman.HuffmanCode;
import huffman.HuffmanCodeTable;
import utilities.Constants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class HuffmanCompressor {

    private BitFileWriter bitFileWriter;
    private ByteFileReader byteFileReader;

    public HuffmanCompressor(Path pathToSource, Path pathToOutput, boolean append) throws FileNotFoundException {
        this.byteFileReader = new ByteFileReader(Constants.READING_SYMBOL_TYPE, pathToSource);
        this.bitFileWriter = new BitFileWriter(pathToOutput, append);
    }

    public void replaceByHuffmanCode(HuffmanCodeTable codeTable) throws IOException {
        int nextSymbol;
        byteFileReader.reopenInputStream();
        while ((nextSymbol = byteFileReader.readNextSymbol()) != Integer.MIN_VALUE) {
            HuffmanCode huffmanCode = codeTable.getBySymbol(nextSymbol);
            bitFileWriter.appendHuffmanCodeToFile(huffmanCode);
        }
        HuffmanCode eofCode = codeTable.getTable().get(codeTable.getTable().size() - 1);
        bitFileWriter.appendValueToFile(eofCode.code, eofCode.length, eofCode.length);
        bitFileWriter.flushAndClose();
    }
}}
