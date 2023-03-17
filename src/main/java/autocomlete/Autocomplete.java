package autocomlete;

import autocomlete.algorithms.SearchAlgorithm;
import autocomlete.algorithms.SimpleAlgorithm;
import autocomlete.reader.DataReader;
import autocomlete.reader.SimpleReader;
import autocomlete.support.AutocompleteUtil;
import autocomlete.support.Indexes;
import autocomlete.support.SimpleCache;
import autocomlete.writers.ConsoleWriter;
import autocomlete.writers.DataWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Autocomplete implements AutoCloseable {
    private DataReader reader;
    private SearchAlgorithm algorithm = new SimpleAlgorithm();
    private DataWriter writer = new ConsoleWriter();
    private SimpleCache cache = SimpleCache.getInstance();
    private Indexes indexes = Indexes.getInstance();
    public static final int MAX_COLUMNS = 14;
    public static final int MIN_COLUMNS = 1;
    public long startTime = 0;
    public long endTime = 0;

    public Autocomplete(int searchColumn, Path pathToDataFile) throws FileNotFoundException {
        reader = new SimpleReader(pathToDataFile);
        if(searchColumn < MIN_COLUMNS || searchColumn > MAX_COLUMNS)
            throw new IllegalArgumentException(
                    "searchColumn value must be between " +
                    MIN_COLUMNS +
                    " and " +
                    MAX_COLUMNS
            );
        AutocompleteUtil.setSearchIndex(searchColumn - 1);//1 is difference between column num and column index
    }

    private Stream<String[]> readData(String searched) throws IOException {
        if(indexes.isEmpty())
            return reader.readFile();
        if(indexes.contains(searched))
            return reader.readIndexed(indexes.getIndexesForStr(searched));
        return reader.read();
    }

    public void find(String searched) throws IOException {
        searched = AutocompleteUtil.keyPostCheck(searched);
        startTime = System.currentTimeMillis();
        if(cache.contains(searched))
            writer.writeFromCache(cache.getCache(searched));
        else
            writer.write(algorithm.search(readData(searched), searched), searched);
        endTime = System.currentTimeMillis();
        writeStatistic();
        rest();
    }

    private void writeStatistic() {
        long workTime = writer.getStartTime() - startTime;
        workTime = (workTime < 0)? 0 : workTime;
        System.out.println("Lines amount: " + writer.getLinesCount() + "\t Algorithm work time: " + workTime);
        System.out.println("General work time: " + (endTime - startTime));
    }

    private void rest() {
        writer.rest();
        cache.restBanned();
        startTime = 0;
        endTime = 0;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
