package io.github.mssjsg.pong.game;

import com.badlogic.gdx.utils.ArrayMap;

import io.github.mssjsg.pong.game.component.Component;

/**
 * Created by sing on 1/2/17.
 */

public class Entity {
    public int tag;
    public ArrayMap<Class<? extends Component>, Component> mComponentArrayMap;

    public Entity() {
        mComponentArrayMap = new ArrayMap<Class<? extends Component>, Component>();
    }

    public Entity addComponent(Component component) {
        mComponentArrayMap.put(component.getClass(), component);
        return this;
    }

    public void removeComponent(Class<? extends Component> type) {
        mComponentArrayMap.removeKey(type);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        return (T)mComponentArrayMap.get(type);
    }
}
