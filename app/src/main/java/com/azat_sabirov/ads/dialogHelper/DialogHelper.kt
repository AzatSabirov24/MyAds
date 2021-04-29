package com.azat_sabirov.ads.dialogHelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.azat_sabirov.ads.MainActivity
import com.azat_sabirov.ads.R
import com.azat_sabirov.ads.accountHelper.AccountHelper
import com.azat_sabirov.ads.databinding.SignDialogBinding

class DialogHelper(private val act: MainActivity) {
    val accHelper = AccountHelper(act)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)
        setDialogState(index, rootDialogElement)

        val dialog = builder.create()

        rootDialogElement.btSignUpIn.setOnClickListener {
            stateOfViews(index,rootDialogElement,dialog)
        }

        rootDialogElement.btForgetPass.setOnClickListener {
            setOnClickResetPassword(rootDialogElement,dialog)
        }

        rootDialogElement.btGoogleSignIn.setOnClickListener {
            accHelper.signInWithGoogle()
        }
        dialog.show()
    }

    private fun setOnClickResetPassword(rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {

        if(rootDialogElement.edSignEmail.text.isNotEmpty()){
            act.mAuth.sendPasswordResetEmail(rootDialogElement.edSignEmail.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(act, R.string.email_reset_password_was_send, Toast.LENGTH_LONG).show()
                dialog?.dismiss()
                }
            }
        }
                else {
                    rootDialogElement.tvDialogMassage.visibility = View.VISIBLE
                }
    }

    private fun stateOfViews(index: Int, rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {
        if (index == DialogConst.SIGN_UP_STATE) {
            accHelper.signUpWithEmail(
                rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString()
            )

        } else {
            accHelper.signInWithEmail(
                rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString()
            )
        }
        dialog?.dismiss()
    }

    private fun setDialogState(index: Int, rootDialogElement: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
        } else {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
            rootDialogElement.btForgetPass.visibility = View.VISIBLE
        }
    }
}