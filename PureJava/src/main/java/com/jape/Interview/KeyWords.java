package com.jape.Interview;

import java.util.*;

/**
 * 题目1：关键词提取和标注，要求如下：
 * 1). 输入参数1为关键词模板。
 * 模板的格式如下：
 * [type1] ['-'] [value1|value2|...] [';'] [type2] ['-'] [value1|value2|...] [';'] [type3] ['-'] [value1|value2|...] ...
 * 2). 输入参数2为文本内容。
 * 3). 输出要求：在关键词后面标注出类型，以'/'分隔，关键词和其他文本之间加空格。
 * 例如，
 * 输入参数1为："演员-李连杰|周杰伦;歌手-周杰|周杰伦;歌曲-青花瓷|双截棍;电影-青蜂侠|英雄"
 * 输入参数2为："青花瓷是周杰伦演唱的一首歌曲，由方文山作词。"
 * 输出的内容为："青花瓷/歌曲 是 周杰伦/演员/歌手 演唱的一首歌曲，由方文山作词。"
 */
public class KeyWords {

    public static void main(String[] args) {

        String template = "演员-李连杰|周杰伦;歌手-周杰|周杰伦;歌曲-青花瓷|双截棍;电影-青蜂侠|英雄";
        String content = "青花瓷是周杰伦演唱的一首歌曲，由方文山作词。";

        KeyWords keyWords = new KeyWords();
        System.err.println(keyWords.callout(template, content));

    }

    /**
     * 标注
     */
    private String callout(String template, String content) {

        Map<String, String> keyWordMap = pickUp(template);
        Map<String, String> havePriorityMap = priority(keyWordMap);

        List<String> alreadyMatched = new ArrayList();

        Iterator it = havePriorityMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            //System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            int matchStart = content.indexOf(key);
            if (matchStart >= 0 && nonInAlreadyMatched(alreadyMatched, content, matchStart)) {
                content = content.replace(key, key + "/" + value + " ");
                //System.err.println(content);
                alreadyMatched.add(key);
            }
        }

        return content;
    }

    /**
     * 当前匹配的地方，是否 不在已经匹配过了的地方
     *
     * @param alreadyMatched
     * @param content
     * @param matchStart
     * @return
     */
    private boolean nonInAlreadyMatched(List<String> alreadyMatched, String content, int matchStart) {

        for (String key : alreadyMatched) {
            if (content.indexOf(key) == matchStart) {
                return false;
            }
        }
        return true;
    }

    /**
     * 提取
     */
    private Map pickUp(String template) {

        Map<String, String> keyWordMap = new HashMap();
        String[] typeValueStrArr = template.split(";");
        for (String typeValueStr : typeValueStrArr) {
            String[] typeValue = typeValueStr.split("-");
            String type = typeValue[0];
            String valueStr = typeValue[1];
            String[] valueArr = valueStr.split("\\|");
            for (String value : valueArr) {
                String alreadyType = keyWordMap.get(value);
                if (alreadyType != null) {
                    alreadyType = alreadyType + "/" + type;
                    keyWordMap.put(value, alreadyType);
                } else {
                    keyWordMap.put(value, type);
                }
            }
        }
        //System.err.println(keyWordMap);
        return keyWordMap;
    }

    /**
     * 长的放前边
     *
     * @param keyWordMap
     * @return
     */
    private Map<String, String> priority(Map<String, String> keyWordMap) {

        Map<String, String> havePriorityMap = new LinkedHashMap<>();

        Object[] keySet = keyWordMap.keySet().toArray();

        String[] keyPriorityArr = new String[keySet.length];
        for (int i = 0; i < keySet.length; i++) {
            keyPriorityArr[i] = (String) keySet[i];
        }
        Arrays.sort(keyPriorityArr, new LengthComparator());

        for (int i = 0; i < keyPriorityArr.length; i++) {
            havePriorityMap.put(keyPriorityArr[i], keyWordMap.get(keyPriorityArr[i]));

        }
        //System.err.println(havePriorityMap);

        return havePriorityMap;

    }

}

/**
 * 按字符串长度排序用的
 */
class LengthComparator implements Comparator<String> {
    public int compare(String a, String b) {
        return b.length() - a.length();
    }
}
