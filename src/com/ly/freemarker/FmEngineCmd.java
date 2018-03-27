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


    public static void main(String[] args) throws IOException, TemplateException {

        if(args==null || args.length!=4){
            System.out.println("args error!!");
            for(String arg:args){
                System.out.print(arg);
            }
            return;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);

        File file = new File(args[0]);
        configuration.setDirectoryForTemplateLoading(file);


        //load template
        Template temp0 = configuration.getTemplate("entry.ftl");
        new FmEngineCmd().genJavaHook(configuration, args, temp0, "projectQarthName");

        Template temp = configuration.getTemplate("hook.ftl");
        new FmEngineCmd().genJavaHook(configuration, args, temp, "hookFileName");
    }

    private void genJavaHook(Configuration cfg, String[] args,  Template temp, String outFileName) throws IOException, TemplateException{

        //Create the root hash
        Map<String, Object> root = Util.configData(args);

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

        System.out.println("gen "+outFileName+" success!");
    }
}
