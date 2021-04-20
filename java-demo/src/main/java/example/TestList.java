package example;
import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
  
public class TestList {  
  
    void init(List<Integer> list) {  
        list.clear();  
        for (int i = 0; i < 10; i++) {  
            list.add(i + 1);  
        }  
    }  
  
    void remove(List<Integer> list) {  
        for (int i = 0; i < 5; i++) {  
            list.remove(i);  
        }  
    } 
    /**
     * fix remove bug
     * @param list
     */
    void remove2(List<Integer> list) {  
        int num = list.size() - 5;  
        for (int i = 0; i < num; i++) {  
            list.remove(i);  
            i--;  
            num--;  
        }  
    }  
  
    void removeTwo(List<Integer> list) {  
        for (int i : list) {  
            if (i < 6) {  
                list.remove(i);  
            }  
        }  
    }  
  
    void removeThree(List<Integer> list) {  
        for (Iterator<Integer> iter = list.iterator(); iter.hasNext();) {  
            int i = iter.next();  
            if (i < 6) {  
                iter.remove();  
            }  
        }  
    }  
  
    public static void main(String[] args) {  
        TestList testList = new TestList();  
        List<Integer> list = new ArrayList<Integer>();  
  
        // 第一种方法  
        testList.init(list);  
        testList.remove(list);  
        System.out.println(list);  
  
        // 第二种方法  
        try {  
            testList.init(list);  
            testList.removeTwo(list);  
            System.out.println(list);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        // 第三种方法  
        testList.init(list);  
        testList.removeThree(list);  
        System.out.println(list);  
    }  
}  




/**
第一种方法：
第一次执行完remove方法后，并不像我们简单想象的那样就把第一个删除了，“1”这个对象被删除了没错，
但是当被删除后List中“2”以后的9个对象的index索引也变了，
都比原来的值减一，换句话说就是剩下的9个对象的index值为从0到8，而不是原来的从1到9了，那么第二次执行remove方法时，
此时list.remove(1)删除的就是“3”这个对象（“3”的index值为1），而不是我们想象的删除“2”对象。
第二种方法：
原因跟上面一样，导致List的next()方法内部出现modCount和expectedModCount不一致导致抛出异常。

所以我们这里建议大家采用第三种方法来删除List集合中某一个对象，这样做是最简单且容易记忆的。
那第一种方法和第二种方法有没有解决的办法呢？目前我知道第一种方法的解决办法，第二种应该是无解的（因为语法结构导致）。
*/