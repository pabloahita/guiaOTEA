package gui.mainMenu.evaluated.ui.recentactivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentEvaluatedBinding;

import cli.organization.Organization;
import cli.user.User;
import gui.mainMenu.admin.ui.recentactivity.RecentViewModel;
import otea.connection.controller.OrganizationsController;

import java.util.Locale;
public class RecentActivity extends Fragment {

    private FragmentRecentEvaluatedBinding binding;
    private RecentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gui.mainMenu.admin.ui.recentactivity.RecentViewModel recentViewModel =
                new ViewModelProvider(this).get(RecentViewModel.class);

        binding = FragmentRecentEvaluatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        User user= (User) getActivity().getIntent().getSerializableExtra("user");

        Organization org=(Organization) getActivity().getIntent().getSerializableExtra("org");

        if(org==null) {
            org = OrganizationsController.Get(user.getIdOrganization(), user.getOrgType(), user.getIllness());
            getActivity().getIntent().putExtra("org",org);
        }

        TextView first_name=binding.firstNameUserMenu;
        TextView last_name=binding.lastNameUserMenu;
        TextView organizationName=binding.orgNameUserMenu;

        if(Locale.getDefault().getLanguage().equals("es")){
            first_name.setText("Nombre: "+user.getFirst_name());
            last_name.setText("Apellidos: "+user.getLast_name());
            organizationName.setText("Organización: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            first_name.setText("Prénom: "+user.getFirst_name());
            last_name.setText("Nom: "+user.getLast_name());
            organizationName.setText("Organisation: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("eu")){
            first_name.setText("Izena: "+user.getFirst_name());
            last_name.setText("Abizena: "+user.getLast_name());
            organizationName.setText("Erakundea: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            first_name.setText("Nom: "+user.getFirst_name());
            last_name.setText("Cognom: "+user.getLast_name());
            organizationName.setText("Organització: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            first_name.setText("Nome: "+user.getFirst_name());
            last_name.setText("Apelido: "+user.getLast_name());
            organizationName.setText("Organización: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("pt")){
            first_name.setText("Nome: "+user.getFirst_name());
            last_name.setText("Sobrenome: "+user.getLast_name());
            organizationName.setText("Organização: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("de")){
            first_name.setText("Vorname:  "+user.getFirst_name());
            last_name.setText("Nachname: "+user.getLast_name());
            organizationName.setText("Organisation: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("it")){
            first_name.setText("Nome: "+user.getFirst_name());
            last_name.setText("Apelido: "+user.getLast_name());
            organizationName.setText("Organización: "+org.getName());
        }else if(Locale.getDefault().getLanguage().equals("nl")){
            first_name.setText("Vornaam: "+user.getFirst_name());
            last_name.setText("Achternaam: "+user.getLast_name());
            organizationName.setText("Organisatie: "+org.getName());
        }else{
            first_name.setText("First name: "+user.getFirst_name());
            last_name.setText("Last name: "+user.getLast_name());
            organizationName.setText("Organization: "+org.getName());
        }

        binding.cardView2.setVisibility(View.GONE);



        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}