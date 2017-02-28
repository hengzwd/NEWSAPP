package newsemc.com.awit.news.newsemcapp.activity;

import java.io.File;

/**
 * Created by PC-18 on 2016/1/26.
 * 操作文件帮助类
 */
public final class OperationFileHelper {
    /**
     * 递归删除文件和文件夹
     */
    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}  
