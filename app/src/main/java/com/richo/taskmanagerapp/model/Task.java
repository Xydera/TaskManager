package com.richo.taskmanagerapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    public String description;

    @NonNull
    public String dueDate;
}

