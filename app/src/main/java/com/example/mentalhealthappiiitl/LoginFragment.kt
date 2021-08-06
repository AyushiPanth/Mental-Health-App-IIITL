package com.example.mentalhealthappiiitl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit private var googleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 456
    lateinit var auth: FirebaseAuth
    val TAG = "TAG"
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
        auth = FirebaseAuth.getInstance()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(getContext(), gso)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
//        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(getContext());
        var account = auth.currentUser
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
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    showSnackBar("SignIn with google is successful " + auth.currentUser?.displayName, activity)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    showSnackBar("SignIn not successful ",activity)
                    updateUI(null)
                }
            }
    }


    private fun showSnackBar(message: String?, activity: Activity?) {
        if (null != activity && null != message) {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account == null)
            showSnackBar("Error Please Sign In Again", activity)
        else
            showSnackBar(
                "Succesful login using your google account " + account.displayName,
                activity
            )
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener(OnCompleteListener {
            showSnackBar("Succesfully signed out", activity)
        })

    }
    /*
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

    private fun updateUI(account: FirebaseUser?) {
        if (account == null)
            showSnackBar("Error Please Sign In Again", activity)
        else
            showSnackBar(
                "Succesful login using your google account " + account.displayName,
                activity
            )
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(OnCompleteListener {
            showSnackBar("Succesfully signed out", activity)
        })

    }
    */
}