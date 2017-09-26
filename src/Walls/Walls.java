package Walls;

import Models.Model;
import Models.ModelData;
import Shaders.ShaderProgram;
import Texturing.Texture;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Walls {
    private static final int SIZE = 20;
    private static final int VERTEX_COUNT = 64;

    private float x;
    private float z;
    List<Model> walls = new ArrayList<>();
    float wallWidth = 10f;

    public Walls(float xCoords, float zCoords, ShaderProgram textureShader, Texture terrainTexture)
    {
        this.x = 10f;
        this.z = 10f;

        float [] xCoordsArray  = {0f, wallWidth - .1f, -.1f, 0f};
        float [] zCoordsArray  = {.1f, 0f, wallWidth, .1f};
        float [] zAxisRotations = {0f, 90f, 0f, 90f};

        ///////////////////////////////
        for(int i=0;i<4;i++)
        {
            Model wallModel = new Model(textureShader, terrainTexture);

            //generuje dane dot modelu terenu
            generateWall(wallModel);

            //set height to 0
            wallModel.modelTransformation.changePosition(new Vector3f(
                    xCoordsArray[i],
                    2.5f,
                    zCoordsArray[i]));

         //   wallModel.modelTransformation.changeScale(new Vector3f(.5f, 1f, .124f));

            wallModel.modelTransformation.changeRotation(new Vector3f(90,0, zAxisRotations[i])); ///


            wallModel.loadToVAO();
            walls.add(wallModel);
        }
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
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * 20;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * 5f;
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

    public List<Model> getWalls() {
        return walls;
    }
}
