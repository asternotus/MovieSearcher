package com.aleisterfly.testattract.presenters;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.aleisterfly.testattract.managers.DataManager;
import com.aleisterfly.testattract.activities.interfaces.IMainScreenView;
import com.aleisterfly.testattract.managers.MovieLoadingObserver;
import com.aleisterfly.testattract.presenters.interfaces.IMainPresenter;

public class MainPresenter extends ViewModel implements IMainPresenter {
    private IMainScreenView mainScreenView;

    public void setMainScreenView(IMainScreenView mainScreenView) {
        this.mainScreenView = mainScreenView;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

        if(!DataManager.getInstance().isLoaded()){
            mainScreenView.showProgressDialog();
        }
        DataManager.getInstance().loadMovieDataAsync(new MovieLoadingObserver() {
            @Override
            public void onSuccess() {
                mainScreenView.hideProgressDialog();
                mainScreenView.setData(DataManager.getInstance().getMovies());
            }

            @Override
            public void onError(Exception ex) {
                mainScreenView.hideProgressDialog();
                mainScreenView.showErrorMessage(ex.getMessage());
            }
        });
    }
}
