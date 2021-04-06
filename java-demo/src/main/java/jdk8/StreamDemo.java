package jdk8;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://blog.csdn.net/Aria_Miazzy/article/details/104389196
 * 常用的 Lambda 表达式使用场景解析和应用
 * @Description
 * @Author fengwen
 * @Date 2020-11-02
 */
public class StreamDemo {
    static Logger log = LoggerFactory.getLogger(StreamDemo.class);
    public static void main(String[] args) {
        testFilter();
        testMap();
        testToMap();
        testMapToInt();
        testFlatMap();
    }

    public static void testFilter() {
        List<String> list= Arrays.asList("hello","word");

        List<String> newList = list.stream()
                // 过滤掉我们希望留下来的值
                // StringUtils.equals(str,"hello") 表示我们希望字符串是 hello 能留下来
                // 其他的过滤掉
                .filter(str -> StringUtils.equals(str, "hello"))
                // Collectors.toList() 帮助我们构造最后的返回结果
                .collect(Collectors.toList());
        log.info("TestFilter result is {}", JSON.toJSONString(newList));
    }

    // 初始化数据
    private static List<StudentDTO> students = new ArrayList<StudentDTO>() {
        {
            // 添加学生数据
            add(new StudentDTO(1L, "W199", "小美", "WM", 100D, new ArrayList<Course>() {
                {
                    // 添加学生学习的课程
                    add(new Course(300L, "语文"));
                    add(new Course(301L, "数学"));
                    add(new Course(302L, "英语"));
                }
            }));
            add(new StudentDTO(2L, "W25", "小美", "WM", 100D, new ArrayList<Course>()));
            add(new StudentDTO(3L, "W3", "小名", "M", 90D, new ArrayList<Course>() {
                {
                    add(new Course(300L, "语文"));
                    add(new Course(304L, "体育"));
                }
            }));
            add(new StudentDTO(4L, "W1", "小蓝", "M", 10D, new ArrayList<Course>() {
                {
                    add(new Course(301L, "数学"));
                    add(new Course(305L, "美术"));
                }
            }));
        }
    };

    public static void testMap() {
        // 得到所有学生的学号
        // 这里 students.stream() 中的元素是 StudentDTO，通过 map 方法转化成 String 的流
        List<String> codes = students.stream()
                //StudentDTO::getCode 是 s->s.getCode() 的简写
                .map(StudentDTO::getCode)
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(codes));
    }
