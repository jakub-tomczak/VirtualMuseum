package Utils;

import Models.ModelData;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {

    public ObjectLoader() {

    }

    public enum FacesMode {
        None,
        VertexNormalIndices,                                //f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3
        VertexNormalIndicesWithoutTextureCoordinateIndices  //f v1//vn1 v2//vn2 v3//vn3
    }

    public static ModelData loadModel(String fileName, FacesMode facesMode) {
        if(facesMode == FacesMode.VertexNormalIndicesWithoutTextureCoordinateIndices)
            throw new RuntimeException("Facesmode VertexNormalIndicesWithoutTextureCoordinateIndices is not supported");
        FileReader fileReader;
        try {
            fileReader = new FileReader(new File(Constants.MODELS_PATH + fileName + ".obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Plik " + fileName + " nie istnieje!");
            return null;
        }


        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> texCoords = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        //used
        float[] verticesArray;
        float[] texCoordsArray = new float[0];  //głupia java
        float[] normalsArray = new float[0];
        int[] indicesArray;


        try {
            while (true) {

                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                if (line.startsWith("#"))
                    continue;

                String[] data = line.split(" ");

                if (line.startsWith("v ")) {
                    vertices.add(parseLine3(data));
                } else if (line.startsWith("vt")) {
                    texCoords.add(parseLine2(data));
                } else if (line.startsWith("vn")) {
                    normals.add(parseLine3(data));
                } else if (line.startsWith("f ")) {
                    texCoordsArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    //zakladamy, że faces sa na końcu pliku, format f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3
                    processFace(bufferedReader, data, indices, texCoords, normals, normalsArray, texCoordsArray, facesMode);
                }

            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //przepisanie do tablic
        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        //przepisanie wierzchołków
        int index = 0;
        for (Vector3f vector3f : vertices) {
            verticesArray[index++] = vector3f.x;
            verticesArray[index++] = vector3f.y;
            verticesArray[index++] = vector3f.z;
        }

        //przepisanie indeksów
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return new ModelData(verticesArray, normalsArray, texCoordsArray, indicesArray);

    }

    private static void processFace(BufferedReader reader,
                                    String[] data,
                                    List<Integer> indices,
                                    List<Vector2f> texCoords,
                                    List<Vector3f> normals,
                                    float[] normalsArray,
                                    float[] texCoordsArray,
                                    FacesMode facesMode) throws IOException {

        String line;

        String splitChar = "/";

        if (facesMode == FacesMode.VertexNormalIndicesWithoutTextureCoordinateIndices)
            splitChar = "//";

        do {

            for (int i = 1; i < 4; i++) {
                String[] vertex = data[i].split(splitChar);
                try {
                    processVertex(vertex, indices, texCoords, normals, normalsArray, texCoordsArray, facesMode);

                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            line = reader.readLine();
            if(line == null)
                break;
            data = line.split(" ");
            if (!line.startsWith("f ")) {
                System.out.println("Line doesn't start with f");
                continue;

            }
        } while (line != null);

    }


    private static Vector3f parseLine3(String[] data) {
        return new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
    }

    private static Vector2f parseLine2(String[] data) {
        return new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2]));
    }


    //processes faces
    private static void processVertex(String[] data,
                                      List<Integer> indices,
                                      List<Vector2f> texCoords,
                                      List<Vector3f> normals,
                                      float[] normalsArray,
                                      float[] texCoordsArray, FacesMode facesMode) throws NumberFormatException {

        //indeksowanie zaczyna się w openGL od 1, a tablicy od 0
        int vertexIndex = Integer.parseInt(data[0]) - 1;

        indices.add(vertexIndex);
        if (facesMode == FacesMode.VertexNormalIndices) {
            Vector2f currentTex = texCoords.get(Integer.parseInt(data[1]) - 1);
            texCoordsArray[vertexIndex * 2] = currentTex.x;
            texCoordsArray[vertexIndex * 2 + 1] = currentTex.y;

        }

        //openGL zaczyna od top-left, blender od bottom-left
        Vector3f currentNorm = normals.get(Integer.parseInt(data[2]) - 1);
        normalsArray[vertexIndex * 3] = currentNorm.x;
        normalsArray[vertexIndex * 3 + 1] = currentNorm.y;
        normalsArray[vertexIndex * 3 + 2] = currentNorm.z;
    }

}
