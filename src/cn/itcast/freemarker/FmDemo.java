package cn.itcast.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class FmDemo {
    public static void main(String[] args) throws IOException, TemplateException {
        //1.创建Configuration指定模板的位置
        Configuration configuration = new Configuration();
        String pathname = "F:\\as\\project-practise\\freemarker\\ftl\\";
        File file = new File(pathname);
        configuration.setDirectoryForTemplateLoading(file);
        //2.获取模板
        Template template = configuration.getTemplate("hello.html");
        //3.准备数据--构建测试数据(项目中:业务数据)
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("jack", "1"));
        users.add(new User("rose", "21"));
        HashMap<String, Object> rootMap = new HashMap<>();
        rootMap.put("users", users);
        //4.模板+数据=输出(带有数据的静态页)
        Writer out = new FileWriter(pathname + "word.html");
        template.process(rootMap, out);
        System.out.println("success");
    }
}
