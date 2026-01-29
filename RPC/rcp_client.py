"""
RPC CLIENT PROGRAM
------------------
This client connects to a cloud-hosted RPC server using XML-RPC.
It remotely invokes procedures to:
1. Fetch student details
2. Get student grade
3. Check pass/fail status

Server is hosted on AWS EC2.
"""

import xmlrpc.client


def print_line():
    """Prints a separator line for better output readability"""
    print("-" * 50)


try:
    # Display connection message
    print_line()
    print("Connecting to Cloud RPC Student Service...")
    print_line()

    # Create XML-RPC proxy object using EC2 Public IP and port
    proxy = xmlrpc.client.ServerProxy(
        "http://16.171.19.213:8000/",
        allow_none=True
    )

    # Student roll number to query
    roll_no = 101
    print(f"Sending request for Roll Number: {roll_no}")
    print_line()

    # ------------------------------
    # 1️⃣ Remote Procedure Call: Get Student Details
    # ------------------------------
    response = proxy.getStudentDetails(roll_no)

    if response["status"] == "success":
        print("Student Details Retrieved Successfully")
        print(f"   Name  : {response['data']['name']}")
        print(f"   Marks : {response['data']['marks']}")
    else:
        print("Error:", response["message"])

    print_line()

    # ------------------------------
    # 2️⃣ Remote Procedure Call: Get Grade
    # ------------------------------
    response = proxy.getGrade(roll_no)

    if response["status"] == "success":
        print("Grade Information")
        print(f"   Grade : {response['grade']}")
    else:
        print("Error:", response["message"])

    print_line()

    # ------------------------------
    # 3️⃣ Remote Procedure Call: Pass / Fail Status
    # ------------------------------
    response = proxy.isPass(roll_no)

    if response["status"] == "success":
        status = "PASS" if response["isPass"] else "FAIL"
        print("Examination Result")
        print(f"   Status : {status}")
    else:
        print("Error:", response["message"])

    print_line()

    # Final success message
    print("All remote procedure calls executed successfully.")
    print("Data fetched from Cloud-hosted RPC Server")

except Exception as e:
    # Handles network / server connection issues
    print("RPC Connection Failed")
    print("Reason:", e)

