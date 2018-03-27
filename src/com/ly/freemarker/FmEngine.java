package com.ly.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FmEngine {


    public static void main(String[] args) throws IOException, TemplateException {

        //Create the root hash data
        Map<String, Object> root = Util.configData("ftl/config.json");

        //config
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        File file = new File(Util.FTLPATH);
        configuration.setDirectoryForTemplateLoading(file);

        //load Template
        Template entry_temp = configuration.getTemplate("entry.ftl");
        Template hook_temp = configuration.getTemplate("hook.ftl");

        //gen hook file
        Util.genJavaHook(entry_temp, (String) root.get("projectQarthName"), root);

        List<Map<String,Object>> datas= (List<Map<String, Object>>) root.get("datas");
        for(int i=0;i<datas.size();i++){
            Map<String,Object> item=datas.get(i);
            Util.genJavaHook(hook_temp, (String) item.get("hookFileName"), item);
        }
    }

}
