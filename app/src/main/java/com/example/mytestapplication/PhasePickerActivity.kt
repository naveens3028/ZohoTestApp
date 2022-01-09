package com.example.mytestapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mytestapplication.Utils.AppConstants
import kotlinx.android.synthetic.main.activity_phase.*
import java.util.ArrayList

class PhasePickerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase)

        if (!hasPermission(AppConstants.ALL_PERMISSIONS)) {
            requestPermission(
                AppConstants.ALL_PERMISSIONS,
                AppConstants.ALL_REQUEST_CODE
            )
        }

        phase1Btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("phase","phase1")
            startActivity(intent)
        }

        phase2Btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("phase","phase2")
            startActivity(intent)
        }
    }

    private fun requestPermission(permissionsList: Array<String>, requestCode: Int) {
        val permissionNeeded: MutableList<String> = ArrayList()
        for (permission in permissionsList) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionNeeded.add(permission)
            }
        }
        if (permissionNeeded.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionNeeded.toTypedArray(), requestCode
            )
        }
    }


    private fun hasPermission(permissionsList: Array<String>): Boolean {
        for (permission in permissionsList) {
            if (ActivityCompat.checkSelfPermission(this, permission.toString())
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppConstants.ALL_REQUEST_CODE) {
            val permissionResults: MutableList<Int> = ArrayList()
            for (grantResult in grantResults) {
                permissionResults.add(grantResult)
            }
            if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }


}