package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cli.organization.Organization;
import cli.organization.data.Address;
import cli.organization.data.Center;
import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;
import gui.adapters.CityAdapter;
import gui.adapters.CountryAdapter;
import gui.adapters.OrgsAdapter;
import gui.adapters.PhoneCodeAdapter;
import gui.adapters.ProvinceAdapter;
import gui.adapters.RegionAdapter;
import misc.FieldChecker;
import otea.connection.controller.AddressesController;
import otea.connection.controller.CentersController;
import otea.connection.controller.CitiesController;
import otea.connection.controller.CountriesController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.ProvincesController;
import otea.connection.controller.RegionsController;
import otea.connection.controller.TranslatorController;
import session.Session;

public class RegisterNewCenter extends AppCompatActivity {

    List<Organization> organizations;
    List<Country> countries;

    List<Region> regions;

    List<Province> provinces;

    List<City> cities;

    Spinner countrySpinner;
    Spinner regionSpinner;
    Spinner regionSpinnerAux;
    Spinner provinceSpinner;
    Spinner provinceSpinnerAux;
    Spinner citySpinner;
    Spinner citySpinnerAux;


    EditText nameProvinceField;
    EditText nameRegionField;
    EditText nameCityField;
    CountryAdapter[] countryAdapter={null};

    RegionAdapter[] regionAdapter={null};

    ProvinceAdapter[] provinceAdapter={null};

    CityAdapter[] cityAdapter={null};

    OrgsAdapter[] orgAdapter={null};

    PhoneCodeAdapter[] phoneCodeAdapter={null};

    Organization[] organization={null};

    Country[] country=new Country[1];

    Region[] region=new Region[1];

    Province[] province=new Province[1];

    City[] city=new City[1];
    Map<String,String> fields;

    List<Region> auxRegList=new ArrayList<>();
    List<Province> auxProList=new ArrayList<>();
    List<City> auxCityList=new ArrayList<>();

