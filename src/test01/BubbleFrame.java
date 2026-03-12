package test01;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BubbleFrame extends JFrame {

    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame() {
        initData();
        setInitLayout();
        addEventLayout();
    }

    private void initData() {
        setTitle("Bubble Bubble");
        setSize(1000, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundMap = new JLabel(new ImageIcon("img/backgroundMap.png")); // 익명클래스 응용
        setContentPane(backgroundMap);

        player = new Player(); // 포함관계
    }

    private void setInitLayout() {
        setLayout(null);
        setResizable(false); //  창 크기 고정
        setLocationRelativeTo(null); // 화면 정중앙 배치 (프레임)

        backgroundMap.add(player);

        setVisible(true);
    }

    private void addEventLayout() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 방향키 코드를 Player의 이동 메서드로 연결
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.right();
                        break;
                    case KeyEvent.VK_UP:
                        player.up();
                        break;
                    //중력이 존재하기 때문에 down은 없음
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    //main
    public static void main(String[] args) {
        new BubbleFrame();
    } // end of main

} // end of class
