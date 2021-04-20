package regex;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCounter {

	/**
	 * 正则统计字符串出现次数
	 * 
	 * @param source
	 * @param regexNew
	 * @return int
	 */
	public static int finder(String source, String regexNew) {
		String regex = "[a-zA-Z]+";
		if (regexNew != null && !regexNew.equals("")) {
			regex = regexNew;
		}
		Pattern expression = Pattern.compile(regex);
		Matcher matcher = expression.matcher(source);
		TreeMap<Object, Integer> myTreeMap = new TreeMap<Object, Integer>();
		int n = 0;
		Object word = null;
		Object num = null;
		while (matcher.find()) {
			word = matcher.group();
			n++;
			if (myTreeMap.containsKey(word)) {
				num = myTreeMap.get(word);
				Integer count = (Integer) num;
				myTreeMap.put(word, new Integer(count.intValue() + 1));
			} else {
				myTreeMap.put(word, new Integer(1));
			}
		}
		return n;
	}
}