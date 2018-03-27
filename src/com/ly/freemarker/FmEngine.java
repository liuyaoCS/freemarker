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

        //load template
        Template temp0 = configuration.getTemplate("entry.ftl");
        new FmEngine().genJavaHook(configuration, temp0, "projectQarthName");

        Template temp = configuration.getTemplate("hook.ftl");
        new FmEngine().genJavaHook(configuration, temp, "hookFileName");
    }

    private void genJavaHook(Configuration cfg, Template temp, String outFileName) throws IOException, TemplateException{
        //Create the root hash
        Map<String, Object> root = Util.configData();

        //create output file
        File dir = new File((String) root.get("projectSrcPath"));
        if(!dir.exists()){
            dir.mkdirs();
        }
        OutputStream fos = new FileOutputStream( new File(dir, root.get(outFileName)+".java"));
        Writer out = new OutputStreamWriter(fos);
        temp.process(root, out);

        fos.flush();
        fos.close();

        System.out.println("gen java code success!");
    }
}
