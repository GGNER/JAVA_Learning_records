package Game;

import java.awt.*;

public class Boom extends GameObject {

    //爆炸图集
    static Image[] imgs = new Image[4];
    //爆炸图片的数字
    int BoomCount = 0;

    static {
        for (int i = 0; i < 4; i++) {
            imgs[i] = Toolkit.getDefaultToolkit().getImage("D:\\IDEA\\zuoye\\duixiang\\images\\boom" + (i + 1) + ".png");
        }
    }

    public Boom(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void paintSelf(Graphics g) {
        if (BoomCount < 4) {
            g.drawImage(imgs[BoomCount], x, y, null);
            BoomCount++;
        }
    }

    @Override
    public Rectangle getRec() {
        return null;
    }
}