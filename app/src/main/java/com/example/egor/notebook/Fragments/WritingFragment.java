package com.example.egor.notebook.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.egor.notebook.Databases.SQLiteFileListHandler;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

import java.io.*;


public class WritingFragment extends Fragment {
    public static final String TAG = WritingFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private EditText mFileContentTextView;
    private Button mWriteInFileButton;
    private BufferedReader mBufferedReader;
    private Context context;
    private Thread rxThread;
    private String mArgFileName;
    private String mParam2;
    private MenuFragmentInteraction mListener;



    public WritingFragment() {
        // Required empty public constructor
    }


    //Fabric method
    public static WritingFragment newInstance(String param1) {
        WritingFragment fragment = new WritingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArgFileName = getArguments().getString(ARG_PARAM1);
            Log.d(TAG, mArgFileName);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writing, container, false);
        mWriteInFileButton = view.findViewById(R.id.write);
        mFileContentTextView = view.findViewById(R.id.file_content);



        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mArgFileName);


        try {
            mBufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(mArgFileName)));
            String fileText = mBufferedReader.readLine();
            mFileContentTextView.setText(fileText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.wtf(TAG, "Не удалось открыть файла: " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mWriteInFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncWriteInFile().execute();
                Toast.makeText(context, "Файл успешно записан!", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof MenuFragmentInteraction) {
            mListener = (MenuFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MenuFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface MenuFragmentInteraction {
        void updateTitle();
    }
    public void update(String text)
    {
        mFileContentTextView.setText(text);
        Log.d(TAG, "update text view");
    }
    class AsyncWriteInFile extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Запустился поток ввода данныых");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                update(mBufferedReader.readLine());
                Log.i(TAG, "Поток закончил работу!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FileManager.getInstance(context).writeInFile(mArgFileName,mFileContentTextView.getText().toString());
                SQLiteFileListHandler sqLite = new SQLiteFileListHandler(getContext());
                File file = FileManager.getInstance(context).getFileByName(mArgFileName);
                sqLite.updateFileInDB(file, false);
                sqLite.getTable();
                mBufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(file.getName())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

