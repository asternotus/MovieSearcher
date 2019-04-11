package com.aleisterfly.testattract.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleisterfly.testattract.R;
import com.aleisterfly.testattract.managers.DataManager;

public class DescriptionFragment extends Fragment {

    private static final String ARGUMENT_PAGE_NUMBER = "page_index";

    private int pageNumber;

    private ImageView iv_cover;
    private TextView tv_title;
    private TextView tv_description;

    public static DescriptionFragment newInstance(int page) {
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        descriptionFragment.setArguments(arguments);
        return descriptionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, null);

        iv_cover = view.findViewById(R.id.iv_cover);
        iv_cover.setImageBitmap(DataManager.getInstance().getMovies().get(pageNumber).getImageBitmap());

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(DataManager.getInstance().getMovies().get(pageNumber).getName());

        tv_description = view.findViewById(R.id.tv_description);
        tv_description.setText(DataManager.getInstance().getMovies().get(pageNumber).getDescription());

        return view;
    }
}
