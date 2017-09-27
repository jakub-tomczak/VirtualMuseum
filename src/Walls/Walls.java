package Walls;

import Models.Model;
import Models.ModelData;
import Shaders.ShaderProgram;
import Texturing.Texture;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Walls {
    private static final int VERTEX_COUNT = 32;

    List<Model> walls = new ArrayList<>();
    float wallWidth = 10f;

    public Walls(ShaderProgram textureShader, Texture terrainTexture)
    {


        float [] xCoordsArray  = {0, 0, wallWidth, wallWidth};
        float [] zCoordsArray  = {0, wallWidth, 0, wallWidth};
        float [] axisRotations = {0, 90f, -90f, 180f};

        for(int i=0;i<4;i++)
        {
            Model wallModel = new Model(textureShader, terrainTexture);

            //generuje dane dot modelu terenu
            generateWall(wallModel);

            //set height to 0
            wallModel.modelTransformation.changePosition(new Vector3f(
                    xCoordsArray[i],
                    0f,
                    zCoordsArray[i]));


            wallModel.modelTransformation.changeRotation(new Vector3f(0,axisRotations[i], 0));


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
                vertices[vertexPointer*3+1] = (float)i/((float)VERTEX_COUNT - 1) * 5f;
                vertices[vertexPointer*3+2] = 0;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 0;
                normals[vertexPointer*3+2] = 1;
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
