package com.mag.digikala.View;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.mag.digikala.Controller.Activities.CardActivity;
import com.mag.digikala.Controller.Activities.SearchActivity;
import com.mag.digikala.R;
import com.mag.digikala.Var.Constants;
import com.mag.digikala.ViewModel.FilteToolbarViewModel;
import com.mag.digikala.ViewModel.MainToolbarViewModel;

public class FilterToolbarFragment extends Fragment {

    private FilteToolbarViewModel viewModel;

    public static final String ARG_SEARCH_STRING = "arg_search_string";
    private String searchString;

    private MaterialButton searchBtn, cardBtn, backBtn;
    private TextView cardNumber;

    public static FilterToolbarFragment newInstance(String searchString) {

        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_STRING, searchString);

        FilterToolbarFragment fragment = new FilterToolbarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterToolbarFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FilteToolbarViewModel.class);
        viewModel.loadData();
        viewModel.getNumberOfCardProducts().observe(this, numberOfCardProducts -> {
            if (numberOfCardProducts == 0) {
                cardNumber.setBackgroundColor(getResources().getColor(R.color.nothing));
                cardNumber.setText(Constants.EMPTY_CHAR);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    cardNumber.setBackground(getResources().getDrawable(R.drawable.cart_counter));
                }
                cardNumber.setText(String.valueOf(numberOfCardProducts));
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_toolbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchString = getArguments().getString(ARG_SEARCH_STRING);

        findComponents(view);

        backBtn.setOnClickListener(backBtnView -> getActivity().finish());

        searchBtn.setOnClickListener(searchBtnView -> startActivity(SearchActivity.newIntent(getContext())));

        cardBtn.setOnClickListener(cardBtnView -> startActivity(CardActivity.newIntent(getContext())));

    }

    private void findComponents(@NonNull View view) {
        cardNumber = view.findViewById(R.id.filter_toolbar_fragment__card_number);
        backBtn = view.findViewById(R.id.filter_toolbar_fragment__back_btn);
        cardBtn = view.findViewById(R.id.filter_toolbar_fragment__cart_btn);
        searchBtn = view.findViewById(R.id.filter_toolbar_fragment__search_btn);
    }

}
