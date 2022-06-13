package game;

import com.badlogic.gdx.graphics.g2d.Animation;

public class MyPers {
    private AnimPlayer idle, jump, walkRight;
    private boolean isJump, isWalk, dir;


    public MyPers(AnimPlayer idle, AnimPlayer jump, AnimPlayer walkRight) {
        idle = new AnimPlayer("Batman.png", 8, 1, 16.0f, Animation.PlayMode.LOOP);
        jump = new AnimPlayer("Batman.png", 8, 1, 16.0f, Animation.PlayMode.LOOP);
        walkRight = new AnimPlayer("Batman.png", 8, 1, 16.0f, Animation.PlayMode.LOOP);
    }
}
