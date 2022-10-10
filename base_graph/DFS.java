package base_graph;

import java.util.HashSet;
import java.util.Stack;

public class DFS {

	public static void dfs(Node start) {
		if(start==null) {
			return;
		}
		
		Stack<Node> stack=new Stack<>();//准备一个栈，记录深度优先遍历的路径
		HashSet<Node> set=new HashSet<>();//准备一个集合用于注册节点
		
		stack.add(start);
		set.add(start);
		System.out.println(start.value);
		
		while(!stack.isEmpty()) {//栈非空
			Node cur= stack.pop();
			for(Node i:cur.nexts) {
				if(!set.contains(i)) {//如果没有注册过，就把父节点压栈，当前节点压栈，注册节点，打印
					stack.push(cur);
					stack.push(i);
					set.add(i);
					System.out.println(i.value);
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
		dfs(startNode);

	}

}
