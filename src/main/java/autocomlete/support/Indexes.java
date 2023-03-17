package autocomlete.support;

import java.util.*;

public class Indexes {
    private static Indexes instance;
    private long generalLen = 0;
    private final Map<String, List<IndexTuple>> indexes = new HashMap<>(44);

    public void add(String key, int lineLength) {
        if(indexes.containsKey(key)) {
            indexes.get(key).add(new IndexTuple(generalLen, lineLength));
        }
        else {
            List<IndexTuple> startPosAndLen = new ArrayList<>();
            startPosAndLen.add(new IndexTuple(generalLen, lineLength));
            indexes.put(key, startPosAndLen);

        }

        generalLen += lineLength;
    }

    public boolean isEmpty() {
        return indexes.isEmpty();
    }

    public List<IndexTuple> getIndexesForStr(String str) {
        String key = AutocompleteUtil.getFirsts(str.toLowerCase(Locale.ROOT), 1);
        return indexes.get(key);
    }

    public boolean contains(String key) {
        key = AutocompleteUtil.getFirsts(key.toLowerCase(Locale.ROOT), 1);
        return indexes.containsKey(key);
    }

    private Indexes(){}

    public static Indexes getInstance() {
        return Objects.requireNonNullElseGet(instance, () -> instance = new Indexes());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(var key : indexes.keySet()) {
            builder.append("\n\t").append(key);
            for(var tuple : indexes.get(key))
                builder.append("\n").append(tuple.toString());
        }

        return builder.toString();
    }

    public class IndexTuple {
        private long position;
        private int length;

        public IndexTuple(long position, int length) {
            this.position = position;
            this.length = length;
        }

        public long getPosition() {
            return position;
        }

        public int getLength() {
            return length;
        }

        public void setPosition(Long position) {
            this.position = position;
        }

        @Override
        public String toString() {
            return "bytesToSkip=" + position +
                    ", length=" + length ;
        }
    }
}
