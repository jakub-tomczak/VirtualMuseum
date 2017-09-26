package Wall;

import Models.Model;
import Models.ModelData;
import Shaders.ShaderProgram;
import Texturing.Texture;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Wall {
    private static final int SIZE = 20;
    private static final int VERTEX_COUNT = 64;

    private float x;
    private float z;
    List<Model> walls = new ArrayList<>();

    public Wall(float xCoords, float zCoords, ShaderProgram textureShader, Texture terrainTexture)
    {
        this.x = xCoords * SIZE;
        this.z = zCoords * SIZE;

        Model wallModel = new Model(textureShader, terrainTexture);

        //generuje dane dot modelu terenu
        generateWall(wallModel);

        //set height to 0
        wallModel.modelTransformation.changePosition(new Vector3f(
                wallModel.modelTransformation.getPosition().x,
                1.2f,
                wallModel.modelTransformation.getPosition().z));


        wallModel.loadToVAO();

        walls.add(wallModel);


    }

    private void generateWall(Model wallModel){
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }

        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        ModelData data = new ModelData(vertices, normals, textureCoords, indices);
        wallModel.setData(data);


         }


}
