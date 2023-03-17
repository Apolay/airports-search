package autocomlete.writers;

import java.util.List;
import java.util.stream.Stream;

public class ConsoleWriter extends DataWriterImpl {
    @Override
    public void write(Stream<String[]> toWrite, String key) {
        toWrite.peek(x ->{if(startTime == 0) startTime = System.currentTimeMillis();})
                .map(this::format)
                .map(line -> caching(key, line))//caching
                .forEach(line -> {
                    linesCount++;
                    System.out.println(line);
                });
    }

    @Override
    public void writeFromCache(List<String> toWrite) {
        toWrite.forEach(line -> {
                    linesCount++;
                    System.out.println(line);
                });
    }
}
