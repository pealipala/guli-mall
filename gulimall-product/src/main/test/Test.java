
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/5/23 12:19
 */

@SpringBootTest
public class Test {
    private static final Log log = LogFactory.getLog(Test.class);

    @org.junit.Test
    public void test() {
        Integer ST1 = 1;
        Integer ST2 = 2;
        Integer ST3 = 3;
        Integer ST4 = 4;
        Integer ST5 = 5;
        String s = "ST1!=!ST2*ST3+ST4-ST5";
        String[] arr = s.split("");
        List<String> list1 = new ArrayList<String>();
        list1.add(0, "!");
        List<Integer> list2 = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("+") || arr[i].equals("-") || arr[i].equals("*") || arr[i].equals("/")) {
                list1.add(arr[i]);//这就取出来运算符了
            }
        }
        String c = s.substring(0, s.indexOf("!"));//只需要第一个出现的！
        String c1 = s.substring(s.indexOf("=") + 1, s.lastIndexOf(""));
        System.out.println(c);
        System.out.println(c1);
        System.out.println(list1);
    }

    @org.junit.Test
    public void test1() {
        List<Map> mapList = new ArrayList<>();
        for (int f = 2; f <= 6; f++) {
            Map map = new HashMap();
            map.put("indCd", "ST" + f);
            map.put("indVal", f);
            mapList.add(map);
        }
        String sb = "ST2!=!ST3*ST4+ST5-ST6";
        String sb2 = sb.replaceAll("!", "");
        StringBuilder s = new StringBuilder(sb2);
        StringBuilder s1 = new StringBuilder(sb2);
        StringBuilder s2 = new StringBuilder(sb2);
        ArrayList<Character> codeList = new ArrayList<>();
        ArrayList<Object> valueList = new ArrayList<>();
        ArrayList<Integer> valList = new ArrayList<Integer>();

        int startIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+' || s.charAt(i) == '=' || s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '-') {
                codeList.add(s1.charAt(i));
                String substring = s2.substring(startIndex, i);
                valueList.add(substring);
                startIndex = i + 1;
            }
            if (i == s.length() - 1) {
                String substring = s2.substring(startIndex, i + 1);
                valueList.add(substring);
            }
        }
        for (Object o : valueList) {
            for (Map map : mapList) {
                if (map.get("indCd").equals(o)) {
                    valList.add((Integer) map.get("indVal"));
                }
            }
        }
        Integer result = valList.get(1);
        for (int k = 1; k < valList.toArray().length - 1; k++) {
            if (codeList.get(k) == '+') {
                result = result + valList.get(k + 1);
            } else if (codeList.get(k) == '-') {
                result = result - valList.get(k + 1);
            } else if (codeList.get(k) == '*') {
                result = result * valList.get(k + 1);
            } else if (codeList.get(k) == '/') {
                result = result / valList.get(k + 1);
            }
        }

        for (Map map : mapList) {
            if (valueList.get(0).equals(map.get("indCd"))) {
                map.put("indVal", result);
            }
        }
        System.out.println(mapList);
        System.out.println(codeList);
        System.out.println(valueList);
        System.out.println(valList);
        System.out.println(result);


    }



    @org.junit.Test
    public void test3() throws ScriptException {
        List<Map> mapList = new ArrayList<>();
        for (int f = 1; f <= 5; f++) {
            Map map = new HashMap();
            map.put("indCd", "ST" + f);
            map.put("indVal", f);
            mapList.add(map);
        }
        String sb = "ST1!=!ST2*(ST3+ST4-ST5)";
        String s = sb.replaceAll("!", "");
        String str1=s.substring(s.indexOf("=")+1,s.length());
        String str2=s.substring(0,s.indexOf("="));
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(mapList);
        Integer res=null;
        for (Map map : mapList) {
            if(str1.contains((String)map.get("indCd"))){
                str1=str1.replaceAll((String)map.get("indCd"),(Integer)map.get("indVal")+"");
            }
            if(((String)map.get("indCd")).equals(str2)){
                res= (Integer) map.get("indVal");
            }
        }
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        res = (Integer) jse.eval(str1);
        System.out.println(str1);
        System.out.println(res);
        System.out.println(mapList);
    }

}
