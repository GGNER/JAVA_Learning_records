package Game;

import java.awt.*;

public abstract class GameObject {
    //图片
    public Image img;
    //坐标
    public int x;
    public int y;
    //游戏界面
    public GamePanel gamePanel;

    //需要将游戏基本要素：图片/坐标/界面，放置在其本身的构造函数
    public GameObject(String img, int x, int y, GamePanel gamePanel) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }

    //绘制方法，参考是画布
    public abstract void paintSelf(Graphics g);

    //移动
    public abstract Rectangle getRec();

    //在这里定义了四种移动方式
    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;

}