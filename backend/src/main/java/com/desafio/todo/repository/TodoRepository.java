package com.desafio.todo.repository;

import com.desafio.todo.entity.Todo;
import com.desafio.todo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    @Query("SELECT t FROM Todo t JOIN FETCH t.user WHERE t.id = :id")
    Optional<Todo> findByIdWithUser(UUID id);

    List<Todo> findByUser(User user, Sort sort);
}
