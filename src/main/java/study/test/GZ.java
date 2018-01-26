package study.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import com.google.common.collect.Lists;

/**
 * Created by fankun on 2018/1/23.
 */
public class GZ {
    public static void main(String[] args) throws Exception{
        File dic = new File("D:\\fw\\pt\\pt");
        File[] files = dic.listFiles();
        List<List<String>> newFileString = Lists.newArrayList();
        for (File file : files) {
//            String[] nameSplit = file.getName().substring(0,file.getName().indexOf(".")).split("-");
//            if(NumberUtils.isNumber(nameSplit[nameSplit.length-1])){
            if(file.getName().substring(file.getName().indexOf("."),file.getName().length()+1).equals("gz")){
                newFileString.add(getLineFromGZFile(file));
            }else{
                newFileString.add(readLineFromFile(file));
            }
        }
        for (File file : files) {

        }
    }

    private static List<String> readLineFromFile(File file) throws Exception{
        return readLineFromStream(new FileInputStream(file));
    }

    private static List<String> readLineFromStream(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);
        List<String> res = Lists.newArrayList();
        while (scanner.hasNextLine()){
            res.add(scanner.nextLine());
        }
        return res;
    }

    public static List<String> getLineFromGZFile(File file) throws Exception{
        InputStream fin = new FileInputStream(file);
        BufferedInputStream in = new BufferedInputStream(fin);
        OutputStream out = Files.newOutputStream(Paths.get("D:\\fw\\test\\"+file.getName()));
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);

        return readLineFromStream(gzIn);
    }
}


