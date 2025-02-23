package com.application.todolist.controller;

import com.application.todolist.entity.Todo;
import com.application.todolist.entity.User;
import com.application.todolist.service.TodoService;
import com.application.todolist.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    public Todo CreateTodo(@RequestBody Todo paramTodo) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        int userId = paramTodo.getUser().getId();
        if (userId == 2 ||  userId == user.getId()) {
            Todo todo = new Todo();
            todo.setTitle(paramTodo.getTitle());
            todo.setStartDate(paramTodo.getStartDate());
            todo.setNotes(paramTodo.getNotes());
            todo.setStar(paramTodo.getStar());
            todo.setUser(paramTodo.getUser());
            todo.setVisibility(paramTodo.getVisibility());

            return todoService.createTodo(todo);
        } else {
            throw new IncorrectCredentialsException();
        }
    }

    @GetMapping("/")
    @RequiresRoles("ordinary")
    public List<Todo> GetTodosUserId(@RequestParam int userId) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        if (user.getId().equals(userId)) {
            return todoService.getTodosByUserId(userId);
        } else {
          throw new IncorrectCredentialsException();
        }
    }

    @GetMapping("/all")
    @RequiresRoles("admin")
    public List<Todo> GetAllTodos(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer importance,
            @RequestParam(required = false) Boolean visibility,
            @RequestParam(required = false) Boolean status
    ) {
        return todoService.getAllTodos(title, importance, visibility, status);
    }

    @GetMapping("/public")
    public List<Todo> GetPublicTodos() {
        return todoService.getAllPublicTodos();
    }

    @DeleteMapping("/{todoId}")
    public void DeleteTodoByUserId(@PathVariable int todoId) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        Optional<Todo> findTodo = todoService.getTodoById(todoId);
        if (findTodo.isPresent()) {
            Todo todo = findTodo.get();
            if ((user == null && todo.getUser().getId() == 2) ||
                (user != null && user.getId().equals(todo.getUser().getId())) ||
                user.getRole().equals("admin")
            ) {
                todoService.deleteTodoById(todoId);
            } else {
                throw new IncorrectCredentialsException();
            }
        }
    }

    @PutMapping("/finish/{todoId}")
    public Todo FinishTodo(@PathVariable int todoId) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Optional<Todo> findTodo = todoService.getTodoById(todoId);
        if (findTodo.isPresent()) {
            Todo todo = findTodo.get();

            if ((user == null && todo.getUser().getId() == 2) ||
                (user != null && user.getId().equals(todo.getUser().getId())) ||
                (user.getRole().equals("admin"))) {
                todo.setFinish(true);
                return todoService.createTodo(todo);
            } else {
                throw new IncorrectCredentialsException();
            }
        }
        return null;
    }

    @PutMapping("/{todoId}")
    public Todo UpdateTodo(@PathVariable int todoId, @RequestBody Todo paramTodo) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Optional<Todo> findTodo = todoService.getTodoById(todoId);
        if (findTodo.isPresent()) {
            Todo todo = findTodo.get();

            if ((user == null && todo.getUser().getId() == 2) ||
                    (user != null && user.getId().equals(todo.getUser().getId())) ||
                    (user.getRole().equals("admin"))) {
                todo.setStar(paramTodo.getStar());
                todo.setVisibility(paramTodo.getVisibility());
                todo.setTitle(paramTodo.getTitle());
                todo.setStartDate(paramTodo.getStartDate());
                todo.setNotes(paramTodo.getNotes());
                return todoService.finishTodo(todo);
            } else {
                throw new IncorrectCredentialsException();
            }
        }
        return null;
    }
}
