package ui;

import huffman.HuffmanDecoder;
import huffman.HuffmanEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utilities.FilePathsUtils.parseExtension;

public class Application {


    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static HuffmanEncoder huffmanEncoder;
    private static HuffmanDecoder huffmanDecoder;

    public static void main(String[] args) {
        Path myFile;
        if (args.length == 0) {
            System.exit(-500);
        } else {
            myFile = Paths.get(args[0]);
            System.out.println();
            String extension = parseExtension(myFile);
            if (extension.equals("hf")) {
                try {
                    huffmanDecoder = new HuffmanDecoder(myFile);
                    logger.info("Starting decoding file: " + myFile.getFileName());
                    huffmanDecoder.decode();
                    logger.info("Decoding file is ended!");
                } catch (IOException e) {
                    logger.error("Exception in HuffmanEncoder.Encode: " + e.getMessage());
                }
            } else {
                try {
                    huffmanEncoder = new HuffmanEncoder(myFile);
                    logger.info("Starting coding file: " + myFile.getFileName());
                    huffmanEncoder.encode();
                    logger.info("Coding file is ended!");
                } catch (IOException e) {
                    logger.error("Exception in HuffmanEncoder.Encode: " + e.getMessage());
                }
            }

        }
    }


}
