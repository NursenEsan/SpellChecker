# Spell Checker - Hash Table with Quadratic Probing

This is a simple spell checker program written in Java. It uses a custom hash table implementation with **quadratic probing** for collision resolution. The program allows users to load a dictionary, insert or delete words, search entries, and spell check a given text file.

---

## Features

- Load a dictionary from a file.
- Insert and delete words from the dictionary.
- Search for specific words.
- Spell check another text file and identify misspelled words.
- Uses **quadratic probing** for collision handling in hash table.
- Automatically resizes the hash table when the load factor exceeds 0.75.

---

## How It Works

1. **Dictionary Load**  
   Load a plain text file containing words (one or more per line) into the hash table.

2. **Spell Check**  
   Check another file for spelling errors by comparing each word with the loaded dictionary.

3. **Word Management**  
   Dynamically insert or remove words from the dictionary.

---

## File Structure

- `SpellChecker.java` – Main program and hash table logic
- `dict.txt` – Sample dictionary file (you can replace this)
- `abc.txt` – Sample text file to check for spelling errors

---

## How to Run

1. Compile the program:

```bash
javac SpellChecker.java
