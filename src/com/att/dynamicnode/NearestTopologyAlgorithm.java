package com.att.dynamicnode;
/**
 * 拓扑算法实现类 编写规范 19-12-6：
 * 输入一个节点组成的数组，通过调用该拓扑算法的方法，确定该数组中的每个节点的簇头，也就包含了网络拓扑的信息。
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 就近拓扑算法类
 * 就近拓扑算法（自己想的）：选取某个坐标最近的节点作为一个簇头
 */
public class NearestTopologyAlgorithm {

    /**
     * 建立拓扑结构
     * @param nodeList 需要建立拓扑结构的节点数组
     * @param seaWidth 海面的演示界面宽度
     * @param seaHeight 海面的演示界面高度
     * @return  拓扑结构中心节点组成的数组
     */
    public static List<Node> buildTopo(List<Node> nodeList, int seaWidth, int seaHeight){
        for(Node n : nodeList){
            n.setCH(null);
        }

        //建立坐标数组coordinateList
        List<int[]> coordinateList = new ArrayList<>();
        coordinateList.add(new int[]{seaWidth/2,seaHeight/2});
        coordinateList.add(new int[]{seaWidth/4,seaHeight/4});
        coordinateList.add(new int[]{seaWidth/4,seaHeight*3/4});
        coordinateList.add(new int[]{seaWidth*3/4,seaHeight/4});
        coordinateList.add(new int[]{seaWidth*3/4,seaHeight*3/4});

        //依据coordinateList，采用就近拓扑算法构造拓扑结构
        List<Node> CHList = NearestTopologyAlgorithm.topoChooseCH(nodeList, coordinateList);
        NearestTopologyAlgorithm.nodeChooseCH(nodeList, CHList);

        return CHList;
    }

    /**
     * 在nodeList中选取一个簇头，选取规则；距离坐标(x,y)最近的节点作为簇头，并返回该节点
     * @param nodeList
     * @param x
     * @param y
     */
    public static Node topoChooseCH(List<Node> nodeList, double x, double y){
        Node temp = nodeList.get(0);
        for(Node n : nodeList){
            temp = n.getDistance(x, y)<temp.getDistance(x, y) ? n : temp;
        }
        temp.setCH(temp);
        return temp;
    }

    /**
     * 在nodeList中选取多个簇头，选取规则：依次遍历coordinateList中的坐标点，每次选取距离坐标点最近的节点作为簇头。返回所有簇头组成的数组。
     * @param nodeList
     * @param coordinateList
     */
    public static List<Node> topoChooseCH(List<Node> nodeList, List<int[]> coordinateList){
        List<Node> CHList = new ArrayList<>();
        for(int[] c : coordinateList){
            CHList.add(topoChooseCH(nodeList, c[0], c[1]));
        }
        return CHList;
    }

    /**
     * nodeList中的每个节点选择最近的簇头加入。
     * @param nodeList
     * @param CHList
     */
    public static void nodeChooseCH(List<Node> nodeList, List<Node> CHList){
        Node tempCH;
        for(Node n : nodeList){
            if(n.isCH()==false){
                tempCH = CHList.get(0);
                for(int i = 1; i < CHList.size(); i++){
                    tempCH = n.getDistance(CHList.get(i))<n.getDistance(tempCH) ? CHList.get(i) : tempCH;
                }
                n.setCH(tempCH);
            }
        }
    }

}
