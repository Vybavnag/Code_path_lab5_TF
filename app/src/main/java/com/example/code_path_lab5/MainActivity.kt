package com.example.code_path_lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //Global Variables
    var petImageURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Code after we have set the content view
        val randomPetButton = findViewById<Button>(R.id.petButton)
        val petImageView = findViewById<ImageView>(R.id.petImage)
        getNextImage(randomPetButton, petImageView)
    }

    private fun getDogImageURL(){
        val client = AsyncHttpClient()

        client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful $json")
                petImageURL = json.jsonObject.getString("message")
                Log.d("petImageURL","pet image URL set: $petImageURL")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }

        }]
    }

    private fun getNextImage(button: Button, imageView: ImageView){
        button.setOnClickListener{
            //getDogImageURL()
            val random = Random.nextInt(0,2)

            if(random == 0){
                getDogImageURL()
            }else if( random == 1){
                getCatImageURL()
            }
            Glide.with(this)
                .load(petImageURL)
                .fitCenter()
                .into(imageView)
        }
    }

    private fun getCatImageURL(){
        val client = AsyncHttpClient()

        client["https://api.thecatapi.com/v1/images/search", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Cat", "response successful $json")
                var resultJSON = json.jsonArray.getJSONObject(0)
                petImageURL = resultJSON.getString("url")
                Log.d("petImageURL","pet image URL set: $petImageURL")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }

        }]
    }
}