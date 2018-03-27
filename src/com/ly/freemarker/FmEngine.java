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
import java.util.Map;

public class FmEngine {


    public static void main(String[] args) throws IOException, TemplateException {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);

        File file = new File(Util.FTLPATH);
        configuration.setDirectoryForTemplateLoading(file);

        new FmEngine().genJavaHook(configuration);
    }

    private void genJavaHook(Configuration cfg) throws IOException, TemplateException{
        //load template
        Template temp = cfg.getTemplate("hook.ftl");

        //Create the root hash
        Map<String, Object> root = Util.configData();

        //create output file
        File dir = new File((String) root.get("projectSrcPath"));
        if(!dir.exists()){
            dir.mkdirs();
        }
        OutputStream fos = new FileOutputStream( new File(dir, root.get("projectClassName")+".java"));
        Writer out = new OutputStreamWriter(fos);
        temp.process(root, out);

        fos.flush();
        fos.close();

        System.out.println("gen java code success!");
    }
}
