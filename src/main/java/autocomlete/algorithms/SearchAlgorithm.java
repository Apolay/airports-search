package autocomlete.algorithms;

import autocomlete.support.AutocompleteUtil;

import java.util.Locale;
import java.util.stream.Stream;

public interface SearchAlgorithm {
    Stream<String[]> search(Stream<String[]> fromStream, String suffix);
}
