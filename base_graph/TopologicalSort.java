package base_graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {

	public static List<Node> topologicalSort(Graph g) {
		//g必须是有向无环图
		List<Node> result=new ArrayList<>();//存储结果
		HashMap<Node,Integer> inMap=new HashMap<>();//存储节点及其度的个数
		Queue<Node> queue=new LinkedList<>();//队列，用于处理数据
		
		//利用g初始化 inMap
		for(Node i:g.nodes.values()) {//values()取到g的所有节点
			inMap.put(i,i.in);
			if(i.in==0) {
				queue.add(i);//度为0的节点直接入队
			}
		}
		
		//如果队列非空开始处理队列
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			result.add(cur);//度为0的节点先处理，放进result中
			//将cur节点的直接邻居的入度减1
			for(Node i:cur.nexts) {
//				i.in--;
//				if(i.in==0) {//如果减完1之后入度为0，则入队
//					queue.add(i);
//				}
				//上面这么干的话用户传入的图就给破坏掉了。
				//正确的做法
				inMap.put(i, inMap.get(i)-1);//在入度表里面更新入度
				if(inMap.get(i)==0) {
					queue.add(i);
				}
				
			}
		}		
		
		return result;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] test= {
				{0,1,2},
				{0,2,3},
				{0,3,4},
				{0,5,3},
		};
		
		//将矩阵转化为图
		Graph graph=GenerateGraphFromMatrix.createGraphFromMatrix(test);
		List<Node> res= topologicalSort(graph);
		for(Node i:res) {
			System.out.print(i.value+",");
		}
		System.out.println();		
	}
}
