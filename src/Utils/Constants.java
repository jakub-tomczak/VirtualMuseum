package Utils;

import org.lwjgl.util.vector.Vector4f;

public class Constants {
    public static final boolean DEBUG = false;

    public static final int SCREENWIDTH = 1280;
    public static final int SCREENHEIGHT = 720;

    public static final Vector4f BACKGROUND_COLOR = new Vector4f(.235f, .247f, .235f, 1);
    public static final String MODELS_PATH = "src/modelsData/";
    public static final String SHADERS_PATH = "src/Shaders/shaders/";
    public static final String TEXTURES_PATH = "res/textures/";
    public static final String TEXTURES_EXTENSION = "PNG";



    public static final float FOV = 70;
    public static final float NEAR_PLANE_PROJECTION= .1f;
    public static final float FAR_PLANE_PROJECTION= 1000;


}
