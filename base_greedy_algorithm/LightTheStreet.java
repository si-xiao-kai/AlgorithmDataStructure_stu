package base_greedy_algorithm;

import java.util.HashSet;


public class LightTheStreet {

	/*题目描述: 
	 * 给定一个字符串str，只由’X’和’.’两种字符构成。'X'表示墙，不能放灯，也不需要点亮，
	 * ’.’表示居民点， 可以放灯,需要点亮，如果灯放在i位置，可以让i-1， i和i+1三个位置被点亮，
	 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。
	 */
	//1.暴力方法
	//对于每个可以放灯的位置，有两种情况，放和不放，排列组合所有的情况序列，
	//排除那些没有将所有需要点亮的位置点亮的序列，从剩下的序列中找到最少的路灯数的序列。
	
	//1）主方法 接受一个由"X"和"."组成的字符串
	public static int lightStreet1(String street) {
		if(street==null||street=="") {
			return 0;
		}
		return recursiveFunc(street.toCharArray(),0,new HashSet<Integer>());
	}
	
	//2）递归方法
	//参数:street--街道字符串、index 当前要做决定是否放灯的位置、lights 记录哪些位置放了灯
	public static int recursiveFunc(char[] street,int index,HashSet<Integer> lights) {
		if(index==street.length) {
			for(int i=0;i<street.length;i++) {
				if(street[i]!='X') {
					if(!lights.contains(i-1)&&!lights.contains(i)&&!lights.contains(i+1)) {
						return Integer.MAX_VALUE;
					}
				}
			}
			return lights.size();
		}
		else {
			int no_num=recursiveFunc(street, index+1, lights);
			int yes_num=Integer.MAX_VALUE;
			if(street[index]!='X') {
				lights.add(index);
				yes_num=recursiveFunc(street, index+1, lights);
				lights.remove(index);
			}
			return Math.min(no_num,yes_num);
		}
	}
	
	
	//2.贪心算法
	public static int lightStreet2(String street) {
		char[] streetArr=street.toCharArray();
		int i=0;
		int lights_num=0;
		while(i<streetArr.length) {
			if(streetArr[i]=='X') {
				i++;
			}
			else { //当i位置是'.'的时候，一定会放一个灯将'.'点亮
				lights_num++;
				if(i+1==streetArr.length) {
					break;
				}
				else {
					if(streetArr[i+1]=='X') {
						i=i+2;
					}
					else {
						i=i+3;
					}
				}
				
			}	
		}
		return lights_num;
	}
	
	//3.编写对数器对比验证贪心算法的正确性
	public static String generateRandomStreet(int maxlength) {
		int len= (int)(Math.random()*maxlength+1);//[1,maxlength]
		char[] strchar=new char[len];
		for(int i=0;i<len;i++) {
			strchar[i]=Math.random()>0.5 ? 'X':'.';
		}
		//return strchar.toString();//这样返回之后无法直接打印String类型，打印的是地址
		return String.valueOf(strchar);//通过valueOf方法，将char数组构造成String
	}
	
	
	public static void main(String[] args) {
		// 1.测试字符串
		System.out.println("=============两个street字符串=============");
		String test1="X...XX.X..X....XXXX..........X";
		System.out.println("test1:"+test1);
		String test2="X...XX.X..X....XXXX..X";
		System.out.println("test2:"+test2);
		//2.测试暴力方法
		System.out.println("=============测试暴力方法=============");
		System.out.println(lightStreet1(test1));
		System.out.println(lightStreet1(test2));
		//3.测试贪心方法
		System.out.println("=============测试贪心方法=============");
		System.out.println(lightStreet2(test1));
		System.out.println(lightStreet2(test2));
		//4.测试随机字符串生成函数
		System.out.println("=============测试随机字符串生成函数=============");
		String str1 = generateRandomStreet(40);
		String str2 = generateRandomStreet(40);
		System.out.println(str1);//
		System.out.println(str2);//
		//5.编写对数器对比验证贪心算法的正确性
		System.out.println("=============编写对数器对比验证贪心算法的正确性=============");
		int testTime=10;
		int maxlen=50;
		for(int i=0;i<testTime;i++) {
			String teststreet=generateRandomStreet(maxlen);
			if(lightStreet1(teststreet)!=lightStreet2(teststreet)) {
				System.out.println("Wrong!");
				break;
			}
		}
		System.out.println("Good Job!");
	}

}