    int[] idCity = {-1};
    int[] idProvince = {-1};
    int[] idRegion = {-1};
    String[] idCountry = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        countries= new ArrayList<>();
        countries.add(new Country("-2","País","Country","Pays","Herrialdea","País","Land","País","Land","Paese","País","-",""));
        countries.addAll(Session.getInstance().getCountries());
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_center);


        Spinner orgSpinner=findViewById(R.id.spinner_orgs);
        if(Session.getInstance().getOrganization().getNameOrg().equals("Fundación Miradas")) {
            getEvaluatedOrgs();
            orgAdapter[0] = new OrgsAdapter(RegisterNewCenter.this, organizations);
            orgAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            orgSpinner.setVisibility(View.VISIBLE);
        }
        else{
            organization[0]=Session.getInstance().getOrganization();
            orgSpinner.setEnabled(false);
        }

        countryAdapter[0]= new CountryAdapter(RegisterNewCenter.this, countries);
        countryAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        phoneCodeAdapter[0] = new PhoneCodeAdapter(RegisterNewCenter.this,countries);
        phoneCodeAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        EditText descriptionCenterField=findViewById(R.id.description_center_reg);
        EditText addressNameField=findViewById(R.id.name_address_reg);
        nameProvinceField = findViewById(R.id.foreign_province_reg);
        nameRegionField = findViewById(R.id.foreign_region_reg);
        nameCityField = findViewById(R.id.foreign_city_reg);
        EditText phoneField=findViewById(R.id.phone_reg);
        EditText emailField = findViewById(R.id.email_reg);
        countrySpinner = findViewById(R.id.spinner_countries_reg);
        regionSpinner = findViewById(R.id.spinner_regions_reg);
        regionSpinnerAux = findViewById(R.id.spinner_regions_reg_aux);
        provinceSpinner = findViewById(R.id.spinner_provinces_reg);
        provinceSpinnerAux = findViewById(R.id.spinner_provinces_reg_aux);
        citySpinner = findViewById(R.id.spinner_cities_reg);
        citySpinnerAux = findViewById(R.id.spinner_cities_reg_aux);
        Spinner phoneCode1=findViewById(R.id.phonecode1);
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);
        orgSpinner.setAdapter(orgAdapter[0]);
        orgSpinner.setEnabled(true);
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);

        auxRegList.add(new Region(-2,"-2","Región","Region","Région","Eskualdea","Regió","Region","Rexión","Region","Regione","Região"));
        RegionAdapter adapterRegAux=new RegionAdapter(RegisterNewCenter.this,auxRegList);

        auxProList.add(new Province(-2,-2,"-2","Provincia","Province","Province","Probintzia","Província","Provincie","Provincia","Provinz","Provincia","Província"));
        ProvinceAdapter adapterProAux=new ProvinceAdapter(RegisterNewCenter.this,auxProList);

        auxCityList.add(new City(-2,-2,-2,"-2","Ciudad","City","Ville","Hiri","Ciutat","Stad","Cidade","Stadt","Città","Cidade"));
        CityAdapter adapterCitAux=new CityAdapter(RegisterNewCenter.this,auxCityList);

        regionSpinnerAux.setAdapter(adapterRegAux);
        regionSpinnerAux.setEnabled(false);
        regionSpinnerAux.setAlpha(0.5f);
        provinceSpinnerAux.setAdapter(adapterProAux);
        provinceSpinnerAux.setEnabled(false);
        provinceSpinnerAux.setAlpha(0.5f);
        citySpinnerAux.setAdapter(adapterCitAux);
        citySpinnerAux.setEnabled(false);
        citySpinnerAux.setAlpha(0.5f);

        fields=new HashMap<String,String>();
        fields.put("centerDescription","");
        fields.put("address","");
        fields.put("nameCity","");
        fields.put("nameProvince","");
        fields.put("nameRegion","");
        fields.put("telephoneCode","");
        fields.put("telephone","");
        fields.put("email","");


        ConstraintLayout background=findViewById(R.id.final_background);


        Drawable correct= ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);

        background.setVisibility(View.GONE);

        orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                organization[0]=orgAdapter[0].getItem(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                country[0] = countryAdapter[0].getItem(position);
                fields.replace("telephoneCode",phoneCodeAdapter[0].getItem(position).getPhone_code());
                phoneCode1.setSelection(position);
                idCountry[0] = country[0].getIdCountry();
                regionSpinnerControl();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });


        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                region[0] = regionAdapter[0].getItem(position);
                idRegion[0] = region[0].getIdRegion();
                provinceSpinnerControl();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province[0] = provinceAdapter[0].getItem(position);
                idProvince[0] = province[0].getIdProvince();
                citySpinnerControl();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city[0] = cityAdapter[0].getItem(position);
                idCity[0] = city[0].getIdProvince();
                if(idCity[0]!=-2){
                    if(Locale.getDefault().getLanguage().equals("es")) {
                        fields.replace("nameCity",city[0].getNameSpanish());
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        fields.replace("nameCity",city[0].getNameFrench());
                    }else if(Locale.getDefault().getLanguage().equals("eu")) {
                        fields.replace("nameCity",city[0].getNameBasque());
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        fields.replace("nameCity",city[0].getNameCatalan());
                    }else if(Locale.getDefault().getLanguage().equals("nl")) {
                        fields.replace("nameCity",city[0].getNameDutch());
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        fields.replace("nameCity",city[0].getNameGalician());
                    }else if(Locale.getDefault().getLanguage().equals("de")) {
                        fields.replace("nameCity",city[0].getNameGerman());
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        fields.replace("nameCity",city[0].getNameItalian());
                    }else if(Locale.getDefault().getLanguage().equals("pt")) {
                        fields.replace("nameCity",city[0].getNamePortuguese());
                    }else{
                        fields.replace("nameCity",city[0].getNameEnglish());
                    }
                }else{
                    fields.replace("nameCity","");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        descriptionCenterField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                descriptionCenterField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text=s.toString();
                if(input_text.equals("")){
                    descriptionCenterField.setError(getString(R.string.please_org_name));
                }
                else{
                    descriptionCenterField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    descriptionCenterField.setError(null);
                }
                fields.replace("centerDescription",input_text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addressNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                addressNameField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text=s.toString();
                if(input_text.equals("")){
                    addressNameField.setError(getString(R.string.please_org_name));
                }
                else{
                    addressNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    addressNameField.setError(null);
                }
                fields.replace("address",input_text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        nameRegionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameRegionField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text=s.toString();
                if(input_text.equals("")){
                    nameRegionField.setError(getString(R.string.please_region));
                }
                else{
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameRegionField.setError(null);
                }
                fields.replace("nameRegion",input_text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nameProvinceField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameProvinceField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text=s.toString();
                if(input_text.equals("")){
                    nameProvinceField.setError(getString(R.string.please_province));
                }
                else{
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameProvinceField.setError(null);
                }
                fields.replace("nameProvince",input_text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nameCityField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameCityField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text=s.toString();
                if(input_text.equals("")){
                    nameCityField.setError(getString(R.string.please_city));
                }
                else{
                    nameCityField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameCityField.setError(null);
                }
                fields.replace("nameCity",input_text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneCode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fields.replace("telephoneCode",phoneCodeAdapter[0].getItem(position).getPhone_code());
                if (fields.get("telephone").equals("")) {
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    phoneField.setError(null);
                } else if (FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone"))) {
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    phoneField.setError(null);
                } else {
                    phoneField.setError(getString(R.string.wrong_phone));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        phoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                phoneField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fields.replace("telephone",s.toString());
                if (fields.get("telephone").equals("")) {
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    phoneField.setError(null);
                } else if (FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone"))) {
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    phoneField.setError(null);
                } else {
                    phoneField.setError(getString(R.string.wrong_phone));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (FieldChecker.emailHasCorrectFormat(inputText)) {
                    emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    emailField.setError(null);
                    fields.replace("email",inputText);
                } else {
                    emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    if (inputText.equals("")) {
                        emailField.setError(getString(R.string.mandatory_email));
                    } else {
                        emailField.setError(getString(R.string.wrong_email));
                    }
                    fields.replace("email","");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        CheckBox acceptLOPD = findViewById(R.id.accept_LOPD);

        Button register = findViewById(R.id.register_finished);
        register.setAlpha(0.5f);
        register.setEnabled(false);

        acceptLOPD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                      if (isChecked) {
                                                          register.setAlpha(1f);
                                                          register.setEnabled(true);
                                                      } else {
                                                          register.setAlpha(0.5f);
                                                          register.setEnabled(false);
                                                      }
                                                  }
                                              }
        );


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionCenterField.setEnabled(false);
                addressNameField.setEnabled(false);
                phoneField.setEnabled(false);
                countrySpinner.setEnabled(false);
                regionSpinner.setEnabled(false);
                provinceSpinner.setEnabled(false);
                citySpinner.setEnabled(false);

                nameRegionField.setEnabled(false);
                nameProvinceField.setEnabled(false);
                nameCityField.setEnabled(false);

                background.setVisibility(View.VISIBLE);


                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        if(!fields.get("centerDescription").equals("") && !fields.get("address").equals("") && idCountry[0]!="-1" && !fields.get("nameProvince").equals("") && !fields.get("nameRegion").equals("") && !fields.get("nameCity").equals("")
                                && FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone"))){

                            int numCenters= CentersController.GetAllByOrganization(organization[0]).size();
                            int numAddresses= AddressesController.GetAll().size();
                            int idOrganization=organization[0].getIdOrganization();
                            String orgType=organization[0].getOrgType();
                            String illness=organization[0].getIllness();

                            Address address=new Address(numAddresses+1,fields.get("address"),idCity[0],idProvince[0],idRegion[0],idCountry[0],fields.get("nameCity"),fields.get("nameProvince"),fields.get("nameRegion"));
                            AddressesController.Create(address);

                            String descriptionEnglish="";
                            String descriptionSpanish="";
                            String descriptionFrench="";
                            String descriptionBasque="";
                            String descriptionCatalan="";
                            String descriptionDutch="";
                            String descriptionGalician="";
                            String descriptionGerman="";
                            String descriptionItalian="";
                            String descriptionPortuguese="";

                            String descriptionText=fields.get("centerDescription");

                            if(!descriptionText.equals("")){
                                List<String> translations=TranslatorController.getInstance().translate(descriptionText,Locale.getDefault().getLanguage());
                                if(Locale.getDefault().getLanguage().equals("es")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=descriptionText;
                                    descriptionFrench=translations.get(1);
                                    descriptionBasque=translations.get(2);
                                    descriptionCatalan=translations.get(3);
                                    descriptionDutch=translations.get(4);
                                    descriptionGalician=translations.get(5);
                                    descriptionGerman=translations.get(6);
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("fr")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=descriptionText;
                                    descriptionBasque=translations.get(2);
                                    descriptionCatalan=translations.get(3);
                                    descriptionDutch=translations.get(4);
                                    descriptionGalician=translations.get(5);
                                    descriptionGerman=translations.get(6);
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("eu")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=translations.get(2);
                                    descriptionBasque=descriptionText;
                                    descriptionCatalan=translations.get(3);
                                    descriptionDutch=translations.get(4);
                                    descriptionGalician=translations.get(5);
                                    descriptionGerman=translations.get(6);
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("ca")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=translations.get(2);
                                    descriptionBasque=translations.get(3);
                                    descriptionCatalan=descriptionText;
                                    descriptionDutch=translations.get(4);
                                    descriptionGalician=translations.get(5);
                                    descriptionGerman=translations.get(6);
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("nl")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=translations.get(2);
                                    descriptionBasque=translations.get(3);
                                    descriptionCatalan=translations.get(4);
                                    descriptionDutch=descriptionText;
                                    descriptionGalician=translations.get(5);
                                    descriptionGerman=translations.get(6);
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("gl")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=translations.get(2);
                                    descriptionBasque=translations.get(3);
                                    descriptionCatalan=translations.get(4);
                                    descriptionDutch=translations.get(5);
                                    descriptionGalician=descriptionText;
                                    descriptionGerman=translations.get(6);
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("de")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=translations.get(2);
                                    descriptionBasque=translations.get(3);
                                    descriptionCatalan=translations.get(4);
                                    descriptionDutch=translations.get(5);
                                    descriptionGalician=translations.get(6);
                                    descriptionGerman=descriptionText;
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("it")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=translations.get(2);
                                    descriptionBasque=translations.get(3);
                                    descriptionCatalan=translations.get(4);
                                    descriptionDutch=translations.get(5);
                                    descriptionGalician=translations.get(6);
                                    descriptionGerman=translations.get(7);
                                    descriptionItalian=descriptionText;
                                    descriptionPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("pt")){
                                    descriptionEnglish= translations.get(0);
                                    descriptionSpanish=translations.get(1);
                                    descriptionFrench=translations.get(2);
                                    descriptionBasque=translations.get(3);
                                    descriptionCatalan=translations.get(4);
                                    descriptionDutch=translations.get(5);
                                    descriptionGalician=translations.get(6);
                                    descriptionGerman=translations.get(7);
                                    descriptionItalian=translations.get(8);
                                    descriptionPortuguese=descriptionText;
                                }else{
                                    descriptionEnglish= descriptionText;
                                    descriptionSpanish=translations.get(0);
                                    descriptionFrench=translations.get(1);
                                    descriptionBasque=translations.get(2);
                                    descriptionCatalan=translations.get(3);
                                    descriptionDutch=translations.get(4);
                                    descriptionGalician=translations.get(5);
                                    descriptionGerman=translations.get(6);
                                    descriptionItalian=translations.get(7);
                                    descriptionPortuguese=translations.get(8);
                                }
                            }


                            Center center=new Center(idOrganization,orgType,illness, numCenters+1,descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese,address.getIdAddress(),fields.get("telephoneCode")+" "+fields.get("telephone"),fields.get("email"));
                            CentersController.Create(center);

                            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                            startActivity(intent);
                        }
                        else{

                            background.setVisibility(View.GONE);

                            if(fields.get("centerDescription").equals("")){
                                descriptionCenterField.setError(getString(R.string.please_description_center));
                            }
                            if(fields.get("address").equals("")){
                                addressNameField.setError(getString(R.string.please_address));
                            }
                            if(!FieldChecker.isPrecharged(country[0].getIdCountry())){
                                if(fields.get("nameCity").equals("")){
                                    nameCityField.setError(getString(R.string.please_city));
                                }
                                if(fields.get("nameProvince").equals("")){
                                    nameProvinceField.setError(getString(R.string.please_province));
                                }
                                if(fields.get("nameRegion").equals("")){
                                    nameRegionField.setError(getString(R.string.please_region));
                                }
                            }
                            if(!FieldChecker.emailHasCorrectFormat(fields.get("email"))){
                                emailField.setError(getString(R.string.wrong_email));
                            }
                            if(!(FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone")))){
                                phoneField.setError(getString(R.string.wrong_phone));
                            }
                        }

                    }
                }, 100);
            }

        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            intent.putExtra("userEmail",getIntent().getSerializableExtra("userEmail"));

            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }

    public List<Organization> getEvaluatedOrgs(){
        if(organizations==null){
            organizations= OrganizationsController.GetAllEvaluatedOrganizations();
        }
        return organizations;
    }


    private void regionSpinnerControl(){
        if(!idCountry[0].equals("-2")){
            if (FieldChecker.isPrecharged(idCountry[0])) {
                regions=Session.getInstance().getRegionsByCountry(idCountry[0]);
                if(regions.size()>1){
                    regions.add(0,auxRegList.get(0));
                    regionSpinner.setVisibility(View.VISIBLE);
                    regionSpinnerAux.setVisibility(View.GONE);
                    regionAdapter[0]=new RegionAdapter(RegisterNewCenter.this,regions);
                    regionAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                    regionSpinner.setAdapter(regionAdapter[0]);
                }
                else{
                    region[0]=regions.get(0);
                    idRegion[0]=-1;
                    regionSpinner.setVisibility(View.GONE);
                    regionSpinnerAux.setVisibility(View.VISIBLE);
                    provinces=Session.getInstance().getProvincesByRegion(-1,idCountry[0]);
                    provinces.add(auxProList.get(0));
                    provinceSpinnerControl();
                }
                nameProvinceField.setVisibility(View.GONE);
                nameRegionField.setVisibility(View.GONE);
                nameCityField.setVisibility(View.GONE);
            } else {
                regionSpinner.setVisibility(View.GONE);
                provinceSpinner.setVisibility(View.GONE);
                citySpinner.setVisibility(View.GONE);
                nameProvinceField.setVisibility(View.VISIBLE);
                nameRegionField.setVisibility(View.VISIBLE);
                nameCityField.setVisibility(View.VISIBLE);
                nameProvinceField.setEnabled(true);
                nameRegionField.setEnabled(true);
                nameCityField.setEnabled(true);
            }
        }else{
            fields.replace("nameRegion","");
            fields.replace("nameProvince","");
            fields.replace("nameCity","");
            regionSpinner.setVisibility(View.GONE);
            provinceSpinner.setVisibility(View.GONE);
            citySpinner.setVisibility(View.GONE);
            regionSpinnerAux.setVisibility(View.VISIBLE);
            provinceSpinnerAux.setVisibility(View.VISIBLE);
            citySpinnerAux.setVisibility(View.VISIBLE);
            nameProvinceField.setVisibility(View.GONE);
            nameRegionField.setVisibility(View.GONE);
            nameCityField.setVisibility(View.GONE);
        }
    }

    private void provinceSpinnerControl(){
        if(idRegion[0]!=-2){
            if(Locale.getDefault().getLanguage().equals("es")) {
                fields.replace("nameRegion",region[0].getNameSpanish());
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                fields.replace("nameRegion",region[0].getNameFrench());
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                fields.replace("nameRegion",region[0].getNameBasque());
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                fields.replace("nameRegion",region[0].getNameCatalan());
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                fields.replace("nameRegion",region[0].getNameDutch());
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                fields.replace("nameRegion",region[0].getNameGalician());
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                fields.replace("nameRegion",region[0].getNameGerman());
            }else if(Locale.getDefault().getLanguage().equals("it")){
                fields.replace("nameRegion",region[0].getNameItalian());
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                fields.replace("nameRegion",region[0].getNamePortuguese());
            }else{
                fields.replace("nameRegion",region[0].getNameEnglish());
            }

            provinces=Session.getInstance().getProvincesByRegion(idRegion[0],idCountry[0]);
            if(provinces.size()>1) {
                provinces.add(0,auxProList.get(0));
            }
            provinceAdapter[0] = new ProvinceAdapter(RegisterNewCenter.this, provinces);
            provinceAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            provinceSpinner.setAdapter(provinceAdapter[0]);
            provinceSpinnerAux.setVisibility(View.GONE);
            provinceSpinner.setVisibility(View.VISIBLE);
            if(provinces.size()==1){
                provinceSpinner.setSelection(0);
                province[0]=(Province) provinceSpinner.getSelectedItem();
                idProvince[0]=province[0].getIdProvince();
                citySpinnerControl();
            }
        }else{
            fields.replace("nameRegion","");
            fields.replace("nameProvince","");
            fields.replace("nameCity","");
            provinceSpinner.setVisibility(View.GONE);
            citySpinner.setVisibility(View.GONE);
            provinceSpinnerAux.setVisibility(View.VISIBLE);
            citySpinnerAux.setVisibility(View.VISIBLE);
        }
    }

    private void citySpinnerControl(){
        if(idProvince[0]!=-2){
            if (Locale.getDefault().getLanguage().equals("es")) {
                fields.replace("nameProvince", province[0].getNameSpanish());
            } else if (Locale.getDefault().getLanguage().equals("fr")) {
                fields.replace("nameProvince", province[0].getNameFrench());
            } else if (Locale.getDefault().getLanguage().equals("eu")) {
                fields.replace("nameProvince", province[0].getNameBasque());
            } else if (Locale.getDefault().getLanguage().equals("ca")) {
                fields.replace("nameProvince", province[0].getNameCatalan());
            } else if (Locale.getDefault().getLanguage().equals("nl")) {
                fields.replace("nameProvince", province[0].getNameDutch());
            } else if (Locale.getDefault().getLanguage().equals("gl")) {
                fields.replace("nameProvince", province[0].getNameGalician());
            } else if (Locale.getDefault().getLanguage().equals("de")) {
                fields.replace("nameProvince", province[0].getNameGerman());
            } else if (Locale.getDefault().getLanguage().equals("it")) {
                fields.replace("nameProvince", province[0].getNameItalian());
            } else if (Locale.getDefault().getLanguage().equals("pt")) {
                fields.replace("nameProvince", province[0].getNamePortuguese());
            } else {
                fields.replace("nameProvince", province[0].getNameEnglish());
            }

            cities=Session.getInstance().getCitiesByProvince(idProvince[0], idRegion[0], idCountry[0]);
            if(cities.size()>1) {
                cities.add(0,auxCityList.get(0));
            }
            cityAdapter[0] = new CityAdapter(RegisterNewCenter.this, cities);

            cityAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            citySpinner.setAdapter(cityAdapter[0]);
            citySpinnerAux.setVisibility(View.GONE);
            citySpinner.setVisibility(View.VISIBLE);
            if(cities.size()==1){
                citySpinner.setSelection(0);
            }
        }else{
            fields.replace("nameProvince","");
            fields.replace("nameCity","");
            citySpinner.setVisibility(View.GONE);
            citySpinnerAux.setVisibility(View.VISIBLE);
        }
    }
}