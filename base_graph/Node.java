package base_graph;

import java.util.ArrayList;

public class Node {
	public int value;//节点值
	public int in;//入度
	public int out;//出度
	
	public ArrayList<Node> nexts;//下行的邻居节点
	public ArrayList<Edge> edges;//下行边
	
	public Node(int value) {
		this.value=value;
		this.in=0;
		this.out=0;
		this.nexts=new ArrayList<>();
		this.edges=new ArrayList<>();
	}
}
