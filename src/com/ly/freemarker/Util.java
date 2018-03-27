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

        root.put("hookFileName", jsonObject.getString("hookFileName"));
        root.put("packageName", jsonObject.getString("packageName"));
        root.put("className", jsonObject.getString("className"));
        root.put("methodName", jsonObject.getString("methodName"));

        List<String> pTypes = new ArrayList<String>();
        JSONArray jsonArray=jsonObject.getJSONArray("pTypes");
        for(int i=0;i<jsonArray.size();i++){
            pTypes.add((String) jsonArray.get(i));
        }
        root.put("pTypes", pTypes);

        root.put("invokeOrigin",jsonObject.getBoolean("invokeOrigin"));

        root.put("before_content", readMethod(FTLPATH+jsonObject.getString("before_content")));
        root.put("after_content", readMethod(FTLPATH+jsonObject.getString("after_content")));
        return root;
    }
    static Map<String, Object> configData(String[] args){

        Map<String, Object> root = new HashMap<String, Object>();

        String jsonStr = Util.readFile(args[1]);
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);

        root.put("projectSrcPath", jsonObject.getString("projectSrcPath"));
        root.put("projectPackageName", jsonObject.getString("projectPackageName"));
        root.put("projectQarthName", jsonObject.getString("projectQarthName"));

        root.put("hookFileName", jsonObject.getString("hookFileName"));
        root.put("packageName", jsonObject.getString("packageName"));
        root.put("className", jsonObject.getString("className"));
        root.put("methodName", jsonObject.getString("methodName"));

        List<String> pTypes = new ArrayList<String>();
        JSONArray jsonArray=jsonObject.getJSONArray("pTypes");
        for(int i=0;i<jsonArray.size();i++){
            pTypes.add((String) jsonArray.get(i));
        }
        root.put("pTypes", pTypes);

        root.put("invokeOrigin",jsonObject.getBoolean("invokeOrigin"));

        root.put("before_content", readMethod(args[2]));
        root.put("after_content", readMethod(args[3]));
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