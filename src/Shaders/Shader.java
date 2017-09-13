package Shaders;

import Interfaces.IApplicationEvents;
import Utils.ApplicationEventsManager;

public class Shader extends ShaderProgram implements IApplicationEvents{

    public Shader(String vertexShaderFileName, String fragmentShaderFileName)
    {
        super(vertexShaderFileName, fragmentShaderFileName);
        ApplicationEventsManager.getInstance().subscribeToApplicationEvents(this);
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
