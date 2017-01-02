package io.github.mssjsg.pong.game;

import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.component.Component;

/**
 * Created by sing on 1/2/17.
 */

public abstract class ComponentSystem<T extends Component> {

    private Class<T> mComponentClass;

    public ComponentSystem(Class<T> componentClass) {
        mComponentClass = componentClass;
    }

    public Class<T> getComponentClass() {
        return mComponentClass;
    }

    public boolean canHandleComponent(Component component) {
        return component.getClass() == mComponentClass;
    }

    public final void update(float delta, Array<Component> components) {
        onUpdate(delta, (Array<T>)components);
    }

    protected abstract void onUpdate(float delta, Array<T> component);
}
