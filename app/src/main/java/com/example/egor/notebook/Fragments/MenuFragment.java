package com.example.egor.notebook.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import com.example.egor.notebook.Adapters.FileListAdapter;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = MenuFragment.class.getSimpleName();
    private RecyclerView mFileRecyclerView;
    private FileManager mFileManager;
    private Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnMenuFragmentDataListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        mFileManager = new FileManager(getContext());
        mFileRecyclerView = v.findViewById(R.id.file_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFileRecyclerView.setLayoutManager(linearLayoutManager);

        FileListAdapter messageListAdapter = new FileListAdapter(getContext(), mFileManager.getFilesNames());
        mFileRecyclerView.setAdapter(messageListAdapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnMenuFragmentDataListener) {
            mListener = (OnMenuFragmentDataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMenuFragmentDataListener {
        void onCreateFragment(Fragment fragment);

    }
}
