package base_greedy_algorithm;

import java.util.PriorityQueue;

public class UseLeastMoneySplitGlod {

	/*
	   一块金条切成两半，是需要花费和长度数值一样的铜板的。比如长度为20的金条，不
	   管怎么切，都要花费20个铜板。一群人想整分整块金条，怎么分最省铜板?
	   
	   例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10， 20， 30三个部分。
	   如果先把长度60的金条分成10和50，花费60;再把长度50的金条分成20和30,花费50;一 共花费110铜板。
	   但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20，花 费30;一共花费90铜板。
	   输入一个数组，返回分割的最小代价。
	   
	   反向思考：合并⇔划分的逆过程，将两个小块金条合并需要花费两个金条的和的代价，怎么样能花费最少的代价拼接起来一个完整的金条。
	   合并的代价==划分的代价。
	 */
	//1.暴力方法（对数器）
	//思路:每次合并任意合并，遍历所有的情况，返回最少的代价。
	//1）主方法
	public static int SplitGold1(int[] arr) {
		if(arr==null || arr.length==0) {
			return 0;
		}
		
		return recursiveFunc(arr,0);
	}	
	//2）暴力递归
	//参数：arr是当前的数组的状态，pre 表示之前合并操作花费的代价
	public static int recursiveFunc(int[] arr,int pre) {
		if(arr.length==1) {//base case 当数组中只有一个元素时，表示完成了合并的整个过程
			return pre;
		}
		int ans=Integer.MAX_VALUE;
		for(int i=0;i<arr.length;i++) {//元素个数大于1的时候，遍历每个元素
			for(int j=i+1;j<arr.length;j++) {//遍历i元素后面的每个元素，用于同i合并
				ans=Math.min(ans,recursiveFunc(merge2elements(arr, i, j) , pre+arr[i]+arr[j]));
			}
		}
		return ans;
	}
	
	//3）功能函数，合并一个数组中的两个元素，返回新数组
	public static int[] merge2elements(int[] arr,int i,int j) {
		int[] newarr=new int[arr.length-1];
		int indexj=0;
		for(int indexi=0;indexi<arr.length;indexi++) {
			if(indexi!=i&&indexi!=j) {
				newarr[indexj++]=arr[indexi];
			}
		}
		newarr[indexj]=arr[i]+arr[j];
		return newarr;
	}
	
	//2.贪心算法
	public static int splitGold2(int[] arr) {
		PriorityQueue<Integer> littleheap=new PriorityQueue<>();//小根堆
		for(int i=0;i<arr.length;i++) {//将数组中的数据添加到小根堆里面
			littleheap.add(arr[i]);
		}
		int ans=0;
		int tmp=0;
		while(littleheap.size()>1) {
			tmp=littleheap.poll()+littleheap.poll();//把最小的两个数拿出来给tmp
			ans+=tmp;
			littleheap.add(tmp);//将合并两个最小值后的结果放回小根堆
		}
		
		return ans;
	}
	
	//3.编写对数器
	public static int[] generateRandomIntArray(int maxlen,int maxvalue) {
		int[] tmp=new int[(int)(Math.random()*maxlen+1)];//[1,maxlen];
		for(int i=0;i<tmp.length;i++) {
			tmp[i]=(int)(Math.random()*maxvalue+1);//[1,maxvalue]
		}
		return tmp;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1.测试数组
		System.out.println("========测试数组========");
		int[] test= {3,5,10,45,20};
		for(int i:test) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		//2.测试merge2elements()方法
		System.out.println("========测试merge2elements()方法========");
		int[] newtest=merge2elements(test, 0, 1);
		for(int i:newtest) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		//3.测试暴力方法
		System.out.println("========测试暴力方法========");
		System.out.println(SplitGold1(test));
			
		//4.测试贪心算法
		System.out.println("========测试贪心方法========");
		System.out.println(splitGold2(test));
		
		//4.测试随机数组生成方法
		System.out.println("========测试随机数组生成方法========");
		int[] arr1=generateRandomIntArray(10, 100);
		for(int i: arr1) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		//5.对数器验证贪心算法的正确性
		System.out.println("========对数器验证贪心算法的正确性========");
		int maxtime=5;
		int maxlen=10;
		int maxvalue=10;
		for(int i=0;i<maxtime;i++) {
			int[] testarr=generateRandomIntArray(maxlen, maxvalue);
			if(SplitGold1(testarr)!=SplitGold1(testarr)) {
				System.out.println("Wrong!");
				break;
			}
		}
		System.out.println("Good Job!");
	}
}
