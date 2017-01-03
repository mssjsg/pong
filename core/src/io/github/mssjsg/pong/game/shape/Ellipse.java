package io.github.mssjsg.pong.game.shape;

/**
 * Created by sing on 1/3/17.
 */

public class Ellipse extends Shape<Ellipse> {
    public float width;
    public float height;

    @Override
    public Ellipse copy() {
        Ellipse ellipse = new Ellipse();
        ellipse.width = width;
        ellipse.height = height;
        return ellipse;
    }
}
