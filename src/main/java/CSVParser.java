import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@code CSVParser} is a CSV reader that reads the file line by line.
 */
public class CSVParser implements AutoCloseable {
    private final BufferedReader in;
    private final Path p;
    private final int size;

    /**
     * Construct a new {@code CSVParser} from a given {@code Path} and an expected line size.
     *
     * @param p    the {@code Path} of the file to be read
     * @param size the expected size of each line
     * @throws IOException              if some if some I/O exception occurs while opening the file
     * @throws IllegalArgumentException if {@code size < 1}
     * @throws NullPointerException     if {@code p} is {@code null}
     */
    public CSVParser(Path p, int size) throws IOException {
        if (size < 1) {
            throw new IllegalArgumentException();
        }
        this.in = Files.newBufferedReader(p, StandardCharsets.UTF_8);
        this.p = p;
        this.size = size;
    }

    /**
     * Returns the line size bound to this instance.
     *
     * @return the line size bound to this instance
     */
    public int size() {
        return size;
    }

    /**
     * Read the next line and output it as an array.
     *
     * @return an array of size {@link #size()} representing the next line
     * @throws IOException              if some if some I/O exception occurs while reading the file
     * @throws IllegalArgumentException if the next line does not contain exactly {@code size()} elements
     */
    public String[] next() throws IOException {
        final String line = in.readLine();
        if (line == null) {
            return null;
        }
        final List<String> lineSplit = new ArrayList<>();
        for (String l : line.split("\\s+")) {
            if (!l.isEmpty()) {
                lineSplit.add(l);
            }
        }
        if (lineSplit.size() != size) {
            throw new IllegalArgumentException(String.format(
                    "Expected %d elements in line \"%s\" of file %s", size, line, p));
        }
        return lineSplit.toArray(new String[size]);
    }

    /**
     * Closes this resource.
     * <p>
     * This method is meant to be used with the try-with-resources block.
     *
     * @throws IOException if some if some I/O exception occurs while closing the file
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
}
