package autocomlete.reader;

import autocomlete.support.Indexes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface DataReader {
    String DELIMITER = ",";
    Stream<String[]> readFile() throws IOException;
    Stream<String[]> readIndexed(List<Indexes.IndexTuple> indexes) throws IOException;
    void close() throws IOException;
    Stream<String[]> read() throws IOException;
}
