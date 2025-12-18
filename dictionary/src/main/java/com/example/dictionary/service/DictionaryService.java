package com.example.dictionary.service;

import com.example.dictionary.exception.WordNotFoundException;
import com.example.dictionary.model.Entry;
import com.example.dictionary.reference.DictionaryReference;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryService {

    public Entry getWord(String word) {

        Map<String, String> dictionary = DictionaryReference.getDictionary();
        String definition = dictionary.get(word);
        Entry entry = new Entry(word, definition);

        if (entry.getDefinition() == null) {
            throw new WordNotFoundException("Word [" + word + "] not found.");
        }

        return entry;
    }

    public List<Entry> getWordsStartingWith(String value) {

        return DictionaryReference.getDictionary()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .startsWith(value))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<Entry> getWordsThatContain(String value) {

        return DictionaryReference.getDictionary()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .contains(value))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<Entry> getWordsThatContainConsecutiveDoubleLetters() {

        return DictionaryReference.getDictionary()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .matches(".*([a-zA-Z])\\1.*"))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                .toList();
    }
    public List<Entry> getWordsEndingWith(String value) {

        return DictionaryReference.getDictionary()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .endsWith(value))
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new Entry(entry.getKey(), entry.getValue()))
                .toList();
    }

}

