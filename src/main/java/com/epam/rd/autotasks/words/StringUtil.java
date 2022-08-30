package com.epam.rd.autotasks.words;

import java.util.ArrayList;
import java.util.Arrays;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (words == null || words.length == 0 || sample == null || sample.isEmpty()) return 0;


        int count = 0;
        String sample2 = sample.toLowerCase().trim();
        for (int i = 0; i < words.length; i++) {
            String qq = words[i].toLowerCase().trim();
            if (words[i] != null &&
                    !words[i].isEmpty() &&
                    words[i].toLowerCase().trim().equals(sample2)) {
                count++;
            }

        }
        return count;

    }

    public static String[] splitWords(String text) {
        ArrayList<String> wordText = new ArrayList<String>();
        boolean isWholeSeparator = true;
        boolean inWord = true;
        int startOfWord = 0;
        if (text != null && !text.trim().isEmpty()) {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c == ',' || c == '.' || c == ';' || c == ':' || c == ' ' || c == '?' || c == '!') {
                    if (!isWholeSeparator && inWord) {
                        wordText.add(text.substring(startOfWord, i));
                        inWord = false;
                    }
                    startOfWord = i + 1;
                } else {
                    isWholeSeparator = false;
                    inWord = true;
                }
            }
            if (!isWholeSeparator && inWord)
                wordText.add(text.substring(startOfWord, text.length()));
            if (isWholeSeparator)
                return null;
            else {
                return wordText.toArray(String[]::new);
            }

        } else return null;

    }



    public static String convertPath(String path, boolean toWin) {
        String result;
        if (path == null || path.isEmpty() ||
                (path.indexOf("/") != -1 && path.indexOf("\\") != -1) || //  contain of '\' and '/'
                (path.indexOf("~") == 0 && path.indexOf("\\") != -1) ||  // start character is '~' and contain of '\'
                (path.indexOf("~", 1) > 0) ||
                (path.indexOf("C:\\", 3) > 0) ||
                (path.indexOf("\\\\") > 0) ||
                (path.indexOf("//") > 0)
        )
            return null;
        if (toWin) {
            result = convert(path, "~", "C:\\User");
            result = convert(result, "/", "C:\\");
            result = result.replace("/", "\\");
            return result;
        } else {
            result = convert(path, "C:\\User", "~");
            result = convert(result, "C:\\", "/");
            result = result.replace("\\", "/");
            return result;
        }
    }

    public static String convert(String path, String patternFrom, String PattegnTo) {
        int i = path.indexOf(patternFrom);
        if (i != -1 && i == 0)
            return PattegnTo + path.substring(patternFrom.length());
        return path;
    }

    public static String joinWords(String[] words) {
        if (words == null || words.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (words[i] != null && !words[i].isEmpty()) {
                sb.append(words[i]);
                sb.append(", ");
            }
        }
        if (sb.length() == 0 )
            return null;
        else {
            sb.delete(sb.length()-2, sb.length());
            sb.insert(0,'[');
            sb.append("]");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS",};
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}