package com.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.entity.Todo;
import com.todo.entity.User;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUser(User user);

}