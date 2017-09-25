package Utils;

import Camera.Camera;
import org.lwjgl.util.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import javax.rmi.CORBA.Util;

import static Utils.Constants.FAR_PLANE_PROJECTION;
import static Utils.Constants.NEAR_PLANE_PROJECTION;

public class MathUtils {
    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale)
    {
        Matrix4f transformationMatrix = new Matrix4f();
        transformationMatrix.setIdentity();
        Matrix4f.translate(translation, transformationMatrix, transformationMatrix);
        Matrix4f.rotate((float)Math.toRadians(rotation.x), new Vector3f(1,0,0), transformationMatrix, transformationMatrix);
        Matrix4f.rotate((float)Math.toRadians(rotation.y), new Vector3f(0,1,0), transformationMatrix, transformationMatrix);
        Matrix4f.rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1), transformationMatrix, transformationMatrix);

        Matrix4f.scale(scale, transformationMatrix, transformationMatrix);

        return transformationMatrix;
    }

    public static Matrix4f createProjectionMatrix()
    {
        float aspectRatio = (float) Constants.SCREENWIDTH / Constants.SCREENHEIGHT;
        float scaleY = (float) ((1f / Math.tan(Math.toRadians(Constants.FOV / 2f))) * aspectRatio);
        float scaleX = scaleY / aspectRatio;
        float frustrumLength = FAR_PLANE_PROJECTION - NEAR_PLANE_PROJECTION;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = scaleX;
        projectionMatrix.m11 = scaleY;
        projectionMatrix.m22 = -((FAR_PLANE_PROJECTION + NEAR_PLANE_PROJECTION) / frustrumLength);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE_PROJECTION * FAR_PLANE_PROJECTION) / frustrumLength);
        projectionMatrix.m33 = 0;

        return projectionMatrix;

    }

    public void metoda()
    {

        System.out.println("dziala");
    }

    public static Matrix4f createViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0),viewMatrix, viewMatrix);
        Vector3f cameraPosition = camera.getPosition();
        //poruszamy świat w odwrotnym kierunku
        cameraPosition = new Vector3f(-1f * camera.getPosition().x, -1f * camera.getPosition().y, -1f * camera.getPosition().z);
        Matrix4f.translate(cameraPosition, viewMatrix, viewMatrix);
        return viewMatrix;
    }
}
