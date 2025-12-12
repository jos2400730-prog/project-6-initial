package com.example.aggregator.controller;

import com.example.aggregator.model.Entry;
import com.example.aggregator.service.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AggregatorController {

    private static final Logger log = LoggerFactory.getLogger(AggregatorController.class);

    private AggregatorService aggregatorService;

    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/")
    public List<Entry> helloWorld() {
        List<Entry> entries = new ArrayList<>();
        entries.add(aggregatorService.getDefinitionFor("hello"));
        entries.add(aggregatorService.getDefinitionFor("world"));
        return entries;
    }

    @GetMapping("/getDefinitionFor/{word}")
    public Entry getDefinitionFor(@PathVariable String word) {
        return aggregatorService.getDefinitionFor(word);
    }

    @GetMapping("/getWordsThatContainSuccessiveLettersAndStarsWith/{chars}")
    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(@PathVariable String chars) {

        return aggregatorService.getWordsThatContainSuccessiveLettersAndStartsWith(chars);
    }

    @GetMapping("/getWordsThatContain/{chars}")
    public List<Entry> getWordsThatContain(String chars) {

        StopWatch sw = new StopWatch();
        sw.start();
        List<Entry> entries = aggregatorService.getWordsThatContain(chars);
        sw.stop();

        long nanoSeconds = sw.getTotalTimeNanos();
        String message = new StringBuilder().append("Retrieved entries for words containing [")
                                            .append(chars)
                                            .append("], ")
                                            .append(" containing ")
                                            .append(entries.size())
                                            .append(" entries in ")
                                            .append(nanoSeconds / 1000000.0)
                                            .append("ms")
                                            .toString();
        log.info(message);

        return entries;
    }
}
