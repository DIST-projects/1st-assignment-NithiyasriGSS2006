import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            StudentService service = new StudentServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("StudentService", service);

            System.out.println("✅ RMI Student Service running on port 1099...");
        } catch (Exception e) {
            System.out.println("❌ Server Error: " + e.getMessage());
        }
    }
}
