package com.woniuxy.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class PictureUtil {
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            // 用java JDK自带的URL去请求
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置该请求的消息头
            // 设置HTTP方法：POST
            connection.setRequestMethod("POST");
            // 设置其Header的Content-Type参数为application/x-www-form-urlencoded
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "uml8HFzu2hFd8iEG2LkQGMxm");
            // 将第二步获取到的token填入到HTTP header
            connection.setRequestProperty("access_token", baiduOcr.getAuth());
            connection.setDoOutput(true);
            connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 把json格式转换成HashMap
    public static HashMap<String, String> getHashMapByJson(String jsonResult) {
        HashMap map = new HashMap<String, String>();
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonResult.toString());
            JSONObject words_result = jsonObject.getJSONObject("words_result");
            Iterator<String> it = words_result.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                JSONObject result = words_result.getJSONObject(key);
                String value = result.getString("words");
                switch (key) {
                    case "姓名":
                        map.put("name", value);
                        break;
                   /* case "民族":
                        map.put("nation", value);
                        break;*/
                    case "住址":
                        map.put("address", value);
                        break;
                    case "公民身份号码":
                        map.put("IDCard", value);
                        break;
                    case "出生":
                        map.put("Birth", value);
                        break;
                    case "性别":
                        map.put("sex", value);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        // 获取本地的绝对路径图片      填上传身份证图片的绝对地址
        File file = new File("E:\\photo\\1.png");
        // 进行BASE64位编码
        String imageBase = BASE64.encodeImgageToBase64(file);
        imageBase = imageBase.replaceAll("\r\n", "");
        imageBase = imageBase.replaceAll("\\+", "%2B");
        // 百度云的文字识别接口,后面参数为获取到的token
        String httpUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?access_token=" + baiduOcr.getAuth();
        String httpArg = "detect_direction=true&id_card_side=front&image=" + imageBase;
        String jsonResult = request(httpUrl, httpArg);
        System.out.println("返回的结果--------->" + jsonResult);
        HashMap<String, String> map = getHashMapByJson(jsonResult);
        Collection<String> values = map.values();//将识别的文字json格式转换成集合
        if (!values.isEmpty()) {    //进行判断，如果是身份证照片进行存储到数据库
            System.out.println("是身份证图片");
            Iterator<String> iterator2 = values.iterator();
            //用迭代器进行循环遍历，共五个参数
            /*第一个是地址信息      北京市海淀区上地十号七栋2单元110室
            * 第二个是身份证号码     532101198906010015
            * 第三个是性别            男
            * 第四个是姓名            百度熊
            * 第五个是出生日期        19890601  */
            while (iterator2.hasNext()) {
                System.out.print(iterator2.next() + ", ");
            }
        }else {
            System.out.println("上传图片错误");
        }

    }

}
