package com.example.eduxo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class CusFetchingActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<CustomerModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cusfetching)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<CustomerModel>()

        getEmployeesData()

    }

    private fun getEmployeesData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(CustomerModel::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = CusAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : CusAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@CusFetchingActivity, CustomerDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("empId", empList[position].empId)
                            intent.putExtra("empName", empList[position].empName)
                            intent.putExtra("empLastname", empList[position].emplastname)
                            intent.putExtra("empAge", empList[position].empAge)
                            intent.putExtra("empSalary", empList[position].empSalary)
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}