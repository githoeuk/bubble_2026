package test03;

/*
    클래스 역할 : 플레이어 충돌 감지 서비스 (백그라운드에서 계속 실행됨) - 메인 쓰레드에 사용하기 부적합

 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class backgroundMapService implements Runnable {

    // ImageIcon / Image - 화면에 그려서 보여주기 위한 이미지
    // --> 픽셀 데이터에 직접 접근이 불가능함
    // BufferedImage
    // -> 메모리에 픽셀 배열로 저장된 이미지
    // -> getRGB(x,y)로 특정 좌표의 색상값을 직접 읽을 수 있음.
    private BufferedImage image;
    private Player player;

    // 의존성 주입 DI(Dependency Injection)
    // Player 를 생성자를 통해서 외부에서 주입 받음
    // 즉 , 이 서비스가 직접 플레이어를 생성하지 않고 외부에서 주입 받아 사용 됨.
    public backgroundMapService(Player player) {
        this.player = player;

        try {
            image = ImageIO.read(new File("img/backgroundMapService.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        // 게임이 끝날 때까지 계속 실행되어야 함 -> 계속 반복
        while (true) {

            Color leftColor = new Color(image.getRGB(player.getX() , player.getY() + 25)); // buffered기능
            Color rightColor = new Color(image.getRGB(player.getX() + 60, player.getY() + 25)); // buffered기능

            //왼쪽 벽 충돌 감지
            if(isRed(leftColor)){
                // 충돌 상태 변수
                player.setLeftWallCrash(true);// 충돌 상태 ON
                player.setLeft(false); // while(left) 종료 -> 이동 멈춤(Tread 종료)
            }else{
                // 벽에서 벗어나면 즉시 해제 --> 다시 이동 가능하게 설정
                player.setLeftWallCrash(false);
            }

            // 오른쪽 벽 충돌 감지
            if(isRed(rightColor)){
                // 충돌 상태 변수
                player.setRightWallCrash(true); // 충돌 상태 ON
                player.setRight(false); // while(left) 종료 -> 이동 멈춤(Tread 종료)
            }else {
                // 벽에서 벗어나면 즉시 해제 --> 다시 이동 가능하게 설정
                player.setRightWallCrash(false);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } // end of while
    }

    // 255,0,0 = Red
    private boolean isRed(Color color){
        return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
    }

}
