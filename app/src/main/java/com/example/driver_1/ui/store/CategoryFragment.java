package com.example.driver_1.ui.store;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.driver_1.R;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CategoryFragment extends Fragment  {

    private CategoryViewModel categoryViewModel;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CategoryFragment newInstance(int columnCount) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        categoryViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryViewModel.getCats().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                // Send Items to recycler view
                CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(strings);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }

    // For the activity to implement
    public interface OnItemSelectedListener {
        void onItemSelected(String searchTerm);
    }

    // Reference to the activity
    private OnItemSelectedListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mIdView;
        public final TextView mContentView;
        public String mItem;



        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_category, parent, false));
            itemView.setOnClickListener(this);
            mIdView = (TextView) itemView.findViewById(R.id.item_number);
            mContentView = (TextView) itemView.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        /** Adapted from ZyBooks
         * Binds a Book to a piece of the recycler view
         * @pre the Book has valid info
         * @param term is the Book to be attached to this chunk of the recycler view
         * @post the Book info is displayed in this part of the recycler view
         */
        public void bind(String term) {
            mIdView.setText(term);
            mItem = term;
        }

        @Override
        public void onClick(View v) {
            // Tell MainActivity what search term was clicked
            mListener.onItemSelected(mItem);
        }
    }
    /**
     * TODO: Replace the implementation with code for your data type.
     */
    public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryFragment.ViewHolder> {


        private final List<String> mValues;

        public CategoryRecyclerViewAdapter(List<String> mValues) {
            this.mValues = mValues;
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CategoryFragment.ViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(mValues.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    /** Adapted from ZyBooks band app
     * @pre app started
     * @param context the app context
     * @post mListener attached to listener
     */
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookSelectedListener");
        }
    }

    /** Adapted from ZyBooks band app
     * @pre listener attached
     * @post listener is detached
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}