
use("Banking_Management")

db.createCollection("tbl_todo")

use("Banking_Management")

// insert one recode

db.tbl_todo.insertOne({
    "todo_title": "Review Loan Applications",
    "todo_desc": "Need to review 5 pending loan applications",
    "todo_date": new Date("2025-03-28T10:00:00"),
    "todo_admin_id": "3",
    "priority": "High"
  });

// insert may recode
  db.tbl_todo.insertMany([
    {
      "todo_title": "Customer Meeting",
      "todo_desc": "Meeting with VIP customer at 2 PM",
      "todo_date": new Date("2025-03-29T14:00:00"),
      "todo_admin_id": "1",
      "priority": "Medium"
    },
    {
      "todo_title": "System Maintenance",
      "todo_desc": "Schedule system maintenance for weekend",
      "todo_date": new Date("2025-03-30T18:00:00"),
      "todo_admin_id": "2",
      "priority": "Low"
    }
  ]);

//   update one recde

  db.tbl_todo.updateOne(
    { "todo_title": "Payment Pending" },
    {
      $set: {
        "priority": "High",
        "status": "In Progress"
      }
    }
  );


  db.tbl_todo.updateMany(
    { "todo_admin_id": "3" },
    {
      $set: {
        "department": "Customer Service"
      }
    }
  );


// delete recode 


  db.tbl_todo.deleteOne({ "todo_title": "Appointment fot FD" });

  db.tbl_todo.deleteMany({ "priority": "Low" });





  db.tbl_todo.aggregate([
    {
      $match: {
        "todo_date": { $gte: new Date("2025-03-01") }
      }
    },
    {
      $count: "tasks_this_month_is_perform_by_admin"
    }
  ]);


  db.tbl_todo.aggregate([
    {
      $sort: { "todo_date": -1 }
    },
    {
      $limit: 5
    }
  ]);




db.tbl_todo.insertOne({
  "todo_title": "Appointment fot FD",
  "todo_desc": "appointment for fd at 1:00 pm",
  "todo_date": "2025-03-22 15:59:44",
  "todo_admin_id": "3"
});


db.tbl_todo.insertOne({
    "todo_title": "Appointment fot FD",
    "todo_desc": "appointment for fd at 1:00 pm",
    "todo_date": "2023-03-22 15:59:44",
    "todo_admin_id": "4"
  });

  db.tbl_todo.insertOne({
    "todo_title": "Appointment fot FD",
    "todo_desc": "appointment for fd at 1:00 pm",
    "todo_date": "2024-05-29 15:59:44",
    "todo_admin_id": "4"
  });

db.tbl_todo.find();

db.tbl_todo.find({ todo_admin_id: { $in: ["2"] } })







