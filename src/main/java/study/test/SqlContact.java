package study.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Created by fankun on 2017/10/26.
 */
public class SqlContact {
    private static final String m =
            "insert into mic2005.mic_app_act_apply (ACT_PRODUCT_ID, ACTIVITY_ID, COM_ID, PROD_ID, STATUS, BUYER_ID, RECIPIENT_NAME, COMPANY_NAME, EMAIL, BUY_TYPE, BUY_REQUIREMENTS, BUY_REQUIREMENTS_OTHER,RECIPIENT_ADDRESS, CITY_TOWN, PROVINCE_STATE, COUNTRY_REGION, ZIP_POST_CODE, TELEPHONE, SOCIAL_WEBSITE_TYPE, SOCIAL_NUMBER, EXPRESS_TYPE, TRACKING_NUMBER, ADDER_NO, ADDER_NAME, ADD_TIME,UPDATER_NO, UPDATER_NAME, UPDATE_TIME, CHECKER_NO, CHECKER_NAME,  RETURN_ADVISE_EN, RETURN_ADVISE_CN)" +
                    "values ({0}, 802, {1}, {2}, ''3'', {3}, ''{4}'', ''{5}'', ''{6}'', " +
                    "'''', '''', '''', '''', '''', '''', ''{7}'', '''', ''{8}'', '''', '''', '''', '''', ''00'', ''fankun'', sysdate, ''00'', ''fankun'', " +
                    "sysdate, '''', '''', '''', '''');";
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D://sql.txt");
        List<String> excels = Files.readAllLines(path);
        List<String> result = Lists.newArrayList();
        for (String excel : excels) {
            String[] argArray = excel.split("\t");
            if(!"614496444,614524264,678453295".contains(String.valueOf(argArray[1]))){
                continue;
            }
            List<String> resultArgs = Lists.newArrayList();
            for (String s : argArray) {
                if(s.contains("'")){
                    StringBuilder sb = new StringBuilder(s);
                    sb.insert(s.indexOf("'"),"'");
                    resultArgs.add(sb.toString());
                }else{
                    resultArgs.add(s);
                }
            }

            result.add(MessageFormat.format(m,resultArgs.toArray())) ;
        }
        Files.write(Paths.get("D://sql2.txt"),result);
    }
}
