package com.cxr.other.utilsSelf;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;


class RWToTxt {

    static Logger logger = LoggerFactory.getLogger(RWToTxt.class);

    public static void main(String[] args) throws IOException {
        BufferedReader reader = IoUtil.getReader(new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\cixingrui\\Desktop\\视频跟贴马甲号\\视频跟贴马甲号\\综艺.txt"), "GBK")));
        while (reader.readLine() != null) {
            StringBuilder stringBuilder = new StringBuilder("http://user.m.163.com/api/dsf/user/createUser?productId=1&ibc=newsapp&token=123&passport=");
            String majia = reader.readLine().split(",")[0];
            stringBuilder.append(majia);
            String body = HttpRequest.post(stringBuilder.toString()).body(new JSONObject()).execute().body();
            Object data = JSONUtil.parseObj(body).get("data");
            Map<String, Integer> convert = Convert.convert(Map.class, data);
            Integer userId = convert.get("userId");
            stringBuilder.setLength(0);
            writeToTXT("马甲号:" + majia + "     userId:" + userId );
        }
    }

    /**
     * 换行写入txt
     * @param str
     */
    public static void writeToTXT(String str) {
        FileOutputStream o = null;
        String path = "D:\\NetEase\\";
        String filename = "majia.txt";
        byte[] buff = new byte[]{};
        try {
            File file = new File(path + filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            buff = str.getBytes();
            o = new FileOutputStream(file, true);
            o.write(buff);
            o.write("\r\n".getBytes());
            o.flush();
            o.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
