package jvmdeepunderstanding.chapter2;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    static class  OOMObject{
        
    }
// -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
    public static void main(String[] args) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        new Thread(
                ()->{
                    while (true){
                        list.add(new OOMObject());
                    }
                }
        ).start();
        new Thread(
                ()->{
                    while (true){
                        try {
                            Thread.sleep(1000);
                            System.out.println("哇哈哈");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        Thread.sleep(900000000);
    }
}
