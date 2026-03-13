package test06;

//열거형
/*
    [enum] : 플레이어의 방향 상태를 설정

    enum 을 사용하는 이유
    1. boolean 2개(is left, is right)로 방향을 관리하면
    둘 다 true가 되는 잘못된 상태가 생길 수 있음
    enum은 정해진 값 중 하나만 가질 수 있어서 안전함

    사용 방법 :
    PlayerWay = PlayerWay.LEFT // 값 설정
    is(PlayerWay == PlayerWay.LEFT) {} // 값 비교

    왜 사용하는가?
    나의 프로젝트나 논리 안에서 데이터의 범위를 지정하고 싶을 떄 사용한다.

 */
public enum PlayerWay {
    LEFT,RIGHT
}
