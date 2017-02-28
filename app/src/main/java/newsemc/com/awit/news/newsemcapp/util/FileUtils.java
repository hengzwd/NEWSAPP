package newsemc.com.awit.news.newsemcapp.util;

import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/7/5.
 */
public class FileUtils {
    private static final String ROOT_DIR="GooglePlay";
    private String SDPATH;
    //定义缓存区大小
    private int FILESIZE = 4 * 1024;
    private static final String TAG = "FileUtils";

    public static String getCacheDir() {
        return getDir("cache");
    }
    public static String getIconDir(){
        return getDir("icon");
    }
    public static String getDownloadDir(){
        return getDir("download");
    }
    private static String getDir(String string) {
        if (isSDAvailable()) {
            return getSDDir(string);
        } else {
            return getDataDir(string);
        }
    }
    /**
     * 获取到手机内存的目录
     * @param string
     * @return
     */
    private static String getDataDir(String string) {
        // data/data/包名/cache/cache
        return UIUtils.getContext().getCacheDir().getAbsolutePath()+"/"+string;
    }
    /**
     * 获取到sd卡的目录
     * @param string
     * @return
     */
    private static String getSDDir(String string) {
        StringBuilder sb=new StringBuilder();
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();///mnt/sdcard
        sb.append(absolutePath);
        sb.append(File.separator);//  /mnt/sdcard/
        sb.append(ROOT_DIR);//mnt/sdcard/GooglePlay
        sb.append(File.separator); //mnt/sdcard/GooglePlay/
        sb.append(string);//mnt/sdcard/GooglePlay/cache
        //sb.append(File.separator); //mnt/sdcard/GooglePlay/cache/
        String filePath = sb.toString();
        File file=new File(filePath);
        if(!file.exists()||!file.isDirectory()){
            if(file.mkdirs()){
                return file.getAbsolutePath();
            }else{
                return "";
            }
        }

        return file.getAbsolutePath();

    }
    /**
     * 判断sd卡是否可以用
     * @return
     */
    private static boolean isSDAvailable() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    //*************************文件储存操作***************************//
    /**
     * 获取SD卡路径
     */
    public FileUtils(){
        SDPATH = Environment.getExternalStorageState()+"/";
    }
    /**
     * 在SD卡上创建文件
     */
    public File createFile(String dirName){
        File dir = new File(SDPATH + dirName);
        dir.mkdirs();
        return dir;
    }
    /**
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName){
        File file = new File(SDPATH + fileName);
        return file.exists();
    }
    /**
     * 将InputStream里面的数据写入到SD卡中
     */
    public File saveFile(String path, String fileName, InputStream inputStream){
        File file = null;
        //定义一个输出流，用来写数据
        OutputStream outputStream = null;
        //创建文件夹
        createFile(path);
        //创建文件
        file = createFile(path + File.separator + fileName);
        try {
            //构造一个新的文件输出流写入文件
            outputStream = new FileOutputStream(file);
            Log.i(TAG, outputStream.toString());
            //创建一个缓存区
            byte[] buffer = new byte[FILESIZE];
            //从输入流中读取的数据存入缓存区
            while ((inputStream.read(buffer)) != -1) {
                //将缓存区的数据写入输出流
                outputStream.write(buffer);
            }
            //刷新流，清空缓冲区数据
            outputStream.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                //关闭流
                outputStream.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }

}
