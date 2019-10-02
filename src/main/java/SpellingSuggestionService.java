import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;

public class SpellingSuggestionService {

    private MultiMap<String, String> _dictionary;

    public SpellingSuggestionService(MultiMap<String, String> dictionary) {
        _dictionary = dictionary;
    }

    public MultiMap<Integer, String> getSuggestions(String word, int maxDistance) {
        Collection<String> collection = _dictionary.get(Soundex.soundex(word));
        MultiMap<Integer, String> sortedWords = new MultiMap<>();

        for (String str: collection) {
            int distance = Levenshtein.distance(word, str);

            if (distance <= maxDistance) {
                sortedWords.put(distance, str);
            }
        }
        return sortedWords;
    }



    private static MultiMap<String, String> populateDict(String filename) throws IOException {
        var dict = new MultiMap<String, String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = null;

            while ((line = br.readLine()) != null) {
                String soundex = Soundex.soundex(line);
                dict.put(soundex, line);
            }
        }
        return dict;
    }

    public static void main(String[] args) {

        var word = (args.length != 0) ? args[0] : "suggeston";

        try {
            var dict = populateDict("src/main/resources/dict.txt");
            var spellingSuggestionService = new SpellingSuggestionService(dict);
            System.out.println("Word misspelled: " + word);
            System.out.println("Matching words: ");

            var suggestions = spellingSuggestionService.getSuggestions(word, 3);

            for (Entry<Integer, Collection<String>> entry: suggestions.entrySet()) {
                int value = entry.getKey();

                for (String str: entry.getValue()) {
                    System.out.println(str + " - " + value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}