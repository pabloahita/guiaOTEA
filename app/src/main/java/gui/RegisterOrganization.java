package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import otea.connection.controller.AddressesController;
import otea.connection.controller.CentersController;
import otea.connection.controller.CountriesController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.TranslatorController;
import otea.connection.controller.UsersController;
import session.FileManager;
import session.Session;

public class RegisterOrganization extends AppCompatActivity {

    private List<Country> countries;

    private List<Region> regions;

    private List<Province> provinces;

    private List<City> cities;


    CountryAdapter[] countryAdapter={null};

    PhoneCodeAdapter[] phoneCodeAdapter;

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

    ImageButton imageOrgButton;

    ImageButton imageDirButton;


    TextView imageOrgText;

    TextView imageDirText;


    InputStream profilePhotoOrg;

    InputStream profilePhotoDir;

    int selectedPhoto=-1;


    String imgDirName="";
    String imgOrgName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_organization);



        phoneCodeAdapter=new PhoneCodeAdapter[2];

        countryAdapter[0] = new CountryAdapter(RegisterOrganization.this, countries);
        countryAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        phoneCodeAdapter[0]=new PhoneCodeAdapter(RegisterOrganization.this,countries);
        phoneCodeAdapter[1]=new PhoneCodeAdapter(RegisterOrganization.this,countries);


        ConstraintLayout loading=findViewById(R.id.final_background);

        loading.setVisibility(View.GONE);

        EditText nameOrgField = findViewById(R.id.org_name_reg);
        EditText addressNameField = findViewById(R.id.name_address_reg);
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
        Spinner phoneCode1=findViewById(R.id.phonecode1);
        Spinner phoneCode2=findViewById(R.id.phonecode2);
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);
        phoneCode2.setAdapter(phoneCodeAdapter[1]);
        phoneCode2.setEnabled(true);

        EditText firstNameField=(EditText)findViewById(R.id.first_name_reg);
        EditText lastNameField=(EditText)findViewById(R.id.last_name_reg);
        EditText emailDirField=(EditText)findViewById(R.id.email_dir_reg);
        EditText passwordField=(EditText)findViewById(R.id.password_reg);
        EditText directorPhoneField=(EditText)findViewById(R.id.telephone_reg);

        Drawable correct=ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);

        imageOrgButton=findViewById(R.id.imageOrg);
        imageDirButton=findViewById(R.id.imageDir);
        imageOrgText=findViewById(R.id.imageOrgText);
        imageDirText=findViewById(R.id.imageDirText);

        int[] idCity = {-1};
        int[] idProvince = {-1};
        int[] idRegion = {-1};
        String[] idCountry = {""};

        List<Organization> orgs = OrganizationsController.getInstance().GetAllEvaluatedOrganizations();
        int idOrganization = orgs.size() + 1;
        String orgType = "EVALUATED";
        String illness = "AUTISM";

        int idAddress = AddressesController.getInstance().GetAll().size() + 1;
        

        Map<String,String> fields=new HashMap<String,String>();
        fields.put("nameOrg","");
        fields.put("address","");
        fields.put("nameCity","");
        fields.put("nameProvince","");
        fields.put("nameRegion","");
        fields.put("telephoneCodeOrg","");
        fields.put("telephoneOrg","");
        fields.put("emailOrg","");
        fields.put("information","");
        fields.put("firstName","");
        fields.put("lastName","");
        fields.put("emailDir","");
        fields.put("passwordDir","");
        fields.put("telephoneCodeDir","");
        fields.put("telephoneDir","");



        imageOrgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RegisterOrganization.this,
                        Manifest.permission.READ_MEDIA_IMAGES)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterOrganization.this,
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                            33);
                    onClick(v);
                }
                else {
                    selectedPhoto=0;
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 100);
                }
            }
        });

        imageDirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RegisterOrganization.this,
                        Manifest.permission.READ_MEDIA_IMAGES)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterOrganization.this,
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                            33);
                    onClick(v);
                }
                else {
                    selectedPhoto=1;
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 100);
                }
            }
        });


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                country[0] = countryAdapter[0].getItem(position);
                fields.replace("telephoneCodeOrg",phoneCodeAdapter[0].getItem(position).getPhone_code());
                fields.replace("telephoneCodeDir",phoneCodeAdapter[1].getItem(position).getPhone_code());
                phoneCode1.setSelection(position);
                phoneCode2.setSelection(position);

                idCountry[0] = country[0].getIdCountry();
                if (FieldChecker.isPrecharged(idCountry[0])) {
                    if(regions.size()>1){
                        regionSpinner.setVisibility(View.VISIBLE);
                        regionAdapter[0] = new RegionAdapter(RegisterOrganization.this, regions.stream().filter(new Predicate<Region>() {
                            @Override
                            public boolean test(Region region) {
                                return region.getIdCountry().equals(idCountry[0]);
                            }
                        }).collect(Collectors.toList()));
                        regionAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        regionSpinner.setAdapter(regionAdapter[0]);
                        regionSpinner.setEnabled(true);
                        provinceSpinner.setEnabled(false);
                    }
                    else{
                        if(regionSpinner.getVisibility()==View.VISIBLE){
                            regionSpinner.setVisibility(View.GONE);
                        }
                        provinceAdapter[0] = new ProvinceAdapter(RegisterOrganization.this, provinces.stream().filter(new Predicate<Province>() {
                            @Override
                            public boolean test(Province province) {
                                return province.getIdCountry().equals(idCountry[0]);
                            }
                        }).collect(Collectors.toList()));
                        provinceAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        provinceSpinner.setAdapter(provinceAdapter[0]);
                        provinceSpinner.setEnabled(true);
                    }
                    provinceSpinner.setVisibility(View.VISIBLE);
                    citySpinner.setVisibility(View.VISIBLE);
                    citySpinner.setEnabled(false);
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
                provinceAdapter[0] = new ProvinceAdapter(RegisterOrganization.this, provinces.stream().filter(new Predicate<Province>() {
                    @Override
                    public boolean test(Province province) {
                        return province.getIdRegion()==idRegion[0] && province.getIdCountry().equals(idCountry[0]);
                    }
                }).collect(Collectors.toList()));
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
                province[0] = provinceAdapter[0].getItem(position);
                idProvince[0] = province[0].getIdProvince();
                if(Locale.getDefault().getLanguage().equals("es")) {
                    fields.replace("nameProvince", province[0].getNameSpanish());
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    fields.replace("nameProvince",province[0].getNameFrench());
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    fields.replace("nameProvince", province[0].getNameBasque());
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    fields.replace("nameProvince",province[0].getNameCatalan());
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    fields.replace("nameProvince", province[0].getNameDutch());
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    fields.replace("nameProvince",province[0].getNameGalician());
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    fields.replace("nameProvince", province[0].getNameGerman());
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    fields.replace("nameProvince",province[0].getNameItalian());
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    fields.replace("nameProvince",province[0].getNamePortuguese());
                }else{
                    fields.replace("nameProvince",province[0].getNameEnglish());
                }
                cityAdapter[0] = new CityAdapter(RegisterOrganization.this, cities.stream().filter(new Predicate<City>() {
                    @Override
                    public boolean test(City city) {
                        return city.getIdProvince()==idProvince[0] && city.getIdRegion()==idRegion[0] && city.getIdCountry().equals(idCountry[0]);
                    }
                }).collect(Collectors.toList()));
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
                fields.replace("nameOrg",input_text);
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
                String input_text = s.toString();
                if (input_text.equals("")) {
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameRegionField.setError(getString(R.string.please_region));
                } else {
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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
                String input_text = s.toString();
                if (input_text.equals("")) {
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    nameProvinceField.setError(getString(R.string.please_province));
                } else {
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameProvinceField.setError(null);
                }
                fields.replace("nameProvince", input_text);
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
                fields.replace("telephoneCodeOrg",phoneCodeAdapter[0].getItem(position).getPhone_code());
                if (fields.get("telephoneOrg").equals("")) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    orgPhoneField.setError(null);
                } else if (FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+fields.get("telephoneOrg"))) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    orgPhoneField.setError(null);
                } else {
                    orgPhoneField.setError(getString(R.string.wrong_phone));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        orgPhoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                orgPhoneField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fields.replace("telephoneOrg",s.toString());
                if (fields.get("telephoneOrg").equals("")) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    orgPhoneField.setError(null);
                } else if (FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+fields.get("telephoneOrg"))) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    orgPhoneField.setError(null);
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
                } else {
                    emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    if (inputText.equals("")) {
                        emailField.setError(getString(R.string.mandatory_email));
                    } else {
                        emailField.setError(getString(R.string.wrong_email));
                    }
                }
                fields.replace("emailOrg",inputText);
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
                fields.replace("information",input_text);
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
                        }
                        fields.replace("firstName",inputText);
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
                        }
                        fields.replace("lastName",inputText);
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
                        fields.replace("emailDir",inputText);
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
                        }
                        else{
                            passwordField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            passwordField.setError(getString(R.string.mandatory_password));
                        }
                        fields.replace("passwordDir",inputText);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );

        phoneCode2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fields.replace("telephoneCodeDir",phoneCodeAdapter[1].getItem(position).getPhone_code());
                if (fields.get("telephoneDir").equals("")) {
                    directorPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    directorPhoneField.setError(null);
                } else if (FieldChecker.isACorrectPhone(fields.get("telephoneCodeDir")+fields.get("telephoneDir"))) {
                    directorPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    directorPhoneField.setError(null);
                } else {
                    directorPhoneField.setError(getString(R.string.wrong_phone));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        directorPhoneField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        directorPhoneField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        fields.replace("telephoneDir",s.toString());
                        if (fields.get("telephoneDir").equals("")) {
                            directorPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            directorPhoneField.setError(null);
                        } else if (FieldChecker.isACorrectPhone(fields.get("telephoneCodeDir")+fields.get("telephoneDir"))) {
                            directorPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            directorPhoneField.setError(null);
                        } else {
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

                if(!fields.get("nameOrg").equals("") && !fields.get("address").equals("") && (FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+fields.get("telephoneOrg")))
                        && FieldChecker.emailHasCorrectFormat(fields.get("emailOrg")) && !fields.get("firstName").equals("") && !fields.get("lastName").equals("") && FieldChecker.emailHasCorrectFormat(fields.get("emailDir")) &&
                        !fields.get("passwordDir").equals("") && (FieldChecker.isACorrectPhone(fields.get("telephoneCodeDir")+fields.get("telephoneDir")))){

                    nameOrgField.setEnabled(false);
                    addressNameField.setEnabled(false);
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

                    loading.setVisibility(View.VISIBLE);

                    String informationEnglish="";
                    String informationSpanish="";
                    String informationFrench="";
                    String informationBasque="";
                    String informationCatalan="";
                    String informationDutch="";
                    String informationGalician="";
                    String informationGerman="";
                    String informationItalian="";
                    String informationPortuguese="";

                    String informationText=fields.get("information");

                    AtomicReference<List<String>> translations=new AtomicReference<>();

                    CompletableFuture<Void> taskInformation= CompletableFuture.runAsync(() -> {
                        translations.set(TranslatorController.getInstance().translate(informationText, Locale.getDefault().getLanguage()));
                    });

                    CompletableFuture<Void> taskPhotoDir = CompletableFuture.runAsync(() -> {
                        imgDirName = "USER_" + (fields.get("emailDir").replace("@", "_").replace(".", "_")) + ".jpg";
                        FileManager.uploadFile(profilePhotoDir, "profile-photos", imgDirName);
                        try{
                            profilePhotoDir.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    });

                    CompletableFuture<Void> taskPhotoOrg = CompletableFuture.runAsync(() -> {
                        imgOrgName="ORG_"+idOrganization+"_"+orgType+"_"+illness+".jpg";
                        FileManager.uploadFile(profilePhotoOrg, "profile-photos", imgOrgName);
                        try{
                            profilePhotoOrg.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    });

                    try{
                        if(!informationText.equals("")){
                            if(profilePhotoDir!=null){
                                if(profilePhotoOrg!=null) {
                                    CompletableFuture.allOf(taskInformation,taskPhotoDir,taskPhotoOrg).get();
                                }
                                else{
                                    CompletableFuture.allOf(taskInformation,taskPhotoDir).get();
                                }
                            }
                            else{
                                if(profilePhotoOrg!=null){
                                    CompletableFuture.allOf(taskInformation,taskPhotoOrg).get();
                                }
                                else{
                                    CompletableFuture.allOf(taskInformation).get();
                                }
                            }
                            if(Locale.getDefault().getLanguage().equals("es")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(1);
                                informationBasque= translations.get().get(2);
                                informationCatalan= translations.get().get(3);
                                informationDutch= translations.get().get(4);
                                informationGalician= translations.get().get(5);
                                informationGerman= translations.get().get(6);
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench=informationText;
                                informationBasque= translations.get().get(2);
                                informationCatalan= translations.get().get(3);
                                informationDutch= translations.get().get(4);
                                informationGalician= translations.get().get(5);
                                informationGerman= translations.get().get(6);
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(2);
                                informationBasque=informationText;
                                informationCatalan= translations.get().get(3);
                                informationDutch= translations.get().get(4);
                                informationGalician= translations.get().get(5);
                                informationGerman= translations.get().get(6);
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(2);
                                informationBasque= translations.get().get(3);
                                informationCatalan=informationText;
                                informationDutch= translations.get().get(4);
                                informationGalician= translations.get().get(5);
                                informationGerman= translations.get().get(6);
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(2);
                                informationBasque= translations.get().get(3);
                                informationCatalan= translations.get().get(4);
                                informationDutch=informationText;
                                informationGalician= translations.get().get(5);
                                informationGerman= translations.get().get(6);
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(2);
                                informationBasque= translations.get().get(3);
                                informationCatalan= translations.get().get(4);
                                informationDutch= translations.get().get(5);
                                informationGalician=informationText;
                                informationGerman= translations.get().get(6);
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(2);
                                informationBasque= translations.get().get(3);
                                informationCatalan= translations.get().get(4);
                                informationDutch= translations.get().get(5);
                                informationGalician= translations.get().get(6);
                                informationGerman=informationText;
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(2);
                                informationBasque= translations.get().get(3);
                                informationCatalan= translations.get().get(4);
                                informationDutch= translations.get().get(5);
                                informationGalician= translations.get().get(6);
                                informationGerman= translations.get().get(7);
                                informationItalian=informationText;
                                informationPortuguese= translations.get().get(8);
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                informationEnglish= translations.get().get(0);
                                informationSpanish= translations.get().get(1);
                                informationFrench= translations.get().get(2);
                                informationBasque= translations.get().get(3);
                                informationCatalan= translations.get().get(4);
                                informationDutch= translations.get().get(5);
                                informationGalician= translations.get().get(6);
                                informationGerman= translations.get().get(7);
                                informationItalian= translations.get().get(8);
                                informationPortuguese=informationText;
                            }else{
                                informationEnglish= informationText;
                                informationSpanish= translations.get().get(0);
                                informationFrench= translations.get().get(1);
                                informationBasque= translations.get().get(2);
                                informationCatalan= translations.get().get(3);
                                informationDutch= translations.get().get(4);
                                informationGalician= translations.get().get(5);
                                informationGerman= translations.get().get(6);
                                informationItalian= translations.get().get(7);
                                informationPortuguese= translations.get().get(8);
                            }

                        }
                        else{
                            if(profilePhotoDir!=null){
                                if(profilePhotoOrg!=null) {
                                    CompletableFuture.allOf(taskPhotoDir,taskPhotoOrg).get();
                                }
                                else{
                                    CompletableFuture.allOf(taskPhotoDir).get();
                                }
                            }
                            else{
                                if(profilePhotoOrg!=null) {
                                    CompletableFuture.allOf(taskPhotoOrg).get();
                                }
                            }
                        }
                    }catch(InterruptedException | ExecutionException e){
                        throw new RuntimeException(e);
                    }




                    Address address = new Address(idAddress, fields.get("address"), idCity[0],idProvince[0],idRegion[0],idCountry[0],fields.get("nameCity"),fields.get("nameProvince"),fields.get("nameRegion"));

                    Organization organization=new Organization(idOrganization,orgType,illness,fields.get("nameOrg"),idAddress,fields.get("emailOrg"),fields.get("telephoneCodeOrg")+" "+fields.get("telephoneOrg"),informationEnglish,informationSpanish,informationFrench,informationBasque,informationCatalan,informationDutch,informationGalician,informationGerman,informationItalian,informationPortuguese,imgOrgName);
                    User directorOrg=new User(fields.get("emailDir"),"DIRECTOR",fields.get("firstName"),fields.get("lastName"),PasswordCodifier.codify(fields.get("passwordDir")),fields.get("telephoneCodeDir")+" "+fields.get("telephoneDir"),idOrganization,orgType,illness,imgDirName);


                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AddressesController.getInstance().Create(address);
                            OrganizationsController.getInstance().Create(organization);
                            UsersController.getInstance().Create(directorOrg);

                            CentersController.getInstance().Create(new Center(organization.getIdOrganization(),organization.getOrganizationType(),organization.getIllness(),1,"Headquarters","Sede principal","Siège social","Egoitza","Seu principal","Hoofdkwartier","Sede principal","Hauptsitz","Sede principale","Sede principal",idAddress,fields.get("telephoneCodeOrg")+" "+fields.get("telephoneOrg"),fields.get("emailOrg")));
                            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                            loading.setVisibility(View.GONE);
                            startActivity(intent);
                        }
                    }, 100);



                }else{
                    if(fields.get("nameOrg").equals("")){
                        nameOrgField.setError(getString(R.string.please_org_name));
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
                    if(!FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+fields.get("telephoneOrg"))){
                        orgPhoneField.setError(getString(R.string.wrong_phone));
                    }
                    if(!FieldChecker.emailHasCorrectFormat(emailField.getText().toString())){
                        orgPhoneField.setError(getString(R.string.wrong_email));
                    }
                    if(fields.get("firstName").equals("")){
                        firstNameField.setError(getString(R.string.mandatory_first_name));
                    }
                    if(fields.get("lastName").equals("")){
                        lastNameField.setError(getString(R.string.mandatory_last_name));
                    }
                    if(!FieldChecker.emailHasCorrectFormat(fields.get("emailDir"))){
                        emailDirField.setError(getString(R.string.wrong_email));
                    }
                    if(!FieldChecker.passwordHasCorrectFormat(fields.get("passwordDir"))){
                        passwordField.setError(getString(R.string.wrong_password));
                    }
                    if(!FieldChecker.isACorrectPhone(fields.get("telephoneCodeDir")+fields.get("telephoneDir"))){
                        directorPhoneField.setError(getString(R.string.wrong_phone));
                    }
                }

            }
        });


    }



    public List<Country> getCountries(){
        if(countries==null){
            countries= CountriesController.getInstance().GetAll(Locale.getDefault().getLanguage());
        }
        return countries;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            try {
                Uri imageUri = data.getData();
                if(selectedPhoto==0){
                    profilePhotoOrg = getContentResolver().openInputStream(imageUri);
                    imageOrgButton.setImageURI(imageUri);
                    imageOrgText.setVisibility(View.GONE);
                }
                else{
                    profilePhotoDir = getContentResolver().openInputStream(imageUri);
                    imageDirButton.setImageURI(imageUri);
                    imageDirText.setVisibility(View.GONE);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}