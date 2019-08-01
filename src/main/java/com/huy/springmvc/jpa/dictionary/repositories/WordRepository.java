package com.huy.springmvc.jpa.dictionary.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huy.springmvc.jpa.dictionary.beans.Word;

@Repository(value = "wordRepository")
public class WordRepository {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;
//    private SessionFactory sessionFactory;

    public void save(final Word word) {
        entityManager.persist(word);
    }

    public void update(final Word word) {
        Word word2 = entityManager.find(Word.class, word.getId());
        word2.setMeaning(word.getMeaning());
    }

    public Word findById(final int id) {
        return entityManager.find(Word.class, id);
    }

    public List<Word> findByWordByPage(final String word, String type, Integer offset, Integer maxResult) {
        TypedQuery<Word> query = (TypedQuery<Word>) entityManager.createQuery(
                "FROM Word as w where w.word like concat(:word,'%') and w.wordtype like :type", Word.class);
        query.setParameter("word", word);
        query.setParameter("type", type);
        List<Word> words = query.setFirstResult(offset != null ? offset : 0)
                .setMaxResults(maxResult != null ? maxResult : 5).getResultList();
        return words;
    }

    public List<Word> findByWord(final String word, String type) {
        TypedQuery<Word> query = (TypedQuery<Word>) entityManager
                .createQuery("FROM Word as w where w.word like concat(:word,'%') and w.wordtype like :type", Word.class);
        query.setParameter("word", word);
        query.setParameter("type", type);
        List<Word> words = query.getResultList();
        return words;
    }

    public void delete(final Word word) {
        Word word2 = entityManager.find(Word.class, word.getId());
        entityManager.remove(word2);
    }

    public List<Word> findAll(Integer offset, Integer maxResult) {
        TypedQuery<Word> query = (TypedQuery<Word>) entityManager.createQuery("FROM Word", Word.class);
        List<Word> words = query.setFirstResult(offset != null ? offset : 0)
                .setMaxResults(maxResult != null ? maxResult : 5).getResultList();
        return words;
    }

    public Long count() {
        return (Long) entityManager.createQuery("Select COUNT(*) FROM Word").getSingleResult();
    }

    public Long countSearch(final String word, String type) {
        return (Long) entityManager
                .createQuery(
                        "Select COUNT(*) FROM Word as w where w.word like concat(:word,'%') and w.wordtype like :type")
                .setParameter("word", word).setParameter("type", type).getSingleResult();
    }
}
