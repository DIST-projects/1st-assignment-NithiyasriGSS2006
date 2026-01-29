"""
RPC SERVER PROGRAM
------------------
This server provides student-related services using XML-RPC.
It is hosted on a cloud server (AWS EC2) and listens on port 8000.

Remote Procedures Provided:
1. getStudentDetails
2. getGrade
3. isPass
"""

from xmlrpc.server import SimpleXMLRPCServer

# ------------------------------
# Sample Student Database
# ------------------------------
students = {
    101: {"name": "Arun", "marks": 85},
    102: {"name": "Divya", "marks": 72},
    103: {"name": "Karthik", "marks": 39}
}


def get_student_details(roll_no):
    """
    Returns student name and marks for a given roll number
    """
    try:
        roll_no = int(roll_no)
        if roll_no in students:
            print(f"[SERVER] Fetching details for Roll No: {roll_no}")
            return {
                "status": "success",
                "data": students[roll_no]
            }
        return {"status": "error", "message": "Student not found"}
    except Exception as e:
        return {"status": "error", "message": str(e)}


def get_grade(roll_no):
    """
    Returns grade based on student marks
    """
    try:
        roll_no = int(roll_no)
        if roll_no not in students:
            return {"status": "error", "message": "Student not found"}

        marks = students[roll_no]["marks"]

        # Grade calculation logic
        grade = (
            "A" if marks >= 80 else
            "B" if marks >= 60 else
            "C" if marks >= 40 else
            "Fail"
        )

        print(f"[SERVER] Calculating grade for Roll No: {roll_no}")
        return {"status": "success", "grade": grade}

    except Exception as e:
        return {"status": "error", "message": str(e)}


def is_pass(roll_no):
    """
    Checks whether the student has passed or failed
    """
    try:
        roll_no = int(roll_no)
        if roll_no not in students:
            return {"status": "error", "message": "Student not found"}

        pass_status = students[roll_no]["marks"] >= 40
        print(f"[SERVER] Checking pass status for Roll No: {roll_no}")

        return {"status": "success", "isPass": pass_status}

    except Exception as e:
        return {"status": "error", "message": str(e)}


# ------------------------------
# RPC Server Initialization
# ------------------------------
server = SimpleXMLRPCServer(
    ("0.0.0.0", 8000),  # Bind to all interfaces for cloud access
    allow_none=True
)

print("RPC Student Service running on port 8000 (Cloud Ready)...")

# Register remote procedures
server.register_function(get_student_details, "getStudentDetails")
server.register_function(get_grade, "getGrade")
server.register_function(is_pass, "isPass")

# Start server
server.serve_forever()

