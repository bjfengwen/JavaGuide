package example;
import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;  
   
/**
 * 权重随机算法在抽奖，资源调度等系统中应用还是比较广泛的，
 * 一个简单的按照权重来随机的实现，权重为几个随机对象(分类)的命中的比例，权重设置越高命中越容易，之和可以不等于100；
 * @author feng
 *
 */
public class WeightRandom {  
    static List<WeightCategory>  categorys = new ArrayList<WeightCategory>();  
    private static Random random = new Random();  
       
    public static void initData() {  
        WeightCategory wc1 = new WeightCategory("A",60);  
        WeightCategory wc2 = new WeightCategory("B",20);  
        WeightCategory wc3 = new WeightCategory("C",20);  
        categorys.add(wc1);  
        categorys.add(wc2);  
        categorys.add(wc3);  
    }  
   
    public static void main(String[] args) {  
          initData();  
          Integer weightSum = 0;  
          for (WeightCategory wc : categorys) {  
              weightSum += wc.getWeight();  
          }  
   
          if (weightSum <= 0) {  
           System.err.println("Error: weightSum=" + weightSum.toString());  
           return;  
          }  
		Integer n = random.nextInt(weightSum); // n in [0, weightSum)
		Integer m = 0;
		int countA=0;
		int countB=0;
		int countC=0;
		for (int i = 0; i < 1000; i++) {
			for (WeightCategory wc : categorys) {
				if (m <= n && n < m + wc.getWeight()) {
					System.out.println("This Random Category is " + wc.getCategory());
					if (wc.getCategory().equals("A")) {
						countA++;
					}
					if (wc.getCategory().equals("B")) {
						countB++;
					}
					if (wc.getCategory().equals("C")) {
						countC++;
					}
					break;
				}
				m += wc.getWeight();
			}
		}
        System.out.println(countA+"--"+countB+"--"+countC);
    }  
   
}  
   
class WeightCategory {  
    private String category;  
    private Integer weight;  
       
   
    public WeightCategory() {  
        super();  
    }  
   
    public WeightCategory(String category, Integer weight) {  
        super();  
        this.setCategory(category);  
        this.setWeight(weight);  
    }  
   
   
    public Integer getWeight() {  
        return weight;  
    }  
   
    public void setWeight(Integer weight) {  
        this.weight = weight;  
    }  
   
    public String getCategory() {  
        return category;  
    }  
   
    public void setCategory(String category) {  
        this.category = category;  
    }  
}