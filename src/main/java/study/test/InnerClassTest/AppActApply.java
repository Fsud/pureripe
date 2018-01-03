package study.test.InnerClassTest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by fankun on 2017/12/11.
 */
public class AppActApply {
    public static void main(String[] args) {
        try{
            int count = 0;
            InputStream stream = new FileInputStream("C:\\Users\\fankun\\Desktop\\1201.xls");
            Workbook workbook = Workbook.getWorkbook(stream);
            Sheet sheet = workbook.getSheet(0);
            for (int i = 0; i < sheet.getRows(); i++) {
                Cell cell = sheet.getCell(3,i);
                if(Arrays.asList("615314644").contains(cell.getContents())){

                    Cell recId = sheet.getCell(0,i);
                    Cell name = sheet.getCell(7,i);
                    if(Arrays.asList("Lakhvir Singh Takhar","Misheck fred sinyangwe").contains(name.getContents())){
                        continue;
                    }
                    if(count>=10){
                        System.out.println("update mic2005.mic_app_act_apply set status = '4',update_time = sysdate,updater_name = 'fankun' where rec_id = " + recId.getContents() +";");
                    }else{
                        System.out.println("update mic2005.mic_app_act_apply set status = '3',update_time = sysdate,updater_name = 'fankun' where rec_id = " + recId.getContents() +";");
                    }
                    count++;

                }else if(Arrays.asList("").contains(cell.getContents())){
                    count++;
                    Cell recId = sheet.getCell(0,i);
                    System.out.println("update mic2005.mic_app_act_apply set status = '3',update_time = sysdate,updater_name = 'fankun' where rec_id = " + recId.getContents() +";");

                }
            }
            System.out.println(count);
        }catch (Exception e){

        }
    }

}
