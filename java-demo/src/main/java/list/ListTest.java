package list;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author fengwen
 * @Date 2020-11-11
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> dbUserRoleList = new ArrayList<String>() {
            {
                add("a");
                add("b");
                add("c");
            }
        };//原来的

        List<String> roleList = new ArrayList<String>() {
            {
                add("a");
                add("c");
                add("f");
            }
        };
        List<String> copyDbUserRoleList = new ArrayList<>(dbUserRoleList);
        List<String> copyRoleList = new ArrayList<>(roleList);

        //la.retainAll(lb); //交集
        // la.addAll(lb);//合集

//        dbUserRoleList.removeAll(roleList); // 差集
//
//
//        System.out.println(dbUserRoleList);//需删除的
//        copyRoleList.removeAll(copyDbUserRoleList); // 差集
//        System.out.println(copyRoleList);//需添加


        dbUserRoleList.retainAll(roleList);
    }
}
