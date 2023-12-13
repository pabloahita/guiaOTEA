package gui.mainMenu.evaluator.ui.recentactivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentEvaluatorBinding;

import java.util.Locale;

import cli.organization.Organization;
import cli.user.User;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.UsersController;

public class RecentActivity extends Fragment {

    private FragmentRecentEvaluatorBinding binding;
    private RecentViewModel mViewModel;

    public static RecentActivity newInstance() {
        return new RecentActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding= FragmentRecentEvaluatorBinding.inflate(inflater, container, false);

        View root=binding.getRoot();


        String userEmail=(String) getActivity().getIntent().getSerializableExtra("userEmail");

        User user= UsersController.getInstance().Get(userEmail);

        if(user!=null){
            Organization org=OrganizationsController.getInstance().Get(user.getIdOrganization(),user.getOrganizationType(),user.getIllness());

            if(org!=null) {
                TextView first_name=binding.firstNameUserMenu;
                TextView last_name=binding.lastNameUserMenu;
                TextView organizationName=binding.orgNameUserMenu;

                if(Locale.getDefault().getLanguage().equals("es")){
                    first_name.setText("Nombre: "+user.getFirst_name());
                    last_name.setText("Apellidos: "+user.getLast_name());
                    organizationName.setText("Organización: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    first_name.setText("Prénom: "+user.getFirst_name());
                    last_name.setText("Nom: "+user.getLast_name());
                    organizationName.setText("Organisation: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    first_name.setText("Izena: "+user.getFirst_name());
                    last_name.setText("Abizena: "+user.getLast_name());
                    organizationName.setText("Erakundea: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    first_name.setText("Nom: "+user.getFirst_name());
                    last_name.setText("Cognom: "+user.getLast_name());
                    organizationName.setText("Organització: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    first_name.setText("Nome: "+user.getFirst_name());
                    last_name.setText("Apelido: "+user.getLast_name());
                    organizationName.setText("Organización: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    first_name.setText("Nome: "+user.getFirst_name());
                    last_name.setText("Sobrenome: "+user.getLast_name());
                    organizationName.setText("Organização: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    first_name.setText("Vorname:  "+user.getFirst_name());
                    last_name.setText("Nachname: "+user.getLast_name());
                    organizationName.setText("Organisation: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    first_name.setText("Nome: "+user.getFirst_name());
                    last_name.setText("Apelido: "+user.getLast_name());
                    organizationName.setText("Organización: "+org.getNameOrg());
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    first_name.setText("Vornaam: "+user.getFirst_name());
                    last_name.setText("Achternaam: "+user.getLast_name());
                    organizationName.setText("Organisatie: "+org.getNameOrg());
                }else{
                    first_name.setText("First name: "+user.getFirst_name());
                    last_name.setText("Last name: "+user.getLast_name());
                    organizationName.setText("Organization: "+org.getNameOrg());
                }

                binding.cardView2.setVisibility(View.GONE);

                binding.imageButton1.setOnClickListener(v -> {
                    binding.cardView2.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(getContext(),gui.SelectToDoIndicatorsEvaluations.class);
                            intent.putExtra("userEmail",userEmail);
                            startActivity(intent);
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.cardView2.setVisibility(View.GONE);
                                }
                            }, 50);
                        }
                    }, 50);

                });

                binding.imageButton2.setOnClickListener(v -> {

                });

                binding.imageButton3.setOnClickListener(v -> {

                });

                binding.imageButton4.setOnClickListener(v -> {
                    binding.cardView2.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(getContext(),gui.RegisterOrganization.class);
                            intent.putExtra("userEmail",userEmail);
                            startActivity(intent);

                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.cardView2.setVisibility(View.GONE);
                                }
                            }, 50);
                        }
                    }, 50);

                });

                binding.imageButton5.setOnClickListener(v -> {
                    binding.cardView2.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(getContext(),gui.RegisterNewCenter.class);
                            intent.putExtra("userEmail",userEmail);
                            //intent.putExtra("org",org);
                            startActivity(intent);

                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.cardView2.setVisibility(View.GONE);
                                }
                            }, 50);
                        }
                    }, 50);

                });

                binding.imageButton6.setOnClickListener(v -> {
                    binding.cardView2.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(getContext(),gui.RegisterNewEvaluatorTeam.class);
                            intent.putExtra("userEmail",userEmail);
                            startActivity(intent);

                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.cardView2.setVisibility(View.GONE);
                                }
                            }, 50);
                        }
                    }, 50);
                });
            }


        }




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecentViewModel.class);
        // TODO: Use the ViewModel
    }

}