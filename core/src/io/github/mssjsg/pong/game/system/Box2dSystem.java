package io.github.mssjsg.pong.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;

import io.github.mssjsg.pong.game.Entity;
import io.github.mssjsg.pong.game.Tags;
import io.github.mssjsg.pong.game.component.HitBody;
import io.github.mssjsg.pong.game.component.Position;
import io.github.mssjsg.pong.game.shape.Circle;
import io.github.mssjsg.pong.game.shape.Rectangle;
import io.github.mssjsg.pong.game.shape.BodyShape;

/**
 * Created by sing on 1/7/17.
 */

public class Box2dSystem extends BaseSystem {

    public static final float PX_TO_BOX = 1f/100f;
    public static final float BOX_TO_PX = 1f/PX_TO_BOX;
    private World mWorld;

    private OrthographicCamera mCamera;
    private Box2DDebugRenderer mDebugRenderer;

    private ArrayMap<Body, Entity> mBodyEntityMap;
    private ArrayMap<Entity, Body> mEntityBodyMap;

    private OnRacketHitBallListener mOnRacketHitBallListener;

    public Box2dSystem(OrthographicCamera camera, OnRacketHitBallListener onRacketHitBallListener) {
        mWorld = new World(new Vector2(0, 0), true);

        mWorld.setContactListener(new BodyContactListener());

        mCamera = camera;
        mDebugRenderer = new Box2DDebugRenderer();
        mBodyEntityMap = new ArrayMap<Body, Entity>();
        mEntityBodyMap = new ArrayMap<Entity, Body>();

        mOnRacketHitBallListener = onRacketHitBallListener;
    }

    @Override
    public void update(float delta) {
        mDebugRenderer.render(mWorld, mCamera.combined);
        mWorld.step(delta, 6, 2);

        for (int i = 0; i < mBodyEntityMap.size; i++) {
            Body body = mBodyEntityMap.getKeyAt(i);
            Entity entity = mBodyEntityMap.get(body);
            Position position = entity.getComponent(Position.class);
            HitBody hitBody = entity.getComponent(HitBody.class);

            position.x = body.getPosition().x * BOX_TO_PX + hitBody.centerX;
            position.y = body.getPosition().y * BOX_TO_PX + hitBody.centerY;
        }
    }

    @Override
    public void dispose() {
        mWorld.dispose();
        mDebugRenderer.dispose();
    }

    public void addEntity(Entity entity, boolean dynamic) {

        Position position = entity.getComponent(Position.class);
        HitBody hitBody = entity.getComponent(HitBody.class);
        BodyShape<?> bodyShape = hitBody.shape;

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = dynamic ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.KinematicBody;

        bodyDef.position.set((position.x - hitBody.centerX) * PX_TO_BOX, (position.y - hitBody.centerY) * PX_TO_BOX);

        Body body = mWorld.createBody(bodyDef);

        bodyDef.linearDamping = 0.0f;
        bodyDef.angularDamping = 0.0f;

        Shape shape;

        if (bodyShape instanceof Rectangle) {
            PolygonShape polygonShape = new PolygonShape();
            shape = polygonShape;
            Rectangle rectangle = (Rectangle)bodyShape;
            polygonShape.setAsBox(rectangle.width / 2 * PX_TO_BOX,
                    rectangle.height / 2 * PX_TO_BOX);
        } else if (bodyShape instanceof Circle) {
            Circle circle = (Circle)bodyShape;
            CircleShape circleShape = new CircleShape();
            shape = circleShape;

            circleShape.setRadius(circle.radius * PX_TO_BOX);
        } else {
            return;
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1;
        fixtureDef.friction = 0;

        body.createFixture(fixtureDef);
        body.getMassData().center.set(hitBody.centerX * PX_TO_BOX, hitBody.centerY * PX_TO_BOX);

        shape.dispose();

        mBodyEntityMap.put(body, entity);
        mEntityBodyMap.put(entity, body);
    }

    private Entity getEntity(Fixture fixture) {
        Body body = fixture.getBody();
        return mBodyEntityMap.get(body);
    }

    public void applyForce(Entity entity, float forceX, float forceY) {
        Body body = mEntityBodyMap.get(entity);
        body.applyForceToCenter(forceX, forceY, true);
    }

    public void applyForce(Entity entity, float forceX, float forceY, float pointX, float pointY) {
        Body body = mEntityBodyMap.get(entity);
        body.applyForce(forceX, forceY, pointX, pointY, true);
    }

    public Body getBody(Entity entity) {
        return mEntityBodyMap.get(entity);
    }

    private class BodyContactListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {

        }

        @Override
        public void endContact(Contact contact) {
            Body body1 = contact.getFixtureA().getBody();
            Entity entity1 = mBodyEntityMap.get(body1);

            Body body2 = contact.getFixtureB().getBody();
            Entity entity2 = mBodyEntityMap.get(body2);

            Body ball = null;
            int racketTag = Tags.TAG_NONE;

            if (entity1.tag == Tags.TAG_BALL) {
                ball = body1;
                racketTag = entity2.tag;
            } else if (entity2.tag == Tags.TAG_BALL) {
                ball = body2;
                racketTag = entity1.tag;
            }

            if (ball != null) {
                switch (racketTag) {
                    case Tags.TAG_RACKET_BOTTOM:
                    case Tags.TAG_RACKET_LEFT:
                    case Tags.TAG_RACKET_RIGHT:
                    case Tags.TAG_RACKET_TOP:
                        mOnRacketHitBallListener.onRacketHitBall(racketTag);
                        break;
                }
            }
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }

    public interface OnRacketHitBallListener {
        void onRacketHitBall(int tag);
    }
}
