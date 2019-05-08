package com.example.egor.notebook.Managers;

import android.content.Context;
import android.util.Log;
import com.example.egor.notebook.Databases.SQLiteFileListHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    private File file;
    private SQLiteFileListHandler fileListDB;
    private ArrayList<File> mFileArrayList = new ArrayList<>();
    private  Context context;
    private BufferedWriter bufferedFileOutput;
    private File currentFile;

    public FileManager(Context context)
    {
        this.context = context;

    }
    public void addFile(File file)
    {
        mFileArrayList.add(file);
    }
    public File makeDocument(String title,String extension) throws IOException {

        File file = new File(title+extension);
        bufferedFileOutput = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(file.getName(), Context.MODE_PRIVATE)));
        fileListDB = new SQLiteFileListHandler(context);
        fileListDB.addFileOnDB(file);
        currentFile = file;
        return file;

    }

    public String writeInFile(String text) throws IOException {
        bufferedFileOutput.write(text);
        bufferedFileOutput.flush();
        bufferedFileOutput.close();
        return "Запись в файл окончена!";
    }
    public String writeInFile(File file, String text) throws IOException {
        bufferedFileOutput = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(file.getName(), Context.MODE_PRIVATE)));

        bufferedFileOutput.write(text);
        bufferedFileOutput.flush();
        bufferedFileOutput.close();
        return "Запись в файл окончена!";
    }

    public File getCurrentDocument() {
        return currentFile;
    }

    private void setCurrentDocument(File currentDocument) {
        this.currentFile = currentDocument;
    }
    public String[] getFilesNames()
    {
        Log.d("FileManager", Arrays.toString(context.fileList()));
        return context.fileList();
    }
    public String getFileExtension(String fileName)
    {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        else
        {
//            TODO: Create error list and code
            extension = "Неизвестное рассширение";
        }
        return extension;
    }

    public class FileCreator
    {
        public String fileName;
        public String fileExtension;
    }
}