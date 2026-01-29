// Import required RMI classes
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

// Import data structures
import java.util.HashMap;
import java.util.Map;

/*
 * StudentServiceImpl
 * ------------------
 * This class implements the remote interface StudentService.
 * It contains the actual business logic executed on the server side.
 */
public class StudentServiceImpl extends UnicastRemoteObject implements StudentService {

    /*
     * Map to store student details
     * Key   → Roll Number (Integer)
     * Value → Another Map containing student data (name, marks)
     */
    Map<Integer, Map<String, Object>> students;

    /*
     * Constructor
     * -----------
     * Initializes the student database.
     * Must throw RemoteException because this is a remote object.
     */
    protected StudentServiceImpl() throws RemoteException {

        // Initialize HashMap
        students = new HashMap<>();

        // Add sample student records
        students.put(101, Map.of("name", "Arun", "marks", 85));
        students.put(102, Map.of("name", "Divya", "marks", 72));
        students.put(103, Map.of("name", "Karthik", "marks", 39));
    }

    /*
     * Remote Method: getStudentDetails
     * --------------------------------
     * Returns student name and marks for a given roll number.
     */
    public Map<String, Object> getStudentDetails(int rollNo) {

        // Response map to send back to client
        Map<String, Object> response = new HashMap<>();

        // Check if student exists
        if (students.containsKey(rollNo)) {

            // Success response
            response.put("status", "success");
            response.put("data", students.get(rollNo));

            // Server-side log
            System.out.println(
                "[SERVER] Sent student details for Roll No: " + rollNo
            );

        } else {
            // Error response
            response.put("status", "error");
            response.put("message", "Student not found");
        }

        return response;
    }

    /*
     * Remote Method: getGrade
     * -----------------------
     * Calculates and returns grade based on marks.
     */
    public Map<String, Object> getGrade(int rollNo) {

        Map<String, Object> response = new HashMap<>();

        // Validate roll number
        if (!students.containsKey(rollNo)) {
            response.put("status", "error");
            response.put("message", "Student not found");
            return response;
        }

        // Fetch marks
        int marks = (int) students.get(rollNo).get("marks");

        // Determine grade using conditional operator
        String grade =
                (marks >= 80) ? "A" :
                (marks >= 60) ? "B" :
                (marks >= 40) ? "C" : "Fail";

        // Success response
        response.put("status", "success");
        response.put("grade", grade);

        // Server-side log
        System.out.println(
            "[SERVER] Grade calculated for Roll No: " + rollNo
        );

        return response;
    }

    /*
     * Remote Method: isPass
     * ---------------------
     * Checks whether the student has passed or failed.
     */
    public Map<String, Object> isPass(int rollNo) {

        Map<String, Object> response = new HashMap<>();

        // Validate roll number
        if (!students.containsKey(rollNo)) {
            response.put("status", "error");
            response.put("message", "Student not found");
            return response;
        }

        // Pass condition: marks >= 40
        boolean pass =
                (int) students.get(rollNo).get("marks") >= 40;

        // Success response
        response.put("status", "success");
        response.put("isPass", pass);

        // Server-side log
        System.out.println(
            "[SERVER] Pass status checked for Roll No: " + rollNo
        );

        return response;
    }
}
