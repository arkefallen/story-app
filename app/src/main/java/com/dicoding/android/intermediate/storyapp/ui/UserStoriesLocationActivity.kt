package com.dicoding.android.intermediate.storyapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.intermediate.storyapp.R
import com.dicoding.android.intermediate.storyapp.data.response.Story
import com.dicoding.android.intermediate.storyapp.databinding.ActivityUserStoriesLocationBinding
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.StoryViewModel
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.StoryViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class UserStoriesLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var storyViewModel : StoryViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityUserStoriesLocationBinding
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserStoriesLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(EXTRA_USER_TOKEN).toString()
        val storyViewModelFactory = StoryViewModelFactory.getInstance(token, this@UserStoriesLocationActivity)
        storyViewModel = ViewModelProvider(this@UserStoriesLocationActivity, storyViewModelFactory)
            .get(StoryViewModel::class.java)

        supportActionBar?.title = "User Stories Location"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        storyViewModel.setStoryCalling(1, 20, 1)
        storyViewModel.getNetworkStories().observe(
            this, { stories ->
                stories?.forEach{ story ->
                    val userStoryPosition = LatLng(story.lat?.toDouble()!!, story.lon?.toDouble()!!)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(userStoryPosition)
                            .title("Cerita dari ${story.name.toString()}")
                    )
                    boundsBuilder.include(userStoryPosition)
                }

                val bounds: LatLngBounds = boundsBuilder.build()
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                        300
                    )
                )
            }
        )

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_type_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val EXTRA_USER_TOKEN = "extra_user_token"
        val EXTRA_USER_STORIES = "extra_user_stories"
    }
}