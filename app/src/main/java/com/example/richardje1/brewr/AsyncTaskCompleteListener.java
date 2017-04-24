package com.example.richardje1.brewr;

/**
 * Created by martin on 4/23/17.
 */

public interface AsyncTaskCompleteListener<T> {
    public void onTaskComplete(T result, int number);
}
