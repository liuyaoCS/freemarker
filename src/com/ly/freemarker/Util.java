package com.ly.freemarker;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Util {

    static final String FTLPATH = "ftl/";

    static Map<String, Object> configData(){

        Map<String, Object> root = new HashMap<String, Object>();

        String jsonStr = Util.readFile("ftl/config.json");
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);

        root.put("projectSrcPath", jsonObject.getString("projectSrcPath"));
        root.put("projectPackageName", jsonObject.getString("projectPackageName"));
        root.put("projectQarthName", jsonObject.getString("projectQarthName"));

        List<Map<String,Object>> datas=new ArrayList<>();

        JSONArray dataArr =jsonObject.getJSONArray("datas");
        for(int i=0;i<dataArr.size();i++){
            JSONObject jObject=dataArr.getJSONObject(i);

            Map<String,Object> item=new HashMap<>();

            item.put("projectSrcPath", root.get("projectSrcPath"));
            item.put("projectPackageName", root.get("projectPackageName"));
            item.put("projectQarthName", root.get("projectQarthName"));

            item.put("hookFileName", jObject.getString("hookFileName"));
            item.put("packageName", jObject.getString("packageName"));
            item.put("className", jObject.getString("className"));
            item.put("methodName", jObject.getString("methodName"));

            List<String> pTypes = new ArrayList<String>();
            JSONArray jsonArray=jObject.getJSONArray("pTypes");
            for(int j=0;j<jsonArray.size();j++){
                pTypes.add((String) jsonArray.get(j));
            }
            item.put("pTypes", pTypes);

            item.put("invokeOrigin",jObject.getBoolean("invokeOrigin"));

            item.put("before_content", readMethod(jObject.getString("before_content")));
            item.put("after_content", readMethod(jObject.getString("after_content")));

            datas.add(item);
        }
        root.put("datas",datas);

        return root;
    }
    static Map<String, Object> configData(String[] args){

        Map<String, Object> root = new HashMap<String, Object>();

        String jsonStr = Util.readFile(args[1]);
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);

        root.put("projectSrcPath", jsonObject.getString("projectSrcPath"));
        root.put("projectPackageName", jsonObject.getString("projectPackageName"));
        root.put("projectQarthName", jsonObject.getString("projectQarthName"));

        List<Map<String,Object>> datas=new ArrayList<>();

        JSONArray dataArr =jsonObject.getJSONArray("datas");
        for(int i=0;i<dataArr.size();i++){
            JSONObject jObject=dataArr.getJSONObject(i);
            Map<String,Object> item=new HashMap<>();

            item.put("projectSrcPath", root.get("projectSrcPath"));
            item.put("projectPackageName", root.get("projectPackageName"));
            item.put("projectQarthName", root.get("projectQarthName"));

            item.put("hookFileName", jObject.getString("hookFileName"));
            item.put("packageName", jObject.getString("packageName"));
            item.put("className", jObject.getString("className"));
            item.put("methodName", jObject.getString("methodName"));

            List<String> pTypes = new ArrayList<String>();
            JSONArray jsonArray=jObject.getJSONArray("pTypes");
            for(int j=0;j<jsonArray.size();j++){
                pTypes.add((String) jsonArray.get(j));
            }
            item.put("pTypes", pTypes);

            item.put("invokeOrigin",jObject.getBoolean("invokeOrigin"));

            item.put("before_content", readMethod(jObject.getString("before_content")));
            item.put("after_content", readMethod(jObject.getString("after_content")));

            datas.add(item);
        }
        root.put("datas",datas);

        return root;
    }
    private static String readFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }
    private static String readMethod(String src) {
        StringBuilder content=new StringBuilder();
        //content.append("\n");
        Scanner scanner= null;
        File file = new File(src);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean firstline=true;
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(firstline){
                content.append(line+"\n");
            }else{
                content.append("\t\t"+line+"\n");
            }
            firstline=false;
        }
        scanner.close();
        return content.toString();
    }
}