package files;


import huffman.HuffmanCode;
import huffman.HuffmanCodeTable;
import utilities.Constants;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class HuffmanDecompressor {

    private BitFileReader bitFileReader;
    private ByteFileWriter byteFileWriter;

    public HuffmanDecompressor(Path pathToSource, Path pathToOutput, long seek) throws IOException {
        this.bitFileReader = new BitFileReader(pathToSource, seek);
        this.byteFileWriter = new ByteFileWriter(Constants.READING_SYMBOL_TYPE, pathToOutput);
    }

    public void replaceHuffmanByOriginal(HuffmanCodeTable codeTable) throws IOException {
        HuffmanCode huffmanCode = new HuffmanCode();
        int nextBit;
        while ((nextBit = bitFileReader.next()) >=0) {
            huffmanCode.appendBit(nextBit);
            Optional<HuffmanCode> byCode = codeTable.getByCode(huffmanCode);
            if (byCode.isPresent()) {
                huffmanCode = byCode.get();
                if (huffmanCode.code == codeTable.getEOF().code)
                    break;
                byteFileWriter.writeNextSymbol(huffmanCode.original);
                huffmanCode = new HuffmanCode();
            } else if (huffmanCode.length > codeTable.getMaxCodeLenght()) {
                throw new IllegalStateException("Code " + Integer.toBinaryString(huffmanCode.code) + " with length "
                        + huffmanCode.length + " is not exist in table.");
            }
        }
        byteFileWriter.flushAndClose();
    }
}

