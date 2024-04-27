package mesh.factory;

import mesh.Mesh;
import mesh.MeshByMonitor;

public class MeshByMonitorFactory extends AbstractMeshFactory{
    @Override
    public Mesh addMesh(int lines, int columns) {
        return new MeshByMonitor(lines, columns);
    }
}
