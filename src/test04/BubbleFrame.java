package test04;



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
        //충돌감지 백그라운드 서비스 시작
        new Thread(new backgroundMapService(player)).start();
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

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        //이동 중이 아니고 and 벽에 충돌하지 않은 상태일때만 left() 호출
                        if(player.isLeft() == false && player.isLeftWallCrash() == false) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        //이동 중이 아니고 and 벽에 충돌하지 않은 상태일때만 left() 호출
                        if(player.isRight() == false && player.isRightWallCrash() == false){
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        player.up();
                        break;
                    //중력이 존재하기 때문에 down은 없음
                    // spacebar를 통한 bubble 구현
                    case KeyEvent.VK_SPACE:
                        fireBubble();
                        break;
                } // end of switch
            }

            @Override
            public void keyReleased(KeyEvent e) {

                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT :
                        player.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setRight(false);
                        break;
                }
            }

        });
    }
    // todo 임시 버블 클래스 생성
    private void fireBubble(){
        Bubble bubble = new Bubble(player);
        backgroundMap.add(bubble);
        // 동적으로 컴포넌트가 그려지기 때문에 버그 발생 가능
        backgroundMap.revalidate(); // 레이아웃 재계산
        backgroundMap.repaint(); // 화면을다시 그려라
    }

    //main
    public static void main(String[] args) {
        new BubbleFrame();
    } // end of main

} // end of class
