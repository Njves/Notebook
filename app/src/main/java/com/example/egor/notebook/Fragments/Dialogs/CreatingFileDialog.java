package com.example.egor.notebook.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

import java.io.IOException;


public class CreatingFileDialog extends DialogFragment {
    private Context context;

    private EditText mFileNameEditText;
    private EditText mFileExtensionEditText;
    private Spinner mCreatingModeSpinner;
    public static final String EXTRA_FILE = "com.example.egor.notebook.file";
    private OnUpdateDataListCallback callback;
    public CreatingFileDialog newInstance(String param)
    {
        CreatingFileDialog creatingFileDialog = new CreatingFileDialog();
        Bundle args = new Bundle();
        args.putString("param1", param);
        creatingFileDialog.setArguments(args);
        return creatingFileDialog;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        callback = (OnUpdateDataListCallback) getTargetFragment();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_file_dialog, null);

        mFileNameEditText = view.findViewById(R.id.file_name_input_dialog);
        mFileExtensionEditText = view.findViewById(R.id.file_extension_input_dialog);
        mCreatingModeSpinner = view.findViewById(R.id.creating_mode_spinner);
        ArrayAdapter<?> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.file_creating_mode,R.layout.support_simple_spinner_dropdown_item);

        mCreatingModeSpinner.setAdapter(spinnerAdapter);
        dialogBuilder.setView(view).setPositiveButton(R.string.file_create_text_create_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!mFileNameEditText.getText().toString().equals(" ") || !mFileExtensionEditText.getText().toString().equals(" "))
                {
                    try {

                            makeFile(mFileNameEditText.getText().toString(), mFileExtensionEditText.getText().toString());



                        /*else
                        {
                            if(!mFileExtensionEditText.getText().toString().equals("")) {
                                makeFile(mFileNameEditText.getText().toString(), "." + mFileExtensionEditText.getText().toString());
                            }
                            else{
                                makeFile(mFileNameEditText.getText().toString(), FileManager.DEFAULT_EXTENSION);
                            }
                        }*/


                        callback.updateList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(context, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(R.string.file_create_text_cancel_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setTitle("Создать файл").create();
        return dialogBuilder.create();
    }
    public interface OnUpdateDataListCallback
    {
        void updateList();
    }
    public  void makeFile(String name, String extension) throws IOException {
        FileManager.getInstance(context).makeDocument(name, extension);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
