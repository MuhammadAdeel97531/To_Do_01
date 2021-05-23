package com.example.todo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemListFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListFrag extends Fragment {
    ItemCollection collection;
    RecyclerView rv;
    ItemListAdapter itemListAdapter;
    TextView addItemBtn;
    ImageView empIV;
    TextView empTV;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemListFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemListFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemListFrag newInstance(String param1, String param2) {
        ItemListFrag fragment = new ItemListFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);
        init(v);
        return v;
    }

    private void init(View v)
    {
        collection = new ItemCollection();
        addItemBtn = v.findViewById(R.id.add_item_btn);
        empIV = v.findViewById(R.id.emp_item_list_iv);
        empTV = v.findViewById(R.id.emp_item_list_tv);
        rv = v.findViewById(R.id.item_rv);
        itemListAdapter = new ItemListAdapter(collection);
        rv.setAdapter(itemListAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public ItemCollection getCollection() {
        return collection;
    }

    public void setCollection(ItemCollection collection) {
        if(collection.getCollection().size() == 0)
        {
            empTV.setVisibility(View.VISIBLE);
            empIV.setVisibility(View.VISIBLE);
        }
        itemListAdapter.setCollection(collection);
    }

    public RecyclerView getRv() {
        return rv;
    }
}