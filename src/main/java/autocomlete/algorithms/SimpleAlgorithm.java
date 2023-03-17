package autocomlete.algorithms;

import static autocomlete.support.AutocompleteUtil.*;
import java.util.stream.Stream;

public class SimpleAlgorithm implements SearchAlgorithm{
    @Override
    public Stream<String[]> search(Stream<String[]> fromStream, final String suffix) {
        return fromStream.filter(line -> findMatch(line, suffix));
    }

    private boolean findMatch(String[] line, String suffix) {
        if(line[getSearchIndex()].startsWith("\"") && suffix.equals("\""))
            return true;
        if(line[getSearchIndex()].startsWith("-") && suffix.equals("-"))
            return true;
        if(line[getSearchIndex()].startsWith("\\") && suffix.equals("\\"))
            return true;
        return getFirsts(line[getSearchIndex()], suffix.length()).equalsIgnoreCase(suffix);
    }
}
