package Shaders;

import Interfaces.IApplicationEvents;
import Utils.ApplicationEventsManager;
import org.lwjgl.util.vector.Matrix4f;

public class TerrainShader extends ShaderProgram implements IApplicationEvents {

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;

    public TerrainShader(String vertexShaderFileName, String fragmentShaderFileName) {
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

    @Override
    protected void getAllUniformLocations() {
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
    }


    @Override
    protected void bindAttributes() {
        bindAttribute(0, "v_position");
        bindAttribute(1, "v_textureCoords");
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
