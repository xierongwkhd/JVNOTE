---
title: Java理解实现二叉排序树
date: 2018-09-26 14:14:10
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/82852647](https://blog.csdn.net/MOKEXFDGH/article/details/82852647)   
    
  ## []()Java实现二叉排序树

 要求：

  
  2. 实现二叉排序树，包括生成、插入，删除，深度 
  4. 对二叉排序树进行先根、中根、和后根遍历 
  6. 每次对树的修改操作和遍历操作的显示结果都需要在屏幕上用树的形状表示出来  结点类：

 
```
public class Node {
	public String data = "";//结点数据
	public Node lChild = null;
	public Node rChild = null;
	
	Node(){}
	
	Node(String data){
		this.data = data;
	}
}

```
 二叉排序树及功能类：

 
```
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class BinarySortTree {
	private static LinkedList<String> charList= new LinkedList<String>();;// 存储字符串数据
	private LinkedList<Node> NodeList=new LinkedList<Node>();;// 存储节点的队列
	private static Node root;
	private Node rnode,pnode=null;//存储结点和父节点
	private int length = 0;//存储二叉排序树的深度

	/*
	*创建二叉排序树
	*/
	public void creatBSTress() throws IOException {
        //将元素存进List中
        getString();
        // 2.根据第一步的结构，创建节点
        creatNodes();
        root = creatTree();
        //System.out.println(root.data);
		//50System.out.println(root.lChild.rChild.data);
		//System.out.println(root.lChild.rChild.data);
        System.out.print("前序遍历：");
		preordertraversal(root);
		System.out.println();
		
		System.out.print("中序遍历：");
		inordertraversal(root);
		System.out.println();
		
		System.out.print("后序遍历：");
		postordertraversal(root);
		System.out.println();
		
		//求二叉排序树的深度
		length = depthBST(root);
		System.out.println("该树的深度为"+length);
		
		//打印二叉排序树
		printBST(root,length);
		
		System.out.print("请输入要查询的字符:");
		Scanner sr = new Scanner(System.in);
		String key = sr.next();
		searchBST(root,key);
		rnode = null;
		
		System.out.print("请输入要插入的字符:");
		Scanner sr2 = new Scanner(System.in);
		String key2 = sr2.next();
		Node newnode = new Node(key2);
		InsertBST(root,newnode);
		printBST(root,length);
		
		System.out.print("请输入要删除的字符:");
		Scanner sr3 = new Scanner(System.in);
		String key3 = sr2.next();
		delete(root,key3);
		printBST(root,length);
	}

	//横向打印二叉树
	private void printBST(Node tree,int len) {
		if(tree.rChild!=null)
			printBST(tree.rChild,len+1);
		for(int i=0;i<len;i++)
			System.out.print("      ");
		System.out.println(tree.data);
		if(tree.lChild!=null)
			printBST(tree.lChild,len+1);
	}

	private int depthBST(Node tree) {
		// TODO Auto-generated method stub
		if(tree==null) {
			return 0;
		}else {
			int lef=depthBST(tree.lChild);
			int rig=depthBST(tree.rChild);
			return Math.max(lef, rig)+1;
		}
		
	}

	//前序遍历
	public void preordertraversal(Node tree) {
		System.out.print(tree.data+" ");
		if(tree.lChild!=null)
			preordertraversal(tree.lChild);
		if(tree.rChild!=null)
			preordertraversal(tree.rChild);
	}
	
	//中序遍历
	public void inordertraversal(Node tree) {
		if(tree.lChild!=null)
			inordertraversal(tree.lChild);
		System.out.print(tree.data+" ");
		if(tree.rChild!=null)
			inordertraversal(tree.rChild);
	}
	
	//后序遍历
	public void postordertraversal(Node tree) {
		if(tree.lChild!=null)
			postordertraversal(tree.lChild);
		if(tree.rChild!=null)
			postordertraversal(tree.rChild);
		System.out.print(tree.data+" ");
	}
	
	//连接结点建立二叉树
	private	Node creatTree() {
		// TODO Auto-generated method stub
		root = NodeList.poll();
		Node temp = root;
		
		while(!NodeList.isEmpty()) {
			Node node =NodeList.poll();
				while(true) {
					if(node.data.compareTo(temp.data)<0) {
						if(temp.lChild==null) {
							temp.lChild  = node;
							break;
						}else {
							temp = temp.lChild;
							continue;
						}
					}else {
						if(temp.rChild==null) {
							temp.rChild = node;
							break;
						}else {
							temp = temp.rChild;
							continue;
						}
					}
				}	
		temp = root;
		}
		return root;
	}
	
	//创建结点队列
	private void creatNodes() {
		//Collections.sort(charList);
		for(int i=0;i<charList.size();i++) {
            String data = charList.get(i);
            Node node = new Node(data);
            NodeList.add(node);
		}
		//System.out.println(NodeList.toString());
	}

	//存储获取的元素
	private void getString() throws IOException {
		System.out.println("请依此输入用于创建二叉树的字符元素并回车，用over结束输入");
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));		  
		String line = null;
		while((line=bufr.readLine()) != null) {
			if("over".equals(line))//区别于==
					break;
			charList.add(line);
		}
		//System.out.print(charList);
		//bufr.close();
	}
	
	public static void main(String[] args) throws IOException {
		BinarySortTree test = new BinarySortTree();
		test.creatBSTress();
		//System.out.println(charList);
	}
	
	//查询
	private Node searchBST(Node tree,String key) {
		if(tree.data==null) {
			System.out.println("不存在");
			return null;
		}else {
			if(key.equals(tree.data)) {
				System.out.println("存在");
				return tree;
			}else
				if(key.compareTo(tree.data)>0) 
					if(tree.rChild==null) {
						System.out.println("不存在");
						return null;
					}else {
					return searchBST(tree.rChild,key);
					}else
					if(tree.lChild==null) {
						System.out.println("不存在");
						return null;
					}else
						return searchBST(tree.lChild,key);
		}
	} 
	
	//插入
	private void InsertBST(Node tree,Node key) {
		
		if(tree.data.equals(key.data))
			System.out.println("树中已存在该字符");
		if(key.data.compareTo(tree.data)>0)
			if(tree.rChild==null)
				tree.rChild=key;
			else
				InsertBST(tree.rChild,key);
			
		else
			if(tree.lChild==null)
				tree.lChild = key;
			else
				InsertBST(tree.lChild,key);
		
	}
	
	//删除
	public void delete(Node tree,String key) {
	rnode = searchBST(tree,key);
	parenter(tree,rnode);
	if(rnode.lChild==null&&rnode.rChild==null) 
	{
		if(pnode!=null) {
		if(pnode.lChild==rnode)
			pnode.lChild=null;
		if(pnode.rChild==rnode)
			pnode.rChild=null;
		}else
			tree = rnode;
	}
	else if(rnode.lChild==null) 
	{
		if(pnode!=null) {
			if(pnode.lChild==rnode)
				pnode.lChild=rnode.rChild;
			if(pnode.rChild==rnode)
				pnode.rChild=rnode.rChild;	
		}else
			tree = rnode;
	}
	else if(rnode.rChild==null)
	{
		if(pnode!=null) {
			if(pnode.lChild==rnode)
				pnode.lChild=rnode.lChild;
			if(pnode.rChild==rnode)
				pnode.rChild=rnode.lChild;
		}else
			tree = rnode;
	}
	else 
	{
		if(pnode.lChild==rnode) {
			pnode.lChild=rnode.lChild;
			while(rnode.lChild.rChild!=null) {
				rnode.lChild= rnode.lChild.rChild;
			}
			rnode.lChild.rChild=rnode.rChild;
		}
		if(pnode.rChild==rnode)
			pnode.rChild=rnode.rChild;
			while(rnode.rChild.lChild!=null) {
				rnode.rChild=rnode.rChild.lChild;
			}
			rnode.rChild.lChild=rnode.lChild;
	}
}
	
	//获取父节点
	private void parenter(Node tree,Node rnode) {
		if(tree.lChild==rnode||tree.rChild==rnode)
			pnode = tree;
		else {
				if(tree.lChild!=null)
					parenter(tree.lChild,rnode);
				if(tree.rChild!=null)
					parenter(tree.rChild,rnode);
		}
	}
}

```
 ![在这里插入图片描述](https://img-blog.csdn.net/20180926140728211?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)  
 ![在这里插入图片描述](https://img-blog.csdn.net/20180926140834662?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

   
  