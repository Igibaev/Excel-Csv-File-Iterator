package igibaev.aday.iterator;

import java.io.File;

/**
 *
 */
public class FileRecognizer {
    private final static String XLSX = ".xlsx";
    private final static String CSV = ".csv";
    private FileRecognizer() {
    }

    public static boolean isXlsxFile(File file) {
        String fileExtension = getFileExtension(file);
        return fileExtension.equalsIgnoreCase(XLSX);
    }

    public static boolean isCSVFile(File file) {
        String fileExtension = getFileExtension(file);
        return fileExtension.equalsIgnoreCase(CSV);
    }

    private static String getFileExtension(File file) {
        String filename = file.getName();
        if (filename.lastIndexOf('.') != -1) {
            String fileExtension = filename.substring(filename.lastIndexOf('.'));
            return fileExtension;
        } else {
            throw new RuntimeException("");
        }
    }
}
