package io.github.mssjsg.pong.game.system;

import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.Entity;

/**
 * Created by sing on 1/2/17.
 */

public abstract class EntitySystem {

    private Array<Entity> mEntities = new Array<Entity>();

    public final void update(float delta) {
        onUpdate(delta, mEntities);
    }

    public void addEntity(Entity entity) {
        mEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        mEntities.removeValue(entity, true);
    }

    protected abstract void onUpdate(float delta, Array<Entity> entities);
}
