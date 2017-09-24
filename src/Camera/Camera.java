package Camera;

import Models.ModelTransformation;
import Utils.MathUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position;
    private float pitch;
    private float yaw;
    private float roll;
    private Matrix4f viewMatrix;
    public Camera() {
        position = new Vector3f(0,0,0);
    }

    //wywoływane co ramkę
    public void update()
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            position.y -= .1f;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            position.y += .1f;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            position.x += .02f;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            position.x -= .02f;
        }
        viewMatrix = MathUtils.createViewMatrix(this);

    }

    public float getRoll() {
        return roll;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}
