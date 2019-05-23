package com.example.egor.notebook.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import com.example.egor.notebook.Adapters.FileListAdapter;
import com.example.egor.notebook.Fragments.Dialogs.CreatingFileDialog;
import com.example.egor.notebook.Fragments.Dialogs.DeletingFileDialog;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

import java.io.File;
import java.util.Arrays;


public class MenuFragment extends Fragment implements CreatingFileDialog.OnUpdateDataListCallback, DeletingFileDialog.OnDeleteFileCallback  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FILE = "file";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = MenuFragment.class.getSimpleName();
    private RecyclerView mFileRecyclerView;
    private FileListAdapter messageListAdapter;
    public static final int REQUEST_CODE = 0;
    private DialogFragment dialog;

    private Context context;
    private FloatingActionButton addFileFab;
    // TODO: Rename and change types of parameters
    private File fileParam;


    private OnMenuFragmentDataListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(File fileParam) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILE, fileParam);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileParam = (File) getArguments().getSerializable(ARG_FILE);

        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.current_action_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        addFileFab = v.findViewById(R.id.add_fab_menu);
        mFileRecyclerView = v.findViewById(R.id.file_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFileRecyclerView.setLayoutManager(linearLayoutManager);

        initFileListAdapter();

        Log.d(TAG, Arrays.toString(context.fileList()));
        addFileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CreatingFileDialog();
                if(getActivity().getSupportFragmentManager() != null) {
                    dialog.show(getActivity().getSupportFragmentManager(), "creating");
                    dialog.setTargetFragment(MenuFragment.this, REQUEST_CODE);
                }else
                {
                    Log.e(TAG, "FragmentManager is null");
                    Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.file_controler_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.file_delete_menu:
            {
                dialog = new DeletingFileDialog();
                dialog.show(getFragmentManager(), "deleting");
                dialog.setTargetFragment(this, REQUEST_CODE);

            }
        }
        return super.onOptionsItemSelected(item);
    }

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

    @Override
    public void updateList() {
        initFileListAdapter();
    }

    public void initFileListAdapter()
    {
        Log.d(TAG, "Update list in callback" );
        messageListAdapter = new FileListAdapter(getContext(), FileManager.getInstance(context).getFilesNames());
        mFileRecyclerView.setAdapter(messageListAdapter);
        messageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete() {
        initFileListAdapter();
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
    public interface OnAddFileOnListAdapter
    {

    }
}
