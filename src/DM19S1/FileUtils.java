package DM19S1;

import java.io.*;
import java.util.*;

public class FileUtils {
    private FileUtils(){}
    public static final String LINE_SEPARATOR = "\r\n";

    public static List<String> readLines(File file, String encoding) throws IOException {

        try(InputStream in = openInputStream(file)) {
            return readLines(in, encoding);
        }
    }
    private static List<String> readLines(InputStream input, String encoding) throws IOException {
        if (encoding == null) {
            return readLines(input);
        } else {
            InputStreamReader reader = new InputStreamReader(input, encoding);
            return readLines(reader);
        }
    }

    private static List<String> readLines(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input);
        return readLines(reader);
    }

    private static List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    private static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    private static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static void writeLines(File file,Collection<?> lines, boolean append)
            throws IOException {

        try(OutputStream out = openOutputStream(file, append)) {
            writeLines(lines,out,LINE_SEPARATOR,"UTF-8");
        }
    }

    public static void write(File file,String line, boolean append)
            throws IOException {

        try(OutputStream out = openOutputStream(file, append)) {
            write(line,out,LINE_SEPARATOR,"UTF-8");
        }
    }
    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    public static void writeLines(Collection<?> lines, OutputStream output, String lineEnding,String encoding) throws IOException {
        if (lines == null) {
            return;
        }
        for (Object line : lines) {
            if (line != null) {
                output.write(line.toString().getBytes(encoding));
            }
            output.write(lineEnding.getBytes(encoding));
        }
    }
    public static void write(String line, OutputStream output, String lineEnding,String encoding) throws IOException {
        if (line == null) {
            return;
        }
        if (line != null) {
            output.write(line.toString().getBytes(encoding));
        }
    }
    public static void main(String[] args) {

    }
}
