package com.example.driver_1.ui.store;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver_1.R;
import com.example.driver_1.data.store.Item;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoreFragment extends Fragment {

    private StoreViewModel storeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storeViewModel =
                new ViewModelProvider(this).get(StoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        /*storeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    // For the activity to implement
    public interface OnItemSelectedListener {
        void onItemSelected(int ItemId);
    }

    // Reference to the activity
    private OnItemSelectedListener mListener;

    /**
     * Adapted from ZyBooks band app BandHolder class
     */
    private class ItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Item mItem;

        private TextView mNameTextView;
        private ImageView mItemPic;

        /** Adapted from ZyBooks band app
         * Creates individual cities in the recycler view
         * @pre the app has started
         * @param inflater inflates the xml
         * @param parent corresponds to the parent view
         * @post a item holder is created
         */
        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list_store, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.storeName);
            mItemPic = (ImageView) itemView.findViewById(R.id.storePic);
        }

        /** Adapted from ZyBooks
         * Binds a item to a piece of the recycler view
         * @pre the item has valid info
         * @param item is the item to be attached to this chunk of the recycler view
         * @post the item info is displayed in this part of the recycler view
         */
        public void bind(Item item) {
            mItem = item;
            //mItem.setTxt(mItemPic);
            mNameTextView.setText("test");
            //mItemPic.setImageDrawable();
        }

        /** Adapted from ZyBooks
         * Responds to a item being selected
         * @pre the item item exists
         * @param view the current view
         * @post the app is notified that a item was selected
         */
        @Override
        public void onClick(View view) {
            // Tell ListActivity what item was clicked
            mListener.onItemSelected(mItem.getId());
        }
    }

    /**
     * Adapted from ZyBooks band app BandAdapter class
     */
    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Item> mItems;

        public ItemAdapter(List<Item> items) {
            mItems = items;
        }

        /** Adapted from ZyBooks band app
         * @pre the app has started
         * @param parent the parent viewgroup
         * @param viewType the view type
         * @return the item holder
         * @post the item holder exists
         */
        @NotNull
        @Override
        public ItemHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ItemHolder(layoutInflater, parent);
        }

        /** Adapted from ZyBooks band app
         * @pre recycler view exists
         * @param holder the piece of the recycler view to be filled
         * @param position which piece of the recycler view to fill
         * @post the item is bound to the holder
         */
        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bind(item);
        }

        // Getter for number of cities
        @Override
        public int getItemCount() {
            return mItems.size();
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
                    + " must implement OnItemSelectedListener");
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