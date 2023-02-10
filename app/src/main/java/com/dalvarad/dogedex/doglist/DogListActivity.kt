package com.dalvarad.dogedex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dalvarad.dogedex.Dog
import com.dalvarad.dogedex.DogDetailActivity
import com.dalvarad.dogedex.DogDetailActivity.Companion.DOG_KEY
import com.dalvarad.dogedex.R
import com.dalvarad.dogedex.databinding.ActivityDogListBinding

class DogListActivity : AppCompatActivity() {

    //Se instancia el viewmodel
    private val dogListVIewModel: DogListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)



        val recycler = mBinding.dogRecicler
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            // pasar el dog a dogDetailActivity
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)

        }
        recycler.adapter = adapter

        dogListVIewModel.dogList.observe(this){
            dogList ->
            adapter.submitList(dogList)
        }
    }


}