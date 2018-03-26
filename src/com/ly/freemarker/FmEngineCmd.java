package com.ly.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FmEngineCmd {

    private static final String GENPATH = "src/com/ly/freemarker/gen";

    public static void main(String[] args) throws IOException, TemplateException {

        if(args==null || args.length!=4){
            System.out.println("args error!");
            return;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);

        File file = new File(Util.FTLPATH);
        configuration.setDirectoryForTemplateLoading(file);

        new FmEngineCmd().genJavaHook(configuration,args);
    }

    private void genJavaHook(Configuration cfg, String[] args) throws IOException, TemplateException{
        //load template
        Template temp = cfg.getTemplate("hook.ftl");

        //Create the root hash
        Map<String, Object> root = Util.configData(args);

        //create output file
        File dir = new File(GENPATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        OutputStream fos = new FileOutputStream( new File(dir, root.get("className")+".java"));
        Writer out = new OutputStreamWriter(fos);
        temp.process(root, out);

        fos.flush();
        fos.close();

        System.out.println("gen java code success!");
    }
}
