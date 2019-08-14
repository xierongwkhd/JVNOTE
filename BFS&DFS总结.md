---
title: BFS&DFS总结
date: 2019-05-16 17:15:26
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/90267616](https://blog.csdn.net/MOKEXFDGH/article/details/90267616)   
    
  ### 文章目录


    * [BFS、DFS 原理](#BFSDFS__5)
    * [BFS 和 DFS 的实现](#BFS__DFS__34)
      * [BFS](#BFS_81)
      * [DFS](#DFS_109)
    * [BFS 和最短路径问题](#BFS__137)  


 _刷题经常会碰到，在这里总结一下_

 
--------
 
## []()BFS、DFS 原理

 **BFS：** 广度优先搜索  
 **DFS：** 深度优先搜索  
 树的 BFS 相当于层次遍历这里主要说一下图的遍历，如下图：  
 ![1](https://img-blog.csdnimg.cn/20190516142115913.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 从 A 开始进行 **BFS** 的过程：

  
  2. 首先和 A 相连的有 B、C，遍历序列为：A B C 
  4. 然后再依次判断下一个点如 B、C ，相连的且不重复的点，遍历序列为：A B C D E 
  6. 重复 2 操作，BFS 序列为：A B C D E F  
      同样如果从 E 开始，则序列为：E C D A B F 或者 E D C B F A（不唯一）  BFS 的过程可用**队列**来描述：

  
  2. 刚开始 A 入队列，然后 A 出队，与 A 相连的 B、C 入队 
  4. 然后 B 出队，与 B 相连且队中没有的入队，即 D 
  6. 重复过程 2 直到队列为空，出队的顺序就是 BFS 结果  
--------
 从 A 开始进行 **DFS** 的过程：

  
  2. 首先 从 A 开始走，其中一种情况：A->B->D->F 
  4. 到 F 后没路走了，就会从上一个点重新走，所以回到 D->E->C 
  6. 此时已经遍历完，所以 DFS 序列为：A B D F E C  DFS 的过程可用**栈**来描述：

  
  2. A 入栈，A 出栈， 然后与 A 相连的点入栈 C、B 
  4. 栈顶 B 出栈，与栈顶 B 相连的点入栈 D 
  6. 重复过程 2 直到栈为空，出栈顺序就是 DFS 结果  
--------
 
## []()BFS 和 DFS 的实现

 首先我们构建上面的图：

 
```
		public class BFSDemo {
		    public static void main(String[] args) {
		        //构造各顶点
		        LinkedList<Character> list_A = new LinkedList<Character>();
		        list_s.add('B');
		        list_s.add('C');
		        LinkedList<Character> list_B = new LinkedList<Character>();
		        list_w.add('A');
		        list_w.add('C');
		        list_w.add('D');
		        LinkedList<Character> list_C = new LinkedList<Character>();
		        list_r.add('A');
		        list_r.add('B');
		        list_r.add('D');
		        list_r.add('E');
		        LinkedList<Character> list_D = new LinkedList<Character>();
		        list_x.add('B');
		        list_x.add('C');
		        list_x.add('E');
		        list_x.add('F');
		        LinkedList<Character> list_E = new LinkedList<Character>();
		        list_v.add('C');
		        list_r.add('D');
		        LinkedList<Character> list_F = new LinkedList<Character>();
		        list_i.add('D');
		        
		        //构造图
		        HashMap<Character, LinkedList<Character>> graph = new HashMap<Character, LinkedList<Character>>();
		        graph.put('A', list_A);
		        graph.put('B', list_B);
		        graph.put('C', list_C);
		        graph.put('D', list_D);
		        graph.put('E', list_E);
		        graph.put('F', list_F);
		        //记录每个顶点离起始点的距离，也即最短距离
		        HashMap<Character, Integer> dist = new HashMap<Character, Integer>();
		        //遍历的起始点
		        char start = 'A';
		        //调用广度优先方法
		        bfs(graph, dist, start);
		        dfs(graph,dist,start);
		    }

```
 
--------
 
### []()BFS

 
```
		    public static void bfs(HashMap<Character, LinkedList<Character>> graph, HashMap<Character, Integer> dist,
		            char start) {
		        Queue<Character> q = new LinkedList<>();
		        q.add(start);// 将s作为起始顶点加入队列
		        dist.put(start, 0);
		        int i = 0;
		        while (!q.isEmpty()) {
		            char top = q.poll();// 取出队首元素
		            i++;
		            System.out.println("The " + i + "th element:" + top + " Distance from A is:" + dist.get(top));
		            int d = dist.get(top) + 1;// 得出其周边还未被访问的节点的距离
		            for (Character c : graph.get(top)) {
		                if (!dist.containsKey(c))// 如果dist中还没有该元素说明还没有被访问
		                {
		                    dist.put(c, d);
		                    q.add(c);
		                }
		            }
		        }
		    }
		}

```
 结果如下：  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190516151849722.png)

 
--------
 
### []()DFS

 
```
		private static void dfs(HashMap<Character, LinkedList<Character>> graph, HashMap<Character, Integer> dist,
		            char start) {
		        Stack<Character> stack = new Stack<>();
		        stack.push(start);
		        dist.put(start, 0);
		        int i = 0;
		        while (!stack.isEmpty()) {
		            char top = stack.pop();// 取出栈顶元素
		            i++;
		            System.out.println("The " + i + "th element:" + top + " Distance from A is:" + dist.get(top));
		            int d = dist.get(top) + 1;
		            for (Character c : graph.get(top)) {
		                if (!dist.containsKey(c))
		                {
		                    dist.put(c, d);
		                    q.push(c);
		                }
		            }
		        }
		    }
		}

```
 结果如下  
 ![3](https://img-blog.csdnimg.cn/20190516151818848.png)

 
--------
 
## []()BFS 和最短路径问题

 **1. 边不带权或权值都相同**  
 定义一个map用来存放当前节点的parent节点

 
```
		HashMap<Character, Character> parent = new HashMap<Character, Character>();
		Character v = 'F';
		while(v!=null){//从 F 到 A 的最短路径       
		    System.out.println(v);
		    v = parent.get(v);    
		}        
		//bfs修改如下
		if (!dist.containsKey(c))
		{
			dist.put(c, d);
			parent.put(c,top);
		    q.push(c);
		}

```
 结果如下：  
 ![4](https://img-blog.csdnimg.cn/20190516153044225.png)  
 **2.边带不同权值的最短路径**  
 使用**优先级队列+ BFS** 来实现 **Dijkstra 算法**

 
```
		  public static void main(String[] args) {                                          
		      //构造各顶点                                                                       
		      LinkedList<Node> list_A = new LinkedList<Node >();                            
		      list_A.add(new Node('B',5));                                                  
		      list_A.add(new Node('C',1));                                                  
		      LinkedList<Node> list_B = new LinkedList<Node>();                             
		      list_B.add(new Node('A',5));                                                  
		      list_B.add(new Node('C',2));                                                  
		      list_B.add(new Node('D',1));                                                  
		      LinkedList<Node> list_C = new LinkedList<Node>();                             
		      list_C.add(new Node('A',1));                                                  
		      list_C.add(new Node('B',2));                                                  
		      list_C.add(new Node('D',4));                                                  
		      list_C.add(new Node('E',8));                                                  
		      LinkedList<Node> list_D = new LinkedList<Node>();                             
		      list_D.add(new Node('B',1));                                                  
		      list_D.add(new Node('C',4));                                                  
		      list_D.add(new Node('E',3));                                                  
		      list_D.add(new Node('F',6));                                                  
		      LinkedList<Node> list_E = new LinkedList<Node>();                             
		      list_E.add(new Node('C',8));                                                  
		      list_E.add(new Node('D',3));                                                  
		      LinkedList<Node> list_F = new LinkedList<Node>();                             
		      list_F.add(new Node('D',6));                                                  
		                                                                                    
		      //构造图                                                                         
		      HashMap<Character, LinkedList<Node>> graph = new HashMap<Character, LinkedList
		      graph.put('A', list_A);                                                       
		      graph.put('B', list_B);                                                       
		      graph.put('C', list_C);                                                       
		      graph.put('D', list_D);                                                       
		      graph.put('E', list_E);                                                       
		      graph.put('F', list_F);                                                       
		      //记录每个顶点离起始点的距离，也即最短距离                                                        
		      HashMap<Character, Integer> dist = new HashMap<Character, Integer>();         
		      HashMap<Character, Character> parent = new HashMap<Character, Character>();   
		      ArrayList<Character> seen = new ArrayList<>();                                
		      
		      //初始化                                                                              
		      dist.put('A',0);                                                              
		      dist.put('B',Integer.MAX_VALUE);                                              
		      dist.put('C',Integer.MAX_VALUE);                                              
		      dist.put('D',Integer.MAX_VALUE);                                              
		      dist.put('E',Integer.MAX_VALUE);                                              
		      dist.put('F',Integer.MAX_VALUE);                                              
		                                                                                    
		      //遍历的起始点                                                                                                                               
		      Node start = new Node('A',0);                                                 
		      //调用广度优先方法                                                                    
		      bfs(graph,dist,seen,parent,start);                                                                                            
		      System.out.println(parent.toString());                                        
		      System.out.println(dist.toString());                                          
		                                                                                    
		  }
		  public static void bfs(HashMap<Character,LinkedList<Node>> graph,HashMap<Character, Integer> dist,ArrayList<Character> seen,HashMap<Character, Character> parent,Node start) {         
			    PriorityQueue<Node> q = new PriorityQueue<>(                                                            
			        new Comparator<Node>() {                                                                            
			            public int compare(Node o1, Node o2){                                                           
			                return o1.getNum()-o2.getNum();                                                             
			            }                                                                                               
			        });                                                                                                 
			    q.add(start);                                                                            
			    while (!q.isEmpty()) {                                                                                  
			        Node top = q.poll();                                                                                
			        char cr = top.getC();                                                                               
			        int dis = top.getNum();                                                                             
			        seen.add(cr);                                                                                       
			        for (Node c : graph.get(cr)) {                                                                      
			            if (!seen.contains(c.getC()))                                          
			            {                                                                                               
			                if(dis+c.getNum()<dist.get(c.getC())){                                                      
			                    q.add(c);                                                                               
			                    parent.put(c.getC(),cr);                                                                
			                    dist.put(c.getC(),dis+c.getNum());                                                      
			                    c.setNum(dis+c.getNum());//更新Node对象中的距离                                                               
			                }                                                                                           
			            }                                                                                               
			        }                                                                                                   
			    }                                                                                                       
			}                                                                                                                                                                                            

```
 结果如下：  
 ![5](https://img-blog.csdnimg.cn/20190516171304386.png)

   
  