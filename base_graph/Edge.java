package base_graph;

public class Edge {
	public int weight;//边权
	public Node from;//始边
	public Node to;//终边
	
	public Edge(int weight,Node from,Node to) {//根据提供的三元素构建一个边
		this.weight=weight;
		this.from=from;
		this.to=to;
	}
	
}
