package base_greedy_algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MaximizeTheProfits {
	/*
	    输入:正数数组costs、正数数组profits、正数K、正数M 
		costs[i]表示i号项目的花费
		profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
		K表示你只能串行的最多做k个项目
		M表示你初始的资金
		说明:每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
		输出:你最后获得的最大钱数。
		
	 */
	
	//最自然的一种贪心之一，不需要对数器来验证了。
	//思路：【当前手里的启动资金M能够做哪些项目，挑能做的中利润最大的做，周而复始，直到最后做满K个，
	//或者做不下去了也就是启动资金不够了，如果没做满K个，手里的资金还够做一些小项目，那就挑收益最大的小项目做了。】
	
	
	//1.定义项目Project类
	public static class Project{
		int cost;
		int profit;
		public Project(int c,int p) {
			cost=c;
			profit=p;
		}
		
	}
	
	//2.贪心算法实现最大化项目收益
	public static int maximizeProfits(Project[] arr,int K,int M) {
		//1)将项目按照花费从小到达排序，（借助小根堆）
		PriorityQueue<Project> lockedProjects = new PriorityQueue<>(new litteHeapCompare());
		for(int i=0;i<arr.length;i++) {
			lockedProjects.add(arr[i]);
			//这里添加Project的时候注意，构造新的对象，不能引用传递,改成下面的这样之后还是不行，根本不是这个问题，而是代码逻辑的问题。
			//Cannot read field "cost" because the return value of "java.util.PriorityQueue.peek()" is null
			//lockedProjects.add(new Project(arr[i].cost, arr[i].profit));//
		}
		
		//2)如果资金能够解锁项目，就把项目扔到大根堆里面，按照利润从大到小排序
		PriorityQueue<Project> unlockedProjects =new PriorityQueue<>(new bigHeapCompare());
		
		//能穿行做最多K个项目
		for(int i=0;i<K;i++) {
			//根据当前的资金，解锁能做的项目放入到大根堆
			while(!lockedProjects.isEmpty()&& lockedProjects.peek().cost<=M ) {//注意两个条件的顺序，如果先peek()会空指针异常
				unlockedProjects.add(lockedProjects.poll());	
			}
			
			//如果没做够k个项目，大根堆空了，也解锁不出来新项目了，返回当前的资金。
			if(unlockedProjects.isEmpty()) {
				return M;
			}
			
			//挑利润最大的项目做了，更新当前资金，然后继续拿新的资金去解锁项目，继续挑最大利润的项目做
			//考虑解锁失败的情况，没解锁成功的话，继续做大根堆里面的项目，直到K个项目做完
			M+=unlockedProjects.poll().profit;
			//这里碰到一个错误，之前写成lockedProjects.poll().profit了，所以导致lockedProjects变成了空【过程中学习了debug的方法】
			//然后就报错了：Cannot read field "cost" because the return value of "java.util.PriorityQueue.peek()" is null
			//处理完上面的bug之后，运行的时候，有时候报错有时候不报错，还是同样的错错误。
			//经过分析随机生成的数据，发现了一个逻辑判断的小bug，
			//!lockedProjects.isEmpty() && lockedProjects.peek().cost<=M 注意两个条件的顺序，如果先peek()会空指针异常
			
		}
		return M ;
	}
	
	//小根堆的比较器 花费少的项目放前面
	public static class litteHeapCompare implements Comparator<Project>{
		@Override
		public int compare(Project p1,Project p2) {
			return p1.cost-p2.cost;
		}
	}
	
	//大根堆比较器 利润高的放前面
	
	public static class bigHeapCompare implements Comparator<Project>{
		@Override
		public int compare(Project p1,Project p2) {
			return p2.profit-p1.profit;
		}
	}
	
	
	
	public static void main(String[] args) {

		//1.生成测试项目
		Project[] test=new Project[5];
		for(int i=0;i<test.length;i++) {
			int c=(int)(Math.random()*100+1);//[1,100]
			int p=(int)(Math.random()*50+1);//[1,50]
			test[i]=new Project(c, p);
		}
		System.out.println("========生成测试项目========");
		for(Project p:test) {
			System.out.println("cost:"+p.cost+",profit:"+p.profit);
		}
		
	
		
		//2.测试贪心算法的结果
		System.out.println("========测试贪心算法的结果=======");
		System.out.println(maximizeProfits(test, 3, 50));
		
		/*
		 * 
		 * ========生成测试项目========
			cost:68,profit:39
			cost:10,profit:47
			cost:51,profit:32
			cost:99,profit:28
			cost:53,profit:22
			========测试贪心算法的结果=======
			168
		 */
		//人工分析
		/*
		 * 50+47+39+32=136+32=168 结果正确 
		 * 
		 */
	}

}
