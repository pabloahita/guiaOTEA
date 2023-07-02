package gui.mainMenu.admin;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;

import com.fundacionmiradas.indicatorsevaluation.databinding.ActivityMainMenuAdminBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import cli.user.User;

public class MainMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
private ActivityMainMenuAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainMenuAdminBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainMenu.toolbarAdmin);
        binding.appBarMainMenu.fabAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;

        User user= (User) getIntent().getSerializableExtra("user");
        NavigationView navigationView = binding.navView;
        View header=navigationView.getHeaderView(0);
        TextView userName=header.findViewById(R.id.user_complete_name_admin);
        TextView email=header.findViewById(R.id.user_email_admin);
        userName.setText(user.getFirst_name()+" "+user.getLast_name());
        email.setText(user.getEmailUser());
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_recent_activity)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.upper_menu_admin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}