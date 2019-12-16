package com.chenzhiwu.beggar.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * 通用方法工具类
 * @author 小懒虫
 * @date 2018/10/15
 */
public class ToolUtil {

    /**
     * 获取随机位数的字符串
     * @param length 随机位数
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 获取ascii码中的字符 数字48-57 小写65-90 大写97-122
            int range = random.nextInt(75)+48;
            range = range<97?(range<65?(range>57?114-range:range):(range>90?180-range:range)):range;
            sb.append((char)range);
        }
        return sb.toString();
    }

    /**
     * 首字母转小写
     */
    public static String lowerFirst(String word){
        if(Character.isLowerCase(word.charAt(0)))
            return word;
        else
            return String.valueOf(Character.toLowerCase(word.charAt(0))) + word.substring(1);
    }

    /**
     * 首字母转大写
     */
    public static String upperFirst(String word){
        if(Character.isUpperCase(word.charAt(0)))
            return word;
        else
            return String.valueOf(Character.toUpperCase(word.charAt(0))) + word.substring(1);
    }

    /**
     * 获取项目不同模式下的根路径
     */
    public static String getProjectPath(){
        String filePath = ToolUtil.class.getResource("").getPath();
        String projectPath = ToolUtil.class.getClassLoader().getResource("").getPath();
        StringBuilder path = new StringBuilder();

        if(!filePath.startsWith("file:/")){
            // 开发模式下根路径
            char[] filePathArray = filePath.toCharArray();
            char[] projectPathArray = projectPath.toCharArray();
            for (int i = 0; i < filePathArray.length; i++) {
                if(projectPathArray.length > i && filePathArray[i] == projectPathArray[i]){
                    path.append(filePathArray[i]);
                }else {
                    break;
                }
            }
        }else if(!projectPath.startsWith("file:/")){
            // 部署服务器模式下根路径
            projectPath = projectPath.replace("/WEB-INF/classes/", "");
            projectPath = projectPath.replace("/target/classes/", "");
            try {
                path.append(URLDecoder.decode(projectPath,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return projectPath;
            }
        }else {
            // jar包启动模式下根路径
            String property = System.getProperty("java.class.path");
            int firstIndex = property.lastIndexOf(System.getProperty("path.separator")) + 1;
            int lastIndex = property.lastIndexOf(File.separator) + 1;
            path.append(property, firstIndex, lastIndex);
        }

        File file = new File(path.toString());
        String rootPath = "/";
        try {
            rootPath = URLDecoder.decode(file.getAbsolutePath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rootPath.replaceAll("\\\\","/");
    }

    /**
     * 获取文件后缀名
     */
    public static String getFileSuffix(String fileName) {
        if(!fileName.isEmpty()){
            int lastIndexOf = fileName.lastIndexOf(".");
            return fileName.substring(lastIndexOf);
        }
        return "";
    }

    /**
     * 将枚举转成List集合
     * @param enumClass 枚举类
     */
    public static Map<Long, String> enumToMap(Class<?> enumClass){
        Map<Long, String> map = new TreeMap<>();
        try {
        Object[] objects = enumClass.getEnumConstants();
        Method getCode = enumClass.getMethod("getCode");
        Method getMessage = enumClass.getMethod("getMessage");
        for (Object obj : objects) {
            Object iCode = getCode.invoke(obj);
            Object iMessage = getMessage.invoke(obj);
            map.put(Long.valueOf(String.valueOf(iCode)), String.valueOf(iMessage));
        }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return map;
    }

    /**
     * 根据枚举code获取枚举对象
     * @param enumClass 枚举类
     * @param code code值
     */
    public static Object enumCode(Class<?> enumClass, Object code){
        try {
            Object[] objects = enumClass.getEnumConstants();
            Method getCode = enumClass.getMethod("getCode");
            for (Object obj : objects) {
                Object iCode = getCode.invoke(obj);
                if(iCode.equals(code)){
                    return obj;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        }
        return "";
    }

    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件路径
     * @return 返回文件内容
     */
    public static String readFile(String file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 将时间转换为时间戳
     * @param s
     * @return
     * @throws ParseException
     */
    public static Integer dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        return (int) (date.getTime()/1000);
    }
    
    /**
     * 将时间转换为13位时间戳
     * @param s String
     * @return Long
     */
    public static Long dateToStamp13(String s){
    	String format;
    	Long stamp;
    	s = s.replace("/", "-");
    	if (8 == s.length()) {
    		format = "yyyyMMdd";
    	} else if (10 == s.length()) {
    		format = "yyyy-MM-dd";
    	} else if (16 == s.length()) {
    		format = "yyyy-MM-dd HH:mm";
    	} else {
    		format = "yyyy-MM-dd HH:mm:ss";
    	}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
        	Date date = simpleDateFormat.parse(s);
        	stamp = (long)date.getTime();
        } catch (ParseException e) {
        	stamp = null;
		}
        return stamp;
    }

    /**
     * 将时间戳转换为时间
     * @param i
     * @return
     */
    public static String stampToDate(Integer i){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(i.longValue()*1000);
        return simpleDateFormat.format(date);
    }
    
    /**
     * 将时间戳转换为时间
     * @param i
     * @return
     */
    public static String stampToDate(Long i, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(i.longValue());
        return simpleDateFormat.format(date);
    }
}
