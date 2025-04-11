package com.example.loginapp.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAll() {
        String jpql = "select b from Board b order by b.id desc";
        Query query = em.createQuery(jpql, Board.class);
        return query.getResultList();
    }
} 