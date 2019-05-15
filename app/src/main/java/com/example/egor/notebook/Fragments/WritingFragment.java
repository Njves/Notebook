package com.example.egor.notebook.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.egor.notebook.Databases.SQLiteFileListHandler;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

import java.io.*;
import java.util.Arrays;


public class WritingFragment extends Fragment {
    public static final String TAG = WritingFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private EditText mInputTitleFile;

    private EditText mFileContentTextView;
    private Button mWriteInFileButton;

    private FileOutputStream mFileOutputStream;
    private FileInputStream mFileInputStream;
    private BufferedReader mBufferedReader;
    private Context context;
    private Thread rxThread;
    private String mParamFileName;
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
            mParamFileName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writing, container, false);


        mWriteInFileButton = view.findViewById(R.id.write);
        mFileContentTextView = view.findViewById(R.id.file_content);
        mInputTitleFile = view.findViewById(R.id.input_file_name);
        mInputTitleFile.setText(mParamFileName);
        String fileName = mInputTitleFile.getText().toString();
        try {
            mBufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
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
                try {
                    FileManager.getInstance(context).writeInFile(mParamFileName,mFileContentTextView.getText().toString());
                    SQLiteFileListHandler sqLite = new SQLiteFileListHandler(getContext());
                    File file = FileManager.getInstance(context).getFileByName(mParamFileName);
                    sqLite.updateFileInDB(file);
                    sqLite.getTable();
                    mBufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(file.getName())));
                    update(mBufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Toast.makeText(context, "Файл успешно записан!", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void update(String text)
    {
        mFileContentTextView.setText(text);
        Log.d(TAG, "update text view");
    }


}
