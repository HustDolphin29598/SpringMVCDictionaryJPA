package com.huy.springmvc.jpa.dictionary.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huy.springmvc.jpa.dictionary.beans.Word;
import com.huy.springmvc.jpa.dictionary.repositories.WordRepository;

@Service
@Transactional
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    public List<Word> findAll(Integer offset, Integer maxResult) {
        return wordRepository.findAll(offset, maxResult);
    }

    public void save(final Word word) {
        wordRepository.save(word);
    }

    public void update(final Word word) {
        wordRepository.update(word);
    }

    public Word findById(final int id) {
        return wordRepository.findById(id);
    }

    public List<Word> findByWordByPage(final String word, String type, Integer offset, Integer maxResult) {
        return wordRepository.findByWordByPage(word, type, offset, maxResult);
    }

    public List<Word> findByWord(final String word, String type) {
        return wordRepository.findByWord(word, type);
    }

    public void delete(final int id) {
        Word word = wordRepository.findById(id);
        if (word != null)
            wordRepository.delete(word);
    }

    public Long count() {
        return wordRepository.count();
    }

    public Long countSearch(final String word, String type) {
        return wordRepository.countSearch(word, type);
    }
}
