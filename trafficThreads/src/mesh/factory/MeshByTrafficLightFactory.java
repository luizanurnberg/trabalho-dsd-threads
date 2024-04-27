package mesh.factory;

import mesh.Mesh;
import mesh.MeshByTrafficLight;

public class MeshByTrafficLightFactory extends AbstractMeshFactory{
    @Override
    public Mesh addMesh(int lines, int columns) {
        return new MeshByTrafficLight(lines, columns);
    }
}
