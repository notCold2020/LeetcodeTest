package com.lrw.ohter;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Solution {
    public static void main(String[] args) throws Exception {
        String[] splitKey = "key1,key2".split(",");
        String[] splitValue = "value1,value2".split(",");
        if(splitKey[0]!="key1,key2"&&splitValue[0]!="value1,value2"&&splitKey.length==splitValue.length){
            System.out.println("truee");
        }
    }

    @AllArgsConstructor
    public class SearchCondition implements Serializable {

        private String name;

        private String key;

        private Integer condition;

        private String value;

        // kryo序列化用
        public SearchCondition() {
        }

        public SearchCondition(String name, Integer condition, String value) {
            this.name = name;
            this.condition = condition;
            this.value = value;
        }

        public SearchCondition(String name, String key, String value) {
            this.name = name;
            this.key = key;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getKey() {
            return key;
        }

        public Integer getCondition() {
            return condition;
        }

        public void setCondition(Integer condition) {
            this.condition = condition;
        }

        public String getValue() {
            return value;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append(",\"key\":\"")
                    .append(key).append('\"');
            sb.append(",\"condition\":")
                    .append(condition);
            sb.append(",\"value\":\"")
                    .append(value).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }

    private void setByKv() {
        SearchCondition condition = new SearchCondition("user_field", "key1,key2", "value1,value2");
        StringBuilder stringBuilder = new StringBuilder();
        String[] splitKey = condition.getKey().split(",");
        String[] splitValue = condition.getValue().split(",");
        //这里校验通过 开始拼接sql
//        if (splitKey != null && splitKey.length != 0) {
        stringBuilder.append("{");
        for (int i = 0; i < splitKey.length; i++) {
            stringBuilder.append("\"" + splitKey[i] + "\"" + ":" +"\"" + splitValue[i] + "\"" ).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        stringBuilder.append("}");

//        stringBuilder.append(String.format("'{\"%s\":\"%s\"}'", condition.getKey(), condition.getValue()));

        System.out.println(stringBuilder);


//        } else {
//            sqlBuilder.append("AND" + condition.getName());
//            sqlBuilder.append(" @> ");
////            {"测试key":"测试value","测试key2":"测试key3"}
//            if (StringUtils.isEmpty(condition.getValue())) {
//                sqlBuilder.append(String.format("'{\"%s\":\"\"}'", condition.getKey()));
//            } else {
//                sqlBuilder.append(String.format("'{\"%s\":\"%s\"}'", condition.getKey(), condition.getValue()));
//            }
//        }
    }
}



