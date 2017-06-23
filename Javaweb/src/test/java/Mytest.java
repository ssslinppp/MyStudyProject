import com.ll.jersey.getTest.model.Student;
import com.ll.springMvc.json.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIULIN on 2017/6/11.
 */
public class Mytest {
    private String aa;
    private Person p;

    public String getAa() {
        return aa;
    }

    public Mytest() {
    }

    public Mytest(String aa, Person p) {

        this.aa = aa;
        this.p = p;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public Person getP() {
        return p;
    }

    public void setP(Person p) {
        this.p = p;
    }

    /**
     * @param id
     * @return
     */
    public static String getStudent(int id) {
        return "asd";
    }


    public static void main(String[] args) {
        System.out.printf("");
        System.out.println("");
        List<Student> list = new ArrayList<>();
        list.add(new Student("asd", 23, "sdf"));

        getStudent(12);


    }

}









