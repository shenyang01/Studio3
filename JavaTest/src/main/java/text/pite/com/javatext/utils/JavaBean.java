package text.pite.com.javatext.utils;

import java.io.Serializable;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/11/9
 */

public class JavaBean implements Serializable {
    private String name;
    private transient String psd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public JavaBean(String name, String psd){
        this.name=name;
        this.psd=psd;

    }

    @Override
    public String toString() {
        return "JavaBean{" +
                "name='" + name + '\'' +
                ", psd='" + psd + '\'' +
                '}';
    }
}
