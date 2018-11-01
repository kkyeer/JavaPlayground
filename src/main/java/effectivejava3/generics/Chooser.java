package effectivejava3.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/18 14:10
 */
public class Chooser<T> {
    private List<T> list = new ArrayList<>(10);

    public Chooser(List<T> list) {
        this.list = list;
    }

    public T randomChoose(){
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }

}
