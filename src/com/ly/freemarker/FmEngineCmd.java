package com.ly.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FmEngineCmd {


    public static void main(String[] args) throws IOException, TemplateException {

        if(args==null || args.length!=2){
            System.out.println("args error!!");
            for(String arg:args){
                System.out.print(arg);
            }
            return;
        }
        //Create the root hash data
        Map<String, Object> root = Util.configData(args[1]);

        //config
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        File file = new File(args[0]);
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
