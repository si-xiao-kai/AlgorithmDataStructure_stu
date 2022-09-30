package base_union_find_set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


public class MyUnionFindSet {

	// 1. 将V 类型的数据包裹一层，打包成一个对象
	public static class Node<V>{
		V value;
		public Node(V v) {
			value=v;
		}	
	}
	
	// 2. 定义并查集类：用户可以给处一系列数据（引用类型），然后用我们的并查集去注册这些数据。在并查集中完成查询、归并集合的操作。
	public static class UnionFindSet<V>{
		//定义三张表
		HashMap<V, Node<V>> nodes; //登记节点
		HashMap<Node<V>, Node<V>> parents;//记录父节点
		HashMap<Node<V>, Integer> sizeMap;//记录每个集合（由代表点表示）的大小
		
		//构造函数
		public UnionFindSet(List<V> list) {
			//初始化三张表
			nodes=new HashMap<>();
			parents=new HashMap<>();
			sizeMap=new HashMap<>();
			
			for(V i: list) {
				//对V变量进行包装
				Node<V> node=new Node<V>(i);
				//登记节点
				nodes.put(i,node);
				//标记父节点就是自己
				parents.put(node, node);
				//初始化sizemap,每个集合都是一个元素
				sizeMap.put(node, 1);
			}
		}
		
		//提供找代表方法，从一个节点出发，向上跳直到跳不动为止
		public  Node<V> findRepresentative(Node<V> v){
			
			Stack<Node<V>> path=new Stack<>();
			while(v!=parents.get(v)) {
				path.push(v);
				v=parents.get(v);
			}
			
			//优化：把查询父节点的链打扁平，既然找到了集合的代表点是v，那么直接将所有的代表点下挂的节点都（直接）挂在代表点下。
			while(!path.isEmpty()) {
				parents.put(path.pop(), v);
			}
			
			return v;
		}
		
		//提供isSameSet方法，判断两个V 变量是否在一个集合中
		public boolean isSameSet(V a,V b) {
			//判断要找的节点v有没有登记过，没登记过不找,直接返回false
			if(!nodes.containsKey(a)||!nodes.containsKey(b)) {
				return false;
			}
			//如果两个代表点不同则a，b不在一个集合
			return findRepresentative(nodes.get(a))==findRepresentative(nodes.get(b));
		}
		
		//提供union方法，合并 a，b所在的两个集合
		public void union(V a,V b) {
			//判断要找的节点v有没有登记过，没登记过不做合并操作
			if(!nodes.containsKey(a)||!nodes.containsKey(b)) {
				return;
			}
			//找到两个节点的代表点
			Node<V> aHeadNode = findRepresentative(nodes.get(a));
			Node<V> bHeadNode = findRepresentative(nodes.get(b));
			
			//那个集合小，小挂大
			int aSize=sizeMap.get(aHeadNode);
			int bSize=sizeMap.get(bHeadNode);
			
			Node<V> big = aSize > bSize?aHeadNode:bHeadNode;
			Node<V> small = big==aHeadNode?bHeadNode: aHeadNode;
			
			//小集合的头节点直接挂在大集合的头上，更新sizemap
			parents.put(small, big);
			sizeMap.put(big, aSize+bSize);
			sizeMap.remove(small);

		}
		
		
		//提供返回集合个数的方法
		public int numofSets() {
			return sizeMap.size();
		}
	}
	
	
	
	public static void main(String[] args) {
		//测试并查集
		List<Integer> testList=new ArrayList<>();
		testList.add(10);
		testList.add(13);
		testList.add(9);
		testList.add(100);
		testList.add(25);
		
		System.out.println("==========测试数据(V=Integer)==========");
		for(Integer i: testList) {
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println("==========测试并查集(V=Integer)==========");
		System.out.println();
		
		UnionFindSet<Integer> myfindset=new UnionFindSet<>(testList);
		System.out.println("==========初始集合个数==========");
		
		System.out.println(myfindset.numofSets());
		System.out.println("==========13和9 在一个集合吗？==========");
		if(myfindset.isSameSet(testList.get(1), testList.get(2))) {
			System.out.println("在一个集合");
		}
		else {
			System.out.println("不在一个集合");
		}
		System.out.println("========== 合并 13和9 ==========");
		myfindset.union(testList.get(1), testList.get(2));
		System.out.println("==========13和9 在一个集合吗？==========");
		if(myfindset.isSameSet(testList.get(1), testList.get(2))) {
			System.out.println("在一个集合");
		}
		else {
			System.out.println("不在一个集合");
		}
		System.out.println("==========当前集合个数==========");
		System.out.println(myfindset.numofSets());
		
	}

}
