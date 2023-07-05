package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import cli.organization.Organization;
import cli.organization.data.Address;
import cli.organization.data.Center;
import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;
import cli.user.User;
import gui.adapters.*;
import misc.FieldChecker;
import misc.PasswordCodifier;
import otea.connection.caller.AddressesCaller;
import otea.connection.caller.CentersCaller;
import otea.connection.caller.CitiesCaller;
import otea.connection.caller.CountriesCaller;
import otea.connection.caller.OrganizationsCaller;
import otea.connection.caller.ProvincesCaller;
import otea.connection.caller.RegionsCaller;
import otea.connection.caller.UsersCaller;

public class RegisterOrganization extends AppCompatActivity {

    List<Country> countries;
    List<Region> regions;
    List<Province> provinces;
    List<City> cities;
    CountryAdapter[] countryAdapter={null};

    RegionAdapter[] regionAdapter={null};

    ProvinceAdapter[] provinceAdapter={null};

    CityAdapter[] cityAdapter={null};
    
    Country[] country=new Country[1];

    Region[] region=new Region[1];

    Province[] province=new Province[1];

    City[] city=new City[1];

    String currIdCountry;
    int currIdRegion;
    int currIdProvince;
    int currIdCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_organization);
        getCountries();
        countryAdapter[0] = new CountryAdapter(RegisterOrganization.this, countries);
        countryAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        ProgressBar progressBar = findViewById(R.id.loading_org_reg);
        progressBar.setVisibility(View.GONE);
        TextView loadingText = findViewById(R.id.please_wait_org_reg);
        loadingText.setVisibility(View.GONE);

        EditText nameOrgField = findViewById(R.id.org_name_reg);
        EditText addressNameField = findViewById(R.id.name_address_reg);
        EditText zipCodeField = findViewById(R.id.org_zip_code);
        EditText nameProvinceField = findViewById(R.id.foreign_province_reg);
        EditText nameRegionField = findViewById(R.id.foreign_region_reg);
        EditText nameCityField = findViewById(R.id.foreign_city_reg);
        EditText emailField = findViewById(R.id.email_reg);
        EditText phoneField = findViewById(R.id.phone_reg);
        EditText moreInfoField = findViewById(R.id.more_info_org_reg);
        Spinner countrySpinner = findViewById(R.id.spinner_countries_reg);
        Spinner regionSpinner = findViewById(R.id.spinner_regions_reg);
        Spinner provinceSpinner = findViewById(R.id.spinner_provinces_reg);
        Spinner citySpinner = findViewById(R.id.spinner_cities_reg);
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);

        ImageButton addDirector = findViewById(R.id.add_director);
        ImageButton addCenter = findViewById(R.id.add_center);


        TextView addCenterText = findViewById(R.id.add_center_text);
        TextView addDirectorText = findViewById(R.id.add_director_text);


        User[] director = {(User) getIntent().getSerializableExtra("director")};


        String[] nameCity = {getIntent().getStringExtra("nameCity")};
        String[] nameProvince = {getIntent().getStringExtra("nameProvince")};
        String[] nameRegion = {getIntent().getStringExtra("nameRegion")};

        int[] idCity = {-1};
        int[] idProvince = {-1};
        int[] idRegion = {-1};
        String[] idCountry = {""};
        int[] zipCode = {-1};
        long[] telephone = {-1};
        List<Organization> orgs = OrganizationsCaller.GetAllEvaluatedOrganizations();
        int idOrganization = orgs.size() + 1;
        String orgType = "EVALUATED";
        String illness = "AUTISM";

        int idAddress = AddressesCaller.GetAll().size() + 1;
        Address address = (Address) getIntent().getSerializableExtra("address");
        Organization organization = (Organization) getIntent().getSerializableExtra("organization");


        String[] zip_code = {getIntent().getStringExtra("zipCode")};
        String[] phone = {getIntent().getStringExtra("telephone")};

        String[] information = {getIntent().getStringExtra("information")};
        String[] email = {getIntent().getStringExtra("email")};


        country[0] = (Country) getIntent().getSerializableExtra("country");
        region[0] = (Region) getIntent().getSerializableExtra("region");
        province[0] = (Province) getIntent().getSerializableExtra("province");
        city[0] = (City) getIntent().getSerializableExtra("city");

        Country[] auxCountry = country;
        Region[] auxRegion = region;
        Province[] auxProvince = province;
        City[] auxCity = city;

        List<Center> centers = (List<Center>) getIntent().getSerializableExtra("centers");


        if (organization != null && address != null) {
            nameOrgField.setText(organization.nameOrg);
            addressNameField.setText(address.addressName);
            zipCodeField.setText(zip_code[0]);
            phoneField.setText(phone[0]);
            emailField.setText(email[0]);
            moreInfoField.setText(information[0]);
        }


        if (director[0] == null) {
            addDirectorText.setText(getString(R.string.add_director));
        } else {
            addDirectorText.setText(getString(R.string.edit_director));
        }


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                country[0] = countryAdapter[0].getItem(position);
                idCountry[0] = country[0].getIdCountry();
                if (idCountry[0].equals("ESP")) {
                    regionSpinner.setVisibility(View.VISIBLE);
                    provinceSpinner.setVisibility(View.VISIBLE);
                    citySpinner.setVisibility(View.VISIBLE);
                    provinceSpinner.setEnabled(false);
                    citySpinner.setEnabled(false);
                    nameProvinceField.setVisibility(View.GONE);
                    nameRegionField.setVisibility(View.GONE);
                    nameCityField.setVisibility(View.GONE);
                    getRegions(country[0].getIdCountry());
                    regionAdapter[0] = new RegionAdapter(RegisterOrganization.this, regions);
                    regionAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                    regionSpinner.setAdapter(regionAdapter[0]);
                    regionSpinner.setEnabled(true);
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
                nameRegion[0] = region[0].getNameRegion();
                getProvinces(region[0].getIdRegion(), region[0].getIdCountry());
                provinceAdapter[0] = new ProvinceAdapter(RegisterOrganization.this, provinces);
                provinceAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                provinceSpinner.setAdapter(provinceAdapter[0]);
                provinceSpinner.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ;
                province[0] = provinceAdapter[0].getItem(position);
                idProvince[0] = province[0].getIdProvince();
                nameProvince[0] = province[0].getNameProvince();
                getCities(province[0].getIdProvince(), province[0].getIdRegion(), province[0].getIdCountry());
                cityAdapter[0] = new CityAdapter(RegisterOrganization.this, cities);
                cityAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                citySpinner.setAdapter(cityAdapter[0]);
                citySpinner.setEnabled(true);
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
                nameCity[0] = city[0].getCityName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        nameOrgField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameOrgField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text = s.toString();
                if (input_text.equals("")) {
                    nameOrgField.setError(getString(R.string.please_org_name));
                } else {
                    nameOrgField.setError(null);
                }
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
                String input_text = s.toString();
                if (input_text.equals("")) {
                    addressNameField.setError(getString(R.string.please_org_name));
                } else {
                    addressNameField.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        zipCodeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                zipCodeField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text = s.toString();
                if (input_text.equals("")) {
                    zipCodeField.setError(getString(R.string.please_org_name));
                } else {
                    zipCodeField.setError(null);
                    zip_code[0] = input_text;
                    try {
                        zipCode[0] = Integer.parseInt(zip_code[0]);
                    } catch (NumberFormatException e) {
                        zipCode[0] = -1;
                    }
                }
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
                String input_text = s.toString();
                if (input_text.equals("")) {
                    nameRegionField.setError(getString(R.string.please_region));
                } else {
                    nameRegionField.setError(null);
                    nameRegion[0] = input_text;
                }
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
                String input_text = s.toString();
                if (input_text.equals("")) {
                    nameProvinceField.setError(getString(R.string.please_province));
                } else {
                    nameProvinceField.setError(null);
                    nameProvince[0] = input_text;
                }
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
                String input_text = s.toString();
                if (input_text.equals("")) {
                    nameCityField.setError(getString(R.string.please_city));
                } else {
                    nameCityField.setError(null);
                    nameCity[0] = input_text;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                phoneField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (inputText.equals("")) {
                    phoneField.setError(getString(R.string.mandatory_phone));
                } else if (FieldChecker.isASpanishNumber(inputText) || FieldChecker.isAForeignNumber(inputText)) {
                    phoneField.setError(null);
                    phone[0] = inputText;
                    try {
                        telephone[0] = Long.parseLong(phone[0]);
                    } catch (NumberFormatException e) {
                        telephone[0] = -1;
                    }
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
                    emailField.setError(null);
                    email[0] = inputText;
                } else {
                    if (inputText.equals("")) {
                        emailField.setError(getString(R.string.mandatory_email));
                    } else {
                        emailField.setError(getString(R.string.wrong_email));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        moreInfoField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text = s.toString();
                information[0] = input_text;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameOrgField.setEnabled(false);
                addressNameField.setEnabled(false);
                zipCodeField.setEnabled(false);
                phoneField.setEnabled(false);
                moreInfoField.setEnabled(false);
                countrySpinner.setEnabled(false);
                regionSpinner.setEnabled(false);
                provinceSpinner.setEnabled(false);
                citySpinner.setEnabled(false);

                nameRegionField.setEnabled(false);
                nameProvinceField.setEnabled(false);
                nameCityField.setEnabled(false);

                progressBar.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);

                loadingText.setText(getString(R.string.going_director));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), gui.RegisterOrgUser.class);
                        intent.putExtra("user", getIntent().getSerializableExtra("user"));
                        intent.putExtra("type", "director");
                        intent.putExtra("director", director[0]);
                        intent.putExtra("idOrganization", idOrganization);
                        intent.putExtra("orgType", orgType);
                        intent.putExtra("illness", illness);
                        intent.putExtra("address", new Address(idAddress, addressNameField.getText().toString(), zipCode[0], idCity[0], idProvince[0], idRegion[0], idCountry[0], nameCity[0], nameProvince[0], nameRegion[0]));
                        intent.putExtra("organization", new Organization(idOrganization, orgType, illness, nameOrgField.getText().toString(), idAddress, telephone[0], emailField.getText().toString(), moreInfoField.getText().toString(), ""));
                        intent.putExtra("country", country[0]);
                        intent.putExtra("province", province[0]);
                        intent.putExtra("region", region[0]);
                        intent.putExtra("city", city[0]);
                        intent.putExtra("email", email[0]);
                        intent.putExtra("zipCode", zip_code[0]);
                        intent.putExtra("telephone", phone[0]);
                        intent.putExtra("information", information[0]);
                        intent.putExtra("email", email[0]);
                        intent.putExtra("nameRegion", nameRegion[0]);
                        intent.putExtra("nameProvince", nameProvince[0]);
                        intent.putExtra("nameCity", nameCity[0]);
                        intent.putExtra("centers", (Serializable) centers);
                        startActivity(intent);
                        director[0] = (User) getIntent().getSerializableExtra("director");
                        if (director[0] == null) {
                            addDirectorText.setText(getString(R.string.add_director));
                        } else {
                            addDirectorText.setText(getString(R.string.edit_director));
                        }
                    }
                }, 100);
            }
        });


        addCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameOrgField.setEnabled(false);
                addressNameField.setEnabled(false);
                zipCodeField.setEnabled(false);
                phoneField.setEnabled(false);
                moreInfoField.setEnabled(false);
                countrySpinner.setEnabled(false);
                regionSpinner.setEnabled(false);
                provinceSpinner.setEnabled(false);
                citySpinner.setEnabled(false);

                nameRegionField.setEnabled(false);
                nameProvinceField.setEnabled(false);
                nameCityField.setEnabled(false);

                progressBar.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);

                loadingText.setText(getString(R.string.going_center));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), gui.RegisterNewCenter.class);
                        intent.putExtra("user", getIntent().getSerializableExtra("user"));
                        intent.putExtra("type", "director");
                        intent.putExtra("director", director[0]);
                        intent.putExtra("idOrganization", idOrganization);
                        intent.putExtra("orgType", orgType);
                        intent.putExtra("illness", illness);
                        intent.putExtra("address", new Address(idAddress, addressNameField.getText().toString(), zipCode[0], idCity[0], idProvince[0], idRegion[0], idCountry[0], nameCity[0], nameProvince[0], nameRegion[0]));
                        intent.putExtra("organization", new Organization(idOrganization, orgType, illness, nameOrgField.getText().toString(), idAddress, telephone[0], emailField.getText().toString(), moreInfoField.getText().toString(), ""));
                        intent.putExtra("country", country[0]);
                        intent.putExtra("province", province[0]);
                        intent.putExtra("region", region[0]);
                        intent.putExtra("city", city[0]);
                        intent.putExtra("email", email[0]);
                        intent.putExtra("zipCode", zip_code[0]);
                        intent.putExtra("telephone", phone[0]);
                        intent.putExtra("information", information[0]);
                        intent.putExtra("email", email[0]);
                        intent.putExtra("nameRegion", nameRegion[0]);
                        intent.putExtra("nameProvince", nameProvince[0]);
                        intent.putExtra("nameCity", nameCity[0]);
                        intent.putExtra("centers", (Serializable) centers);
                        startActivity(intent);
                        director[0] = (User) getIntent().getSerializableExtra("director");
                        if (director[0] == null) {
                            addDirectorText.setText(getString(R.string.add_director));
                        } else {
                            addDirectorText.setText(getString(R.string.edit_director));
                        }
                    }
                }, 100);
            }
        });

        CheckBox acceptLOPD = findViewById(R.id.accept_LOPD);

        Button register = findViewById(R.id.register_finished);
        register.setEnabled(false);

        acceptLOPD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                      if (isChecked) {
                                                          register.setEnabled(true);
                                                      } else {
                                                          register.setEnabled(false);
                                                      }
                                                  }
                                              }
        );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!nameOrgField.getText().equals("") && !addressNameField.getText().equals("") && !zipCodeField.getText().equals("") && (FieldChecker.isAForeignNumber(phoneField.getText().toString()) || FieldChecker.isASpanishNumber(phoneField.getText().toString())) && FieldChecker.emailHasCorrectFormat(emailField.getText().toString()) && director[0]!=null){
                    Address address = new Address(idAddress, addressNameField.getText().toString(), zipCode[0],idCity[0],idProvince[0],idRegion[0],idCountry[0],nameCity[0],nameProvince[0],nameRegion[0]);
                    Organization organization=new Organization(idOrganization,orgType,illness,nameOrgField.getText().toString(),idAddress,telephone[0],emailField.getText().toString(),moreInfoField.getText().toString(),"");
                    User directorOrg=director[0];
                    directorOrg.setPassword(PasswordCodifier.codify(directorOrg.getPassword()));


                    nameOrgField.setEnabled(false);
                    addressNameField.setEnabled(false);
                    zipCodeField.setEnabled(false);
                    phoneField.setEnabled(false);
                    moreInfoField.setEnabled(false);
                    countrySpinner.setEnabled(false);
                    regionSpinner.setEnabled(false);
                    provinceSpinner.setEnabled(false);
                    citySpinner.setEnabled(false);

                    nameRegionField.setEnabled(false);
                    nameProvinceField.setEnabled(false);
                    nameCityField.setEnabled(false);

                    progressBar.setVisibility(View.VISIBLE);
                    loadingText.setVisibility(View.VISIBLE);

                    loadingText.setText(getString(R.string.please_wait_reg_orgs));

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AddressesCaller.Create(address);
                            OrganizationsCaller.Create(organization);
                            UsersCaller.Create(directorOrg);
                            organization.setEmailOrgPrincipal(directorOrg.getEmailUser());
                            OrganizationsCaller.Update(organization.getIdOrganization(),organization.getOrganizationType(),organization.getIllness(),organization);
                            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
                            intent.putExtra("user",getIntent().getSerializableExtra("user"));
                            startActivity(intent);
                        }
                    }, 100);
                }else{
                    if(nameOrgField.getText().equals("")){
                        nameOrgField.setError(getString(R.string.please_org_name));
                    }
                    if(addressNameField.getText().equals("")){
                        addressNameField.setError(getString(R.string.please_address));
                    }
                    if(zipCodeField.getText().equals("")){
                        zipCodeField.setError(getString(R.string.please_zipcode));
                    }
                    if(!country[0].getIdCountry().equals("ESP")){
                        if(nameCityField.getText().equals("")){
                            nameCityField.setError(getString(R.string.please_city));
                        }
                        if(nameProvinceField.getText().equals("")){
                            nameProvinceField.setError(getString(R.string.please_province));
                        }
                        if(nameRegionField.getText().equals("")){
                            nameRegionField.setError(getString(R.string.please_region));
                        }
                    }
                    if(!FieldChecker.isAForeignNumber(phoneField.getText().toString()) && !FieldChecker.isASpanishNumber(phoneField.getText().toString())){
                        phoneField.setError(getString(R.string.wrong_phone));
                    }
                    if(!FieldChecker.emailHasCorrectFormat(emailField.getText().toString())){
                        phoneField.setError(getString(R.string.wrong_email));
                    }
                    if(director[0]!=null){
                        addDirectorText.setError(getString(R.string.please_director));
                    }
                }

            }
        });


    }
    public List<Country> getCountries(){
        if(countries==null){
            countries= CountriesCaller.GetAll();
        }
        return countries;
    }

    public List<Region> getRegions(String idCountry){
        if(regions==null){
            regions= RegionsCaller.GetRegionsByCountry(idCountry);
            currIdCountry=idCountry;
        }
        return regions;
    }

    public List<Province> getProvinces(int idRegion,String idCountry){
        if(provinces==null || currIdRegion!=idRegion || currIdCountry!=idCountry){
            provinces= ProvincesCaller.GetProvincesByRegion(idRegion,idCountry);
            currIdRegion=idRegion;
            currIdCountry=idCountry;
        }
        return provinces;
    }

    public List<City> getCities(int idProvince, int idRegion,String idCountry){
        if(cities==null || currIdProvince!=currIdProvince || currIdRegion!=idRegion || currIdCountry!=idCountry){
            cities= CitiesCaller.GetCitiesByProvince(idProvince,idRegion,idCountry);
            currIdProvince=idProvince;
            currIdRegion=idRegion;
            currIdCountry=idCountry;
        }
        return cities;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
            intent.putExtra("user",getIntent().getSerializableExtra("user"));
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}