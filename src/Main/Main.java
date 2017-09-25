package Main;

import Models.Model;
import Models.ModelsRenderer;
import Camera.Camera;
import Shaders.Shader;
import Texturing.Texture;
import Utils.ApplicationEventsManager;
import Utils.Constants;
import Utils.DisplayManager;
import Utils.ObjectLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import static Utils.Constants.DEBUG;
import static org.lwjgl.opengl.GL11.*;

public class Main {


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

        Camera mainCamera = new Camera();
        ModelsRenderer renderer = new ModelsRenderer();
        renderer.useCamera(mainCamera);
        Shader shader1 = new Shader("v_textured", "f_textured");


        //TODO:
        //projectionMatrix bindować do zmiennej jednorodnej tylko raz

        //będzie kilka obrazów, więc aby nie ładować za każdym razem modelu lepiej przerzucić dane
        //albo podpiąć się pod ten sam vao

        //shader jest przypisywany do modelu
        //w renderer.renderModels jest wywyoływane startUsingShader i stopUsingShader
        //każdy obiekt 3D to instancja klasy Model
        //posiada ona ModelData - do przechowywania informacji o wierzchołkach
        //ModelTransformation - do przechowywania informacji o transformacji obiektu
        //Shader - do przechowywania informacji o shaderze
        Texture texture = Texture.loadTexture("bohomaz", 0);
        Texture texture1 = Texture.loadTexture("bohomaz", 0);
        Model model1 = new Model("szescian2", shader1, texture);
        Model model2 = new Model("szescian2", shader1, texture1);
        //zmiana położenia oraz rotacji modelu
        model1.modelTransformation.changePosition(new Vector3f(.5f, .5f, -1f));
        model1.modelTransformation.changeRotation(new Vector3f(1, 1, 1));         //obroty sa podawane w kątach, podczas tworzenia macierzy transformacji są przeliczane na radiany
        model1.modelTransformation.changeScale(new Vector3f(.25f, .25f, .25f));

        //wystarczy ustawić dla jednego modelu używającego tego samego shadera - chyba
        model2.loadProjectionMatrix(renderer.getProjectionMatrix());

        model2.modelTransformation.changePosition(new Vector3f(-.5f, -.5f, 0f));

        //dopisz model do listy modeli które sa renderowane w każdej klatce
        // renderer.addModelsToRender(model);
        renderer.addModelsToRender(model1);
        renderer.addModelsToRender(model2);


        while (!Display.isCloseRequested()) {

            prepare();
            renderer.renderModels();
            displayManager.update();



          /*  model2.modelTransformation.changePosition(
                    new Vector3f(model2.modelTransformation.getPosition().x,
                            model2.modelTransformation.getPosition().y,
                            model2.modelTransformation.getPosition().z - .1f
                    )
            );
            model1.modelTransformation.changePosition(
                    new Vector3f(model1.modelTransformation.getPosition().x,
                            model1.modelTransformation.getPosition().y,
                            model1.modelTransformation.getPosition().z - .1f
                    )
            );*/
            model2.modelTransformation.rotate(new Vector3f(3,3,3));

        }


        ApplicationEventsManager.getInstance().onApplicationEnded();
        displayManager.destroy();
        Texture.clearTextures();

    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(Constants.BACKGROUND_COLOR.x, Constants.BACKGROUND_COLOR.y, Constants.BACKGROUND_COLOR.z, Constants.BACKGROUND_COLOR.w);
    }

}