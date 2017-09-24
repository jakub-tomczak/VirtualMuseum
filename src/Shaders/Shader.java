package Shaders;

import Interfaces.IApplicationEvents;
import Utils.ApplicationEventsManager;
import org.lwjgl.util.vector.Matrix4f;

public class Shader extends ShaderProgram implements IApplicationEvents{

    private int transformationMatrixLocaiton ;

    public Shader(String vertexShaderFileName, String fragmentShaderFileName)
    {
        super(vertexShaderFileName, fragmentShaderFileName);
        ApplicationEventsManager.getInstance().subscribeToApplicationEvents(this);
    }

    //ładuje macierz transformacji do zmiennej uniform
    public void loadTransformationLocation(Matrix4f matrix4f)
    {
        super.loadMat4f(transformationMatrixLocaiton ,matrix4f);
    }

    @Override
    protected void getAlUniformLocations() {
       transformationMatrixLocaiton = super.getUniformLocation("transformationMatrx");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
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
