package base_greedy_algorithm;

import java.util.Arrays;
import java.util.Comparator;

public class BestConferenceArrange {

	//0.定义会议类，有两个属性，开始时间和结束时间
	public static class Conference{
		int start;//会议开始时间
		int end;//会议结束时间
		
		public Conference(int start,int end) {
			this.start=start;
			this.end=end;
		}
	}
	
	//1.暴力方法，枚举所有可以安排的会议序列,返回可以安排的最大会议数
	
	//1）主方法
	public static int conferenceArrange1(Conference[] conferences) {
		if(conferences==null||conferences.length==0) {
			return 0;
		}
		 
		return recursiveProcessFunc(conferences,0,0);
	}
	
	//2）recursiveProcessFunc（）返回某一时间状态下可以安排的最大会议数量
	
	//参数restConferences表示剩余的没有安排的会议，done_num表示已经安排的会议的数量，time_now表示现在的时间点
	//在time_now时间点，已经安排了done_num个会议，剩下的没有安排的会议，在restConferences列表中
	public static int recursiveProcessFunc(Conference[] restConferences, int done_num,int time_now) {
		if(restConferences.length==0) {
			return done_num;
		}
		int max=done_num;
		for(int i=0;i<restConferences.length;i++) {
			if(restConferences[i].start>time_now) {
				Conference[] nexts=deleteIndexConference(restConferences, i);
				max=Math.max(max, recursiveProcessFunc(nexts, done_num+1, restConferences[i].end));	
			}	
		}
		return max;  
	}
	
	//3）deleteIndexConference（） 功能函数，剔除一个会议
	public static Conference[] deleteIndexConference(Conference[] conferences,int index) {
		int j=0;
		Conference[] tmp=new Conference[conferences.length-1];
		for(int i=0;i<conferences.length;i++) {
			if(i!=index) {
				tmp[j++]=conferences[i];
			}
		}	
		return tmp;
	}
	
	//2.最优先安排结束时间早的会议的贪心算法
	
	//1）定义比较器
	
	public static class MyComparator implements Comparator<Conference>{
		@Override
		public int compare(Conference c1,Conference c2) {
			return c1.end-c2.end;
		}
		
	}
	
	//2）实现最优先安排结束时间早的会议的贪心算法
	public static int conferenceArrange2(Conference[] conferences) {
		//按照自定义的比较器排序，结束时间早的会议排在前面
		Arrays.sort(conferences,new MyComparator());
		int count=0;
		int time_now=0;
		for(int i=0;i<conferences.length;i++) {
			if(conferences[i].start>time_now) {
				count++;
				time_now=conferences[i].end;
			}
		}
		return count;
		
	}
	
	//3.编写对数器，用大量样本验证上述贪心算法的有效性
	
	//1）generateRandomConferenceArrays（）生成随机长度随机会议的会议列表
	public static Conference[] generateRandomConferenceArrays(int maxarrlen) {
		Conference[] tmp=new Conference[(int)(Math.random()*maxarrlen+1)];
		//[0,1) [0,maxarrlen),[1,maxarrlen]
		for(int i=0;i<tmp.length;i++) {
			int start=(int)(Math.random()*25);
			int end=(int)(Math.random()*25);
			if(start==end) {
				tmp[i]=new Conference(start, start+1);
			}
			else {
				tmp[i]=new Conference(Math.min(start,end),Math.max(start, end));
			}
		}
		return tmp;
	}
	
	
	
	public static void main(String[] args) {
		
		//1.产生一个用于测试Conferences数组test
		System.out.println("=================生成测试会议列表test=================");
		Conference[] test=new Conference[5];
		for(int i=0;i<test.length;i++) {
			int start=(int)(Math.random()*25); //[0,24]
			int end=(int)(Math.random()*25);
			//System.out.println("start="+start+",end="+end);
			if(start==end) {
				end=start+1;
				test[i]=new Conference(start, end);
				//System.out.println("相同随机值处理！");
			}
			else {
				//System.out.println("不相同随机值处理！");
				
				//逻辑错误:
				//start=Math.min(start, end);
				//end=Math.max(start, end);
				//千万注意，上面这样赋值会导致逻辑错误start已经被覆盖了
				//导致如果一大，一小的话 15,12，
				//执行start=Math.min(start, end);之后 start=12,
				//end=Math.max(start, end);等价于end=Math.max(12, 12);
				//两句之后 start=end=12
				test[i]=new Conference(Math.min(start, end), Math.max(start, end));
			}
			//error:
			//test[i].start=Math.min(start, end);//不能这样赋值 Cannot assign field "start" because "test[i]" is null
			//test[i].end=Math.max(start, end);//不能这样赋值 Cannot assign field "start" because "test[i]" is null
		}
		System.out.println("生成的会议列表如下:");
		for(Conference c:test) {
			System.out.println("start:"+ c.start+"  end:"+c.end);
		}//简洁的遍历方法
//		for(int i=0;i<test.length;i++) {
//			System.out.println("Start:"+test[i].start+",end:"+test[i].end);
//		}
		
		//2.测试剔除会议的方法
		System.out.println("=================测试剔除会议的方法=================");
		
		Conference[] res=deleteIndexConference(test, 0);
		for(Conference c :res) {
			System.out.println("start:"+ c.start+"  end:"+c.end);
		}
		
		//3.测试暴力尝试所有可能结果的方法
		System.out.println("=================测试暴力尝试所有可能结果的方法=================");
		System.out.println("会议列表:");
		for(Conference c:test) {
			System.out.println("start:"+ c.start+"  end:"+c.end);
		}
		System.out.println("能安排的最多会议的数量:"+conferenceArrange1(test));
		
		//4.最优先安排结束时间早的会议的贪心算法
		System.out.println("=================测试最优先安排结束时间早的会议的贪心算法=================");
		System.out.println("会议列表:");
		for(Conference c:test) {
			System.out.println("start:"+ c.start+"  end:"+c.end);
		}
		System.out.println("能安排的最多会议的数量:"+conferenceArrange2(test));
		
		//5.测试生成随机长度随机会议的会议列表的方法
		System.out.println("=================测试生成随机长度随机会议的会议列表的方法=================");
		Conference[] t1=generateRandomConferenceArrays(10);
		for(Conference c:t1) {
			System.out.println("start:"+ c.start+"  end:"+c.end);
		}
		
		//6.使用对数器思想测试贪心算法的正确性
		System.out.println("=================使用对数器校验贪心算法=================");
		int testtime=1000;
		int maxarrlen=20;
		for(int i=0;i<testtime;i++) {
			Conference[] testarr=generateRandomConferenceArrays(maxarrlen);
			if(conferenceArrange1(testarr)!=conferenceArrange2(testarr)) {
				System.out.println("Something Wrong!");
				break;
			}
		}
		System.out.println("Good Job!");
	}
}
