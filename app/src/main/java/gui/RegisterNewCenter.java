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

public class RegisterNewCenter extends AppCompatActivity {

    List<Organization> organizations;
    List<Country> countries;
    List<Region> regions;
    List<Province> provinces;
    List<City> cities;
    CountryAdapter[] countryAdapter={null};

    RegionAdapter[] regionAdapter={null};

    ProvinceAdapter[] provinceAdapter={null};

    CityAdapter[] cityAdapter={null};

    OrgsAdapter[] orgAdapter={null};

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


        EditText descriptionCenterField=findViewById(R.id.description_center_reg);
        EditText addressNameField=findViewById(R.id.name_address_reg);
        EditText zipCodeField=findViewById(R.id.org_zip_code);
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
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);
        orgSpinner.setAdapter(orgAdapter[0]);
        orgSpinner.setEnabled(true);


        ConstraintLayout background=findViewById(R.id.final_background);


        Drawable correct= ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);

        background.setVisibility(View.GONE);

        int[] idCity={-1};
        int[] idProvince={-1};
        int[] idRegion={-1};
        String[] idCountry={""};
        int[] zipCode={-1};
        long[] telephone={-1};

        String[] nameCity={getIntent().getStringExtra("nameCity")};
        String[] nameProvince={getIntent().getStringExtra("nameProvince")};
        String[] nameRegion={getIntent().getStringExtra("nameRegion")};






        String[] zip_code={""};
        String[] phone={""};

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
                idCountry[0]=country[0].getIdCountry();
                if(idCountry[0].equals("ESP")){
                    regionSpinner.setVisibility(View.VISIBLE);
                    provinceSpinner.setVisibility(View.VISIBLE);
                    citySpinner.setVisibility(View.VISIBLE);
                    provinceSpinner.setEnabled(false);
                    citySpinner.setEnabled(false);
                    nameProvinceField.setVisibility(View.GONE);
                    nameRegionField.setVisibility(View.GONE);
                    nameCityField.setVisibility(View.GONE);
                    getRegions(country[0].getIdCountry());
                    regionAdapter[0]=new RegionAdapter(RegisterNewCenter.this, regions);
                    regionAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                    regionSpinner.setAdapter(regionAdapter[0]);
                    regionSpinner.setEnabled(true);
                }
                else{
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
                nameRegion[0]=region[0].getNameRegion();
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
                nameProvince[0]=province[0].getNameProvince();
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
                nameCity[0] = city[0].getCityName();
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

        zipCodeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                zipCodeField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text=s.toString();
                if(input_text.equals("")){
                    zipCodeField.setError(getString(R.string.please_org_name));
                }
                else{
                    zipCodeField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    zipCodeField.setError(null);
                    zip_code[0]=input_text;
                    try {
                        zipCode[0] = Integer.parseInt(zip_code[0]);
                    }catch(NumberFormatException e){
                        zipCode[0]=-1;
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

        phoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                phoneField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText=s.toString();
                if(inputText.equals("")){
                    phoneField.setError(getString(R.string.mandatory_phone));
                }
                else if(FieldChecker.isASpanishNumber(inputText)||FieldChecker.isAForeignNumber(inputText)){
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    phoneField.setError(null);
                    phone[0]=inputText;
                    try {
                        telephone[0] = Long.parseLong(phone[0]);
                    }catch(NumberFormatException e){
                        telephone[0]=-1;
                    }
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
                zipCodeField.setEnabled(false);
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
                        String zipCode=zipCodeField.getText().toString();
                        String nameProvince;
                        String nameRegion;
                        String nameCity;
                        if(currIdCountry.equals("ESP")){
                            nameProvince=province[0].getNameProvince();
                            nameRegion=region[0].getNameRegion();
                            nameCity=city[0].getCityName();
                        }
                        else{
                            nameProvince=nameProvinceField.getText().toString();
                            nameRegion=nameRegionField.getText().toString();
                            nameCity=nameCityField.getText().toString();
                        }
                        String telephone=phoneField.getText().toString();
                        if(!centerDescription.equals("") && !addressName.equals("") && !zipCode.equals("") && !nameProvince.equals("") && !nameRegion.equals("") && !nameCity.equals("") && (FieldChecker.isASpanishNumber(telephone) || FieldChecker.isAForeignNumber(telephone))){

                            int numCenters= CentersController.GetAllByOrganization(organization[0]).size();
                            int numAddresses= AddressesController.GetAll().size();
                            int zip_code=Integer.parseInt(zipCode);
                            long phone=Long.parseLong(telephone);
                            int idOrganization=organization[0].getIdOrganization();
                            String orgType=organization[0].getOrgType();
                            String illness=organization[0].getIllness();

                            Address address=new Address(numAddresses+1,addressName,zip_code,idCity[0],idProvince[0],idRegion[0],idCountry[0],nameCity,nameProvince,nameRegion);
                            AddressesController.Create(address);

                            Center center=new Center(idOrganization,orgType,illness, numCenters+1,centerDescription,address.idAddress,phone,email[0]);
                            CentersController.Create(center);

                            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
                            intent.putExtra("user",getIntent().getSerializableExtra("user"));
                            intent.putExtra("org",getIntent().getSerializableExtra("org"));
                            startActivity(intent);
                        }
                        else{
                            descriptionCenterField.setEnabled(true);
                            addressNameField.setEnabled(true);
                            zipCodeField.setEnabled(true);
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
                            if(zipCode.equals("")){
                                zipCodeField.setError(getString(R.string.please_zipcode));
                            }
                            if(!(FieldChecker.isASpanishNumber(telephone) || FieldChecker.isAForeignNumber(telephone))){
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
        if(regions==null){
            regions= RegionsController.GetRegionsByCountry(idCountry);
            currIdCountry=idCountry;
        }
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