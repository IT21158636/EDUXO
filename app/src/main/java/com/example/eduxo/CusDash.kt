package com.example.eduxo
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CusDash : AppCompatActivity() {

    private lateinit var btnInsertData: Button

    private lateinit var btnFetchData: Button
    private lateinit var btnSign: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()


        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)


        btnInsertData.setOnClickListener {
            val intent = Intent(this, CusInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, CusFetchingActivity::class.java)
            startActivity(intent)
        }






    }
}