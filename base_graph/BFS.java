package base_graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	public static void bfs(Node start) {
		if(start==null) {
			return ;
		}
		Queue<Node> queue=new LinkedList<>();//准备一个队列
		HashSet<Node> set=new HashSet<>();//准备一个集合
		
		//起始节点注册、入队
		set.add(start);
		queue.add(start);
		
		while(!queue.isEmpty()) {//队列非空
			Node cur = queue.poll();//弹出一个节点，弹出就打印
			System.out.println(cur.value);
			
			for(Node i:cur.nexts) {//遍历弹出节点的所有下行节点
				if(!set.contains(i)) {//没有注册过的话就注册并入队
					queue.add(i);
					set.add(i);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[][] test= {
				{0,1,3},
				{0,3,4},
				{0,4,2},
				{0,2,5},
				{0,2,1}
		};
		Graph g=GenerateGraphFromMatrix.createGraphFromMatrix(test);
		//从1开始做宽度优先遍历
		
		Node startNode= g.nodes.get(1);
		bfs(startNode);
	}
}
