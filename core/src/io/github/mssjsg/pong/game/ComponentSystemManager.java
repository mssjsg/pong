package io.github.mssjsg.pong.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import io.github.mssjsg.pong.game.component.Component;

/**
 * Created by sing on 1/2/17.
 */

public class ComponentSystemManager {

    private ArrayMap<Class<? extends Component>, ComponentSystem<? extends Component>> mComponentSystems;

    private ArrayMap<Class<? extends Component>, Array<Component>> mComponentListMap;
    private ArrayMap<Integer, Array<Component>> mObjectMap;

    public ComponentSystemManager() {
        mComponentSystems = new ArrayMap<Class<? extends Component>, ComponentSystem<? extends Component>>();
        mComponentListMap = new ArrayMap<Class<? extends Component>, Array<Component>>();
        mObjectMap = new ArrayMap<Integer, Array<Component>>();
    }

    public void update(float delta) {
        for (Class<? extends Component> c : mComponentSystems.keys) {
            Array<Component> components = mComponentListMap.get(c);
            mComponentSystems.get(c).update(delta, components);
        }
    }

    public <T extends Component> void addSystem(Class<T> c, ComponentSystem<T> system) {
        mComponentSystems.put(c, system);
    }

    public void removeSystem(ComponentSystem<?> system) {
        mComponentSystems.removeValue(system, true);
    }

    public void addComponents(int id, Component... components) {

        Array<Component> objectComponents = mObjectMap.get(id);

        if (objectComponents == null) {
            objectComponents = new Array<Component>();
            mObjectMap.put(id, objectComponents);
        }

        for (Component component : components) {
            Array<Component> list = mComponentListMap.get(component.getClass());

            if (list == null) {
                list = new Array<Component>();
                mComponentListMap.put(component.getClass(), list);
            }

            list.add(component);

            objectComponents.add(component);
        }
    }

    public void removeComponents(int id, Component... components) {
        Array<Component> objectComponents = mObjectMap.get(id);

        for (Component component : components) {
            Array<Component> list = mComponentListMap.get(component.getClass());

            if (list != null) {
                list.removeValue(component, true);
            }

            if (objectComponents != null) {
                objectComponents.removeValue(component, true);
            }
        }
    }

    public int addObject(Component... components) {
        int id = mObjectMap.size;
        addComponents(id, components);
        return id;
    }

    public void reset() {
        mComponentSystems = new ArrayMap<Class<? extends Component>, ComponentSystem<? extends Component>>();
        mComponentListMap = new ArrayMap<Class<? extends Component>, Array<Component>>();
        mObjectMap = new ArrayMap<Integer, Array<Component>>();
    }

}
