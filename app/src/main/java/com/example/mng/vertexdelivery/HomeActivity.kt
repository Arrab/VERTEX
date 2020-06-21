package com.example.mng.vertexdelivery

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.eventBus.CategoryClick
import com.example.mng.vertexdelivery.eventBus.DeliveryItemClick
import com.example.mng.vertexdelivery.eventBus.PickUpItemClick
import com.example.mng.vertexdelivery.eventBus.PickUpMenuItemClick
import com.example.mng.vertexdelivery.model.DeliveryModel
import com.example.mng.vertexdelivery.model.PickUpModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dmax.dialog.SpotsDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var dialog:AlertDialog?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_menu, R.id.nav_pickup_details
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Event

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onCategorySelected(event:CategoryClick){
        if(event.isSuccess){
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_pickup_list)
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onDeliveryItemClick(event:DeliveryItemClick){
        if (event.deliveryCategoryModel != null){
            dialog!!.show()
            FirebaseDatabase.getInstance().getReference(Common.DELIVERY_REF).child(event.deliveryCategoryModel.package_id!!)
                .addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        dialog!!.dismiss()
                        Toast.makeText(this@HomeActivity,"Error on HomeActivity ${p0.message}",Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()){
                            Common.deliverySelected = p0.getValue(DeliveryModel::class.java)
                            FirebaseDatabase.getInstance()
                                .getReference(Common.DELIVERY_REF)
                                .child(event.deliveryCategoryModel.package_id!!)
                                .addListenerForSingleValueEvent(object: ValueEventListener{
                                    override fun onCancelled(p0: DatabaseError) {
                                        Toast.makeText(this@HomeActivity,"Error on HomeActivity 1 ${p0.message}",Toast.LENGTH_SHORT).show()

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        if (p0.exists()){

                                            Common.deliverySelected = p0.getValue(DeliveryModel::class.java)
                                            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_delivery_details)


                                        } else{
                                            Toast.makeText(this@HomeActivity,"Error on HomeActivity 2 ${p0}",Toast.LENGTH_SHORT).show()
                                        }
                                        dialog!!.dismiss()
                                    }


                                })
                        } else{
                            dialog!!.dismiss()
                            Toast.makeText(this@HomeActivity,"Error on HomeActivity 3 ${p0}",Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onPickUpItemClick(event:PickUpItemClick){
        if(event.pickUpCategorModel != null){
            dialog!!.show()
            FirebaseDatabase.getInstance().getReference(Common.PICKUP_REF).child(event.pickUpCategorModel.task_id!!)
                .addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        dialog!!.dismiss()
                        Toast.makeText(this@HomeActivity,"Error on HomeActivity ${p0.message}",Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()){
                            Common.pickupSelected = p0.getValue(PickUpModel::class.java)
                            FirebaseDatabase.getInstance()
                                .getReference(Common.PICKUP_REF)
                                .child(event.pickUpCategorModel.task_id!!)
                                .addListenerForSingleValueEvent(object: ValueEventListener{
                                    override fun onCancelled(p0: DatabaseError) {
                                        Toast.makeText(this@HomeActivity,"Error on HomeActivity 1 ${p0.message}",Toast.LENGTH_SHORT).show()

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        if (p0.exists()){

                                            Common.pickupSelected = p0.getValue(PickUpModel::class.java)
                                            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_pickup_details)


                                        } else{
                                            Toast.makeText(this@HomeActivity,"Error on HomeActivity 2 ${p0}",Toast.LENGTH_SHORT).show()
                                        }
                                        dialog!!.dismiss()
                                    }


                                })
                        } else{
                            dialog!!.dismiss()
                            Toast.makeText(this@HomeActivity,"Error on HomeActivity 3 ${p0}",Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }
    }
}