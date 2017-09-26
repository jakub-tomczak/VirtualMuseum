package Camera;

import Utils.MathUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class Camera {
    private Vector3f position;
    private float pitch=0;
    private float yaw=0;
    private float roll=0;
    private float moveSpeed=0.1f;
    private float sensitivity = 0.3f;
    private Matrix4f viewMatrix;
    public Camera() {
        position = new Vector3f(0,0,0);
        viewMatrix = MathUtils.createViewMatrix(this);

    }

    float dx = 0.0f;
    float dy = 0.0f;
    float angle = 0f;
    float angleDiff = 10f;
    float walkFactor = 0.1f;
    float height = 1f;
    public void walkForward(float distance) {
        position.x += distance * (float) Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
        angle+= angleDiff;
        position.y = height * (float) Math.sin(Math.toRadians(angle)) * walkFactor;

    }

    public void walkBackwards(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw));
        angle-= angleDiff;
        position.y = height * (float) Math.sin(Math.toRadians(angle)) * walkFactor;

    }

    public void walkLeft(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

    public void walkRight(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    //wywoływane co ramkę
    public void update()
    {
        calculateAngle();
        if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            walkForward(moveSpeed );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            walkBackwards(moveSpeed );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            walkLeft(moveSpeed );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            walkRight(moveSpeed );
        }

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
       // time = Sys.getTime();
       // lastTime = time;
      /*  try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        dx = Mouse.getDX();
        dy = Mouse.getDY();

        yaw+=(dx * sensitivity);
        pitch-=(dy * sensitivity);
    }
    public void Mouse()
    {

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
