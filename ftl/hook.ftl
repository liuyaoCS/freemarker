package ${packageName};

import com.qihoo.qarth.QarthLog;
import com.qihoo.qarth.QarthMethod;
import java.lang.reflect.Member;


public class ${className} extends QarthMethod {

    public ${className}() {
        QarthLog.e("QarthLog", "TestHook");
        className = "${packageName}.${className}";
        name = "${methodName}";
        parameterTypes = new Class[]{
            <#list ptypes as ptype>
                ${ptype}.class<#sep>,</#sep>
            </#list>
        };
    }

    private  void before(Object thiz, Object[] args) {
        //NOTE: add code below
        ${before_content}
    }

    private  void after(Object thiz, Object[] args) {
        //NOTE: add code below
        ${after_content}
    }

    @Override
    protected Object invoke(Member method, Object thiz, Object[] args) throws Throwable {
        before(thiz,args);
        Object ret = null;
        <#if invokeOrigin>
        ret = super.invoke(method, thiz, args);
        </#if>
        after(thiz,args);
        return ret;
    }
}