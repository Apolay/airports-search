package autocomlete.reader;

import autocomlete.Autocomplete;
import autocomlete.support.AutocompleteUtil;
import autocomlete.support.Indexes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public abstract class DataReaderImp implements DataReader {
    protected Indexes indexes = Indexes.getInstance();

    public String[] indexing(String line) {
        String[] res = concatValuesFromOneColumn(line.split(DataReader.DELIMITER));

        indexes.add(
                AutocompleteUtil.getFirsts(
                        res[AutocompleteUtil.getSearchIndex()].toLowerCase(Locale.ROOT),
                        1),
                line.getBytes(StandardCharsets.UTF_8).length + 1
        );
        return res;
    }

    public String[] concatValuesFromOneColumn(String[] line) {
        if ( line.length > Autocomplete.MAX_COLUMNS) {
            String elem;
            String[] firstPart;
            String[] newLine = new String[line.length - 1];
            if(line[2].startsWith(" ")) {
                elem = line[1] + "," + line[2];
                firstPart = new String[]{line[0], elem};
                System.arraycopy(firstPart, 0, newLine, 0, firstPart.length);
                System.arraycopy(line, 3, newLine, firstPart.length, line.length - 3);
            } else if(line[3].startsWith(" ")) {
                elem = line[2] + "," + line[3];
                firstPart = new String[]{line[0], line[1], elem};
                System.arraycopy(firstPart, 0, newLine, 0, firstPart.length);
                System.arraycopy(line, 4, newLine, firstPart.length, line.length - 4);
            }else
                throw new IllegalArgumentException(
                        "Length of line: " + Arrays.toString(line) +
                                " more then " + Autocomplete.MAX_COLUMNS);


            return newLine;
        } else {

            return line;
        }

    }

    @Override
    public abstract Stream<String[]> readFile() throws IOException;

    @Override
    public abstract Stream<String[]> readIndexed(List<Indexes.IndexTuple> indexes) throws IOException;

    @Override
    public abstract Stream<String[]> read() throws IOException;

    @Override
    public abstract void close() throws IOException;
}
