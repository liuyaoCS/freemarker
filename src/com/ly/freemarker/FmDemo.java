package com.ly.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
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

import javax.management.Attribute;


public class FmDemo {
    private static final String FTLPATH = "F:\\as\\project-practise\\freemarker\\ftl\\";
    private static final String GENPATH = "F:/as/project-practise/Freemarker/src/com/ly/freemarker/gen";

    public static void main(String[] args) throws IOException, TemplateException {
        //1.创建Configuration指定模板的位置
        Configuration configuration = new Configuration();

        File file = new File(FTLPATH);
        configuration.setDirectoryForTemplateLoading(file);

        new FmDemo().genHtml(configuration);
        new FmDemo().genJava(configuration);
    }
    private void genHtml(Configuration cfg)throws IOException, TemplateException{
        //2.获取模板
        Template template = cfg.getTemplate("hello.html");
        //3.准备数据--构建测试数据(项目中:业务数据)
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("jack", "1"));
        users.add(new User("rose", "21"));
        HashMap<String, Object> rootMap = new HashMap<>();
        rootMap.put("users", users);
        //4.模板+数据=输出(带有数据的静态页)
        Writer out = new FileWriter(FTLPATH + "word.html");
        template.process(rootMap, out);
        System.out.println("success");
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

        System.out.println("gen code success!");
    }
}
