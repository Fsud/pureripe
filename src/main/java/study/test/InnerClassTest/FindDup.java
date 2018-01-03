package study.test.InnerClassTest;

import jxl.Sheet;
import jxl.Workbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Created by fankun on 2017/12/29.
 */
public class FindDup {
    public static void main(String[] args) throws Exception{
        InputStream stream = new FileInputStream("C:\\Users\\fankun\\Desktop\\删除脏数据IBS.xls");
        Workbook workbook = Workbook.getWorkbook(stream);
        Sheet sheet = workbook.getSheet(0);
        Set<String> set = Sets.newHashSet();
        Set<String> removeRecIds = Sets.newHashSet();
        for (int i = 0; i < sheet.getRows(); i++) {
            String boardId = sheet.getCell(2,i).getContents();
            String catCode = sheet.getCell(3,i).getContents();
            if(set.contains(boardId+"_"+catCode)){
                removeRecIds.add(sheet.getCell(0,i).getContents());
            }else{
                set.add(boardId+"_"+catCode);
            }
        }
        removeRecIds.forEach(recId-> System.out.println("update mic2005.mic_expo_board_catcode set status = '0' where board_cat_id = "+ recId +";"));
    }
}
