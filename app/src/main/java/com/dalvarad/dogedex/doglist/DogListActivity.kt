package com.dalvarad.dogedex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dalvarad.dogedex.DogDetailActivity
import com.dalvarad.dogedex.DogDetailActivity.Companion.DOG_KEY
import com.dalvarad.dogedex.api.ApiResponseStatus
import com.dalvarad.dogedex.databinding.ActivityDogListBinding


private const val GRID_SPAN_COUNT = 3

class DogListActivity : AppCompatActivity() {

    //Se instancia el viewmodel
    private val dogListVIewModel: DogListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val loadingWheel = mBinding.loadingWheel


        val recycler = mBinding.dogRecicler
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            // pasar el dog a dogDetailActivity
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)

        }

        adapter.setLongOnItemClickListener {
            dogListVIewModel.addDogToUser(it.id)
        }
        recycler.adapter = adapter

        dogListVIewModel.dogList.observe(this) { dogList ->
            adapter.submitList(dogList)
        }

        dogListVIewModel.status.observe(this) { status ->

            when (status) {
                is ApiResponseStatus.Error -> {
                    //ocultar progresbar
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.message, Toast.LENGTH_LONG).show()
                }
                is ApiResponseStatus.Loading -> {
                    //Mostrar progressbar
                    loadingWheel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Succes -> {
                    //ocultar progresbar
                    loadingWheel.visibility = View.GONE
                }

            }


        }


    }
}