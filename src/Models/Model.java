package Models;

import Utils.Constants;
import Utils.ObjectLoader;
import Utils.VBOManager;

public class Model {


    private int vaoID;
    private int vertexCount;

    ModelData data;

    public Model(float[] vertices, int[] indices) {
        data = new ModelData(indices.length);

        data.setIndices(indices);
        data.setVertices(vertices);

        load();
    }


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

    public Model(String filename)
    {
        ModelData modelData = ObjectLoader.loadModel(filename, ObjectLoader.FacesMode.VertexNormalIndices);
        this.data = modelData;
        load();
    }



    public void load()
    {
        loadToVAO();
    }

    private void loadToVAO()
    {
        VBOManager.getInstance().loadToVAO(this);
    }


    public void loadModel(String fileName) {
        String fullPath = Constants.MODELS_PATH + fileName;
        ObjectLoader objectLoader = new ObjectLoader();

        //use object loader to put data into vertices, normals and texCoords lists

    }

    public  int getVaoID()
    {
        return  vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

}
