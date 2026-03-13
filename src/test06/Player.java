package test06;

import javax.swing.*;

public class Player extends JLabel implements Moveable {

    // 플레이어의 현재 좌표 상태값
    private int x;
    private int y;

    // 좌우 방향 이미지 (방향키에 따라서 이미지 전환)
    private ImageIcon playerR;
    private ImageIcon playerL;

    // 속도 상수
    private final int SPEED = 4;            // 좌우 이동 속도(픽셀단위)
    private final int JUMP_SPEED = 2;       // 점프 낙하 속도
    private final int JUMP_HEIGHT = 130;    // 점프 최대 높이

    // 이동 상태 플래그
    // ture = 해당 방향으로 이동 중 (while 루프 조건)
    // fasle = 멈춤 (while 루프 탈출 -> Thread 종료)
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    // 벽 충돌 상태 플래그
    private boolean leftWallCrash;
    private boolean rightWallCrash;

    // 플레이어의 현재 방향 (enum 타입)
    // left() 메서드 호출 시 =  PlayerWay.LEFT변경
    // RIGHT() 메서드 호출 시 =  PlayerWay.RIGHT변경
    private PlayerWay playerWay = PlayerWay.RIGHT; // 게임 시작 시 오른쪽을 바람 봄

    // getter

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeftWallCrash() {
        return leftWallCrash;
    }

    public boolean isRightWallCrash() {
        return rightWallCrash;
    }

    public PlayerWay getPlayerWay() {
        return playerWay;
    }

    // Bubble Frame 의 key 이벤트에서 호출할 수 있도록 setter 설정
    // setter

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeftWallCrash(boolean leftWallCrash) {
        this.leftWallCrash = leftWallCrash;
    }

    public void setRightWallCrash(boolean rightWallCrash) {
        this.rightWallCrash = rightWallCrash;
    }

    public void setPlayerWay(PlayerWay playerWay) {
        this.playerWay = playerWay;
    }

    public Player() {
        initDate();
        setInitLayout();
    }

    private void initDate() {
        playerR = new ImageIcon("img/playerR.png");
        playerL = new ImageIcon("img/playerL.png");
    }

    private void setInitLayout() {
        // 캐릭터 초기 설정
        x = 55;
        y = 535;
        setSize(50, 50);
        //초기 방향
        setIcon(playerR);
        // 초기 위치
        setLocation(x, y);
    }

    @Override
    public void left() {
        if (left) {
            return; // 왼쪽으로 이동 중일 시 중복 thread 생성 방지
        }
        playerWay = PlayerWay.LEFT;
        left = true;
        setIcon(playerL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // left 가 true인 동안 계속 이동 처리
                // keyReleased에서 setLeft(False)가 되면 While문 탈출
                while (left) {
                    x = x - SPEED;
                    setLocation(x, y);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start(); // end of Thread
    }

    @Override
    public void right() {
        if (right) { // 쓰레드 중복 생성 제한
            return;
        }
        playerWay = PlayerWay.RIGHT;
        right = true;
        setIcon(playerR);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (right) {
                    x = x + SPEED;
                    setLocation(x, y);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start(); // end of Thread
    }

    @Override
    public void up() {

        if (up) {
            return;
        }
        up = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //130 / 2 = 65반복 65픽셀만큼 상승
                for (int i = 0; i < JUMP_HEIGHT / JUMP_SPEED; i++) {
                    y = y - JUMP_SPEED;
                    setLocation(x, y);
                    try {
                        Thread.sleep(5); // 5ms 간격(하강 보다 느리게 설정 (하강 : 3ms))
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                up = false; // 점프 최고점 도달 -> 상승 상태 해지
                down(); // 하강 시작

            }
        }).start();
    }


    /*
        낙하 문법 변경
        for -> while
        while(true)
        BackgroudPlayerService 가 바닥 감지
        setDown(false) 호출 --> down 상태 값을 false 변경하다면 while(down) 이 종료 -> 낙하 종료
     */
    @Override
    public void down() {
        down = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(down){
                     y += JUMP_SPEED;
                     setLocation(x,y);
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();

    }
}
