package com.takusemba.cropmesample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import com.takusemba.cropmesample.R
import com.takusemba.cropmesample.models.Photo
import com.takusemba.cropmesample.ui.OnPhotoClickListener

/**
 * Adapter to show [photos]
 */
class PhotoAdapter(
    private val photos: List<Photo>,
    private val listener: OnPhotoClickListener,
    private val length: Int
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_photo, viewGroup, false)
    val image = view.findViewById<ImageView>(R.id.image)
    val params = image.layoutParams
    params.width = length
    params.height = length
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val photo = photos[position]
    Picasso.get().load(photo.uri).resize(IMAGE_SIZE, IMAGE_SIZE).centerCrop().into(holder.image)
    holder.itemView.setOnClickListener { listener.onPhotoClicked(photo) }
  }

  override fun getItemCount(): Int {
    return photos.size
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)
  }

  companion object {

    private const val IMAGE_SIZE = 200
  }
}