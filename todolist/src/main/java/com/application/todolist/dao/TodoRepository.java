package com.application.todolist.dao;

import com.application.todolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Query("select t from Todo t where t.user.id = :userId")
    List<Todo> findByUserId(@Param("userId") int userId);

    @Query("select t from Todo t where t.visibility = true")
    List<Todo> findByPublic();

    @Query("select t from Todo t where (:title = '' or t.title like %:title%) and (:star is null or t.star = :star) and (:visibility is null or t.visibility = :visibility) and (:finish is null or t.finish = :finish)")
    List<Todo> findByAdmin(
            @Param("title") String title,
            @Param("star") Integer star,
            @Param("visibility") Boolean visibility,
            @Param("finish") Boolean finish
    );
}
