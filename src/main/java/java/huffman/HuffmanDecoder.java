package huffman;


import files.FrequencyTableReader;
import files.HuffmanDecompressor;
import utilities.Constants;
import utilities.FilePathsUtils;
import utilities.FrequencyTableSavingType;

import java.io.*;
import java.nio.file.Path;

public class HuffmanDecoder {

    private HuffmanDecompressor huffmanDecompressor;
    private FrequencyTableReader frequencyTableReader;

    private Path pathToSourceFile;
    private Path pathToOutputFile;

    public HuffmanDecoder(Path pathToFile) throws FileNotFoundException {
        this.pathToSourceFile = pathToFile;
        this.pathToOutputFile = FilePathsUtils.deleteExtension(pathToFile);

        if(Constants.FREQUENCY_TABLE_SAVING_TYPE == FrequencyTableSavingType.IN_OUTPUT_FILE)
            this.frequencyTableReader = new FrequencyTableReader(pathToFile);
        else {
            Path dictionaryPath = FilePathsUtils.switchExtension(pathToFile,"dictionary");
            this.frequencyTableReader = new FrequencyTableReader(dictionaryPath);
        }
    }

    public void decode() throws IOException {
        FrequencyTable frequencyTable = frequencyTableReader.readCoded();
        HuffmanCodeTree huffmanCodeTree =
                new HuffmanCodeTree.CodeTreeBuilder().fromFrequencyTable(frequencyTable);
        huffmanCodeTree.walkAndMap();
        HuffmanCodeTable codeTable = new HuffmanCodeTable(huffmanCodeTree.walkAndMap());

        long seekOfTable;
        if (Constants.FREQUENCY_TABLE_SAVING_TYPE == FrequencyTableSavingType.IN_OUTPUT_FILE){
            seekOfTable = frequencyTable.countBytes();}
        else
            seekOfTable = 0;
        this.huffmanDecompressor = new HuffmanDecompressor(pathToSourceFile, pathToOutputFile, seekOfTable);
        huffmanDecompressor.replaceHuffmanByOriginal(codeTable);
        System.out.println();
    }
}
