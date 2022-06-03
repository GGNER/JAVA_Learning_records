package Game;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.ArrayList;

public class EnemyBullet extends Bullet {
    public EnemyBullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }

    public void hitPlayer() {
        ArrayList<Tank> players = this.gamePanel.playerList;
        for (Tank player : players) {
            if (this.getRec().intersects(player.getRec())) {
                this.gamePanel.boomList.add(new Boom("", player.x + 20, player.y + 30, this.gamePanel));
                this.gamePanel.playerList.remove(player);
                this.gamePanel.removeList.add(this);
                player.alive = false;
                break;
            }
        }
    }

    //在绘制里添加子弹运动轨迹这样就可以看见了
    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        this.go();
        this.hitPlayer();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}