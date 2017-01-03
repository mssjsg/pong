package io.github.mssjsg.pong.game.component;

/**
 * Created by sing on 1/2/17.
 */

public class HitBody extends Body {

    public HitBody() {
    }

    public HitBody(Body body) {
        super(body);
    }

    @Override
    protected Body createObject() {
        return new HitBody();
    }
}
