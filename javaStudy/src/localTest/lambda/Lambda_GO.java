package localTest.lambda;

import java.util.ArrayList;
import java.util.List;

public class Lambda_GO {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.forEach(s -> System.out.println(s));
    }

}
