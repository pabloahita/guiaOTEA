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
import android.widget.EditText;
import android.widget.Spinner;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.List;
import java.util.Locale;

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

public class RegisterNewCenter extends AppCompatActivity {

    List<Organization> organizations;
    List<Country> countries;

    List<Country> countriesWithPhoneCode;
    List<Region> regions;
    List<Province> provinces;
    List<City> cities;
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
    String currIdCountry;
    int currIdRegion;
    int currIdProvince;

    List<Center> centers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_center);

        getEvaluatedOrgs();
        orgAdapter[0]=new OrgsAdapter(RegisterNewCenter.this,organizations);
        orgAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        getCountries();
        countryAdapter[0]= new CountryAdapter(RegisterNewCenter.this, countries);
        countryAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        phoneCodeAdapter[0] = new PhoneCodeAdapter(RegisterNewCenter.this,countries);
        phoneCodeAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        EditText descriptionCenterField=findViewById(R.id.description_center_reg);
        EditText addressNameField=findViewById(R.id.name_address_reg);
        EditText nameProvinceField=findViewById(R.id.foreign_province_reg);
        EditText nameRegionField=findViewById(R.id.foreign_region_reg);
        EditText nameCityField=findViewById(R.id.foreign_city_reg);
        EditText phoneField=findViewById(R.id.phone_reg);
        EditText emailField = findViewById(R.id.email_reg);
        Spinner countrySpinner=findViewById(R.id.spinner_countries_reg);
        Spinner regionSpinner=findViewById(R.id.spinner_regions_reg);
        Spinner provinceSpinner=findViewById(R.id.spinner_provinces_reg);
        Spinner citySpinner=findViewById(R.id.spinner_cities_reg);
        Spinner orgSpinner=findViewById(R.id.spinner_orgs);
        Spinner phoneCode1=findViewById(R.id.phonecode1);
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);
        orgSpinner.setAdapter(orgAdapter[0]);
        orgSpinner.setEnabled(true);
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);


        ConstraintLayout background=findViewById(R.id.final_background);


        Drawable correct= ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);

        background.setVisibility(View.GONE);

        int[] idCity={-1};
        int[] idProvince={-1};
        int[] idRegion={-1};
        String[] idCountry={""};

        String[] nameCity={getIntent().getStringExtra("nameCity")};
        String[] nameProvince={getIntent().getStringExtra("nameProvince")};
        String[] nameRegion={getIntent().getStringExtra("nameRegion")};


        String[] phone=new String[2];

        String[] information={};
        String[] email={""};

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
                idCountry[0] = country[0].getIdCountry();
                if (FieldChecker.isPrecharged(idCountry[0])) {
                    getRegions(country[0].getIdCountry());
                    if(regions.size()>1){
                        regionSpinner.setVisibility(View.VISIBLE);
                        regionAdapter[0] = new RegionAdapter(RegisterNewCenter.this, regions);
                        regionAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        regionSpinner.setAdapter(regionAdapter[0]);
                        regionSpinner.setEnabled(true);
                        provinceSpinner.setEnabled(false);
                    }
                    else{
                        if(regionSpinner.getVisibility()==View.VISIBLE){
                            regionSpinner.setVisibility(View.GONE);
                        }
                        getProvinces(-1, idCountry[0]);
                        provinceAdapter[0] = new ProvinceAdapter(RegisterNewCenter.this, provinces);
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
                idRegion[0]=region[0].getIdRegion();
                nameRegion[0]="";
                if(Locale.getDefault().getLanguage().equals("es")) {
                    nameRegion[0] = region[0].getNameSpanish();
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    nameRegion[0]=region[0].getNameFrench();
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    nameRegion[0] = region[0].getNameBasque();
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    nameRegion[0]=region[0].getNameCatalan();
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    nameRegion[0] = region[0].getNameDutch();
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    nameRegion[0]=region[0].getNameGalician();
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    nameRegion[0]= region[0].getNameGerman();
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    nameRegion[0]=region[0].getNameItalian();
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    nameRegion[0]=region[0].getNamePortuguese();
                }else{
                    nameRegion[0]=region[0].getNameEnglish();
                }
                getProvinces(region[0].getIdRegion(),region[0].getIdCountry());
                provinceAdapter[0]=new ProvinceAdapter(RegisterNewCenter.this,provinces);
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {;
                province[0]=provinceAdapter[0].getItem(position);
                idProvince[0]=province[0].getIdProvince();
                nameProvince[0]="";
                if(Locale.getDefault().getLanguage().equals("es")) {
                    nameProvince[0] = province[0].getNameSpanish();
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    nameProvince[0]=province[0].getNameFrench();
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    nameProvince[0] = province[0].getNameBasque();
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    nameProvince[0]=province[0].getNameCatalan();
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    nameProvince[0] = province[0].getNameDutch();
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    nameProvince[0]=province[0].getNameGalician();
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    nameProvince[0]= province[0].getNameGerman();
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    nameProvince[0]=province[0].getNameItalian();
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    nameProvince[0]=province[0].getNamePortuguese();
                }else{
                    nameProvince[0]=province[0].getNameEnglish();
                }
                getCities(province[0].getIdProvince(), province[0].getIdRegion(), province[0].getIdCountry());
                cityAdapter[0] = new CityAdapter(RegisterNewCenter.this, cities);
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
                nameCity[0]="";
                if(Locale.getDefault().getLanguage().equals("es")) {
                    nameCity[0] = city[0].getNameSpanish();
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    nameCity[0]=city[0].getNameFrench();
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    nameCity[0] = city[0].getNameBasque();
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    nameCity[0]=city[0].getNameCatalan();
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    nameCity[0] = city[0].getNameDutch();
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    nameCity[0]=city[0].getNameGalician();
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    nameCity[0]= city[0].getNameGerman();
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    nameCity[0]=city[0].getNameItalian();
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    nameCity[0]=city[0].getNamePortuguese();
                }else{
                    nameCity[0]=city[0].getNameEnglish();
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
                    descriptionCenterField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    descriptionCenterField.setError(getString(R.string.please_org_name));
                }
                else{
                    descriptionCenterField.setError(null);
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
                String input_text=s.toString();
                if(input_text.equals("")){
                    addressNameField.setError(getString(R.string.please_org_name));
                }
                else{
                    addressNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    addressNameField.setError(null);
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
                String input_text=s.toString();
                if(input_text.equals("")){
                    nameRegionField.setError(getString(R.string.please_region));
                }
                else{
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameRegionField.setError(null);
                    nameRegion[0]=input_text;
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
                String input_text=s.toString();
                if(input_text.equals("")){
                    nameProvinceField.setError(getString(R.string.please_province));
                }
                else{
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameProvinceField.setError(null);
                    nameProvince[0]=input_text;
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
                String input_text=s.toString();
                if(input_text.equals("")){
                    nameCityField.setError(getString(R.string.please_city));
                }
                else{
                    nameCityField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    nameCityField.setError(null);
                    nameCity[0]=input_text;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneCode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phone[0] = phoneCodeAdapter[0].getItem(position).getPhone_code();
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
                String inputText=s.toString();
                phone[1]=inputText;
                if(phone[1].equals("")){
                    phoneField.setError(getString(R.string.mandatory_phone));
                }
                else if(FieldChecker.isACorrectPhone(phone[0]+phone[1])){
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    phoneField.setError(null);
                }
                else{
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


        Button register=findViewById(R.id.register_finished);
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
                        String centerDescription=descriptionCenterField.getText().toString();
                        String addressName=addressNameField.getText().toString();
                        String nameProvince;
                        String nameRegion;
                        String nameCity;
                        if(FieldChecker.isPrecharged(currIdCountry)){
                            if(Locale.getDefault().getLanguage().equals("es")){
                                nameProvince=province[0].getNameSpanish();
                                nameRegion=region[0].getNameSpanish();
                                nameCity=city[0].getNameSpanish();
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                nameProvince=province[0].getNameFrench();
                                nameRegion=region[0].getNameFrench();
                                nameCity=city[0].getNameFrench();
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                nameProvince=province[0].getNameBasque();
                                nameRegion=region[0].getNameBasque();
                                nameCity=city[0].getNameBasque();
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                nameProvince=province[0].getNameCatalan();
                                nameRegion=region[0].getNameCatalan();
                                nameCity=city[0].getNameCatalan();
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                nameProvince=province[0].getNameDutch();
                                nameRegion=region[0].getNameDutch();
                                nameCity=city[0].getNameDutch();
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                nameProvince=province[0].getNameGalician();
                                nameRegion=region[0].getNameGalician();
                                nameCity=city[0].getNameGalician();
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                nameProvince=province[0].getNameGerman();
                                nameRegion=region[0].getNameGerman();
                                nameCity=city[0].getNameGerman();
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                nameProvince=province[0].getNameItalian();
                                nameRegion=region[0].getNameItalian();
                                nameCity=city[0].getNameItalian();
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                nameProvince=province[0].getNamePortuguese();
                                nameRegion=region[0].getNamePortuguese();
                                nameCity=city[0].getNamePortuguese();
                            }else{
                                nameProvince=province[0].getNameEnglish();
                                nameRegion=region[0].getNameEnglish();
                                nameCity=city[0].getNameEnglish();
                            }

                        }
                        else{
                            nameProvince=nameProvinceField.getText().toString();
                            nameRegion=nameRegionField.getText().toString();
                            nameCity=nameCityField.getText().toString();
                        }

                        if(!centerDescription.equals("") && !addressName.equals("") && !nameProvince.equals("") && !nameRegion.equals("") && !nameCity.equals("") && FieldChecker.isACorrectPhone(phone[0]+phone[1])){

                            int numCenters= CentersController.GetAllByOrganization(organization[0]).size();
                            int numAddresses= AddressesController.GetAll().size();
                            int idOrganization=organization[0].getIdOrganization();
                            String orgType=organization[0].getOrgType();
                            String illness=organization[0].getIllness();

                            Address address=new Address(numAddresses+1,addressName,idCity[0],idProvince[0],idRegion[0],idCountry[0],nameCity,nameProvince,nameRegion);
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

                            String descriptionText=centerDescription;

                            if(!descriptionText.equals("")){
                                if(Locale.getDefault().getLanguage().equals("es")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"es","en");
                                    descriptionSpanish=descriptionText;
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"es","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"es","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"es","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"es","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"es","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"es","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"es","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"es","pt");
                                }else if(Locale.getDefault().getLanguage().equals("fr")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"es","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"fr","es");
                                    descriptionFrench=descriptionText;
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"fr","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"fr","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"fr","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"fr","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"fr","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"fr","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"fr","pt");
                                }else if(Locale.getDefault().getLanguage().equals("eu")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"eu","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"eu","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"eu","fr");
                                    descriptionBasque=descriptionText;
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"eu","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"eu","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"eu","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"eu","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"eu","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"eu","pt");
                                }else if(Locale.getDefault().getLanguage().equals("ca")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"ca","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"ca","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"ca","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"ca","eu");
                                    descriptionCatalan=descriptionText;
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"ca","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"ca","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"ca","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"ca","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"ca","pt");
                                }else if(Locale.getDefault().getLanguage().equals("nl")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"nl","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"nl","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"nl","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"nl","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"nl","ca");
                                    descriptionDutch=descriptionText;
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"nl","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"nl","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"nl","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"nl","pt");
                                }else if(Locale.getDefault().getLanguage().equals("gl")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"gl","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"gl","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"gl","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"gl","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"gl","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"gl","nl");
                                    descriptionGalician=descriptionText;
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"gl","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"gl","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"gl","pt");
                                }else if(Locale.getDefault().getLanguage().equals("de")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"de","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"de","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"de","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"de","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"de","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"de","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"de","gl");
                                    descriptionGerman=descriptionText;
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"de","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"de","pt");
                                }else if(Locale.getDefault().getLanguage().equals("it")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"it","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"it","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"it","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"it","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"it","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"it","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"it","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"it","de");
                                    descriptionItalian=descriptionText;
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"it","pt");
                                }else if(Locale.getDefault().getLanguage().equals("pt")){
                                    descriptionEnglish= TranslatorController.getInstance().translate(descriptionText,"pt","en");
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"pt","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"pt","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"pt","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"pt","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"pt","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"pt","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"pt","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"pt","it");
                                    descriptionPortuguese=descriptionText;
                                }else{
                                    descriptionEnglish=descriptionText;
                                    descriptionSpanish=TranslatorController.getInstance().translate(descriptionText,"en","es");
                                    descriptionFrench=TranslatorController.getInstance().translate(descriptionText,"en","fr");
                                    descriptionBasque=TranslatorController.getInstance().translate(descriptionText,"en","eu");
                                    descriptionCatalan=TranslatorController.getInstance().translate(descriptionText,"en","ca");
                                    descriptionDutch=TranslatorController.getInstance().translate(descriptionText,"en","nl");
                                    descriptionGalician=TranslatorController.getInstance().translate(descriptionText,"en","gl");
                                    descriptionGerman=TranslatorController.getInstance().translate(descriptionText,"en","de");
                                    descriptionItalian=TranslatorController.getInstance().translate(descriptionText,"en","it");
                                    descriptionPortuguese=TranslatorController.getInstance().translate(descriptionText,"en","pt");
                                }
                            }


                            Center center=new Center(idOrganization,orgType,illness, numCenters+1,descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese,address.getIdAddress(),phone[0]+" "+phone[1],email[0]);
                            CentersController.Create(center);

                            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
                            intent.putExtra("user",getIntent().getSerializableExtra("user"));
                            intent.putExtra("org",getIntent().getSerializableExtra("org"));
                            startActivity(intent);
                        }
                        else{
                            descriptionCenterField.setEnabled(true);
                            addressNameField.setEnabled(true);
                            phoneField.setEnabled(true);
                            countrySpinner.setEnabled(true);
                            if(country[0].getIdCountry().equals("ESP")) {
                                regionSpinner.setEnabled(true);
                                provinceSpinner.setEnabled(true);
                                citySpinner.setEnabled(true);
                            }

                            else {
                                nameRegionField.setEnabled(true);
                                nameProvinceField.setEnabled(true);
                                nameCityField.setEnabled(true);

                            }
                            background.setVisibility(View.GONE);

                            if(centerDescription.equals("")){
                                descriptionCenterField.setError(getString(R.string.please_description_center));
                            }
                            if(addressName.equals("")){
                                addressNameField.setError(getString(R.string.please_address));
                            }
                            if(!(FieldChecker.isACorrectPhone(phone[0]+phone[1]))){
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
            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
            intent.putExtra("user",getIntent().getSerializableExtra("user"));
            intent.putExtra("org",getIntent().getSerializableExtra("org"));

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

    public List<Country> getCountries(){
        if(countries==null){
            countries= CountriesController.GetAll(Locale.getDefault().getLanguage());
        }
        return countries;
    }

    public List<Region> getRegions(String idCountry){
        regions= RegionsController.GetRegionsByCountry(idCountry);
        currIdCountry=idCountry;
        return regions;
    }

    public List<Province> getProvinces(int idRegion, String idCountry){
        if(provinces==null || currIdRegion!=idRegion || currIdCountry!=idCountry){
            provinces= ProvincesController.GetProvincesByRegion(idRegion,idCountry);
            currIdRegion=idRegion;
            currIdCountry=idCountry;
        }
        return provinces;
    }

    public List<City> getCities(int idProvince, int idRegion, String idCountry){
        if(cities==null || currIdProvince!=currIdProvince || currIdRegion!=idRegion || currIdCountry!=idCountry){
            cities= CitiesController.GetCitiesByProvince(idProvince,idRegion,idCountry);
            currIdProvince=idProvince;
            currIdRegion=idRegion;
            currIdCountry=idCountry;
        }
        return cities;
    }

    public List<Center> getCenters(){return centers;}
}