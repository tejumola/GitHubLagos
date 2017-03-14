package tarrotsystem.com.githublagos.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pttextview.widget.PTTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarrotsystem.com.githublagos.R;
import tarrotsystem.com.githublagos.Utils.RestClient;
import tarrotsystem.com.githublagos.model.GithubUser;
import tarrotsystem.com.githublagos.model.Item;
import tarrotsystem.com.githublagos.model.Login;
import tarrotsystem.com.githublagos.view.RecyclerItemClickListener;

/**
 * Created by DOTECH on 08/03/2017.
 */

class DeveloperItemViewHolder extends RecyclerView.ViewHolder{
    private CircleImageView userAvatar;
    private PTTextView userName, userAbout;
    private Item itemList;
    private View view;


    public DeveloperItemViewHolder(View itemView) {
        super(itemView);
        initViews(itemView);
    }

    private void initViews(View itemView) {
        this.view=itemView;
        userAvatar = (CircleImageView) itemView.findViewById ( R.id.avatar_img);
        userName = (PTTextView) itemView.findViewById ( R.id.username);
        userAbout = (PTTextView) itemView.findViewById ( R.id.detail);
    }

    public void setItem(final Item item, final RecyclerItemClickListener listener, RestClient.GitApiInterface service){
        this.itemList=item;
        userName.setText(itemList.getLogin());

        //Using picasso library to display image
        Picasso.with(itemView.getContext())
                .load(itemList.getAvatarUrl())
                .centerInside().resize(200,200)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(userAvatar);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });


        service.getLogin(itemList.getLogin()).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.d("MainActivity", "Status Code = " + response.code());
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    Login result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                     userAbout.setText(result.getBio());
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });


    }

}
