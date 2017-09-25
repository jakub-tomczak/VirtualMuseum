package Camera;

import Models.ModelTransformation;
import Utils.MathUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import static java.lang.Math.*;

public class Camera {
    private Vector3f position;
    private float pitch=20;
    private float yaw=0;
    private float roll=0;
    private Matrix4f viewMatrix;
    public Camera() {
        position = new Vector3f(0,0,0);
    }

    //wywoływane co ramkę
    public void update()
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            position.z += .1f;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            position.z -= cos(toRadians(yaw))*0.1f;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            position.x += .02f;

        } else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            position.x -= .02f;
        }
    //    calculatePitch();
        calculateAngle();
        viewMatrix = MathUtils.createViewMatrix(this);

    }
    public void calculatePitch()
    {
        if(Mouse.isButtonDown(1))
        {
            float pitchChange= Mouse.getDY()*0.1f;
            yaw += pitchChange;
        }
    }

    public void calculateAngle()
    {
        if(Mouse.isButtonDown(0))
        {
            float pitchChange= Mouse.getDX()*0.1f;
            yaw += pitchChange;
            pitchChange= Mouse.getDY()*0.1f;
            pitch -= pitchChange;
        }
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
