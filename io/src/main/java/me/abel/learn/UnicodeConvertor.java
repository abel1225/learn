package me.abel.learn;

import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class UnicodeConvertor {
    //测试
    public static void main(String[] args) throws ScriptException {
        //Unicode码
        String aa = "{\"mobile\": \"13812155582\", \"name\": \"\\u5468\\u6653\\u7ea2\", \"departname\": \"\\u5ba2\\u6237\\u5173\\u7cfb\\u7ba1\\u7406\\u90e8\", \"storeid\": \"3207\", \"storename\": \"\\u6c5f\\u9634\\u5b9d\\u8bda\\u5e97\", \"licenseplate\": \"\\u82cfB2713E\", \"vin\": \"WBAKB2108CDV38646\", \"contacttel\": \"13901528830\", \"customername\": \"   \\u6c5f\\u9634\\u5e02\\u949f\\u6cf0\\u95e8\\u7a97\\u5236\\u54c1\\u6709\\u9650\\u516c\\u53f8\", \"ordercount\": \"1\", \"ordertype\": \"SALE\", \"typecode\": \"A10299_F02Z9999B08\", \"carbrand\": \"A102/\\u8fdb\\u53e3\\u5b9d\\u9a6c\", \"carseries\": \"F02\", \"typename\": \"730Li Sedan 730Li Vantage\"}";
        //转中文
        String cnAa = get(aa);
//        String unicodeAa = cnToUnicode(cnAa);
//        System.out.println("中文转Unicode结果： "+unicodeAa);
    }

    public static String get(String str) throws ScriptException {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByExtension("js");
//        Object res = engine.eval("escape('Aa111111!@#')");
//        System.out.println(res);
        Object res1 = engine.eval("unescape('"+str+"')");
        System.out.println(res1);
        return res1.toString();
    }

    public static String getFixStr( String str )
    {
        StringBuilder ret = new StringBuilder();
        Pattern p = compile("(\\\\u{4})");
        Matcher m = p.matcher(str);
        while(m.find()){
            String xxx = m.group(0);
            System.out.println(xxx);
            ret.append(str.replaceAll( "\\" + xxx, ascii2native( xxx ) ));
        }
        return ret.toString();
    }

    private static String ascii2native(String ascii) {
        int n = ascii.length() / 6;
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0, j = 2; i < n; i++, j += 6) {
            String code = ascii.substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            sb.append(ch);
        }
        return sb.toString();
    }
}
