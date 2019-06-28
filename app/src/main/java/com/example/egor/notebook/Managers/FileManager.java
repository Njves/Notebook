package com.example.egor.notebook.Managers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.example.egor.notebook.Databases.SQLiteFileListHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    private static final String TAG = FileManager.class.getSimpleName();
    private final File APP_DIRECTORY = new File(Environment.getExternalStorageDirectory() + "notepad/files/");

    private SQLiteFileListHandler fileListDB;
    private Context context;
    private BufferedWriter bufferedFileOutput;
    private BufferedReader fileReader;
    private PrintWriter fileWriter;
    private File currentFile;
    private static FileManager instance;
    public final static String DEFAULT_EXTENSION = ".txt";
    private FileManager(Context context)
    {
        this.context = context;
    }
    public static FileManager getInstance(Context context)
    {
        if(instance==null)
        {
            instance = new FileManager(context);
        }

        return instance;
    }

    public File makeDocument(String title,String extension) throws IOException {
        title = title.trim();
        extension = extension.trim();

        if(!extension.equals(" ")) {

            File file = new File(context.getFilesDir() + File.separator + title + extension);
            bufferedFileOutput = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(file.getName(), Context.MODE_PRIVATE)));
            fileListDB = new SQLiteFileListHandler(context);
            fileListDB.addFileOnDB(file);
            return file;
        }

        return null;
    }
    // Функции для работы с директориями
    public File createDirectory(String dirName)
    {
        File mainDir = context.getFilesDir();
        File childDir = new File(mainDir, dirName);
        childDir.mkdirs();
        return childDir;
    }
    public List<File> getFileListInMainDir()
    {
        List<File> files = new ArrayList<>();
        File file = context.getFilesDir();

        files.addAll(Arrays.asList(file.listFiles()));
        Log.d(TAG, files.toString() + " - Files in main dir");
        return files;
    }
    public List<File> getChildFileList(String dir)
    {

        File mainDir = context.getFilesDir();
        File childDir = new File(mainDir, dir);
        System.out.println(childDir);
        List<File> list = new ArrayList<>();
        for (int i = 0; i < childDir.listFiles().length; i++) {
            list.add(childDir.listFiles()[i]);
        }
        Log.d(TAG, "Название директории: " + dir + ". Список файлов: " + list.toString());
        return list;
    }


    public File getFileByName(String fileName)
    {
        File file;
        file = new File(context.getFilesDir() + File.separator + fileName);
        return file;
    }

    public String writeInFile(String text) throws IOException {

        bufferedFileOutput.write(text);
        bufferedFileOutput.flush();
        bufferedFileOutput.close();
        return "Запись в файл окончена!";
    }
    public String writeInFile(String fileName, String text) throws IOException {

        fileName = fileName.trim();
        bufferedFileOutput = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE)));
        bufferedFileOutput.write(text);
        bufferedFileOutput.close();
        return "Запись в файл окончена!";
    }
    public void deleteFileByName(String name)
    {
        name = name.trim();
        String[] fileList = getFilesNames();
        for (int i = 0; i < fileList.length; i++) {
            if(fileList[i].equals(name))
            {
                context.deleteFile(name);
            }
            else
            {
                Toast.makeText(context, "Файла не существует", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String getFileDate(String fileName)
    {
        fileListDB = new SQLiteFileListHandler(context);
        return fileListDB.getFileDate(fileName);
    }
    public void deleteFilesByCount(int count)
    {
        String[] fileList = getFilesNames();
        for (int i = 0; i < count; i++) {
            context.deleteFile(fileList[i]);
        }



    }
    public void deleteFilesByCount(int count, String[] fileNames)
    {
        if(fileNames!=null) {
            for (int i = 0; i < count; i++) {
                context.deleteFile(fileNames[i]);
            }
        }
        else
        {
//            TODO: Add error
        }

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
    public String[] getFilesListByName(String dirName)
    {

        File childDir = new File(context.getFilesDir(), dirName);
        return childDir.list();
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

    public String getFileName(String fileName)
    {
        String name = "";
        int i = fileName.lastIndexOf(".");
        if(i>0)
        {
            name = fileName.substring(i, name.length());
            return name;
        }

        return null;
    }
    // Создание рассширенной директории в файловой системе устройства
    public void makeExternalDirectory() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "SD-Card is missing");
            Toast.makeText(context, "SD-Карта отсутствует", Toast.LENGTH_LONG).show();
            return;
        }

        if (!APP_DIRECTORY.exists()) {
            APP_DIRECTORY.mkdirs();
        } else {
            Toast.makeText(context, "Директория уже создана!", Toast.LENGTH_SHORT).show();
        }

    }


}


