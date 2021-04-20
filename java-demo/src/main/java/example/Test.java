package example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 基本算法描述如下: 
 
1、每个广告增加权重  
2、将所有匹配广告的权重相加sum，  
3、以相加结果为随机数的种子，生成1~sum之间的随机数rd  
4、.接着遍历所有广告，访问顺序可以随意.将当前节点的权重值加上前面访问的各节点权重值得curWt,判断curWt >=  rd,
如果条件成立则返回当前节点,如果不是则继续累加下一节点. 直到符合上面的条件,由于rd<=sum 因此一定存在curWt>=rd。 
 * @author feng
 *
 */
public class Test { 
	 
		public static void main(String[] args) {

		List<Node> arrNodes = new ArrayList<Node>();
		Node n = new Node(10, "测试1");
		arrNodes.add(n);
		n = new Node(20, "测试2");
		arrNodes.add(n);
		n = new Node(30, "测试3");
		arrNodes.add(n);
		n = new Node(40, "测试4");
		arrNodes.add(n);

		// Collections.sort(arrNodes, new Node());
		Map<String, Integer> showMap = null;
		int sum = getSum(arrNodes);
		int random = 0;
		Node kw = null;
		for (int k = 0; k < 20; k++) {
			showMap = new LinkedHashMap<String, Integer>();
			for (int i = 0; i < 100; i++) {
				random = getRandom(sum);
				kw = getKW(arrNodes, random);
				if (showMap.containsKey(kw.kw)) {
					showMap.put(kw.kw, showMap.get(kw.kw) + 1);
				} else {
					showMap.put(kw.kw, 1);
				}
				// System.out.println(i + " " +random + " " + getKW(arrNodes, random));
			}
			System.out.print(k + " ");
			System.out.println(showMap);
		}
	}

	public static Node getKW(List<Node> nodes, int rd) {
		Node ret = null;
		int curWt = 0;
		for (Node n : nodes) {
			curWt += n.weight;
			if (curWt >= rd) {
				ret = n;
				break;
			}
		}
		return ret;
	}

	public static int getSum(List<Node> nodes) {
		int sum = 0;
		for (Node n : nodes)
			sum += n.weight;
		return sum;
	}

	public static int getRandom(int seed) {
		return (int) Math.round(Math.random() * seed);
	}
}

class Node implements Comparator {
	int weight = 0;
	String kw = "";

	public Node() {
	}

	public Node(int wt, String kw) {
		this.weight = wt;
		this.kw = kw;
	}

	public String toString() {
		StringBuilder sbBuilder = new StringBuilder();
		sbBuilder.append(" weight=").append(weight);
		sbBuilder.append(" kw").append(kw);
		return sbBuilder.toString();
	}

	public int compare(Object o1, Object o2) {
		Node n1 = (Node) o1;
		Node n2 = (Node) o2;
		if (n1.weight > n2.weight)
			return 1;
		else
			return 0;
	}
}