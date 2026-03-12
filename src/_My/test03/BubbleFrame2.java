package _My.test03;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BubbleFrame2 extends JFrame {

    public static void main(String[] args) {
        new BubbleFrame2();
    }

    private JLabel backgroundMap;
    private Player2 player2;

    public BubbleFrame2() {
        initData();
        setInitLayout();
        addEventLayout();
        new Thread(new BackgroundService2(player2)).start();
    }

    private void initData() {
        setTitle("Bubble Bubble");
        setSize(1000, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundMap = new JLabel(new ImageIcon("img/backgroundMap.png"));
        setContentPane(backgroundMap); // ?

        player2 = new Player2(); // 포함 관계
    }

    private void setInitLayout() {
        setLayout(null);
        setResizable(false); // 창 고정
        setLocationRelativeTo(null); // 화면 정중앙 배치 (프레임)

        backgroundMap.add(player2); // 충돌 감지 실행

        setVisible(true);
    }

    private void addEventLayout() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_LEFT:
                        // 왼쪽 벽에 충돌하지 않고 왼쪽으로 이동 아닐 시 left_tread() 호출
                        if (player2.isLeft() == false && player2.isLeftWallCrash() == false) {
                            player2.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(player2.isRight() == false && player2.isRightWallCrash() == false){
                            player2.right();
                        }
                        break;
                    case  KeyEvent.VK_UP:
                            player2.up();
                        break;
                }// end of switch
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT :
                        player2.setLeft(false);
                        break;

                    case KeyEvent.VK_RIGHT:
                        player2.setRight(false);
                        break;
                } // end of Released_switch
            } // end of keyReleased
        }); // end of addKeyListener
    } // end of addEventLayout
} // end of class
