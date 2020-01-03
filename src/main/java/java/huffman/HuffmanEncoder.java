package huffman;


import files.FrequencyTableReader;
import files.FrequencyTableWriter;
import files.HuffmanCompressor;
import utilities.Constants;
import utilities.FilePathsUtils;
import utilities.FrequencyTableSavingType;

import java.io.IOException;
import java.nio.file.Path;

public class HuffmanEncoder {

    private FrequencyTableWriter frequencyTableWriter;
    private FrequencyTableReader frequencyTableReader;
    private HuffmanCompressor huffmanCompressor;
    private Path pathToSourceFile;
    private Path pathToOutputFile;

    private FrequencyTable frequencyTable;


    public HuffmanEncoder(Path pathToFile) throws IOException {
        this.pathToSourceFile = pathToFile;
        this.pathToOutputFile = FilePathsUtils.switchExtension(pathToFile, "hf");
        this.frequencyTableWriter = new FrequencyTableWriter(pathToOutputFile);
        this.frequencyTableReader = new FrequencyTableReader(pathToSourceFile);

        boolean isAppendCodeFile = Constants.FREQUENCY_TABLE_SAVING_TYPE ==FrequencyTableSavingType.IN_OUTPUT_FILE;
        this.huffmanCompressor = new HuffmanCompressor(pathToSourceFile, pathToOutputFile,isAppendCodeFile);
    }

    public void encode() throws IOException {
        frequencyTable = frequencyTableReader.makeFromSource();
        HuffmanCodeTree tree = new HuffmanCodeTree.CodeTreeBuilder().fromFrequencyTable(frequencyTable);
        HuffmanCodeTable codeTable = new HuffmanCodeTable(tree.walkAndMap());
        saveCodeTable();
        huffmanCompressor.replaceByHuffmanCode(codeTable);
    }


    private void saveCodeTable() throws IOException {
        if (Constants.FREQUENCY_TABLE_SAVING_TYPE == FrequencyTableSavingType.IN_OUTPUT_FILE)
            frequencyTableWriter.writeInSingleFile(frequencyTable);
        else if(Constants.FREQUENCY_TABLE_SAVING_TYPE == FrequencyTableSavingType.IN_SEPARATE_FILE)
            frequencyTableWriter.writeInSeparatelyFile(frequencyTable);
    }
}
