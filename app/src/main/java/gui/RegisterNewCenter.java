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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    private List<Country> countries;

    private List<Region> regions;

    private List<Province> provinces;

    private List<City> cities;

    List<Region> regionsCurrCountry;

    List<Province> provincesCurrRegion;

    List<City> citiesCurrProvince;
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
    List<Center> centers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_center);

        countries=Session.getInstance().getCountries();
        regions=Session.getInstance().getRegions();
        provinces=Session.getInstance().getProvinces();
        cities=Session.getInstance().getCities();

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
        EditText nameProvinceField=findViewById(R.id.foreign_province_reg);
        EditText nameRegionField=findViewById(R.id.foreign_region_reg);
        EditText nameCityField=findViewById(R.id.foreign_city_reg);
        EditText phoneField=findViewById(R.id.phone_reg);
        EditText emailField = findViewById(R.id.email_reg);
        Spinner countrySpinner=findViewById(R.id.spinner_countries_reg);
        Spinner regionSpinner=findViewById(R.id.spinner_regions_reg);
        Spinner provinceSpinner=findViewById(R.id.spinner_provinces_reg);
        Spinner citySpinner=findViewById(R.id.spinner_cities_reg);
        Spinner phoneCode1=findViewById(R.id.phonecode1);
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);
        orgSpinner.setAdapter(orgAdapter[0]);
        orgSpinner.setEnabled(true);
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);

        Map<String,String> fields=new HashMap<String,String>();
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

        int[] idCity={-1};
        int[] idProvince={-1};
        int[] idRegion={-1};
        String[] idCountry={""};


        String[] phone=new String[2];

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
                fields.replace("telephoneCodeOrg",phoneCodeAdapter[0].getItem(position).getPhone_code());
                fields.replace("telephoneCodeDir",phoneCodeAdapter[1].getItem(position).getPhone_code());
                phoneCode1.setSelection(position);

                idCountry[0] = country[0].getIdCountry();
                if (FieldChecker.isPrecharged(idCountry[0])) {
                    if(regions.size()>1){
                        regionSpinner.setVisibility(View.VISIBLE);
                        regionAdapter[0] = new RegionAdapter(RegisterNewCenter.this, regions.stream().filter(new Predicate<Region>() {
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
                        provinceAdapter[0] = new ProvinceAdapter(RegisterNewCenter.this, provinces.stream().filter(new Predicate<Province>() {
                            @Override
                            public boolean test(Province province) {
                                return province.getIdRegion()==-1 && province.getIdCountry().equals(idCountry[0]);
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
                provinceAdapter[0] = new ProvinceAdapter(RegisterNewCenter.this, provinces.stream().filter(new Predicate<Province>() {
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
                ;
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
                cityAdapter[0] = new CityAdapter(RegisterNewCenter.this, cities.stream().filter(new Predicate<City>() {
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
                if(phone[1].equals("")){
                    phoneField.setError(getString(R.string.mandatory_phone));
                    fields.replace("telephone","");
                }
                else if(FieldChecker.isACorrectPhone(phone[0]+phone[1])){
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    phoneField.setError(null);
                    fields.replace("telephone",inputText);
                }
                else{
                    phoneField.setError(getString(R.string.wrong_phone));
                    fields.replace("telephone","");
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


                        if(!fields.get("centerDescription").equals("") && !fields.get("address").equals("") && !fields.get("nameProvince").equals("") && !fields.get("nameRegion").equals("") && !fields.get("nameCity").equals("")
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
                                    descriptionSpanish=translations.get(1);
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


                            Center center=new Center(idOrganization,orgType,illness, numCenters+1,descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese,address.getIdAddress(),fields.get("telephoneCode")+" "+fields.get("telephone"),email[0]);
                            CentersController.Create(center);

                            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
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

                            if(fields.get("centerDescription").equals("")){
                                descriptionCenterField.setError(getString(R.string.please_description_center));
                            }
                            if(fields.get("address").equals("")){
                                addressNameField.setError(getString(R.string.please_address));
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


    public List<Center> getCenters(){return centers;}
}