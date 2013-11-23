package com.cscentr.microvisio;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import java.util.Properties;
import com.cscentr.microvisio.FullscreenActivity.FileDialogDepends;

/**
 * <p>Helper for implementing the file open 
 * dialog and its data handling actions.</p>
 *
 * @author Edward Muhutdinov <wtiger@mail.ru>
 */

public class FileDialog extends AlertDialog{
   Context context;
   File dir;
   String[] files;
   AlertDialog.Builder builder;
   AlertDialog dialog;
   FilenameFilter filenameFilter;
   Properties plugin_props;
   FileDialogDepends fileDialogDepends;

   public static String FILENAME_FILTER = 
      "(?i)^feeder\\-.*?\\.txt$";
   public static String CURRENT_PATH = "/sdcard";
   
   public static String ROOT_PATH = 
      Environment.getExternalStorageDirectory().getPath();;

   
   public FileDialog(Context context) {
      super(context);
      this.context=context;
      
      filenameFilter=new FilenameFilter(){
         @Override
         public boolean accept(File directory, String fileName) {
            return true;
           // fileName.matches(FILENAME_FILTER);
         }
      };
   }

/**
 * ����� ������� �������� �����.
 * @param fileDialogDepends � ������, ������
 * �� ������� ���������� �� ���������� activity ���
 * ����������� � ���.
 */
   public void openFileDialog(FileDialogDepends fileDialogDepends){
      this.fileDialogDepends=fileDialogDepends;
      openFileDialog();
   }

/**
 * ���������� ��������� ��� ������������
 * ������������ ������� �����.
 */
   private void openFileDialog(){
      builder = new AlertDialog.Builder(context);
       
      dir=new File(CURRENT_PATH);
      builder.setTitle(R.string.select_file);
      files=concatAll(dirs(dir),files(dir,filenameFilter));
      builder.setItems(files, listenerFileDialog);
      builder.setNegativeButton(android.R.string.cancel, null);
      
      dialog = builder.create();
      dialog.show();
   }
    
/**
 * ������������ ������� �� ������� ������ �� �������:
 * � ���� ������ <..> � ���������
 *    �� ������� ����� � �������� openFileDialog
 * � ���� ������� ����� � ���������
 *    � ����� � �������� openFileDialog
 * � ���� ������ ���� � ��������� �������
 *    ���� � ���������, �������� fileSelected,
 * � ���� ������ ������ � ��������� ������
 */
   private DialogInterface.OnClickListener listenerFileDialog=
         new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
         dialog.dismiss();

         if (files[which].equals("..")){//������ �� <..>
            dir=new File(dir.getParent());
         }else dir=new File(CURRENT_PATH+"/"+files[which]); 
         if(dir.isFile()){ //������ ����
            Editor ed = context.getSharedPreferences(
               "preferences",Context.MODE_PRIVATE).edit();
            ed.putString("pref_current_path", CURRENT_PATH);
            ed.apply();
            fileSelected(dir);                
         }else if(dir.isDirectory()){ //������� �����
            if (!files[which].equals(".."))
               dir=new File(CURRENT_PATH+"/"+files[which]);
            CURRENT_PATH=dir.toString();
            openFileDialog(fileDialogDepends);
         }
      }
   };
    
/**
 * ���� ������.
 * @param file � ��������� ����.
 */
   public void fileSelected(File file){

      //����� ����� ���������, ������� �����������, �����
      //���� ������. �� ���� �������� �����, ���������,
      //����������� � ��� �����.
	   
      //����� �������� ����������� � ���������� activity.
      fileDialogDepends.refresh(file.getName());

   }

/**
 * ������ ��������������� ������ ����� �� ����� path.
 * 1) ������������ �����, ������������ � �����.
 * 2) � ������ ������ ���������� ����� "..". 
 * 3) ���������� �������������� � ������������� ������, ���
 * �������������������� �������� ���������� ���������� 
 * ��������������� ����������.
 */
   public static String[] dirs(File path){
      final List<String> files = new ArrayList<String>();
      
      if(!path.toString().equals(ROOT_PATH))files.add("..");

      for(File a:path.listFiles()){
         if(a.isDirectory() && !a.getName().
               toString().startsWith("."))
                  files.add(a.getName().toString()+"/");
      }
      String[] res=(String[]) 
         files.toArray(new String[files.size()]);
      Arrays.sort(res);
      return res;
   }

/**	
 * ������ ��������������� ������ ������ �� ����� path,
 * ��������������� � ������������ � �������� filter.
 * ���������� �������������� � ������������� ������, ���
 * �������������������� �������� ���������� ���������� 
 * ��������������� ����������.
 */
   public static String[] files(File path,
         FilenameFilter filter){
      final List<String> files = new ArrayList<String>();
      
      for(File a:path.listFiles(filter))
         if(a.isFile())files.add(a.getName().toString());
      
      String[] res=(String[]) 
         files.toArray(new String[files.size()]);
      Arrays.sort(res);
      return res;
   }

/**
 * ����������� ��������.
 * @param first
 * @param rest
 * @return
 */
   public static <T> T[] concatAll(T[] first, T[]... rest) {
         int totalLength = 0;
      if(first!=null)totalLength=first.length;
      for (T[] array : rest)
         if(array!=null) totalLength += array.length;
      T[] result = Arrays.copyOf(first, totalLength);
      int offset = first.length;
      for (T[] array : rest) {
         if(array!=null){
            System.arraycopy(array, 0, 
               result, offset, array.length);
            offset += array.length;
         }
      }
      return result;
   }
}
