package base_graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;


public class Kruskal {

	//1.实现并查集作为实现Kruskal的工具
	public static class UnionSet{
		HashMap<Node, Node> fatherMap;//fatherMap表示节点的父子关系
		HashMap<Node, Integer> sizeMap;//集合大小，集合由代表点表示
		
		public UnionSet(List<Node> nodeList) {//根据传入的node节点列表构建并查集
			fatherMap = new HashMap<>();
			sizeMap = new HashMap<>();
			for(Node iNode : nodeList) {
				fatherMap.put(iNode, iNode);
				sizeMap.put(iNode, 1);
			}
		}
		
		public Node findRepresentative(Node a) {//在一个集合中找到一个点的代表点
			if(!fatherMap.containsKey(a)) {
				System.out.println("节点不存在！没法找！");
			}
			Stack<Node> path = new Stack<>();//用于扁平优化
			while(a!=fatherMap.get(a)) {
				path.push(a);
				a=fatherMap.get(a);
			}
			
			while(!path.isEmpty()) {
				fatherMap.put(path.pop(), a);
			}
			
			return a;
		}
		
		public boolean isSameSet(Node a,Node b) {
			return findRepresentative(a)==findRepresentative(b);
		}
		
		public void union(Node a,Node b) {
			if(isSameSet(a, b)) {//本身就在一个集合就不合并
				return;
			}
			Node aHeadNode=findRepresentative(a);
			Node bHeadNode=findRepresentative(b);
			int asize=sizeMap.get(aHeadNode);//a集合的大小
			int bsize=sizeMap.get(bHeadNode);//b集合的大小
			
			if(asize>=bsize) {
				fatherMap.put(bHeadNode, aHeadNode);
				sizeMap.put(aHeadNode, asize+bsize);
				sizeMap.remove(bHeadNode);
			}
			else {
				fatherMap.put(aHeadNode, bHeadNode);
				sizeMap.put(bHeadNode, asize+bsize);
				sizeMap.remove(aHeadNode);
			}	
		}
	}
	
	//2.基于并查集实现最小生成树算法Kruskal
	//定义比较器
	public static class Mycomparator implements Comparator<Edge>{
		@Override
		public int compare(Edge e1,Edge e2) {
			return e1.weight-e2.weight;
		}
	}
	
	
	public static Integer minCostValueKruskal(Graph g) {
		if(g==null) {
			System.out.println("请指定一个有效的图！");
			return Integer.MAX_VALUE;
		}
		//用图g的节点初始化List
		List<Node> gNodes=new ArrayList<>();
		for(Node iNode : g.nodes.values()) {
			gNodes.add(iNode);
		}
		
		//初始化并查集
		UnionSet unionSet=new UnionSet(gNodes);
		
		//构建小根堆对边进行排序
		PriorityQueue<Edge> edges=new PriorityQueue<>(new Mycomparator());
		
		//初始化小根堆
		for(Edge e:g.edges) {
			edges.add(e);
		}
		
		//实现kruskal算法
		int result=0;
		while(!edges.isEmpty()) {
			Edge edge=edges.poll();//最小代价到大代价遍历边
			if(!unionSet.isSameSet(edge.from, edge.to)){//如果两个端点不在一个集合
				result+=edge.weight;//使用了这条边，并累加代价
				unionSet.union(edge.from, edge.to);//合并到一个集合中
			}
		}
		
		return result;
	}
	
	
	
	public static void main(String[] args) {
		//测试算法
		int[][] test= {
				{1,1,2},
				{2,2,3},
				{3,2,4},
				{50,3,4},
				{100,1,4},
		};	
		Graph graph= GenerateGraphFromMatrix.createGraphFromMatrix(test);
		System.out.println(minCostValueKruskal(graph));//1+2+3=6
	}
}
