package tarrotsystem.com.githublagos.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pttextview.widget.PTTextView;
import tarrotsystem.com.githublagos.R;
import tarrotsystem.com.githublagos.model.Item;

/**
 * Created by DOTECH on 09/03/2017.
 */

public class DetailActivity extends AppCompatActivity{

    private PTTextView userName;
    private PTTextView profileUrl;
    private PTTextView Bio;
    private FloatingActionButton fab;
    private CircleImageView userAvatar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userName = (PTTextView)findViewById ( R.id.username);
        profileUrl = (PTTextView)findViewById ( R.id.profile_url);
        userAvatar = (CircleImageView)findViewById ( R.id.avatar_imageView);
        initView();

    }

    private void initView() {
        Intent intent=getIntent();
        final Item item = (Item)intent.getSerializableExtra("Item");
        userName.setText(item.getLogin());
        profileUrl.setText(item.getHtmlUrl());

        //Using picasso library to display image
        Picasso.with(this)
                .load(item.getAvatarUrl())
                .centerInside().resize(200,200)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(userAvatar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("Check out this awesome developer <a href="+item.getHtmlUrl()+">"+item.getLogin()+"</a>"));
                startActivity(Intent.createChooser(sharingIntent,"Share using"));
            }
        });

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getHtmlUrl()));
                startActivity(browserIntent);
            }
        });
    }


}
