package test04;

import javax.swing.*;

// 물방울 클래스
public class Bubble extends JLabel {

    private int x;
    private int y;

    private ImageIcon bubbleIcon;
    private Player player; //플레이어의 위치값 , 방향을 알아야하기 때문

    public Bubble(Player player) {
        this.player = player;
        initData();
        serInitLayout();
    }

    // getter
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    // setter
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private void initData() {
        bubbleIcon = new ImageIcon("img/bubble.png");

    }

    private void serInitLayout() {
        x = player.getX();
        y = player.getY();
        setSize(50, 50);
        setLocation(x, y);
        setVisible(true);
    }


}
