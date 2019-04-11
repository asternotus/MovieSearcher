package com.aleisterfly.testattract.activities.interfaces;

import com.aleisterfly.testattract.models.Movie;

import java.util.List;

public interface IMainScreenView extends IScreenView {
    void setData(List<Movie> movies);

    void showErrorMessage(String message);

    void showProgressDialog();

    void hideProgressDialog();
}
