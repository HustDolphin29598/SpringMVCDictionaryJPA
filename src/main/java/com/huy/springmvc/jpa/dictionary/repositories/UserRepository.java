package com.huy.springmvc.jpa.dictionary.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.huy.springmvc.jpa.dictionary.beans.User;

@Repository(value = "userRepository")
@Component
public class UserRepository {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings({ "unchecked" })
    public List<User> findUserByName(String name) {
        TypedQuery<User> query = (TypedQuery<User>)entityManager.createQuery("FROM User as u where u.username = :name ");
        query.setParameter("name", name);
        List<User> users = query.getResultList();
        return users;
    }
}
