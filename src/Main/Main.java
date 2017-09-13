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

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                };
        int [] indices = {
                0, 1, 3,
                3, 1, 2
        };
        ModelsRenderer renderer = new ModelsRenderer();
        Shader shader = new Shader("vertexShader", "fragmentShader");



        Model model = new Model(vertices, indices);  //new Model("szescian2");

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