// 运行结果为：TestMap 所有学生的学号为 ["W199","W25","W3","W1"]


    public static void testToMap() {
        Map<String, String> codeNameMap = students.stream()
                .collect(Collectors.toMap(StudentDTO::getCode, StudentDTO::getName));
        log.info("testToMap result is {}", JSON.toJSONString(codeNameMap));
    }

    public static void testToMap2() {
        Map<String, StudentDTO> codeNameMap = students.stream()
                .collect(Collectors.toMap(s->s.getCode(),s->s ));
        log.info("testToMap result is {}", JSON.toJSONString(codeNameMap));
    }


    public static void testMapToInt() {
        List<Integer> ids = students.stream()
                //mapToInt 方法的功能和 map 方法一样，只不过 mapToInt 返回的结果已经没有泛型，已经明确是 int 类型的流了
                .mapToInt(s->Integer.valueOf(s.getId()+""))
                // 一定要有 mapToObj，因为 mapToInt 返回的是 IntStream，因为已经确定是 int 类型了
                // 所有没有泛型的，而 Collectors.toList() 强制要求有泛型的流，所以需要使用 mapToObj
                // 方法返回有泛型的流
                .mapToObj(s->s)
                .collect(Collectors.toList());
        log.info("TestMapToInt result is {}", JSON.toJSONString(ids));

        // 计算学生总分
        Double sumScope = students.stream()
                .mapToDouble(s->s.getScope())
                // DoubleStream/IntStream 有许多 sum（求和）、min（求最小值）、max（求最大值）、average（求平均值）等方法
                .sum();
        log.info("TestMapToInt 学生总分为： is {}", sumScope);
    }
    //flatMap 方法也是可以做一些流的转化，和 map 方法不同的是，其明确了 Function 函数的返回值的泛型是流，源码如下：
    public static void testFlatMap(){
        // 计算学生所有的学习课程，flatMap 返回 List<课程> 格式
        List<Course> courses = students.stream().flatMap(s->s.getLearningCources().stream())
                .collect(Collectors.toList());
        log.info("TestMapToInt flatMap 计算学生的所有学习课程如下 {}", JSON.toJSONString(courses));

        // 计算学生所有的学习课程，map 返回两层课程嵌套格式
        List<List<Course>> courses2 = students.stream().map(s->s.getLearningCources())
                .collect(Collectors.toList());
        log.info("TestMapToInt map 计算学生的所有学习课程如下 {}", JSON.toJSONString(courses2));

        List<Stream<Course>> courses3 = students.stream().map(s->s.getLearningCources().stream())
                .collect(Collectors.toList());
        log.info("TestMapToInt map 计算学生的所有学习课程如下  {}", JSON.toJSONString(courses3));
    }

    public static void testDistinct(){
        // 得到学生所有的名字，要求是去重过的
        List<String> beforeNames = students.stream().map(StudentDTO::getName).collect(Collectors.toList());
        log.info("TestDistinct 没有去重前的学生名单 {}",JSON.toJSONString(beforeNames));

        List<String> distinctNames = beforeNames.stream().distinct().collect(Collectors.toList());
        log.info("TestDistinct 去重后的学生名单 {}",JSON.toJSONString(distinctNames));

        // 连起来写
        List<String> names = students.stream()
                .map(StudentDTO::getName)
                .distinct()
                .collect(Collectors.toList());
        log.info("TestDistinct 去重后的学生名单 {}",JSON.toJSONString(names));
    }

    public void testSorted(){
        // 学生按照学号排序
        List<String> beforeCodes = students.stream().map(StudentDTO::getCode).collect(Collectors.toList());
        log.info("TestSorted 按照学号排序之前 {}",JSON.toJSONString(beforeCodes));

        List<String> sortedCodes = beforeCodes.stream().sorted().collect(Collectors.toList());
        log.info("TestSorted 按照学号排序之后 is {}",JSON.toJSONString(sortedCodes));

        // 直接连起来写
        List<String> codes = students.stream()
                .map(StudentDTO::getCode)
                // 等同于 .sorted(Comparator.naturalOrder()) 自然排序
                .sorted()
                .collect(Collectors.toList());
        log.info("TestSorted 自然排序 is {}",JSON.toJSONString(codes));

        // 自定义排序器
        List<String> codes2 = students.stream()
                .map(StudentDTO::getCode)
                // 反自然排序
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        log.info("TestSorted 反自然排序 is {}",JSON.toJSONString(codes2));
    }
    public void testLimit(){
        List<String> beforeCodes = students.stream().map(StudentDTO::getCode).collect(Collectors.toList());
        log.info("TestLimit 限制之前学生的学号为 {}",JSON.toJSONString(beforeCodes));

        List<String> limitCodes = beforeCodes.stream()
                .limit(2L)
                .collect(Collectors.toList());
        log.info("TestLimit 限制最大限制 2 个学生的学号 {}",JSON.toJSONString(limitCodes));

        // 直接连起来写
        List<String> codes = students.stream()
                .map(StudentDTO::getCode)
                .limit(2L)
                .collect(Collectors.toList());
        log.info("TestLimit 限制最大限制 2 个学生的学号 {}",JSON.toJSONString(codes));
    }

    public static void testReduce(){
        // 计算一下学生的总分数
        Double sum = students.stream()
                .map(StudentDTO::getScope)
                // scope1 和 scope2 表示循环中的前后两个数
                .reduce((scope1,scope2) -> scope1+scope2)
                .orElse(0D);
        log.info("总成绩为 {}",sum);

        Double sum1 = students.stream()
                .map(StudentDTO::getScope)
                // 第一个参数表示成绩的基数，会从 100 开始加
                .reduce(100D,(scope1,scope2) -> scope1+scope2);
        log.info("总成绩为 {}",sum1);
    }

    public void testListToMap(){
        // 学生根据名字进行分类
        Map<String, List<StudentDTO>> map1 = students.stream()
                .collect(Collectors.groupingBy(StudentDTO::getName));
        log.info("testListToMap groupingBy 学生根据名字进行分类 result is Map<String,List<StudentDTO>> {}",
                JSON.toJSONString(map1));

        // 统计姓名重名的学生有哪些
        Map<String, Set<String>> map2 = students.stream()
                .collect(Collectors.groupingBy(StudentDTO::getName,
                        Collectors.mapping(StudentDTO::getCode,Collectors.toSet())));
        log.info("testListToMap groupingBy 统计姓名重名结果 is {}",
                JSON.toJSONString(map2));

        // 学生转化成学号为 key 的 map
        Map<String, StudentDTO> map3 = students.stream()
                //第一个入参表示 map 中 key 的取值
                //第二个入参表示 map 中 value 的取值
                //第三个入参表示，如果前后的 key 是相同的，是覆盖还是不覆盖，(s1,s2)->s1 表示不覆盖，(s1,s2)->s2 表示覆盖
                .collect(Collectors.toMap(s->s.getCode(),s->s,(s1,s2)->s1));
        log.info("testListToMap groupingBy 学生转化成学号为 key 的 map result is{}",
                JSON.toJSONString(map3));

    }

    static class User{
        private   boolean sex;
        private   int age;
        private   String name ;

        public User(){}
        public User(String name,int age ,boolean sex){
            this.name= name;
            this.age =age;
            this.sex=sex;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static void testsummarizingInt(){
        //https://blog.csdn.net/u012843361/article/details/83094827
        List<User> listUser = new ArrayList<>();
        listUser.add(new User("李白", 20, true));
        listUser.add(new User("杜甫", 40, true));
        listUser.add(new User("李清照", 18, false));
        listUser.add(new User("李商隐", 23, true));
        listUser.add(new User("杜牧", 39, true));
        listUser.add(new User("苏小妹", 16, false));

        IntSummaryStatistics summaryStatistics = listUser.stream().collect(Collectors.summarizingInt(User::getAge));
        System.out.println("年龄平均值：" + summaryStatistics.getAverage()); // 年龄平均值：26.0
        System.out.println("人数：" + summaryStatistics.getCount()); // 人数：6
        System.out.println("年龄最大值：" + summaryStatistics.getMax()); // 年龄最大值：40
        System.out.println("年龄最小值：" + summaryStatistics.getMin()); // 年龄最小值：16
        System.out.println("年龄总和：" + summaryStatistics.getSum()); // 年龄总和：156

        // 根据指定条件取最大值： 取年纪最大的人
        Optional<User> optional = listUser.stream().collect(Collectors.maxBy(Comparator.comparing((user) -> {
            return user.getAge();
        })));
        if (optional.isPresent()) { // 判断是否有值
            User user = optional.get();
            System.out.println("最大年纪的人是:" + user.getName()); // 输出==》 最大年纪的人是:杜甫
        }
    }
}
