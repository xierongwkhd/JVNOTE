---
title: 数据结构(Java)：用单链表实现多项式相加
date: 2018-05-27 17:54:27
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/80471106](https://blog.csdn.net/MOKEXFDGH/article/details/80471106)   
    
  要求：   
 1.已知有两个多项式Pn(x)和Qm(x),并且在Pn(x)和Qm(x)中指数相差很多,设计算法，求Pn(x)+Qm(x)   
 2.进行加法运算时不重新开辟存储空间。

 
--------
 
```
//定义节点类
class Node{
    public int coef;//系数  
    public int exp;//指数  
    public Node next=null;//下个节点  
    public Node(){  
        coef=0;  
        exp=0;  
    }  
    public Node(int coef,int exp){  
        this.coef=coef;  
        this.exp=exp;  
    } 
}
```
 
```
//多项式类
public class PolynList {
     //多项式相加
     public Node add(Node link1, Node link2) {
            Node pre=link1;  
            Node qre=link2;  
            Node p=pre.next;  
            Node q=qre.next;  
            Node result=p;  
            while(p!=null && q!=null){  
                if(p.exp<q.exp){  
                    pre=p;  
                    p=p.next;  
                }else if(p.exp>q.exp){  
                    Node temp=q.next;  
                    pre.next=q;  
                    q.next=p;  
                    q=temp;  
                }else{  
                    p.coef=p.coef+q.coef;  
                    if(p.coef==0){  
                        pre.next=p.next;  
                        p=pre.next;  
                    }else{  
                        pre=p;  
                        p=p.next;  
                    }  
                    qre.next=q.next;  
                    q=qre.next;  
                }  
            }  
            if(q!=null){  
                pre.next=q;  
            }  
            return result;  
        }  

    //添加数据方法
    public Node insert(Node link,int coef,int exp) {//添加节点  
         Node node=new Node(coef,exp);  
         if(link==null){  
             link.next=node;  
         }else{  
             Node temp=link;  
             while(temp.next!=null){  
                 temp=temp.next;  
             }  
             temp.next=node;  
         }  
         return link;
        } 
    }
//主方法
public static void main(String[] args) { 
         PolynList ts = new PolynList();
         Node link1=new Node(); 
         Node link2=new Node(); 
         //第一个多项式
         ts.insert(link1,4,0);
         ts.insert(link1,5,2);
         ts.insert(link1,4,8);
         ts.insert(link1,6,12);
         //第二个多项式
         ts.insert(link2,6,1);
         ts.insert(link2,6,3);
         ts.insert(link2,3,8);
         ts.insert(link2,4,15);
         ts.insert(link2,8,20);

         link1 = ts.add(link1, link2); 

         while(link1!=null){  
            if(link1.exp== 0)
                System.out.print(link1.coef);
            else
                System.out.print(link1.coef+"x^"+link1.exp);  
             link1=link1.next;  
             if(link1!=null)
                System.out.print("+");
         }  
     }
}
```
   
  