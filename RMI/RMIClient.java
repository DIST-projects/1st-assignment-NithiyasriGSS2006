// Import classes to connect to RMI registry
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Import Map for receiving structured data
import java.util.Map;

/*
 * RMIClient
 * ---------
 * This program acts as the RMI client.
 * It runs on the local system and communicates with
 * the Student RMI Server running on AWS EC2.
 */
public class RMIClient {

    /*
     * Utility method to print a separator line
     * (Used only for formatted output)
     */
    private static void line() {
        System.out.println("--------------------------------------------------");
    }

    /*
     * Main method – entry point of the RMI client
     */
    public static void main(String[] args) {
        try {

            // Display connection header
            line();
            System.out.println(" Connecting to Cloud RMI Student Service...");
            line();

            /*
             * Step 1: Connect to the RMI registry
             * - Public IP of EC2 instance
             * - Default RMI registry port = 1099
             */
            Registry registry = LocateRegistry.getRegistry(
                    "16.171.19.213", 1099
            );

            /*
             * Step 2: Look up the remote object by name
             * "StudentService" must match the name used in server bind()
             */
            StudentService service =
                    (StudentService) registry.lookup("StudentService");

            // Roll number to be queried
            int rollNo = 101;

            System.out.println(
                " Requesting data for Roll No: " + rollNo
            );
            line();

            /*
             * Step 3: Invoke remote method to get student details
             */
            Map<String, Object> details =
                    service.getStudentDetails(rollNo);

            if (details.get("status").equals("success")) {

                // Extract student data from response
                Map data = (Map) details.get("data");

                System.out.println(" Student Details Retrieved");
                System.out.println("   Name  : " + data.get("name"));
                System.out.println("   Marks : " + data.get("marks"));

            } else {
                System.out.println(
                    " Error: " + details.get("message")
                );
            }

            line();

            /*
             * Step 4: Invoke remote method to get grade
             */
            Map<String, Object> grade =
                    service.getGrade(rollNo);

            if (grade.get("status").equals("success")) {
                System.out.println(
                    " 🎓 Grade : " + grade.get("grade")
                );
            } else {
                System.out.println(
                    " Error: " + grade.get("message")
                );
            }

            line();

            /*
             * Step 5: Invoke remote method to check pass/fail status
             */
            Map<String, Object> pass =
                    service.isPass(rollNo);

            if (pass.get("status").equals("success")) {

                // Convert boolean result to readable output
                String result =
                        (boolean) pass.get("isPass")
                                ? "PASS"
                                : "FAIL";

                System.out.println(" Result : " + result);

            } else {
                System.out.println(
                    " Error: " + pass.get("message")
                );
            }

            // Final output
            line();
            System.out.println(
                "All RMI method calls executed successfully."
            );
            System.out.println(
                "Data received from Cloud-hosted RMI Server"
            );

        } catch (Exception e) {

            /*
             * Handles network errors, registry errors,
             * or remote invocation failures
             */
            System.out.println(
                " RMI Client Error: " + e.getMessage()
            );
        }
    }
}
