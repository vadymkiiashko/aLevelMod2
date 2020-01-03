package utilities;


import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathsUtils {

    public static String parseExtension(Path path) {
        String[] splitedFileName = path.getFileName().toString().split("\\.");
        return splitedFileName[splitedFileName.length - 1];
    }

    public static Path switchExtension(Path path, String newExtension) {
        return Paths.get(path.getFileName().toString().split("\\.")[0] + "." + newExtension);
    }

    public static Path deleteExtension(Path path) {
        return Paths.get(path.getFileName().toString().split("\\.")[0]);
    }
}
