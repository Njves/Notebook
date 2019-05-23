package com.example.egor.notebook.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

public class DeletingFileDialog extends DialogFragment {
    public static final String TAG = DeletingFileDialog.class.getSimpleName();
    private Spinner mSpinnerFileList;
    private OnDeleteFileCallback callback;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        callback = (OnDeleteFileCallback) getTargetFragment();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_files_dialog,null);

        mSpinnerFileList = dialogView.findViewById(R.id.spinner_remove_file_dialog);

        ArrayAdapter<?> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, FileManager.getInstance(getContext()).getFilesNames());
        mSpinnerFileList.setAdapter(arrayAdapter);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity()).setTitle("Удалить файлы").setView(dialogView).setPositiveButton(R.string.file_delete_placeholder, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileManager.getInstance(getContext()).deleteFileByName(mSpinnerFileList.getSelectedItem().toString());
                callback.onDelete();



            }
        }).setIcon(R.drawable.ic_delete_black_24dp);
        return dialogBuilder.create();
    }
    public interface OnDeleteFileCallback
    {
        void onDelete();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
