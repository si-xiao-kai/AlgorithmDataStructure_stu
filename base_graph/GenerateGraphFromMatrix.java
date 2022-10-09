package base_graph;

public class GenerateGraphFromMatrix {
	//matrix 
	//weight from to
	/*
	 * [4,1,3]
	 * [5,2,6]
	 * [1,2,3]
	 */
	public static Graph createGraphFromMatrix(int[][] matrix) {
		Graph g=new Graph();
		for(int i=0;i<matrix.length;i++) {
			int weight=matrix[i][0];
			int from=matrix[i][1];
			int to=matrix[i][2];
			
			if(!g.nodes.containsKey(from)) {//如果没有节点编号就去创建节点
				g.nodes.put(from, new Node(from));
				
			}
			if(!g.nodes.containsKey(to)) {
				g.nodes.put(to, new Node(to));
			}
			
			Node fromNode = g.nodes.get(from);//获取from节点
			Node toNode=g.nodes.get(to);//获取to节点
			
			Edge edge=new Edge(weight, fromNode, toNode);//建立一条边
			g.edges.add(edge);//放到g的边集合中
			
			fromNode.nexts.add(toNode);//from节点添加下行节点
			fromNode.edges.add(edge);//from 节点添加下行边
			fromNode.out++;//from节点出度++
			toNode.in++;//to节点入度++
		}
		return g;	
		
	}
	
	public static void main(String[] args) {
		int[][] test = {
					{4,1,3},
					{5,2,6},
					{1,2,3}
				  };
		Graph graph = createGraphFromMatrix(test);
		System.out.println("节点数:"+graph.nodes.size());
		System.out.println("边数:"+graph.edges.size());
		System.out.println("1的入度:"+graph.nodes.get(1).in+",1的出度:"+graph.nodes.get(1).out);
		System.out.println("1到3的代价:"+graph.nodes.get(1).edges.get(0).weight);
		System.out.println("done!");
	}
}
