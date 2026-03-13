package bubble;

public interface Moveable {
    void left();
    void right();
    void up();

    // adapter 클래스가 너무 많이 생경서 default 문법을
    // 인터페이스에서 사용할 수 있도록 만들어 줬다.
    // 즉 default 키워드를 사용하면 인터페이스 안에서 일반 메서드를 선언할 수 있다.
    default void down() {}
}
