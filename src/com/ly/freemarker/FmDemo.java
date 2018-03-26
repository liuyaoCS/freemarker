package com.ly.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.management.Attribute;


public class FmDemo {
    private static final String FTLPATH = "ftl/";
    private static final String GENPATH = "src/com/ly/freemarker/gen";

    public static void main(String[] args) throws IOException, TemplateException {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);

        File file = new File(FTLPATH);
        configuration.setDirectoryForTemplateLoading(file);

//        new FmDemo().genHtml(configuration);
//        new FmDemo().genJava(configuration);

        new FmDemo().genJavaHook(configuration);
    }
    private void genHtml(Configuration cfg)throws IOException, TemplateException{
        //template
        Template template = cfg.getTemplate("hello.html");
        //data
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("jack", "1"));
        users.add(new User("rose", "21"));
        HashMap<String, Object> rootMap = new HashMap<>();
        rootMap.put("users", users);
        //template+data=output
        Writer out = new FileWriter(FTLPATH + "word.html");
        template.process(rootMap, out);
        System.out.println("gen html code success!");
    }
    private void genJava(Configuration cfg) throws IOException, TemplateException{

//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template temp = cfg.getTemplate("person.ftl");

        // Create the root hash
        Map<String, Object> root = new HashMap<String, Object>();

        root.put("packageName", "com.ly.freemarker.gen");
        root.put("className", "Person");
        root.put("author", "liu yao");

        List<Attribute> attr_list = new ArrayList<Attribute>();
        attr_list.add(new Attribute("id", "Long"));
        attr_list.add(new Attribute("name", "String"));
        attr_list.add(new Attribute("age", "Integer"));
        attr_list.add(new Attribute("hobby", "List<String>"));

        root.put("attrs", attr_list);

//      Writer out = new OutputStreamWriter(System.out);
//      Writer out = new OutputStreamWriter(System.out);
        File dir = new File(GENPATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        OutputStream fos = new FileOutputStream( new File(dir, "Person.java"));
        Writer out = new OutputStreamWriter(fos);
        temp.process(root, out);

        fos.flush();
        fos.close();

        System.out.println("gen java code success!");
    }
    private void genJavaHook(Configuration cfg) throws IOException, TemplateException{
        //load template
        Template temp = cfg.getTemplate("hook.ftl");

        // Create the root hash
        Map<String, Object> root = new HashMap<String, Object>();

        root.put("packageName", "com.ly.freemarker.gen");
        root.put("className", "TestDemo");
        root.put("methodName", "test");

        List<String> ptypes = new ArrayList<String>();
        ptypes.add("Long");
        ptypes.add("String");
        ptypes.add("Integer");
//        ptypes.add("List<String>");

        root.put("ptypes", ptypes);

        root.put("invokeOrigin",true);

        root.put("before_content",readFile("before.method"));
        root.put("after_content",readFile("after.method"));

        //create output file
        File dir = new File(GENPATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        OutputStream fos = new FileOutputStream( new File(dir, "TestDemo.java"));
        Writer out = new OutputStreamWriter(fos);
        temp.process(root, out);

        fos.flush();
        fos.close();

        System.out.println("gen javahook code success!");
    }
    private String readFile(String src) {
        StringBuilder content=new StringBuilder();
        //content.append("\n");
        Scanner scanner= null;
        File file = new File(FTLPATH+src);
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
