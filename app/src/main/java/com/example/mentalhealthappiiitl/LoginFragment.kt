package com.example.mentalhealthappiiitl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mentalhealthappiiitl.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit private var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 456
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        /*val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )*/
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account?.email.toString()
                .endsWith("iiitl.ac.in")
        )
            updateUI(account)
        else
            showSnackBar("sign in not succesful login with clg id", activity)
    }

    override fun onResume() {
        super.onResume()
        binding.loginButton.setOnClickListener {
            signIn()
        }
        binding.logoutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signIn() {
        var signInIntent: Intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            var account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java);

            // Signed in successfully, show authenticated UI.
            if (account.email.toString().endsWith("iiitl.ac.in"))
                updateUI(account)
            else {
                mGoogleSignInClient.signOut().addOnCompleteListener {
                    showSnackBar("sign in not succesful login with clg id", activity)
                }
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(
                "TAG",
                "signInResult:failed code=" + e.getStatusCode() + "\t" + e.stackTrace.toString() + "\t" + e.status.toString() + "\t" + e.localizedMessage.toString()
            )
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account == null)
            showSnackBar("Error Please Sign In Again", activity)
        else
            showSnackBar(
                "Succesful login using your google account " + account.displayName,
                activity
            )
    }

    private fun showSnackBar(message: String?, activity: Activity?) {
        if (null != activity && null != message) {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(OnCompleteListener {
            showSnackBar("Succesfully signed out", activity)
        })

    }
}