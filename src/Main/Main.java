package Main;

import Models.Model;
import Models.ModelsRenderer;
import Shaders.Shader;
import Utils.ApplicationEventsManager;
import Utils.Constants;
import Utils.DisplayManager;
import Utils.ObjectLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    private static final boolean DEBUG = false;


    public static void main(String[] args) {

        new Main();
    }

    private void SystemInfo() {
        System.out.println("OS name " + System.getProperty("os.name"));
        System.out.println("OS version " + System.getProperty("os.version"));
        System.out.println("LWJGL version " + org.lwjgl.Sys.getVersion());
        System.out.println("OpenGL version " + glGetString(GL_VERSION));
    }

    public Main() {
        Utils.DisplayManager displayManager = new DisplayManager();


        ApplicationEventsManager.getInstance().onApplicationStarted();

        float[] vertices2 =
                {
                        0.100000f, -1.000000f, -1.000000f
                        , 0.100000f, -1.000000f, 1.000000f
                        , -0.100000f, -1.000000f, 1.000000f
                        , -0.100000f, -1.000000f, -1.000000f
                        , 0.100000f, 1.000000f, -0.999999f
                        , 0.100000f, 1.000000f, 1.000001f
                        , -0.100000f, 1.000000f, 1.000000f
                        , -0.100000f, 1.000000f, -1.000000f
                };

        float[] vertices3 =
                {

                        .5f, .5f, .5f,
                        .5f, .5f, -.5f,
                        -.5f, .5f, -.5f,
                        -.5f, .5f, .5f,

                        .5f, -.5f, .5f,
                        .5f, -.5f, -.5f,
                        -.5f, -.5f, -.5f,
                        -.5f, -.5f, .5f
                };

        int []indices3 =
                {
                        0,1,2,
                        2,3,0,
                        0,5,1,
                        1,5,2,
                        2,5,6,
                        6,2,3,
                        3,6,7,
                        7,3,0,
                        0,7,4,
                        4,0,5,
                        5,6,4,
                        4,6,7

                };

        float[] vertices = {
                -1f, 1f, 0f,
                -1f, -1f, 0f,
                1f, -1f, 0f,
                1f, 1f, 0f,
        };
        int[] indices2 =
                {
                        0, 2, 3,
                        4, 6, 5, 4, 1,
                        0, 1, 6,
                        2, 6, 3,
                        2, 0, 7,
                        4, 0, 1,
                        2, 4, 7,
                        6, 4, 5,
                        1, 1, 5,
                        6, 6, 7,
                        3, 0, 3,
                        7
                };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };
        ModelsRenderer renderer = new ModelsRenderer();
        Shader shader = new Shader("vertexShader", "fragmentShader");


        Model model = new Model(vertices3, indices3);//new Model("szescian2"); //

        renderer.addModelsToRender(model);


        if (DEBUG) {
            SystemInfo();
        }

        while (!Display.isCloseRequested()) {
            shader.startUsingShader();

            prepare();
            renderer.renderModels();
            displayManager.update();


            shader.stopUsingShader();
        }


        ApplicationEventsManager.getInstance().onApplicationEnded();
        displayManager.destroy();

    }

    public void prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(Constants.BACKGROUND_COLOR.x, Constants.BACKGROUND_COLOR.y, Constants.BACKGROUND_COLOR.z, Constants.BACKGROUND_COLOR.w);
    }

}