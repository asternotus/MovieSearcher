package com.aleisterfly.testattract.presenters.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aleisterfly.testattract.presenters.MainPresenter;

public class PresenterFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainPresenter.class)){
            return (T) new MainPresenter();
        }
        return null;
    }
}
