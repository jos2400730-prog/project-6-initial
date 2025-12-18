package com.example.aggregator.service;

import com.example.aggregator.client.AggregatorRestClient;
import com.example.aggregator.model.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AggregatorService {

    private AggregatorRestClient restClient;

    public AggregatorService(AggregatorRestClient restClient) {
        this.restClient = restClient;
    }

    public Entry getDefinitionFor(String word) {

        return restClient.getDefinitionFor(word);
    }

    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(String chars) {

        List<Entry> wordsThatStartWith = restClient.getWordsStartingWith(chars);
        List<Entry> wordsThatContainSuccessiveLetters = restClient.getWordsThatContainConsecutiveLetters();

        List<Entry> common = new ArrayList<>(wordsThatStartWith);
        common.retainAll(wordsThatContainSuccessiveLetters);

        return common;

    }

    public List<Entry> getAllPalindromes() {
        List<Entry> candidates = new ArrayList<>();

        // Iterate from 'a' to 'z'
        for (char ch = 'a'; ch <= 'z'; ch++) {
            String letter = String.valueOf(ch);

            // Get words starting and ending with the current letter
            List<Entry> startsWith = restClient.getWordsStartingWith(letter);
            List<Entry> endsWith = restClient.getWordsEndingWith(letter);

            // Keep only entries that exist in both lists
            for (Entry entryStart : startsWith) {
                for (Entry entryEnd : endsWith) {
                    if (entryStart.getWord().equals(entryEnd.getWord())) {
                        candidates.add(entryStart);
                        break; // avoid duplicates
                    }
                }
            }
        }

        // Filter candidates for palindromes
        List<Entry> palindromes = new ArrayList<>();
        for (Entry entry : candidates) {
            String word = entry.getWord();
            StringBuilder reversed = new StringBuilder();
            for (int i = word.length() - 1; i >= 0; i--) {
                reversed.append(word.charAt(i));
            }
            if (word.equals(reversed.toString())) {
                palindromes.add(entry);
            }
        }

        // Sort palindromes alphabetically by word
        palindromes.sort((e1, e2) -> e1.getWord().compareTo(e2.getWord()));

        return palindromes;
    }


    public List<Entry> getWordsThatContain(String chars) {
        return restClient.getWordsThatContain(chars);
    }
}
