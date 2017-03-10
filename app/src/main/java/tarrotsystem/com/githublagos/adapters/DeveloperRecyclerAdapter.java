package tarrotsystem.com.githublagos.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import tarrotsystem.com.githublagos.R;
import tarrotsystem.com.githublagos.Utils.RestClient;
import tarrotsystem.com.githublagos.model.Item;
import tarrotsystem.com.githublagos.model.Login;
import tarrotsystem.com.githublagos.view.RecyclerItemClickListener;

/**
 * Created by DOTECH on 08/03/2017.
 */

public class DeveloperRecyclerAdapter extends RecyclerView.Adapter<DeveloperItemViewHolder> {
    public static final String TAG = DeveloperRecyclerAdapter.class.getSimpleName();
    private List<Item> users;
    private final RecyclerItemClickListener mListener;
    RestClient.GitApiInterface service;



    public DeveloperRecyclerAdapter(RestClient.GitApiInterface service,List<Item> itemData,RecyclerItemClickListener listener){
        this.users = itemData;
        this.mListener=listener;
        this.service=service;

    }


    @Override
    public DeveloperItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_listview, null);
        return new DeveloperItemViewHolder (itemLayoutView);
    }

    @Override
    public void onBindViewHolder(DeveloperItemViewHolder holder, int position) {
        holder.setItem (users.get(position),mListener,service);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
