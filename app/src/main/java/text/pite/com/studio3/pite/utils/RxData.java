package text.pite.com.studio3.pite.utils;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/12/15
 */

public class RxData {
    private String name="555";
    private int  age=5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "RxData{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
