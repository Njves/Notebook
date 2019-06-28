package com.example.egor.notebook.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.egor.notebook.Activity.MainActivity;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileListHolder>
{
    private final static String TAG = "FileListAdapter";
    private Context context;
    private View.OnClickListener mOnItemClickListener;
    private FileListListener mListener;
    private Fragment host;
    private int count;
    private ArrayList<File> list = new ArrayList<>();
    private List<File> fileList;
    private String path;


    public FileListAdapter(Context context, Fragment host, List<File> fileList)
    {
        this.context = context;
        this.host = host;
        this.fileList = fileList;
        Log.d(TAG, fileList.toString());


    }
    @NonNull
    @Override
    public FileListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.file_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutIdForListItem,viewGroup, false);
        return new FileListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FileListHolder fileListHolder, int i) {
        fileListHolder.bindFileNames(i, fileList);

    }


    @Override
    public int getItemCount() {
        return fileList.size();
    }




    public interface FileListListener
    {
        void updateAdapter(String dir);
        void showWritingFragment(String fileName);
    }


    class FileListHolder extends RecyclerView.ViewHolder
    {
        private TextView mTextViewFileName;
        private TextView mTextViewFileDate;
        private TextView mTextViewExtension;

        public FileListHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewFileName = itemView.findViewById(R.id.file_list_item_name);
            mTextViewFileDate = itemView.findViewById(R.id.file_list_item_creation_date);
            mTextViewExtension = itemView.findViewById(R.id.file_list_item_extension);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context instanceof MainActivity) {
                        mListener = (FileListListener) context;

                        Log.d(TAG, "Позиция в списке " + getAdapterPosition());
                        int pos = getAdapterPosition();
                        File currentFile = fileList.get(getAdapterPosition());
                        Log.d(TAG, currentFile.toString() + " is Directory - " + currentFile.isDirectory());
                        path = Singelton.getInstance().dir = Singelton.getInstance().dir + File.separator + fileList.get(getAdapterPosition()).getName();
                        if(currentFile.isDirectory())
                        {
                            mListener = (FileListListener) host;

                            mListener.updateAdapter(path);
                            fileList.clear();

                        }
                        else
                        {
                            String[] fileNameArray = FileManager.getInstance(context).getFilesNames();
                            mListener.showWritingFragment(path);
                            Log.d(TAG, "Название файла - " + fileNameArray[getAdapterPosition()]);

                        }



                    }
                    else
                    {
                        throw new RuntimeException("Must implements FileListListener on MainActivity");
                    }
                }
            });
        }
        public void bindFileNames(int i, List<File> fileList)
        {
            if(i>=0)
            {
                Resources res = context.getResources();
                String placeholderName = res.getString(R.string.file_list_holder_name);
                String placeholderExtension = res.getString(R.string.file_list_holder_extension);
                String placeHolderDate = res.getString(R.string.file_list_holder_date);

                File currentFile = fileList.get(i);
                Log.d(TAG, "File list: " + currentFile);


                i = i+1;
                mTextViewFileName.setText(placeholderName + " " + currentFile.getName() + " #" + i);
                mTextViewExtension.setText(placeholderExtension+FileManager.getInstance(context).getFileExtension(currentFile.getName()));
                //mTextViewFileDate.setText(placeHolderDate + FileManager.getInstance(context).getFileDate(currentFile.getName()));
                Log.d(TAG, "Список файлов: " + list);

            }

        }


    }
}
class Singelton
{
    public static Singelton instance;
    public String dir = "";
    private Singelton()
    {

    }

    public static Singelton getInstance() {
        if(instance==null)
        {
            instance = new Singelton();

        }
        return instance;

    }
}