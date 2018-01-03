package com.fankun.pureRipe.util;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.config.DefaultKey;

/**
 * Created by fankun on 2017/6/15.
 */
@DefaultKey("htmlUtil")
public class HtmlUtil {

    public static String testqwe(){
        return "1";
    }

    public static String transform(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        return parseNtoBR(parseBRtoN(str));
    }

    public static String parseBRtoN(String str) {
        if (!StringUtils.isEmpty(str)) {
            str = str.replaceAll("<br />", "\n");
            str = str.replaceAll("<br/>", "\n");
            str = str.replaceAll("<br>", "\n");
            str = str.replaceAll("<BR>", "\n");
            str = str.replaceAll("\n\n", "\n");
        }

        return str;
    }

    public static String parseNtoBR(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        str = str.replaceAll("&(?!#[0-9]{2,5};|[a-zA-Z0-9]{2,7};)", "&amp;");
        str = str.replaceAll("& ", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");

        str = str.replaceAll("(\r\n|\r|\n|\n\r)","<br />");

        return str;
    }
}