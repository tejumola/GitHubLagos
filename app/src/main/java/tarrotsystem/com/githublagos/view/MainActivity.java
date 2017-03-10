package tarrotsystem.com.githublagos.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarrotsystem.com.githublagos.R;
import tarrotsystem.com.githublagos.adapters.DeveloperRecyclerAdapter;
import tarrotsystem.com.githublagos.model.GithubUser;
import tarrotsystem.com.githublagos.model.Item;
import tarrotsystem.com.githublagos.Utils.RestClient;
import tarrotsystem.com.githublagos.model.Login;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DeveloperRecyclerAdapter adapter;
    private Toolbar mToolbar;
    private static final String TAG = MainActivity.class.getSimpleName();
    List<Item> users;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.developer_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        users = new ArrayList<>();

        final RestClient.GitApiInterface service = RestClient.getClient();
        Call<GithubUser> call = service.getLagosUsers("location:lagos");
        call.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                Log.d("MainActivity", "Status Code = " + response.code());
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    GithubUser result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                    users = result.getItems();
                    Log.d("MainActivity", "Items = " + users.size());
                    adapter = new DeveloperRecyclerAdapter(service, users, new RecyclerItemClickListener() {
                        @Override
                        public void onItemClick(Item item) {
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("Item",item);
                            startActivity(intent);
                        }
                    });

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    //Handle errors
                }
            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.ic_andela);
        dialog.setTitle("Andela Android Learning Community");
        dialog.setMessage("Designed and Developed by Tejumola David Timmy or the Andela Android Learning Community." );
        dialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater ( ).inflate ( R.menu.menu_main, menu );
        return super.onCreateOptionsMenu ( menu );
    }

}
