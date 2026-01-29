import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface StudentService extends Remote {
    Map<String, Object> getStudentDetails(int rollNo) throws RemoteException;
    Map<String, Object> getGrade(int rollNo) throws RemoteException;
    Map<String, Object> isPass(int rollNo) throws RemoteException;
}
