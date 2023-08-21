package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        EditText orgPhoneField = findViewById(R.id.phone_reg);
        EditText moreInfoField = findViewById(R.id.more_info_org_reg);
        Spinner countrySpinner = findViewById(R.id.spinner_countries_reg);
        Spinner regionSpinner = findViewById(R.id.spinner_regions_reg);
        Spinner provinceSpinner = findViewById(R.id.spinner_provinces_reg);
        Spinner citySpinner = findViewById(R.id.spinner_cities_reg);
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);

        EditText firstNameField=(EditText)findViewById(R.id.first_name_reg);
        EditText lastNameField=(EditText)findViewById(R.id.last_name_reg);
        EditText emailDirField=(EditText)findViewById(R.id.email_dir_reg);
        EditText passwordField=(EditText)findViewById(R.id.password_reg);
        EditText directorPhoneField=(EditText)findViewById(R.id.telephone_reg);

        Drawable correct=ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);




        String[] nameCity = {""};
        String[] nameProvince = {""};
        String[] nameRegion = {""};

        int[] idCity = {-1};
        int[] idProvince = {-1};
        int[] idRegion = {-1};
        String[] idCountry = {""};
        int[] zipCode = {-1};
        long[] orgPhone = {-1};

        String[] firstName={""};
        String[] lastName={""};
        String[] emailDir={""};
        String[] password={""};
        long[] dirPhone={-1};

        List<Organization> orgs = OrganizationsCaller.GetAllEvaluatedOrganizations();
        int idOrganization = orgs.size() + 1;
        String orgType = "EVALUATED";
        String illness = "AUTISM";

        int idAddress = AddressesCaller.GetAll().size() + 1;
        Address address = (Address) getIntent().getSerializableExtra("address");
        Organization organization = (Organization) getIntent().getSerializableExtra("organization");


        String[] zip_code = {getIntent().getStringExtra("zipCode")};

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

        List<Center> centers = new LinkedList<Center>();



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
                    nameOrgField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    nameOrgField.setError(getString(R.string.please_org_name));
                } else {
                    nameOrgField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                    addressNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    addressNameField.setError(getString(R.string.please_org_name));
                } else {
                    addressNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                    zipCodeField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    zipCodeField.setError(getString(R.string.please_org_name));
                } else {
                    zipCodeField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameRegionField.setError(getString(R.string.please_region));
                } else {
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    nameProvinceField.setError(getString(R.string.please_province));
                } else {
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                    nameCityField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    nameCityField.setError(getString(R.string.please_city));
                } else {
                    nameCityField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameCityField.setError(null);
                    nameCity[0] = input_text;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        orgPhoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                orgPhoneField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (inputText.equals("")) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    orgPhoneField.setError(getString(R.string.mandatory_phone));
                } else if (FieldChecker.isASpanishNumber(inputText) || FieldChecker.isAForeignNumber(inputText)) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    orgPhoneField.setError(null);
                    try {
                        orgPhone[0] = Long.parseLong(inputText);
                    } catch (NumberFormatException e) {
                        orgPhone[0] = -1;
                    }
                } else {
                    orgPhoneField.setError(getString(R.string.wrong_phone));
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
                    email[0] = inputText;
                } else {
                    emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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

        firstNameField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        firstNameField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(inputText.equals("")){
                            firstNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            firstNameField.setError(getString(R.string.mandatory_first_name));
                        }else{
                            firstNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            firstNameField.setError(null);
                            firstName[0]=inputText;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }
                }
        );

        lastNameField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        lastNameField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(inputText.equals("")){
                            lastNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            lastNameField.setError(getString(R.string.mandatory_first_name));
                        }else{
                            lastNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            lastNameField.setError(null);
                            lastName[0]=inputText;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }
                }
        );

        emailDirField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        emailDirField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(FieldChecker.emailHasCorrectFormat(inputText)){
                            emailDirField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            emailDirField.setError(null);
                            emailDir[0]=inputText;
                        }
                        else{
                            emailDirField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            if(inputText.equals("")){
                                emailDirField.setError(getString(R.string.mandatory_email));
                            }
                            else{
                                emailDirField.setError(getString(R.string.wrong_email));
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );

        passwordField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        passwordField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        Pattern patronPassword=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$&%#.!\"^¨_:;/+><()=¿?¡!-]).{6,}$");
                        Matcher matcher= patronPassword.matcher(inputText);
                        if(matcher.matches()){
                            passwordField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            passwordField.setError(null);
                            password[0]=inputText;
                        }
                        else{
                            passwordField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            passwordField.setError(getString(R.string.mandatory_password));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );

        directorPhoneField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        directorPhoneField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(inputText.equals("")){
                            directorPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            directorPhoneField.setError(getString(R.string.mandatory_phone));
                        }
                        else if(FieldChecker.isASpanishNumber(inputText)||FieldChecker.isAForeignNumber(inputText)){
                            directorPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            directorPhoneField.setError(null);
                            try{
                                dirPhone[0]=Long.parseLong(inputText);
                            }catch(NumberFormatException e){
                                dirPhone[0]=-1;
                            }
                        }
                        else{
                            directorPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            directorPhoneField.setError(getString(R.string.wrong_phone));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );



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


                if(!nameOrgField.getText().equals("") && !addressNameField.getText().equals("") && !zipCodeField.getText().equals("") && (FieldChecker.isAForeignNumber(orgPhoneField.getText().toString()) || FieldChecker.isASpanishNumber(orgPhoneField.getText().toString()))
                        && FieldChecker.emailHasCorrectFormat(emailField.getText().toString()) && !firstNameField.getText().equals("") && !lastNameField.getText().equals("") && FieldChecker.emailHasCorrectFormat(emailDirField.getText().toString()) && !passwordField.getText().equals("") && (FieldChecker.isAForeignNumber(directorPhoneField.getText().toString()) || FieldChecker.isASpanishNumber(directorPhoneField.getText().toString()))){
                    Address address = new Address(idAddress, addressNameField.getText().toString(), zipCode[0],idCity[0],idProvince[0],idRegion[0],idCountry[0],nameCity[0],nameProvince[0],nameRegion[0]);
                    Organization organization=new Organization(idOrganization,orgType,illness,nameOrgField.getText().toString(),idAddress,orgPhone[0],emailField.getText().toString(),moreInfoField.getText().toString(),"");
                    User directorOrg=new User(emailDir[0],"ORGANIZATION",firstName[0],lastName[0],password[0],dirPhone[0],idOrganization,orgType,illness);
                    directorOrg.setPassword(PasswordCodifier.codify(directorOrg.getPassword()));


                    nameOrgField.setEnabled(false);
                    addressNameField.setEnabled(false);
                    zipCodeField.setEnabled(false);
                    orgPhoneField.setEnabled(false);
                    moreInfoField.setEnabled(false);
                    countrySpinner.setEnabled(false);
                    regionSpinner.setEnabled(false);
                    provinceSpinner.setEnabled(false);
                    citySpinner.setEnabled(false);

                    nameRegionField.setEnabled(false);
                    nameProvinceField.setEnabled(false);
                    nameCityField.setEnabled(false);

                    firstNameField.setEnabled(false);
                    lastNameField.setEnabled(false);
                    emailDirField.setEnabled(false);
                    passwordField.setEnabled(false);
                    directorPhoneField.setEnabled(false);

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
                    if(!FieldChecker.isAForeignNumber(orgPhoneField.getText().toString()) && !FieldChecker.isASpanishNumber(orgPhoneField.getText().toString())){
                        orgPhoneField.setError(getString(R.string.wrong_phone));
                    }
                    if(!FieldChecker.emailHasCorrectFormat(emailField.getText().toString())){
                        orgPhoneField.setError(getString(R.string.wrong_email));
                    }
                    if(firstName[0].equals("")){
                        firstNameField.setError(getString(R.string.mandatory_first_name));
                    }
                    if(lastName[0].equals("")){
                        lastNameField.setError(getString(R.string.mandatory_last_name));
                    }
                    if(!FieldChecker.emailHasCorrectFormat(emailDir[0])){
                        emailDirField.setError(getString(R.string.wrong_email));
                    }
                    if(!FieldChecker.passwordHasCorrectFormat(password[0])){
                        passwordField.setError(getString(R.string.wrong_password));
                    }
                    if(!(FieldChecker.isASpanishNumber(directorPhoneField.getText().toString()) || FieldChecker.isAForeignNumber(directorPhoneField.getText().toString()))){
                        directorPhoneField.setError(getString(R.string.wrong_phone));
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