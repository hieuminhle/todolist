package com.application.todolist.service;

import com.application.todolist.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    public Todo createTodo(Todo todo);

    public List<Todo> getTodosByUserId(int userId);

    public List<Todo> getAllPublicTodos();

    public List<Todo> getAllTodos(String title, Integer importance, Boolean visibility, Boolean finish);

    public void deleteTodoById(int todoId);

    public Todo finishTodo(Todo todo);

    public Optional<Todo> getTodoById(int id);
}
