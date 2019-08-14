---
title: Java理解实现哈夫曼树以其编码解码
date: 2018-10-16 22:30:44
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/83098729](https://blog.csdn.net/MOKEXFDGH/article/details/83098729)   
    
  # []()哈夫曼树以其编码解码

 要求：  
 1.从终端读入字符集大小为n（即字符的个数），逐一输入n个字符和相应的n个权值（即字符出现的频度），建立哈夫曼树，进行编码并且输出。  
 将它存于文件hfmtree中（选做）。  
 2.利用已建好的哈夫曼编码文件hfmtree，对键盘输入的正文进行译码。输出字符正文，再输出该文的二进制码。  
 [测试数据]  
 用下表中给出的字符集(n=27)和频度的实际统计数据建立哈夫曼树：  
 ![在这里插入图片描述](https://img-blog.csdn.net/2018101622191312?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)  
 并实现以下报文的译码和输出：“THIS PROGRAM IS MY FAVORITE”。

 结点代码：

 
```
public class HNode {

    public String code = "";// 节点的哈夫曼编码
    public String data = "";// 节点的数据
    public int count;// 节点的权值
    public HNode lChild;
    public HNode rChild;

    public HNode() {
    }

    public HNode(String data, int count) {
        this.data = data;
        this.count = count;
    }

    public HNode(int count, HNode lChild, HNode rChild) {
        this.count = count;
        this.lChild = lChild;
        this.rChild = rChild;
    }

    public HNode(String data, int count, HNode lChild, HNode rChild) {
        this.data = data;
        this.count = count;
        this.lChild = lChild;
        this.rChild = rChild;
    }
}

```
 哈夫曼树及编码解码

 
```
public class Huffman {
    private String str;// 字符串    
    private HNode root;// 哈夫曼二叉树的根节点
    private boolean flag;// 最新的字符是否已经存在的标签
    private LinkedList<CharData> charList;// 存储不同字符的队列 相同字符存在同一位置
    private LinkedList<HNode> NodeList;// 存储节点的队列

     private class CharData {
         int num = 1; // 字符个数
         char c; // 字符

         public CharData(char ch){
             c = ch;
         }
     }

    /**
     * 构建哈夫曼树
     */
    public void creatHfmTree(String str) {
        this.str = str;

        NodeList = new LinkedList<HNode>();
        charList = new LinkedList<CharData>();

        // 1.统计字符串中字符以及字符的出现次数
        // 以CharData类来统计出现的字符和个数
        getCharNum(str);

        // 2.根据第一步的结构，创建节点
        creatNodes();

        // 3.对节点权值升序排序
        Sort(NodeList);

        // 4.取出权值最小的两个节点，生成一个新的父节点
        // 5.删除权值最小的两个节点，将父节点存放到列表中
        creatTree();

        // 6.重复第四五步，就是那个while循环
        // 7.将最后的一个节点赋给根节点
        root = NodeList.get(0);
    }

    /**
     * 统计出现的字符及其频率
     */
    private void getCharNum(String str) {

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i); // 从给定的字符串中取出字符
            flag = true;

            for (int j = 0; j < charList.size(); j++) {
                CharData data = charList.get(j);

                if(ch == data.c){ 
                    // 字符对象链表中有相同字符则将个数加1
                    data.num++;
                    flag = false;
                    break;
                }
            }

            if(flag){
                // 字符对象链表中没有相同字符则创建新对象加如链表
                charList.add(new CharData(ch)); 
            }
        }
    }

    /**
     * 将出现的字符创建成单个的结点对象
     */
    private void creatNodes() {

        for (int i = 0; i < charList.size(); i++) {
            String data = charList.get(i).c + "";
            int count = charList.get(i).num;

            HNode node = new HNode(data, count); // 创建节点对象
            NodeList.add(node); // 加入到节点链表
        }

    }

    /**
     * 构建哈夫曼树
     */
    private void creatTree() {

        while (NodeList.size() > 1) {// 当节点数目大于一时
            // 4.取出权值最小的两个节点，生成一个新的父节点
            // 5.删除权值最小的两个节点，将父节点存放到列表中
            HNode left = NodeList.poll();
            HNode right = NodeList.poll();

            // 在构建哈夫曼树时设置各个结点的哈夫曼编码
            left.code = "0";
            right.code = "1";
            setCode(left);
            setCode(right);

            int parentWeight = left.count + right.count;// 父节点权值等于子节点权值之和
            HNode parent = new HNode(parentWeight, left, right);

            NodeList.addFirst(parent); // 将父节点置于首位
            Sort(NodeList); // 重新排序，避免新节点权值大于链表首个结点的权值
        }
    }

    /**
     * 升序排序
     */
    private void Sort(LinkedList<HNode> nodelist) {
        for (int i = 0; i < nodelist.size() - 1; i++) {
            for (int j = i + 1; j < nodelist.size(); j++) {
                HNode temp;
                if (nodelist.get(i).count > nodelist.get(j).count) {
                    temp = nodelist.get(i);
                    nodelist.set(i, nodelist.get(j));
                    nodelist.set(j, temp);
                }

            }
        }

    }

    /**
     * 设置结点的哈夫曼编码
     */
    private void setCode(HNode root) {

        if (root.lChild != null) {
            root.lChild.code = root.code + "0";
            setCode(root.lChild);
        }

        if (root.rChild != null) {
            root.rChild.code = root.code + "1";
            setCode(root.rChild);
        }
    }

    /**
     * 遍历
     */
    private void output(HNode node) {

        if (node.lChild == null && node.rChild == null) {
            System.out.println(node.data + ": " + node.code);
        }
        if (node.lChild != null) {
            output(node.lChild);
        }
        if (node.rChild != null) {
            output(node.rChild);
        }
    }

    /**
     * 输出结果字符的哈夫曼编码
     */
    public void output() {
        output(root);
    }

    private String hfmCodeStr = "";// 哈夫曼编码连接成的字符串

    /**
     * 编码
     */
    public String toHufmCode(String str) {

        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            search(root, c);
        }
        return hfmCodeStr;
    }

    private void search(HNode root, String c) {
        if (root.lChild == null && root.rChild == null) {
            if (c.equals(root.data)) {
                hfmCodeStr += root.code; // 找到字符，将其哈夫曼编码拼接到最终返回二进制字符串的后面
            }
        }
        if (root.lChild != null) {
            search(root.lChild, c);
        }
        if (root.rChild != null) {
            search(root.rChild, c);
        }
    }

    // 保存解码的字符串
    String result="";
    boolean target = false; // 解码标记
    /**
     * 解码
     * @param codeStr
     * @return
     */
    public String CodeToString(String codeStr) {

        int start = 0;
        int end = 1;

        while(end <= codeStr.length()){
            target = false;
            String s = codeStr.substring(start, end);
            matchCode(root, s); // 解码
            // 每解码一个字符，start向后移
            if(target){
                start = end;
            }
            end++;
        }
        return result;
    }
    
    private void matchCode(HNode root, String code){
        if (root.lChild == null && root.rChild == null) {
            if (code.equals(root.code)) {
                result += root.data; // 找到对应的字符，拼接到解码字符穿后
                target = true; // 标志置为true
            }
        }
        if (root.lChild != null) {
            matchCode(root.lChild, code);
        }
        if (root.rChild != null) {
            matchCode(root.rChild, code);
        }
    }
    
    public static void main(String[] args) {

        Huffman huff = new Huffman();// 创建哈弗曼对象
        String data = "THIS PROGRAM IS MY FAVORITE"; 
        huff.creatHfmTree(data);// 构造树
        huff.output(); // 显示字符的哈夫曼编码
        // 将目标字符串利用生成好的哈夫曼编码生成对应的二进制编码
        String hufmCode = huff.toHufmCode(data); 
        System.out.println("编码:" + hufmCode);
        // 将上述二进制编码再翻译成字符串
        System.out.println("解码：" + huff.CodeToString(hufmCode));            
    }
}

```
 效果如下：  
 ![在这里插入图片描述](https://img-blog.csdn.net/2018101622264640?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

   
  