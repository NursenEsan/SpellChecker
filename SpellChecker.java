package Proje2;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpellChecker<K> {
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private Entry<K>[] table;
    private int size;

    public SpellChecker() {
        this.table = new Entry[2000];
        this.size = 0;
    }

    private int hash(K key) {
        int hashValue = key.hashCode() % table.length;
        return Math.abs(hashValue);
    }

    private int probe(int hash, int i) {
        // Quadratic probing: (hash + i^2) % table.length
        return (hash + i * i) % table.length;
    }

    public void insert(K key) {
        if (size >= LOAD_FACTOR_THRESHOLD * table.length) {
            resize();
        }
        int hash = hash(key);
        for (int i = 0; i < table.length; i++) {
            int j = probe(hash, i);
            if (table[j] == null) {
                table[j] = new Entry<>(key);
                size++;
                return;
            }
        }
    }

    public void delete(K key) {
        int hash = hash(key);
        for (int i = 0; i < table.length; i++) {
            int j = probe(hash, i);
            if (table[j] == null) {
                return;
            }
            if (key.equals(table[j].getKey())) {
                table[j] = null;
                size--;
                return;
            }
        }
    }

    public void loadDictionary(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!word.isEmpty()) {
                    insert((K) word);
                }
            }
            scanner.close();
            System.out.println("Dictionary loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public void filesSpellCheck(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] words = scanner.next().toLowerCase().replaceAll("[^a-z]", "").split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty() && !search((K) word)) {
                        System.out.println("Spelling error occurred: " + word);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public boolean search(K key) {
        int hash = hash(key);
        for (int i = 0; i < table.length; i++) {
            int j = probe(hash, i);
            if (table[j] == null) {
                return false;
            }
            if (key.equals(table[j].getKey())) {
                return true;
            }
        }
        return false;
    }

    private void resize() {
        Entry<K>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        size = 0;
        for (Entry<K> entry : oldTable) {
            if (entry != null) {
                insert(entry.getKey());
            }
        }
    }

    public static void main(String[] args) {
        SpellChecker<String> dictionary = new SpellChecker<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Load a dictionary from a given text file.");
            System.out.println("2. Search for an entry in this dictionary.");
            System.out.println("3. Insert a word to the dictionary.");
            System.out.println("4. Delete a word from the dictionary.");
            System.out.println("5. Spell check a file.");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Current Directory: " + System.getProperty("user.dir"));
                    System.out.print("Enter the dictionary file name (e.g., dict.txt): ");
                    String fileName = scanner.nextLine();
                    dictionary.loadDictionary(fileName);
                    break;

                case 2:
                    System.out.print("Enter the word to search: ");
                    String searchWord = scanner.nextLine();
                    System.out.println("Word found: " + dictionary.search(searchWord));
                    break;
                case 3:
                    System.out.print("Enter the word to insert: ");
                    String insertWord = scanner.nextLine();
                    dictionary.insert(insertWord);
                    System.out.println("Word inserted successfully.");
                    break;
                case 4:
                    System.out.print("Enter the word to delete: ");
                    String deleteWord = scanner.nextLine();
                    dictionary.delete(deleteWord);
                    System.out.println("Word deleted successfully.");
                    break;
                case 5:
                    System.out.print("Enter the file name for spell check (e.g., dict.txt):"
                                     + "#We used the abc.txt file for spell check ");
                    String spellCheckFileName = scanner.nextLine();
                    dictionary.filesSpellCheck(spellCheckFileName);
                    break;
                case 6:
                    System.out.println("Exiting program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static class Entry<T> {
        private final T key;

        public Entry(T key) {
            this.key = key;
        }

        public T getKey() {
            return key;
        }
    }
}