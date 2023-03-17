package autocomlete.reader;

import autocomlete.support.AutocompleteUtil;
import autocomlete.support.Indexes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class SimpleReader extends DataReaderImp {
    private final Path pathToDataFile;
    private RandomAccessFile file;
    private boolean inIsOpen;

    public SimpleReader(Path pathToDataFile) throws FileNotFoundException {
        file = new RandomAccessFile(pathToDataFile.toString(), "r");
        inIsOpen = true;
        this.pathToDataFile = pathToDataFile;
    }

    @Override
    public Stream<String[]> readFile() throws IOException {
             return Files.lines(pathToDataFile)
                     .map(this::indexing)
                     .sorted(Comparator.comparing(x -> x[AutocompleteUtil.getSearchIndex()]));
    }

    @Override
    public Stream<String[]> readIndexed(List<Indexes.IndexTuple> indexes) {
        return indexes.stream().map(this::readIndexedBytesAsString)
                .map(line -> line.split(DELIMITER))
                .map(this::concatValuesFromOneColumn)
                .sorted(Comparator.comparing(x -> x[AutocompleteUtil.getSearchIndex()]));

    }

    @Override
    public Stream<String[]> read() throws IOException {
        return Files.lines(pathToDataFile)
                .map(line -> line.split(DELIMITER))
                .map(this::concatValuesFromOneColumn)
                .sorted(Comparator.comparing(x -> x[AutocompleteUtil.getSearchIndex()]));
    }

    private String readIndexedBytesAsString(Indexes.IndexTuple indexTuple) {
        try {
            file.seek(indexTuple.getPosition());
            byte[] line = new byte[indexTuple.getLength()];
            file.read(line);
            return new String(line, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Seek or read failed while proceed" + indexTuple);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        if(inIsOpen)
            file.close();
    }
}




















