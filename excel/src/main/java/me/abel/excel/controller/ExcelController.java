package me.abel.excel.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.abel.excel.util.EasyExcelUtils;
import me.abel.excel.util.HutoolExcelUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("excel")
public class ExcelController {

    @RequestMapping("/hutoolExport")
    public void hutoolExport(HttpServletResponse response) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("code", "客户编号");
        headerMap.put("name", "客户名称");
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> hashMap = Maps.newHashMap();
        hashMap.put("code", "aaa");
        list.add(hashMap);
        hashMap.put("name", "AAA");
        list.add(hashMap);
        List<JSONObject> jsonObjectList = JSON.parseObject(JSON.toJSONString(list), new TypeReference<List<JSONObject>>() {});
        HutoolExcelUtils.doExport(response, "test", jsonObjectList, headerMap);
    }

    @RequestMapping("/easyExcelExport")
    public void easyExcelExport(HttpServletResponse response) {
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put("code", Lists.newArrayList("客户编号"));
        headerMap.put("name", Lists.newArrayList("客户名称"));
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> hashMap = Maps.newHashMap();
        hashMap.put("code", "aaa");
        list.add(hashMap);
        hashMap.put("name", "AAA");
        list.add(hashMap);
        List<JSONObject> jsonObjectList = JSON.parseObject(JSON.toJSONString(list), new TypeReference<List<JSONObject>>() {});
        List<List<Object>> convertObject = EasyExcelUtils.convertObject(jsonObjectList, headerMap.keySet());
        EasyExcelUtils.doExport(response, "test", convertObject, Lists.newArrayList(headerMap.values()));
    }

}
