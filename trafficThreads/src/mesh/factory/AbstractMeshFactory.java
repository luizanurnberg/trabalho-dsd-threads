package mesh.factory;

import mesh.Mesh;

public abstract class AbstractMeshFactory {
    public abstract Mesh addMesh(int lines, int columns);
}
