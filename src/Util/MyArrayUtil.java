package Util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyArrayUtil {

    //将字符串转化成Integer数组
    public static ArrayList<Integer> getIntArr(String str) {

        // 正则表达式匹配整数（包括正负号）
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        ArrayList<Integer> integers = new ArrayList<>();
        while (matcher.find()) {
            // 将匹配的字符串转换为整数并添加到列表中
            integers.add(Integer.parseInt(matcher.group()));
        }
        return integers;
    }

    // 检测字符串转化成数组的合理性
    public static boolean isValidStr(String input) {

        String trimmedInput = input.trim();
        // 使用正则表达式检查字符串，确保只有整数和\\s,且整数有两个以上
        return trimmedInput.matches("(\\d+\\s+)+\\d+") && !trimmedInput.isEmpty();
    }

    //得到数组最大值
    public static Integer getMax(ArrayList<Integer>arr){
            int max = arr.getFirst();
            for (int value : arr) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
    }

    //得到数组最小值
    public static Integer getMin(ArrayList<Integer>arr){
            int min = arr.getFirst();
            for (int value : arr) {
                if (value < min) {
                    min = value;
                }
            }
            return min;
    }

    //删除数组最大值
    public static void removeMin(ArrayList<Integer>arr){
            int min = getMin(arr);
            arr.remove(Integer.valueOf(min));
    }
}




