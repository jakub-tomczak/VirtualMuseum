package Models;

import Shaders.Shader;
import Utils.Constants;
import Utils.ObjectLoader;
import Utils.VBOManager;

public class Model {
    Shader shaderProgram;


    private int vaoID;
    private int vertexCount;

    ModelData data;

    //do tworzenia modelu z palca,
    //używamy tutaj index buffering, ze względu na tablice z indeksami
    public Model(float[] vertices, int[] indices) {
        data = new ModelData(indices.length);

        data.setIndices(indices);
        data.setVertices(vertices);

        loadToVAO();
    }



    //to się przyda do ładowania modeli z pliku,
    //wystarczy podać nazwę pliku, który jest w folderze modelsData, bez rozszerzenia
    public Model(String filename)
    {
        ModelData modelData = ObjectLoader.loadModel(filename, ObjectLoader.FacesMode.VertexNormalIndices); //to facesMode niech zostanie
        //załadowanie obiektu z wierzchołkami, normlanymi i współrzędnymi teksturowania
        this.data = modelData;
        //ładuj do vao
        loadToVAO();
    }


    //ładowanie do bufora VAO, wykonywane jednokrotnie po inicjalizacji obiektu
    //jeżeli obiekt nie ma być generowany to nie powinien być ładowany do bufora VAO
    private void loadToVAO()
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
