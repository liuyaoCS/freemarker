package ${projectPackageName};

import com.qihoo.qarth.QarthContext;
import com.qihoo.qarth.QarthLog;

public class QarthEntry {
    public static void init(QarthContext qc) {
        QarthLog.e("QarthEntry", "QarthEntry: init");
        <#list datas as data>
        new ${data.hookFileName}().hook(qc.patchClassLoader);
        </#list>

    }
}