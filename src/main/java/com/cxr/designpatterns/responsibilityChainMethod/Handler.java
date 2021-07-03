package com.cxr.designpatterns.responsibilityChainMethod;

public abstract class Handler {
    protected Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public abstract void handleRequest(Integer times);
}
