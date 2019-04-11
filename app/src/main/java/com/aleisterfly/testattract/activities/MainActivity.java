package com.aleisterfly.testattract.activities;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aleisterfly.testattract.R;
import com.aleisterfly.testattract.activities.interfaces.IMainScreenView;
import com.aleisterfly.testattract.adapters.MovieAdapter;
import com.aleisterfly.testattract.managers.DataManager;
import com.aleisterfly.testattract.models.Movie;
import com.aleisterfly.testattract.presenters.MainPresenter;
import com.aleisterfly.testattract.presenters.factories.PresenterFactory;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ParentActivity implements IMainScreenView {

    private MainPresenter mainPresenter;
    private ListView listView;
    private MovieAdapter movieAdapter;

    private DrawerLayout drawerLayout;

    private EditText et_search;

    private LinearLayout content_frame;
    private ProgressDialog dialog;

    private List<Movie> filteredMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isTablet()){
            setContentView(R.layout.activity_main_tablet);
        }else{
            setContentView(R.layout.activity_main);
        }

        content_frame = findViewById(R.id.content_frame);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);

        if(!isTablet()) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.burger_button);
            drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.setScrimColor(Color.TRANSPARENT);

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                    float slideX = drawerView.getWidth() * slideOffset;
                    content_frame.setTranslationX(slideX);
                }
            };

            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        }

        listView = findViewById(R.id.lv_movies);
        et_search = findViewById(R.id.et_search);

        PresenterFactory presenterFactory = new PresenterFactory();
        mainPresenter = ViewModelProviders.of(this, presenterFactory).get(MainPresenter.class);
        mainPresenter.setMainScreenView(this);

        getLifecycle().addObserver(mainPresenter);

        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        listView.setAdapter(movieAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index;
                if(!filteredMovies.isEmpty()){
                    Movie m = filteredMovies.get(position);
                    index = DataManager.getInstance().getMovies().indexOf(m);
                } else{
                    index = position;
                }

                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                intent.putExtra(getString(R.string.position), index);
                startActivity(intent);
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredMovies.clear();

                for (Movie m: DataManager.getInstance().getMovies()) {
                    if(m.getName().toLowerCase().contains(s.toString().toLowerCase())){
                        filteredMovies.add(m);
                    }
                }

                setData(filteredMovies);
            }
        });
    }

    @Override
    public void setData(final List<Movie> movies) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                movieAdapter.setMovies(movies);
                movieAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showErrorMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void showProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage(getString(R.string.please_wait_message));
                dialog.show();
            }
        });
    }

    @Override
    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(dialog != null){
                    dialog.hide();
                    dialog = null;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!isTablet()) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}