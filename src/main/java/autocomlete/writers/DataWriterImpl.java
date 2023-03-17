package autocomlete.writers;

import autocomlete.support.AutocompleteUtil;
import autocomlete.support.SimpleCache;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class DataWriterImpl implements DataWriter{
    protected int linesCount;
    protected long startTime;
    protected SimpleCache cache = SimpleCache.getInstance();

    protected String format(String[] line) {
        if(line[line.length - 1].endsWith("\n"))
            line[line.length - 1] = line[line.length - 1].substring(0, line.length - 1);
        return line[AutocompleteUtil.getSearchIndex()] + "[" + String.join(",", Arrays.asList(line)) + "]";
    }

    protected String caching(String key, String line) {
        return cache.cacheResult(key, line);
    }

    @Override
    public abstract void write(Stream<String[]> toWrite, String key);

    @Override
    public abstract void writeFromCache(List<String> toWrite);

    @Override
    public int getLinesCount() {
        return linesCount;
    }

    @Override
    public void rest() {
        linesCount = 0;
        startTime = 0;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

}
