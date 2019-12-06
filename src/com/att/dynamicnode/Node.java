package com.att.dynamicnode;

import java.awt.*;

public class Node {
    private static int totalId = 0;
    private int id;
    private double x;
    private double y;
    private double nodeWidth = 10;
    private double nodeHeight = 10;
    private double speed = 10;
    private double speedX;
    private double speedY;
    private double communicationRange = 50;
    private Node CH = null;
    private Color color;

    public Node(double x, double y){
        this.id = ++totalId;
        this.x = x;
        this.y = y;
        this.speedX = Math.random()*speed-speed/2;
        this.speedY = Math.random()*speed-speed/2;
    }

    public Node(double x, double y, double speed) {
        this(x, y);
        this.speed = speed;
    }

    public Node(double x, double y, double speed, double communicationRange) {
        this(x, y, speed);
        this.communicationRange = communicationRange;
    }

    public int getTotalId(){
        return totalId;
    }

    public int getId(){
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public double getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(double nodeWidth) {
        this.nodeWidth = nodeWidth;
    }

    public double getNodeHeight() {
        return nodeHeight;
    }

    public void setNodeHeight(double nodeHeight) {
        this.nodeHeight = nodeHeight;
    }

    /**
     * 得到节点的中心坐标
     * @return
     */
    public double getCenterX(){return this.getX()+this.nodeWidth/2;}

    public double getCenterY(){return this.getY()+this.nodeHeight/2;}

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCommunicationRange() {
        return communicationRange;
    }

    public void setCommunicationRange(double communicationRange) {
        this.communicationRange = communicationRange;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCH(Node CH){
        this.CH = CH;
    }

    public Node getCH() {
        return CH;
    }

    /**
     * 判断节点是否是簇头
     * @return
     */
    public boolean isCH() {
        return (this.CH!=null)&&(this.CH==this) ? true : false;
    }

    /**
     * 节点随机移动
     */
    public void move(){
        this.x = this.x + speedX;
        this.y = this.y + speedY;
    }

    /**
     * 节点在一定范围内移动
     * @param startX 移动范围的起始横坐标
     * @param startY 移动范围的起始横坐标
     * @param width 移动范围的宽
     * @param height 移动范围的高
     */
    public void move(int startX, int startY, int width, int height){
        if(this.x<=startX || this.x>=startX+width)
            this.speedX = -this.speedX;
        if(this.y<=startY || this.y>=startY+height)
            this.speedY = -this.speedY;
        move();
    }

    /**
     * 计算与某个节点的距离
     * @param n
     * @return
     */
    public double getDistance(Node n){
        return Math.sqrt(Math.pow((this.getX()-n.getX()), 2) + Math.pow(this.getY()-n.getY(), 2));
    }

    /**
     * 计算与某个坐标之间的距离
     * @param x
     * @param y
     * @return
     */
    public double getDistance(double x, double y){
        return Math.sqrt(Math.pow((this.getX()-x), 2) + Math.pow(this.getY()-y, 2));
    }

}
