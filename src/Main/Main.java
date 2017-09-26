package Main;

import Models.Model;
import Light.*;
import java.util.*;
import Models.ModelsRenderer;
import Shaders.TexturedShader;
import Shaders.ShaderProgram;
import Shaders.TerrainShader;
import Terrain.Terrain;
import Texturing.Texture;
import Camera.Camera;
import Utils.ApplicationEventsManager;
import Utils.Constants;
import Utils.DisplayManager;
import Utils.ObjectLoader;
import Walls.Walls;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

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

        Camera mainCamera = new Camera(new Vector3f(5,1,5));

        List<Light>lights = new ArrayList<Light>();
       // lights.add(new Light(new Vector3f(10,0,0),new Vector3f(1,1,1))); // pozycja i kolor
        lights.add(new Light(new Vector3f(10,10,20),new Vector3f(1,1,1)));
        //lights.add(new Light(new Vector3f(100,10,0),new Vector3f(1,1,1)));
       // lights.add(new Light(new Vector3f(-100,-5,-50),new Vector3f(1,1,1)));
        ModelsRenderer renderer = new ModelsRenderer();
        renderer.useCamera(mainCamera);
        renderer.useLight(lights);
        TexturedShader shader1 = new TexturedShader("v_textured", "f_textured");


        //TODO:
        //projectionMatrix bindować do zmiennej jednorodnej tylko raz

        //będzie kilka obrazów, więc aby nie ładować za każdym razem modelu lepiej przerzucić dane
        //albo podpiąć się pod ten sam vao

        //shader jest przypisywany do modelu
        //w renderer.renderModels jest wywyoływane startUsingShader i stopUsingShader
        //każdy obiekt 3D to instancja klasy Model
        //posiada ona ModelData - do przechowywania informacji o wierzchołkach
        //ModelTransformation - do przechowywania informacji o transformacji obiektu
        //TexturedShader - do przechowywania informacji o shaderze
        Texture texture = Texture.loadTexture("bohomaz", 0);
        Texture texture1 = Texture.loadTexture("bohomaz", 0);
        Texture texture2 = Texture.loadTexture("marble3", 0);
        Texture texture3 = Texture.loadTexture("frameWood", 0);
        Texture texture4 = Texture.loadTexture("dama", 0);
        Texture texture5 = Texture.loadTexture("rejtan", 0);
        texture1.setReflectivity(0.1f);
        texture1.setCameraReflectDistance(5000);
        texture2.setReflectivity(1f);
        texture2.setCameraReflectDistance(10);

        //dach
        Texture roofTexture = Texture.loadTexture("nightSky1024x1024", 0);
        Model roof = new Model("dach", shader1, roofTexture, ObjectLoader.FacesMode.VertexNormalIndices);
        roof.modelTransformation.changePosition(new Vector3f(5,2.4f,5));
     //   column.modelTransformation.changeScale(new Vector3f(.25f,.25f,.25f));
        renderer.addModelsToRender(roof);

        //instalacja
        Texture artObjectTexture = Texture.loadTexture("rust", 0);
        Model artObject = new Model("instalacja", shader1, artObjectTexture, ObjectLoader.FacesMode.VertexNormalIndices);
        artObject.modelTransformation.changeScale(new Vector3f(.1f,.1f,.1f));
        artObject.modelTransformation.changePosition(new Vector3f(4,.4f,4));
        renderer.addModelsToRender(artObject);

        //kolumna
        Texture columnTexture = Texture.loadTexture("simple2",0);
        Model column = new Model("kolumna", shader1, columnTexture, ObjectLoader.FacesMode.VertexNormalIndices);
        column.modelTransformation.changePosition(new Vector3f(1,.4f,1));
        column.modelTransformation.changeScale(new Vector3f(.25f,.25f,.25f));
        renderer.addModelsToRender(column);

        //smok
        Texture dragonTexture = Texture.loadTexture("dragonScale256x256", 0);
        Model dragon = new Model("dragon", shader1, dragonTexture, ObjectLoader.FacesMode.VertexNormalIndices);
        dragon.modelTransformation.changeScale(new Vector3f(.2f,.2f,.2f));
        dragon.modelTransformation.changePosition(new Vector3f(5,.4f,.5f));
        dragon.modelTransformation.rotate(new Vector3f(0,0,0));
        renderer.addModelsToRender(dragon);

        //lampy
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                Texture lampTexture = Texture.loadTexture("simple2", 0);
                Model lamp = new Model("lamp", shader1, lampTexture, ObjectLoader.FacesMode.VertexNormalIndices);
                lamp.modelTransformation.changePosition(new Vector3f(2.5f + i*5f,2f,2.5f + j*5f));
                renderer.addModelsToRender(lamp);

            }
        }


        //lampa
      /*  Texture lampTexture = Texture.loadTexture("simple2", 0);
        Model lamp = new Model("lamp2", shader1, lampTexture, ObjectLoader.FacesMode.VertexNormalIndices);
        lamp.modelTransformation.changePosition(new Vector3f(2.5f,5f,2.5f));
        renderer.addModelsToRender(lamp);
        */

        //dawid
        Model dawid = new Model("david6", shader1, texture2, ObjectLoader.FacesMode.VertexNormalIndices);
        dawid.modelTransformation.changePosition(new Vector3f(5f,1.18f,5f));
        dawid.modelTransformation.changeScale(new Vector3f(.3f, .3f, .3f));
        renderer.addModelsToRender(dawid);

        //ramka damy
        Model frame = new Model("woodenFrame", shader1, texture3, ObjectLoader.FacesMode.VertexNormalIndices);
        frame.modelTransformation.changePosition(new Vector3f(9.7f,1.5f,4f));
        frame.modelTransformation.changeScale(new Vector3f(.4f, .4f, 0.3f));
        frame.modelTransformation.rotate(new Vector3f(0,180,1));
        renderer.addModelsToRender(frame);

        //dama z gronostajem
        Model lady = new Model("dama", shader1, texture4, ObjectLoader.FacesMode.VertexNormalIndices);
        lady.modelTransformation.changePosition(new Vector3f(9.55f,2.35f,4.18f));
        lady.modelTransformation.changeScale(new Vector3f(.28f, .4f, .3f));
        lady.modelTransformation.rotate(new Vector3f(0,28,180));
        renderer.addModelsToRender(lady);

        //ramka rejtana
        Model frame2 = new Model("woodenFrame", shader1, texture3, ObjectLoader.FacesMode.VertexNormalIndices);
        frame2.modelTransformation.changePosition(new Vector3f(9.75f,1.5f,7f));
        frame2.modelTransformation.changeScale(new Vector3f(.4f, .6f, 0.45f));
        frame2.modelTransformation.rotate(new Vector3f(90,180,0));
        renderer.addModelsToRender(frame2);

        //rejtan
        Model rejtan = new Model("rejtan", shader1, texture5, ObjectLoader.FacesMode.VertexNormalIndices);
        rejtan.modelTransformation.changePosition(new Vector3f(9.7f,1.5f,7f));
        rejtan.modelTransformation.changeScale(new Vector3f(.28f, .4f, .35f));
       // rejtan.modelTransformation.rotate(new Vector3f(0,28,180));
        renderer.addModelsToRender(rejtan);

        //podstawka
        Model stand = new Model("mat", shader1, texture2, ObjectLoader.FacesMode.VertexNormalIndices);
        stand.modelTransformation.changePosition(new Vector3f(1,-0.2f,8));
        stand.modelTransformation.changeScale(new Vector3f(.5f,.5f,.5f));
        renderer.addModelsToRender(stand);



        Texture terrainTexture = Texture.loadTexture("woodenSurface", 0);
        ShaderProgram multipleTextureShader = new TerrainShader("v_terrain", "f_terrain");

        //sciany
        Texture wallTexture = Texture.loadTexture("wallBricks128x128", 0);



        //zmiana położenia oraz rotacji modelu
       /* model1.modelTransformation.changePosition(new Vector3f(.5f, .5f, -1f));
        model1.modelTransformation.changeRotation(new Vector3f(1, 1, 1));         //obroty sa podawane w kątach, podczas tworzenia macierzy transformacji są przeliczane na radiany
        model1.modelTransformation.changeScale(new Vector3f(.25f, .25f, .25f));*/

        //wystarczy ustawić dla jednego modelu używającego tego samego shadera - chyba
        dragon.loadProjectionMatrix(renderer.getProjectionMatrix());

        //dopisz model do listy modeli które sa renderowane w każdej klatce
        // renderer.addModelsToRender(model);


        Mouse.setGrabbed(true);

        //podłoga
        terrainTexture.setCameraReflectDistance(1);
        terrainTexture.setReflectivity(0);
        Terrain terrain = new Terrain(1,1, multipleTextureShader, terrainTexture);
        terrain.getTerrainModel().loadProjectionMatrix(renderer.getProjectionMatrix());
        terrain.getTerrainModel().modelTransformation.changePosition(new Vector3f(0,.3f,0));

        Walls wallsGenerator = new Walls(.1f,.1f, multipleTextureShader, wallTexture);

        while (!Display.isCloseRequested()) {

            prepare();
            renderer.renderTerrain(terrain);
            renderer.renderWalls(wallsGenerator);
            renderer.renderModels();
            displayManager.update();



          /* model2.modelTransformation.changePosition(
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

          artObject.modelTransformation.rotate(new Vector3f(0,2,0));
          dawid.modelTransformation.rotate(new Vector3f(0,2,0));

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