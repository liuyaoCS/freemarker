package com.ly.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class FmEngine {


    public static void main(String[] args) throws IOException, TemplateException {

        //Create the root hash data
        Map<String, Object> root = Util.configData();

        //config
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        File file = new File(Util.FTLPATH);
        configuration.setDirectoryForTemplateLoading(file);

        //gen qarth file
        Template entry_temp = configuration.getTemplate("entry.ftl");
        new FmEngine().genJavaHook(entry_temp, (String) root.get("projectQarthName"), root);


        //gen hook file
        Template hook_temp = configuration.getTemplate("hook.ftl");

        List<Map<String,Object>> datas= (List<Map<String, Object>>) root.get("datas");

        for(int i=0;i<datas.size();i++){
            Map<String,Object> item=datas.get(i);
            new FmEngine().genJavaHook(hook_temp, (String) item.get("hookFileName"), item);
        }
    }


    private void genJavaHook(Template temp, String outFileName, Map<String,Object> root) throws IOException, TemplateException{

        //create output file
        File dir = new File((String) root.get("projectSrcPath"));
        if(!dir.exists()){
            dir.mkdirs();
        }
        OutputStream fos = new FileOutputStream( new File(dir, outFileName+".java"));
        Writer out = new OutputStreamWriter(fos);
        temp.process(root, out);

        fos.flush();
        fos.close();

        System.out.println("gen "+outFileName+" success!");
    }
}
