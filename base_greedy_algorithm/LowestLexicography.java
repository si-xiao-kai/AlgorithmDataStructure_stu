package base_greedy_algorithm;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class LowestLexicography {

	//学习TreeSet的使用，按照字典序排序字符串数组
	public static void TreeSet_useage_stu() {
		TreeSet<String> strTreeSet = new TreeSet<>();
		
		strTreeSet.add("a");
		strTreeSet.add("cd");
		strTreeSet.add("xy");
		strTreeSet.add("mty");
		System.out.println("strTreeSet:"+strTreeSet);
	}
	
	//1.对数器方法，暴力枚举字符串数组中字符串拼接的排列组合，返回字典序最小的拼接结果
	
	//返回字典序最小的字符串拼接结果
	public static String LowestLexicographyString01 (String[] strs) {
		if(strs==null||strs.length==0)
		{
			return "";
		}
		//需要重新定义一个TreeSet然后返回，
		return strsFullPermutation(strs).first(); 
		//TreeSet<String> ans=strsFullPermutation(strs);
		//return ans.size()==0? "":ans.first();
		
	}
	
	// 返回字符串数组中字符串拼接的全排列 full permutation（递归）
	public static TreeSet<String> strsFullPermutation(String[] strs) {
		TreeSet<String> resSet=new TreeSet<>();
		//如果传入空数组，返回空字符串的resSet TreeSet;
		if(strs.length==0) {
			resSet.add("");
			return resSet;
		}
		//如果传入的字符串数组不为空，那么递归返回全排列
		//
		for(int i=0;i<strs.length;i++) {
			String first=strs[i];
			//返回除去first之后剩余的字符串数组
			String[] restStrs=removeIndexstrFromStrs(strs, i);
			TreeSet<String> next=strsFullPermutation(restStrs);//剩余字符串数组的全排列
			for(String s:next) {
				resSet.add(first+s);
			}
		}
		return resSet;
	}
	
	//功能函数，removeIndexstrFromStrs从字符串数组中删除一个字符串返回剩下的字符串数组。*
	public static String[] removeIndexstrFromStrs(String[] strs,int index) {
		String[] tmp=new String[strs.length-1];
		int j=0;//关键再给一个变量记录新数组的下标变化
//		for(int i=0;i<strs.length;i++) {
//			if(i==index)
//			{
//				continue;
//			}
//			tmp[j++]=strs[i];	
//		}
		//简化
		for(int i=0;i<strs.length;i++) {
			if(i!=index) {
				tmp[j++]=strs[i];	
			}
				
		}
		return tmp;
	}
	
	
	//2.自定义贪心策略实现返回字符串数组拼接最小字典序的方法
	
	//（1）自定义比较器
	public static class MyComparator implements Comparator<String>{
		@Override
		public int compare(String s1,String s2) {
			return (s1+s2).compareTo(s2+s1);
		}
	}//s1和s2,如果s1拼接s2的字典序小于等于s2拼接s1的字典序，则s1排在s2的前面
	
	// （2）通过自定义的排序策略对字符串数组进行排序，获得最点序最小的拼接字符串
	public static String LowestLexicographyString02(String[] strs) {
		if(strs==null||strs.length==0) {
			return "";
		}
		Arrays.sort(strs,new MyComparator());
		String ans="";
		for(int i=0;i<strs.length;i++) {
			ans+=strs[i];
		}
		return ans;
	}
	
	// 3.编写对数器测试方法的正确性
	//（1）随机生成字符串数组生成函数
	public static String[] generateRandomStringArray(int arrlength,int strlength) {
		String[] strs=new String[(int)(arrlength*Math.random()+1)];//[1,arrlength]随机长度
		for(int i=0;i<strs.length;i++) {
			strs[i]=generateRandomString(strlength);
		}
		return strs;
	}
	
	//（2）随机字符串生成函数
	public static String generateRandomString(int strlen) {
		char[] res=new char[(int)(Math.random()*strlen+1)];//[1,strlen]随机长度的字符数组
		String s="";
		for(int i=0;i<res.length;i++) {
			res[i]=(char)(Math.random()*25+97); //97-a 122-z //[97,122]中的随机数
			s+=res[i];
		}
		return s;
	}
	//（3）字符串数组拷贝函数
	public static String[] copyStringArray(String[] strs) {
		String[] newstrStrings=new String[strs.length];
		for(int i=0;i<strs.length;i++) {
			newstrStrings[i]=strs[i];
		}
		return newstrStrings;
	}
	
	
	public static void main(String[] args) {
		System.out.println("TreeSet的使用:");
		TreeSet_useage_stu();//按照字典序排序字符串 
		//out:
		//strTreeSet:[a, cd, mty, xy]
		
		System.out.println("测试removeIndexstrFromStrs 功能:");
		//测试removeIndexstrFromStrs 功能
		String[] teststrs= {"a","ab","bc"};
		String[] strs=removeIndexstrFromStrs(teststrs, 0);
		for(int i=0;i<strs.length;i++)
		{
			System.out.println(strs[i]);
		}
		//out:
		//ab
		//bc
		
		
		//测试全排列方法
		System.out.println("测试全排列方法:");
		TreeSet<String> fullresult =strsFullPermutation(teststrs);
		System.out.println(fullresult);
		//out:
		//[aabbc, ababc, abbca, abcab, bcaab, bcaba]
		
		//测试暴力方法(对数器)
		System.out.println(LowestLexicographyString01(teststrs));
		//out:
		//aabbc
		
		//测试贪心算法
		System.out.println(LowestLexicographyString02(teststrs));
		//out:
		//aabbc
		
		//测试随机生成字符串的函数
		System.out.println((int)(10*Math.random()+1)); //[1,10]中的随机数
		System.out.println((char)(Math.random()*26+97));//[97,122]中的随机数
		System.out.println(generateRandomString(10));
		
		//测试随机生成字符串数组的函数
		System.out.println("测试随机生成字符串数组的函数:");
		String[] strstest = generateRandomStringArray(10, 10);//数组长度[1,10]、字符串长度[1,10]
		for(int i=0;i<strstest.length;i++) {
			System.out.println(strstest[i]);
		}
		
		//使用对数器验证贪心算法的正确性
		System.out.println("使用对数器验证贪心算法的正确性:");
		int testTimes=5;
		int arrlen=10;
		int strlen=10;
		for(int i =0; i<testTimes; i++) {
			String[] strs1=generateRandomStringArray(arrlen, strlen);
			String[] strs2=copyStringArray(strs1);
			String res1=LowestLexicographyString01(strs1);
			String res2=LowestLexicographyString02(strs2);
			if(!res1.equals(res2)) {
				System.out.println("Somthing Wrong!");
				break;
			}
//			if(res1!=res2) {
//				System.out.println("Somthing Wrong!");
//				break;//res1 res2 是字符串对象，不要用 != == 判断，用equals才能去判断值是否一样。
//			}
		}
		System.out.println("Good Job!");
		//out：
		//测试使用对数器验证贪心算法的正确性:
		//Good Job!
	}
}
