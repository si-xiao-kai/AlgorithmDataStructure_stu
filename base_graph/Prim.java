package base_graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

	//写比较器用于在小根堆中比较Edge
	public static class MyComparator implements Comparator<Edge>{
		@Override
		public int compare(Edge e1,Edge e2) {
			return e1.weight-e2.weight;
		}
		
	}
	
	
	public static List<Edge> prim(Graph g) {
		HashSet<Node> nodeset=new HashSet<>();//保存解锁的节点
		HashSet<Edge> edgeset=new HashSet<>();//注册被解锁的边，防止重复添加解锁的边
		PriorityQueue<Edge> unlockededges=new PriorityQueue<>(new MyComparator());//小根堆保存被解锁的边，并从小到达排序
		List<Edge> result=new ArrayList<>();//保存被选取的边集合
		
		for(Node i:g.nodes.values()) {//遍历图g中的每个节点
			//外层for循环的作用是防止g是一个森林，而不是一个单一的联通图。如果g是一个不连通的图，
			//那么可以在每个图中分别找到生成树
			if(!nodeset.contains(i)) {//如果节点在nodeset中没出现表示没有被解锁，需要处理以保证联通性
				nodeset.add(i);//解锁i
				for(Edge e:i.edges) {//解锁i的邻边
					unlockededges.add(e);//放入小根堆中
					edgeset.add(e);//在edgeset中注册，如果后面向小根堆中添加的边在集合中注册过，不用重复添加。
				}
				while(!unlockededges.isEmpty()) {//考虑小根堆中的边
					Edge curEdge = unlockededges.poll();//弹出最小权值的边
					if(!nodeset.contains(curEdge.to)) {//如果to节点没有被解锁（也就是没有被联通），就解锁到nodeset中
						nodeset.add(curEdge.to);
						result.add(curEdge);//添加该边到结果中
						for(Edge e: curEdge.to.edges) {//解锁to节点的邻边
							if(!edgeset.contains(e)) {//如果edgeset里面有的话就不需要重复添加了，只添加哪些没添加过的边
								edgeset.add(e);//注册
								unlockededges.add(e);//解锁到小根堆
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	
	
	
	public static void main(String[] args) {
		// 定义无向图需要指明两个方向的路径
		int[][] test= {
				{1,1,3},
				{1,3,1},
				{10,1,2},
				{10,2,1},
				{5,1,4},
				{5,4,1},
				{12,3,2},
				{12,2,3},
				{11,3,4},
				{11,4,3},
				{5,5,7},
				{5,7,5},
				{6,5,6},
				{6,6,5},
				{10,5,8},
				{10,8,5},
				{1,6,7},
				{1,7,6},
				{4,6,8},
				{4,8,6},
				{6,8,7},
				{6,7,8}
		};
		//非联通图
		Graph graph= GenerateGraphFromMatrix.createGraphFromMatrix(test);
		List<Edge> result=prim(graph);
		for(Edge e:result) {
			System.out.println("from:"+e.from.value+",to:"+e.to.value+",weight:"+e.weight);
		}
		
		
	}

}
