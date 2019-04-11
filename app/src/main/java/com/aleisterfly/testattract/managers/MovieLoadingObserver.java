package com.aleisterfly.testattract.managers;

public interface MovieLoadingObserver {
    void onSuccess();
    void onError(Exception ex);
}
