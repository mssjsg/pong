package io.github.mssjsg.pong.game;

import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.pong.game.component.Component;

/**
 * Created by sing on 1/2/17.
 */
public class GameObjectBuilder {

    private ComponentSystemManager mComponentSystemManager;

    private Array<Component> mComponents;

    public GameObjectBuilder(ComponentSystemManager componentSystemManager) {
        mComponentSystemManager = componentSystemManager;
        mComponents = new Array<Component>();
    }

    public GameObjectBuilder addComponent(Component component) {
        mComponents.add(component);
        return this;
    }

    public int build() {
        if (mComponents == null) {
            throw new IllegalStateException("cannot call again");
        }

        Array<Component> components = mComponents;
        mComponents = null;
        return mComponentSystemManager.addObject((Component[]) components.toArray(Component.class));
    }
}
