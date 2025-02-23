package com.application.todolist.service.impl;

import com.application.todolist.dao.TodoRepository;
import com.application.todolist.dao.UserRepository;
import com.application.todolist.entity.Todo;
import com.application.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getTodosByUserId(int userId) {
        return todoRepository.findByUserId(userId);
    }

    @Override
    public List<Todo> getAllPublicTodos() {
        return todoRepository.findByPublic();
    }

    @Override
    public List<Todo> getAllTodos(String title, Integer importance, Boolean visibility, Boolean finish) {
        return todoRepository.findByAdmin(title, importance, visibility, finish);
    }

    @Override
    public void deleteTodoById(int todoId) {
        todoRepository.deleteById(todoId);
    }

    @Override
    public Todo finishTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Optional<Todo> getTodoById(int id) {
        return todoRepository.findById(id);
    }
}
