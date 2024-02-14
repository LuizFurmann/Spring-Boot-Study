package com.example.study.models;

import jakarta.persistence.*;

@Entity
@Table(name = Task.TABLE_TASK)
public class Task {
    public static final String TABLE_TASK = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne
    private User user;

    @Column(name = "description", length = 255, nullable = false)
    private String desciption;
}
