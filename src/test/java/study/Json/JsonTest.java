package study.Json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * Created by fankun on 2017/11/23.
 */
public class JsonTest {
    private List<String> groups;

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public static void main(String[] args) {
        JsonTest jsonTest = new JsonTest();
        jsonTest.setGroups(Lists.newArrayList("123","234"));
        System.out.println(JSON.toJSON(jsonTest));
    }
}
