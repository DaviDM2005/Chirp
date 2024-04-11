import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dav.project.chirp.R
import dav.project.chirp.postItem.Item
import java.util.ArrayList
import java.util.Calendar

class Home : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find RecyclerView from the inflated layout
        recyclerView = view.findViewById(R.id.postsRecyclerView)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create and set adapter with dummy data
        val itemAdapter = ItemAdapter(requireContext(), getItemsList())
        recyclerView.adapter = itemAdapter

        return view
    }

    // Dummy data for testing
    private fun getItemsList(): List<Item> {
        // Replace this with your actual data source
        val list = ArrayList<Item>()
        // Add some dummy items for testing
        val currentDate = Calendar.getInstance().time // Define currentDate here
        list.add(Item( "Porsche model turbo s", R.drawable.porsche2, currentDate))
        list.add(Item( "Porsche turbo", R.drawable.porsche1, currentDate))
        list.add(Item( "Description 1", R.drawable.post, currentDate))
        return list
    }
}
