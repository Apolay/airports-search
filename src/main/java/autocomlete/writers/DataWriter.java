package autocomlete.writers;

import java.util.List;
import java.util.stream.Stream;

public interface DataWriter {
    void write(Stream<String[]> toWrite, String key);
    void writeFromCache(List<String> toWrite);
    int getLinesCount();
    void rest();
    long getStartTime();
}