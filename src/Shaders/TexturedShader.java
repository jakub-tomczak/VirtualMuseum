package Shaders;

import Camera.Camera;
import Light.Light;
import java.util.*;
import Interfaces.IApplicationEvents;
import Utils.ApplicationEventsManager;
import Utils.MathUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TexturedShader extends ShaderProgram implements IApplicationEvents {

    private static int MAX_LIGHTS = 4;
    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int lightPositionLocation[];
    private int lightColorLocation[];
    private int cameraReflectionLocation;
    private int reflectivityLocation;


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
    public void loadLights(List<Light>lights)
    {
        for(int i=0;i<MAX_LIGHTS;i++)
        {
            if(i<lights.size())
            {
                super.loadVec3f(lightPositionLocation[i],lights.get(i).getPosition());
                super.loadVec3f(lightColorLocation[i],lights.get(i).getColor());
            }
            else
            {
                super.loadVec3f(lightPositionLocation[i],new Vector3f(0,0,0));
                super.loadVec3f(lightColorLocation[i],new Vector3f(0,0,0));
            }
        }
    }
    public void loadShineVariables(float cameraLight,float reflectivity)
    {
        super.loadFloat(cameraReflectionLocation,cameraLight);
        super.loadFloat(reflectivityLocation,reflectivity);
    }

    @Override
    protected void getAllUniformLocations() {
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
        lightPositionLocation= new int[MAX_LIGHTS];
        lightColorLocation = new int[MAX_LIGHTS];
        for(int i=0;i<MAX_LIGHTS;i++)
        {
            lightPositionLocation[i]=super.getUniformLocation("lightPosition[" + i + "]");
            lightColorLocation[i]=super.getUniformLocation("lightColor[" + i + "]");

        }
        cameraReflectionLocation=super.getUniformLocation("cameraReflection");
        reflectivityLocation=super.getUniformLocation("reflectivity");
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
