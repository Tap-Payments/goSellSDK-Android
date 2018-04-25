package company.tap.gosellapi.internal.fragments;

import android.content.Context;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.tap.gosellapi.R;
import company.tap.gosellapi.internal.adapters.MainRecyclerViewAdapter;

public class MainScreenFragment extends Fragment {

    public MainScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.gosellapi_fragment_main_screen, container, false);
        initMainRecyclerView(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void initMainRecyclerView(View view) {
        RecyclerView mainRecyclerView = view.findViewById(R.id.mainRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter();

        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(adapter);
    }
}
