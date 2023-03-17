package autocomlete.support;

public class AutocompleteUtil {
    private static int searchIndex;
    private static boolean searchWillRepeat = true;

    public static String getFirsts(String str, int amount) {
        if(str.length() == 1 || str.equals("\"\"") || str.equals("\\N"))
            return str;

        if(amount > str.length())
            amount = str.length();

        if (str.startsWith("\"")) {
            if(str.length() == amount)
                return str.substring(1, amount);
            return str.substring(1, amount + 1);// ignore "\""
        } else
            return str.substring(0, amount);
    }

    public static String keyPostCheck(String str) {
        if(str.startsWith("\"") && str.length() != 1 && !str.equals("\"\""))
            return str.substring(1);
        return str;
    }

    public static int getSearchIndex() {
        return searchIndex;
    }

    public static void setSearchIndex(int searchIndex) {
        AutocompleteUtil.searchIndex = searchIndex;
    }

    public static boolean isSearchWillRepeat() {
        return searchWillRepeat;
    }

    public static void setSearchWillRepeat(boolean searchWillRepeat) {
        AutocompleteUtil.searchWillRepeat = searchWillRepeat;
    }
}
