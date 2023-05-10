package com.example.eduxo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CusInsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var etLastname: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.cusFname)
        etEmpAge = findViewById(R.id.cusEmail)
        etLastname = findViewById(R.id.cusLname)
        etEmpSalary = findViewById(R.id.cusPassword)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val empName = etEmpName.text.toString()
        val empLastname = etLastname.text.toString()
        val empAge = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()


        if (empName.isEmpty()) {
            etEmpName.error = "Please enter first name"
        }
        if (empLastname.isEmpty()) {
            etLastname.error = "Please enter Last name"
        }
        if (empAge.isEmpty()) {
            etEmpAge.error = "Please enter email"
        }
        if (empSalary.isEmpty()) {
            etEmpSalary.error = "Please enter password"
        }

        val empId = dbRef.push().key!!

        val employee = CustomerModel(empId, empName,empLastname, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etLastname.text.clear()
                etEmpAge.text.clear()
                etEmpSalary.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}