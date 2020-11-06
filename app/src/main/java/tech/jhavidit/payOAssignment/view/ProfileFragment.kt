package tech.jhavidit.payOAssignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import org.koin.android.ext.android.bind
import tech.jhavidit.payOAssignment.R
import tech.jhavidit.payOAssignment.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userID : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile, container, false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()
        userID = firebaseAuth.currentUser!!.uid

        val documentReference = firestore.collection("users").document(userID)
        documentReference.addSnapshotListener { value, error ->
            val name = value?.getString("firstName")+" "+value?.getString("lastName")
            binding.docName.text = name
            binding.docAddressInfo.text = value?.getString("address")
            binding.docPhoneNum.text = value?.getString("phone")
            binding.emailInfo.text = value?.getString("emailID")
        }


    }



}