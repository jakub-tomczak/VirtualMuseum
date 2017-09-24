package Utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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
}
