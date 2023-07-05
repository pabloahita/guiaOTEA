package gui.mainMenu.evaluator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.fundacionmiradas.indicatorsevaluation.databinding.ActivityMainMenuEvaluatorBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import cli.user.User;
import gui.RegisterOrganization;


public class MainMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainMenuEvaluatorBinding binding;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuEvaluatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainMenu.toolbarEvaluator);

        binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setVisibility(View.GONE);
        binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setVisibility(View.GONE);
        binding.appBarMainMenu.fabAddNewEvaluatorTeam.setVisibility(View.GONE);
        binding.appBarMainMenu.textNewEvaluatedOrg.setVisibility(View.GONE);
        binding.appBarMainMenu.textNewIndicatorTest.setVisibility(View.GONE);
        binding.appBarMainMenu.textNewEvaluatorTeam.setVisibility(View.GONE);

        binding.appBarMainMenu.progressBar.setVisibility(View.GONE);
        binding.appBarMainMenu.pleaseWait.setVisibility(View.GONE);


        binding.appBarMainMenu.fabEvaluator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                if(binding.appBarMainMenu.fabAddNewEvaluatedOrganization.getVisibility()==View.VISIBLE) {
                    binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setVisibility(View.GONE);
                    binding.appBarMainMenu.textNewEvaluatedOrg.setVisibility(View.GONE);
                }else{
                    binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setVisibility(View.VISIBLE);
                    binding.appBarMainMenu.textNewEvaluatedOrg.setVisibility(View.VISIBLE);
                }
                if(binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.getVisibility()==View.VISIBLE) {
                    binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setVisibility(View.GONE);
                    binding.appBarMainMenu.textNewIndicatorTest.setVisibility(View.GONE);
                }else{
                    binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setVisibility(View.VISIBLE);
                    binding.appBarMainMenu.textNewIndicatorTest.setVisibility(View.VISIBLE);
                }
                if(binding.appBarMainMenu.fabAddNewEvaluatorTeam.getVisibility()==View.VISIBLE) {
                    binding.appBarMainMenu.fabAddNewEvaluatorTeam.setVisibility(View.GONE);
                    binding.appBarMainMenu.textNewEvaluatorTeam.setVisibility(View.GONE);
                }else{
                    binding.appBarMainMenu.fabAddNewEvaluatorTeam.setVisibility(View.VISIBLE);
                    binding.appBarMainMenu.textNewEvaluatorTeam.setVisibility(View.VISIBLE);
                };
            }
        });



        binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"WORK",Toast.LENGTH_SHORT).show();
                binding.appBarMainMenu.progressBar.setVisibility(View.VISIBLE);
                binding.appBarMainMenu.pleaseWait.setVisibility(View.VISIBLE);
                binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setVisibility(View.GONE);
                binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setVisibility(View.GONE);
                binding.appBarMainMenu.fabAddNewEvaluatorTeam.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewEvaluatedOrg.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewIndicatorTest.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewEvaluatorTeam.setVisibility(View.GONE);

                binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setEnabled(false);
                binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setEnabled(false);
                binding.appBarMainMenu.fabAddNewEvaluatorTeam.setEnabled(false);
                binding.appBarMainMenu.textNewEvaluatedOrg.setEnabled(false);
                binding.appBarMainMenu.textNewIndicatorTest.setEnabled(false);
                binding.appBarMainMenu.textNewEvaluatorTeam.setEnabled(false);


                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(getApplicationContext(), gui.RegisterOrganization.class);
                        intent.putExtra("user",getIntent().getSerializableExtra("user"));
                        startActivity(intent);
                    }
                }, 100);




            }
        });
        binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.appBarMainMenu.progressBar.setVisibility(View.VISIBLE);
                binding.appBarMainMenu.pleaseWait.setVisibility(View.VISIBLE);
                binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setVisibility(View.GONE);
                binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setVisibility(View.GONE);
                binding.appBarMainMenu.fabAddNewEvaluatorTeam.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewEvaluatedOrg.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewIndicatorTest.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewEvaluatorTeam.setVisibility(View.GONE);

                binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setEnabled(false);
                binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setEnabled(false);
                binding.appBarMainMenu.fabAddNewEvaluatorTeam.setEnabled(false);
                binding.appBarMainMenu.textNewEvaluatedOrg.setEnabled(false);
                binding.appBarMainMenu.textNewIndicatorTest.setEnabled(false);
                binding.appBarMainMenu.textNewEvaluatorTeam.setEnabled(false);

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Cambiar a elegir equipo evaluador
                        Intent intent=new Intent(getApplicationContext(),gui.SelectToDoIndicatorsEvaluations.class);
                        intent.putExtra("user",getIntent().getSerializableExtra("user"));
                        startActivity(intent);
                    }
                }, 100);


            }
        });
        binding.appBarMainMenu.fabAddNewEvaluatorTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.appBarMainMenu.progressBar.setVisibility(View.VISIBLE);
                binding.appBarMainMenu.pleaseWait.setVisibility(View.VISIBLE);
                binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setVisibility(View.GONE);
                binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setVisibility(View.GONE);
                binding.appBarMainMenu.fabAddNewEvaluatorTeam.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewEvaluatedOrg.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewIndicatorTest.setVisibility(View.GONE);
                binding.appBarMainMenu.textNewEvaluatorTeam.setVisibility(View.GONE);

                binding.appBarMainMenu.fabAddNewEvaluatedOrganization.setEnabled(false);
                binding.appBarMainMenu.fabAddNewIndicatorsEvaluation.setEnabled(false);
                binding.appBarMainMenu.fabAddNewEvaluatorTeam.setEnabled(false);
                binding.appBarMainMenu.textNewEvaluatedOrg.setEnabled(false);
                binding.appBarMainMenu.textNewIndicatorTest.setEnabled(false);
                binding.appBarMainMenu.textNewEvaluatorTeam.setEnabled(false);

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Cambiar a elegir equipo evaluador
                        Intent intent=new Intent(getApplicationContext(),gui.RegisterNewEvaluatorTeam.class);
                        intent.putExtra("user",getIntent().getSerializableExtra("user"));
                        startActivity(intent);
                    }
                }, 100);
            }
        });


        DrawerLayout drawer = binding.drawerLayout;

        User user= (User) getIntent().getSerializableExtra("user");
        NavigationView navigationView = binding.navView;
        View header=navigationView.getHeaderView(0);
        TextView userName=header.findViewById(R.id.user_complete_name_evaluator);
        TextView email=header.findViewById(R.id.user_email_evaluator);
        userName.setText(user.getFirst_name()+" "+user.getLast_name());
        email.setText(user.getEmailUser());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_recent_activity_evaluator)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu_evaluator);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.upper_menu_evaluator, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu_evaluator);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == event.KEYCODE_BACK) {
                if (dialog == null || !dialog.isShowing()) {
                    // Mostrar el cuadro de diálogo solo si no se está mostrando actualmente
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(getString(R.string.do_you_want_to_exit_otea))
                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialog = builder.create();
                    dialog.show();
                    return true;
                } else {
                    // Si el cuadro de diálogo ya se está mostrando, se cierra
                    dialog.dismiss();
                    return true;
                }
            }

            return super.onKeyDown(keyCode, event);
        }


    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }







}