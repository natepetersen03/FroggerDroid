package com.example.froggerdroid.data;

public interface Updatable {
    default void update() { }

    default void fixedUpdate() { }
}
