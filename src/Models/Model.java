package Models;

import Shaders.TexturedShader;
import Light.Light;
import Shaders.ShaderProgram;
import Texturing.Texture;
import Utils.ObjectLoader;
import Utils.VBOManager;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Model {

    private int vaoID;
    private int vertexCount;

    ModelData data;
    public ModelTransformation modelTransformation;
    private ShaderProgram modelShader;
    public Texture modelTexture;
    //ustawia domyslna transformacje na
    //pozycje 0,0,0
    //rotacje 0,0,0
    //skale 1,1,1
    private Model()
    {
        modelTransformation = new ModelTransformation(
                new Vector3f(0  ,0,0),    //pozycja
                new Vector3f(1,1,1),    //rotacja
                new Vector3f(.5f,.5f,.5f));   //skala
    }

    public Model(ShaderProgram shader, Texture texture)
    {
        this();
        this.modelShader = shader;
        this.modelTexture = texture;
    }



    //to się przyda do ładowania modeli z pliku,
    //wystarczy podać nazwę pliku, który jest w folderze modelsData, bez rozszerzenia
    public Model(String filename, TexturedShader shader, Texture texture, ObjectLoader.FacesMode facesMode)
    {
        this();
        ModelData modelData = ObjectLoader.loadModel(filename, facesMode); //to facesMode niech zostanie
        //załadowanie obiektu z wierzchołkami, normlanymi i współrzędnymi teksturowania
        this.data = modelData;
        this.modelTexture = texture; //przypisanie do vao jest wczesnije
        this.modelShader = shader;
        //ładuj do vao
        loadToVAO();

    }

    ///ładowanie tekstury
    public void loadTexture()
    {
        if(modelTexture != null)
        {
            modelTexture.setTextureActive();
        }else
        {
            System.out.println("model texturer jest pusty!");
        }
    }

    ///ładowanie shadera

    public void startUsingShader()
    {
        if(modelShader != null)
        {
            modelShader.startUsingShader();
        }
    }

    public void stopUsingShader()
    {
        if(modelShader != null)
        {
            modelShader.stopUsingShader();
        }
    }

    public void loadTransformationMatrix()
    {
        modelShader.loadTransformationLocation(modelTransformation.getTansformationMatrix());
    }

    public void loadProjectionMatrix(Matrix4f projectionMatrix)
    {
        modelShader.startUsingShader();
        modelShader.loadProjectionMatix(projectionMatrix);
        modelShader.stopUsingShader();
    }

    public void loadViewMatrix(Matrix4f viewMatrix)
    {
        modelShader.loadViewMatrix(viewMatrix);
    }

    public void loadLight(Light light)
    {
        modelShader.loadLight(light);
    }


    //ładowanie do bufora VAO, wykonywane jednokrotnie po inicjalizacji obiektu
    //jeżeli obiekt nie ma być generowany to nie powinien być ładowany do bufora VAO
    public void loadToVAO()
    {
        VBOManager.getInstance().loadToVAO(this);
    }



    //gettery i settery
    public ModelData getData() {
        return data;
    }

    public void setData(ModelData data) {
        this.data = data;
    }

    public int getVertexCount() {
        if(data == null)
            return 0;
        return data.vertexCount;
    }


    public  int getVaoID()
    {
        return  vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

}
