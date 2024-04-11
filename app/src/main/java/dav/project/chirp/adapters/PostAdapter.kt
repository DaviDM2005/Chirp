import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dav.project.chirp.R
import dav.project.chirp.postItem.Item
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class ItemAdapter(private val context: Context, private val items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var lastClickTime: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.each_post, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val imageView: ImageView = itemView.findViewById(R.id.ivMedia)
        private val dateTextView: TextView = itemView.findViewById(R.id.tvDate)
        private val ivLike: ImageView = itemView.findViewById(R.id.ivLike)

        init {
            // Gesture detector for detecting double tap on ivMedia
            val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    toggleLikeState(ivLike)
                    return true
                }
            })

            // Set listener for ivMedia
            imageView.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
            }

            // Single tap listener for ivLike
            ivLike.setOnClickListener {
                toggleLikeState(ivLike)
            }
        }

        fun bind(item: Item) {
            postDescription.text = item.postDescription
            imageView.setImageResource(item.postImage)
            updateDateText(item)
        }

        private fun updateDateText(item: Item) {
            runnable = Runnable {
                val diffInMillis = System.currentTimeMillis() - item.date.time
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

                dateTextView.text = when {
                    minutes < 1 -> "Just now"
                    minutes < 60 -> "$minutes minutes ago"
                    diffInMillis < TimeUnit.HOURS.toMillis(24) -> "24 hours ago"
                    else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.date)
                }

                // Schedule the next update after 1 minute
                handler.postDelayed(runnable, TimeUnit.MINUTES.toMillis(1))
            }

            // Start the first update immediately
            runnable.run()
        }

        private fun toggleLikeState(likeImageView: ImageView) {
            // Check current image resource
            val currentImageResource = likeImageView.drawable

            // Change image resource based on current state
            val newImageResource =
                if (currentImageResource.constantState == ContextCompat.getDrawable(
                        context,
                        R.drawable.heart
                    )?.constantState
                ) {
                    // If the current image is the heart, change it to liked_heart
                    R.drawable.liked_heart
                } else {
                    // If the current image is liked_heart, change it to heart
                    R.drawable.heart
                }

            // Set the new image resource
            likeImageView.setImageResource(newImageResource)

            // Apply animation
            val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            likeImageView.startAnimation(anim)

            // Update UI
        }
    }

    // Stop the handler when adapter is detached
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        handler.removeCallbacks(runnable)
    }
}
