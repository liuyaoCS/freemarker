package ${projectPackageName};

import com.qihoo.qarth.QarthContext;
import com.qihoo.qarth.QarthLog;

public class QarthEntry {
    public static void init(QarthContext qc) {
        QarthLog.e("QarthEntry", "QarthEntry: init");
        new ${hookFileName}().hook(qc.patchClassLoader);
    }
}