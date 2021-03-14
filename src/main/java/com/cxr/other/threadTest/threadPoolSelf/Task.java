package com.cxr.other.threadTest.threadPoolSelf;

public class Task {
    private int id;
    private Runnable job;

    public Task(Runnable job) {
        this.job = job;
    }

    public Task(int id, Runnable job) {
        this.id = id;
        this.job = job;
    }

    public void job() {
        job.run();
    }

    public int getid() {
        return id;
    }
}
