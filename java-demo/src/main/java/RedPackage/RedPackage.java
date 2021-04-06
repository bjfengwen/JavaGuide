package RedPackage;

import java.util.Random;

public class RedPackage {
	int remainSize;
	double remainMoney;
	{
		remainSize = 10;
		remainMoney = 100;
	}

	public static double getRandomMoney(RedPackage _redPackage) {
		// remainSize 剩余的红包数量
		// remainMoney 剩余的钱
		if (_redPackage.remainSize == 1) {
			// 如果只剩最后一个红包
			_redPackage.remainSize--;
			// 剩余红包个数减一
			return (double) Math.round(_redPackage.remainMoney * 100) / 100;
			// 返回double型的值，也就是最后一个红包的面额（换算成元）
		}
		// 若剩余红包不止一个
		Random r = new Random();
		// 定义了一个随机数 r
		double min = 0.01;
		// 定义红包最小面额是一分钱
		// 也就是我抢的最多的那个面额
		double max = _redPackage.remainMoney / _redPackage.remainSize * 2;
		// 定义红包的最大面额是 剩余平均值X2
		double money = r.nextDouble() * max;
		money = money <= min ? 0.01 : money;
		money = Math.floor(money * 100) / 100;
		// Math.floor的意思是取小于这个浮点数最接近整数
		// 这就是你抢到的钱
		_redPackage.remainSize--;
		// 红包个数减一
		_redPackage.remainMoney -= money;
		// 总钱数扣除
		return money;
		// 小手一抖，红包我有
	}

	public static void main(String[] args) {
		RedPackage _redPackage = new RedPackage();
		double count = 0;
		for (int i = 0; i < 10; i++) {
			double randomMoney = getRandomMoney(_redPackage);
			count += randomMoney;
			System.out.println(randomMoney);
		}
		System.out.println(count);

	}
}
