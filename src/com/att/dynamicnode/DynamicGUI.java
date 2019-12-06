package com.att.dynamicnode;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DynamicGUI extends JPanel implements Runnable {

    private int windowWidth = 1000;
    private int windowHeight = 800;
    private int nodeNum = 150;
    private List<Node> nodeList = new ArrayList<>();    //节点数组，用于存放所有的节点
    private List<Node> CHList = new ArrayList<>();      //簇头数组，用于存放所有簇头节点

    public DynamicGUI(){
        this.initializeNodeList();
    }

    /**
     * 初始化nodeList,生成nodeNum个节点，并随机分配节点初始坐标
     */
    private void initializeNodeList(){
        for(int i = 0; i < nodeNum; i++){
            nodeList.add(new Node(new Random().nextInt(1000), new Random().nextInt(800)));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.paintNearestTopo(g);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            //节点移动
            for(Node n : nodeList){
                n.move(0, 0, this.getWidth(), this.getHeight());
            }

            this.repaint();
        }
    }

    public void start(){
        JFrame jf = new JFrame();
        DynamicGUI dGUI = new DynamicGUI();
        //dGUI.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        jf.setSize(windowWidth, windowHeight);
        jf.add(dGUI);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread t = new Thread(dGUI);
        t.start();
    }

    /**
     * 为nodeList中的节点分配节点的颜色，颜色在colorList中依次选取，如果节点数目超过了colorList中的颜色数目，就重头再选
     * @param nodeList 待分配颜色的节点数组
     */
    public void setClusterColor(List<Node> nodeList){
        Color[] colorList = new Color[]{Color.black, Color.blue, Color.green, Color.red, Color.yellow,
                Color.cyan, Color.gray, Color.magenta, Color.orange, Color.pink, Color.darkGray, Color.lightGray};
        int colorIndex = 0;
        for(Node n : nodeList){
            n.setColor(colorList[colorIndex++ % (colorList.length)]);
        }
    }

    /**
     * 绘制节点及拓扑关系，包括建立拓扑，画节点，画节点之间的连线，画簇头之间的连线
     * @param g
     */
    private void paintNearestTopo(Graphics g){
        //采用就近原则算法建立拓扑结构
        CHList = NearestTopologyAlgorithm.buildTopo(nodeList, this.getWidth(), this.getHeight());

        //设置簇头节点颜色
        setClusterColor(CHList);
        //设置每个节点的颜色与簇头节点颜色一致
        for(Node n : nodeList){
            n.setColor(n.getCH().getColor());
        }
        //画出nodeList中的每个节点
        for(Node n : nodeList){
            g.setColor(n.getColor());
            g.fillOval((int)n.getX(), (int)n.getY(), (int)n.getNodeWidth(), (int)n.getNodeHeight());
        }

        //画出连线
        g.setColor(Color.black);
        for(Node n : nodeList){
            g.drawLine((int)n.getCenterX(), (int)n.getCenterY(),
                    (int)n.getCH().getCenterX(), (int)n.getCH().getCenterY());
        }

        //画普通簇头与中心簇头的连线，中心簇头：距离窗口中心点最近的簇头
        Node centerNode = CHList.get(0);
        for(Node n : CHList){
            centerNode = n.getDistance(windowWidth/2, windowHeight/2)<centerNode.getDistance(windowWidth/2, windowHeight/2)?
                    n : centerNode;
        }
        for(Node n : CHList){
            g.drawLine((int)n.getCenterX(), (int)n.getCenterY(),
                    (int)centerNode.getCenterX(), (int)centerNode.getCenterY());
        }
    }
}