package files;

import utilities.BitUtils;

import java.io.*;
import java.nio.file.Path;

public class BitFileReader {
    private InputStream input;
    private byte[] buffer;
    private int position;


    public BitFileReader(Path pathToFile) throws FileNotFoundException {
        this.input = new BufferedInputStream(new FileInputStream(pathToFile.toFile()));
        this.buffer = new byte[1];
        this.position = -1;
    }

    public BitFileReader(Path pathToFile,long seek) throws IOException {
        this(pathToFile);
        for (long i = 0; i <seek ; i++) {
            input.read();
        }
    }

    public int next(int count) throws IOException {
        if (count <= Integer.SIZE) {
            int result = 0;
            for (int i = 0; i < count; i++) {
                result = BitUtils.merge(result, (byte) next());
            }
            return result;
        }
        throw new IllegalArgumentException("Integer overflow on filling");
    }

    public int next() throws IOException {
        if (position != -1) {
            int result = BitUtils.getBit(buffer[0], position);
            position--;
            return result;
        } else {
            if (appendToBuffer() > 0) {
                position = 7;
                input.read(buffer);
                return next();
            } else
                return -1;
        }
    }

    private int appendToBuffer() throws IOException {
        if (input.available() > 0) {
            input.read(buffer);
            return 1;
        } else return -1;
    }
}
