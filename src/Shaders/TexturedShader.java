package Shaders;

import Camera.Camera;
import Light.Light;
import Interfaces.IApplicationEvents;
import Utils.ApplicationEventsManager;
import Utils.MathUtils;
import org.lwjgl.util.vector.Matrix4f;

public class TexturedShader extends ShaderProgram implements IApplicationEvents {

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int lightPositionLocation;
    private int lightColorLocation;

    public TexturedShader(String vertexShaderFileName, String fragmentShaderFileName) {
        super(vertexShaderFileName, fragmentShaderFileName);
        ApplicationEventsManager.getInstance().subscribeToApplicationEvents(this);
    }

    //ładuje macierz transformacji do zmiennej uniform
    public void loadTransformationLocation(Matrix4f matrix4f) {
        super.loadMat4f(transformationMatrixLocation, matrix4f);
    }

    public void loadProjectionMatix(Matrix4f projectionMatrix)
    {
        super.loadMat4f(projectionMatrixLocation, projectionMatrix);
    }
    public void loadViewMatrix(Matrix4f viewMatrix)
    {
        super.loadMat4f(viewMatrixLocation, viewMatrix);
    }
    public void loadLight(Light light)
    {
        super.loadVec3f(lightPositionLocation,light.getPosition());
        super.loadVec3f(lightColorLocation,light.getColor());
    }

    @Override
    protected void getAllUniformLocations() {
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        lightPositionLocation = super.getUniformLocation("lightPosition");
        lightColorLocation = super.getUniformLocation("lightColor");
    }


    @Override
    protected void bindAttributes() {
        bindAttribute(0, "v_position");
        bindAttribute(1, "v_textureCoords");
        bindAttribute(2, "normal");
    }

    @Override
    public void onApplicationStarted() {
        ;
    }

    @Override
    public void onApplicationEnded() {
        cleanUP();
    }


}
