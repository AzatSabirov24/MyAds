package com.azat_sabirov.ads

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.azat_sabirov.ads.act.EditAdsAct
import com.azat_sabirov.ads.databinding.ActivityMainBinding
import com.azat_sabirov.ads.dialogHelper.DialogConst
import com.azat_sabirov.ads.dialogHelper.DialogHelper
import com.azat_sabirov.ads.dialogHelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvAccount: TextView
    private lateinit var rootElement: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    dialogHelper.accHelper.signInFireBaseWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("MyLog", "API error: ${e.message}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_new_ads){
            val i = Intent(this, EditAdsAct::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    private fun init() {
        setSupportActionBar(rootElement.mainContent.toolbar)
        val toggle =
            ActionBarDrawerToggle(
                this,
                rootElement.drawerLayout,
                rootElement.mainContent.toolbar,
                R.string.open,
                R.string.close
            )
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState();
        rootElement.navView.setNavigationItemSelectedListener(this)
        tvAccount = rootElement.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ads -> {
                Toast.makeText(this, "my_ads", Toast.LENGTH_SHORT).show();
            }
            R.id.id_car -> {

            }
            R.id.id_pc -> {

            }
            R.id.id_smart -> {

            }
            R.id.id_dm -> {

            }
            R.id.sign_up -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
                uiUpdate(mAuth.currentUser)
            }
            R.id.sign_in -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
                uiUpdate(mAuth.currentUser)

            }
            R.id.sign_out -> {
                uiUpdate(null)
                mAuth.signOut()
            }

        }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?) {
        tvAccount.text = if (user == null) {
            resources.getString(R.string.not_reg)
        } else {
            user.email
        }
    }
}