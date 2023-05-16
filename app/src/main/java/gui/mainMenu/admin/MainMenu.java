package gui.mainMenu.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fundacionmiradas.indicatorsevaluation.R;

import com.fundacionmiradas.indicatorsevaluation.databinding.ActivityMainMenuAdminBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class MainMenu extends AppCompatActivity {

    private AppBarConfiguration burgerMenuConfiguration;

    private AppBarConfiguration floatingMenuConfiguration;
    private ActivityMainMenuAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAdminMainMenu.toolbar);


        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainMenu.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.admin_main_menu_floating, popupMenu.getMenu());
                floatingMenuConfiguration = new AppBarConfiguration.Builder(
                        R.id.add_new_evaluated_org, R.id.add_new_complete_evaluation, R.id.add_new_simple_evaluation)
                        .build();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_new_evaluated_org:
                                //Snackbar.make(view, "UNDER CONSTRUCTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                return true;
                            case R.id.add_new_complete_evaluation:
                                //Snackbar.make(view, "UNDER CONSTRUCTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                return true;
                            case R.id.add_new_simple_evaluation:
                               // Snackbar.make(view, "UNDER CONSTRUCTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                return true;
                            /*case R.id.add_new_evaluated_org_center:
                                // Snackbar.make(view, "UNDER CONSTRUCTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                return true;*/
                            /*case R.id.add_new_simple_evaluation:
                                // Snackbar.make(view, "UNDER CONSTRUCTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                return true;
                            case R.id.add_new_simple_evaluation:
                                // Snackbar.make(view, "UNDER CONSTRUCTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                return true;
                            case R.id.add_new_simple_evaluation:
                                // Snackbar.make(view, "UNDER CONSTRUCTION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                return true;*/
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        burgerMenuConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, burgerMenuConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.appBarAdminMainMenu.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar las acciones de los elementos de menú seleccionados
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, gui.settings.SettingsActivity.class);
                startActivity(intent);
                return true;
            /*case R.id.toolbar_menu_item_2:
                // Handle menu item 2 click
                return true;
            case R.id.toolbar_menu_item_3:
                // Handle menu item 3 click
                return true;
            */default:
                return super.onOptionsItemSelected(item);
        }
    }
}