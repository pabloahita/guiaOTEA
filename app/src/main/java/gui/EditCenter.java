package gui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import gui.adapters.CenterAdapter;
import gui.adapters.CityAdapter;
import gui.adapters.CountryAdapter;
import gui.adapters.OrgsAdapter;
import gui.adapters.PhoneCodeAdapter;
import gui.adapters.ProvinceAdapter;
import gui.adapters.RegionAdapter;
import misc.FieldChecker;
import misc.ListCallback;
import otea.connection.controller.AddressesController;
import otea.connection.controller.CentersController;
import otea.connection.controller.CitiesController;
import otea.connection.controller.ProvincesController;
import otea.connection.controller.RegionsController;
import otea.connection.controller.TranslatorController;
import session.EditCenterUtil;
import session.FileManager;
import session.Session;
import session.StringPasser;

public class EditCenter extends AppCompatActivity {

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

    ConstraintLayout base;


    EditText nameProvinceField;
    EditText nameRegionField;
    EditText nameCityField;
    CountryAdapter[] countryAdapter={null};

    RegionAdapter[] regionAdapter={null};

    ProvinceAdapter[] provinceAdapter={null};

    CityAdapter[] cityAdapter={null};

    OrgsAdapter[] orgAdapter={null};

    PhoneCodeAdapter[] phoneCodeAdapter={null};


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

    TextInputLayout tilRegion;

    TextInputLayout tilProvince;

    TextInputLayout tilCity;

    Button imageCenterButton;

    ImageView imageCenter;

    InputStream profilePhotoCenter;

    String imgCenterName="";

    ImageButton helpButton;

    Center currCenter;

    Address address;

    boolean photoHasChanged=false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        countries= new ArrayList<>();
        countries.add(new Country("-2","País","Country","Pays","Herrialdea","País","Land","País","Land","Paese","País","-",""));
        countries.addAll(Session.getInstance().getCountries());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_center);

        base=findViewById(R.id.base);
        base.setVisibility(View.VISIBLE);

        tilRegion=findViewById(R.id.tilRegion);
        tilProvince=findViewById(R.id.tilProvince);
        tilCity=findViewById(R.id.tilCity);

        helpButton=findViewById(R.id.helpButton);

        currCenter= EditCenterUtil.getInstance().getCenter();
        address = EditCenterUtil.getInstance().getAddress();

        countryAdapter[0]= new CountryAdapter(EditCenter.this, countries);
        countryAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        phoneCodeAdapter[0] = new PhoneCodeAdapter(EditCenter.this,countries);
        phoneCodeAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        EditText descriptionCenterField=findViewById(R.id.description_center_reg);
        String descr="";

        fields=new HashMap<String,String>();
        fields.put("centerDescription","");
        fields.put("address","");
        fields.put("nameCity","");
        fields.put("nameProvince","");
        fields.put("nameRegion","");
        fields.put("telephoneCode","");
        fields.put("telephone","");
        fields.put("email","");

        if(Locale.getDefault().getLanguage().equals("es")) {
            descr=currCenter.getDescriptionSpanish();
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            descr=currCenter.getDescriptionFrench();
        }else if(Locale.getDefault().getLanguage().equals("eu")) {
            descr=currCenter.getDescriptionBasque();
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            descr=currCenter.getDescriptionCatalan();
        }else if(Locale.getDefault().getLanguage().equals("nl")) {
            descr=currCenter.getDescriptionDutch();
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            descr=currCenter.getDescriptionGalician();
        }else if(Locale.getDefault().getLanguage().equals("de")) {
            descr=currCenter.getDescriptionGerman();
        }else if(Locale.getDefault().getLanguage().equals("it")){
            descr=currCenter.getDescriptionItalian();
        }else if(Locale.getDefault().getLanguage().equals("pt")) {
            descr=currCenter.getDescriptionPortuguese();
        }else{
            descr=currCenter.getDescriptionEnglish();
        }
        descriptionCenterField.setText(descr);
        fields.put("centerDescription",descr);
        EditText addressNameField=findViewById(R.id.name_address_reg);
        addressNameField.setText(address.getName());
        fields.put("address",address.getName());
        nameProvinceField = findViewById(R.id.foreign_province_reg);
        nameRegionField = findViewById(R.id.foreign_region_reg);
        nameCityField = findViewById(R.id.foreign_city_reg);
        EditText phoneField=findViewById(R.id.phone_reg);
        phoneField.setText(currCenter.getTelephone().split(" ")[1]);
        fields.put("telephoneCode",currCenter.getTelephone().split(" ")[0]);
        fields.put("telephone",currCenter.getTelephone().split(" ")[1]);
        EditText emailField = findViewById(R.id.email_reg);
        emailField.setText(currCenter.getEmail());
        fields.put("email",currCenter.getEmail());
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
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);

        auxRegList.add(new Region(-2,"-2","Región","Region","Région","Eskualdea","Regió","Region","Rexión","Region","Regione","Região"));
        RegionAdapter adapterRegAux=new RegionAdapter(EditCenter.this,auxRegList);

        auxProList.add(new Province(-2,-2,"-2","Provincia","Province","Province","Probintzia","Província","Provincie","Provincia","Provinz","Provincia","Província"));
        ProvinceAdapter adapterProAux=new ProvinceAdapter(EditCenter.this,auxProList);

        auxCityList.add(new City(-2,-2,-2,"-2","Ciudad","City","Ville","Hiri","Ciutat","Stad","Cidade","Stadt","Città","Cidade"));
        CityAdapter adapterCitAux=new CityAdapter(EditCenter.this,auxCityList);



        regionSpinnerAux.setAdapter(adapterRegAux);
        regionSpinnerAux.setEnabled(false);
        regionSpinnerAux.setAlpha(0.5f);
        provinceSpinnerAux.setAdapter(adapterProAux);
        provinceSpinnerAux.setEnabled(false);
        provinceSpinnerAux.setAlpha(0.5f);
        citySpinnerAux.setAdapter(adapterCitAux);
        citySpinnerAux.setEnabled(false);
        citySpinnerAux.setAlpha(0.5f);



        ConstraintLayout background=findViewById(R.id.final_background);


        Drawable correct= ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);

        background.setVisibility(View.GONE);

        imageCenterButton=findViewById(R.id.uploadPhoto);
        imageCenter=findViewById(R.id.profilePhoto);



        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        try {
                            profilePhotoCenter = getContentResolver().openInputStream(uri);
                            imageCenter.setImageURI(uri);
                            photoHasChanged=true;
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch(NullPointerException ignored){

                        }
                    }
                });

        ActivityResultLauncher<Void> mTakePicture = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap bitmap) {
                        // Handle the returned Bitmap
                        try {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                            byte[] bitmapdata = bos.toByteArray();
                            bos.close();
                            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                            profilePhotoCenter = bs;
                            imageCenter.setImageBitmap(bitmap);
                            bs.close();
                            photoHasChanged=true;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }catch(NullPointerException ignored){

                        }
                    }
                });

        imageCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.select_source))
                        .setPositiveButton(getString(R.string.camera), (dialog, which) -> mTakePicture.launch(null))
                        .setNegativeButton(getString(R.string.gallery), (dialog, which) -> mGetContent.launch("image/*"))
                        .show();
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
                descriptionCenterField.setError(null);
                if(input_text.isEmpty()){
                    descriptionCenterField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
                else{
                    descriptionCenterField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                addressNameField.setError(null);
                if(input_text.isEmpty()){
                    addressNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
                else{
                    addressNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                nameRegionField.setError(null);
                if(input_text.isEmpty()){
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
                else{
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                nameProvinceField.setError(null);
                if(input_text.isEmpty()){
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
                else{
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                nameCityField.setError(null);
                if(input_text.isEmpty()){
                    nameCityField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
                else{
                    nameCityField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                phoneField.setError(null);
                if (FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone"))) {
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);

                } else{
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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
                phoneField.setError(null);
                if (FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone"))) {
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);

                } else{
                    phoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        emailField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        emailField.setError(null);
                        if (FieldChecker.emailHasCorrectFormat(inputText)) {
                            emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                        } else {
                            emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                        }
                        fields.replace("email",inputText);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    msg="Para poder editar correctamente un centro o servicio, deberá rellenar los siguientes campos:" +
                            "<ul>" +
                            "<li><b>Descripción del centro: </b>En este campo deberá introducir la descripción del centro o servicio</li>" +
                            "<li><b>Dirección: </b>En este campo deberá introducir la dirección postal del centro o servicio, sin introducir ciudad, ni región, ni provincia ni país. Por ejemplo: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>País: </b>En este campo debe seleccionar el país donde se ubica el centro o servicio. Si el país seleccionado es <i>España, Andorra, Argentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador, El Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguay, Portugal, Perú, Puerto Rico, Uruguay o Venezuela</i>, los campos de <i><b>Región</b></i>, <i><b>Provincia</b></i> y <i><b>Ciudad</b></i> serán seleccionables, por lo contrario tendrá que escribirlos de forma manual.</li>" +
                            "<li><b>Región: </b>En este campo deberá introducir o seleccionar la región donde se ubica el centro o servicio. Campo no disponible para los siguientes países: <i>Andorra, Cuba, Puerto Rico y Uruguay</i> por la de división territorial de dichos países.</li>" +
                            "<li><b>Provincia: </b>En este campo deberá introducir o seleccionar la provincia donde se ubica el centro o servicio.</li>" +
                            "<li><b>Ciudad: </b>En este campo deberá introducir o seleccionar la ciudad donde se ubica el centro o servicio.</li>" +
                            "<li><b>Número de teléfono: </b>En este campo deberá introducir el número de teléfono del centro o servicio. Si lo desea, puede seleccionar el prefijo correspondiente al país seleccionándolo en el desplegable de la parte izquierda, aunque también puede cambiarlo con el desplegable <i><b>País</b></i>.</li>" +
                            "<li><b>Email: </b>En este campo deberá introducir el email correspondiente al centro o servicio.</li>" +
                            "</ul>" +
                            "Si lo desea, puede añadir una fotografía de perfil del centro, presionando sobre <i><b>Cambiar foto</b></i>.";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    msg="Afin de modifier correctement un centre ou un service, vous devez remplir les champs suivants :" +
                            "<ul>" +
                            "<li><b>Description du centre: </b>Dans ce champ vous devez saisir la description du centre ou du service</li>" +
                            "<li><b>Adresse: </b>Dans ce champ, vous devez saisir l'adresse postale du centre ou du service, sans saisir de ville, de région, de province ou de pays. Par exemple: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Pays: </b>Dans ce champ, vous devez sélectionner le pays où se trouve le centre ou le service. Si le pays sélectionné est<i>Espagne, Andorre, Argentine, Bolivie, Chili, Colombie, Costa Rica, Cuba, République dominicaine, Équateur, El Salvador, Guatemala, Honduras, Mexique, Nicaragua, Panama, Paraguay, Portugal, Pérou, Port Rico, Uruguay ou Venezuela</i>, les champs <i><b>Région</b></i>, <i><b>Province</b></i> et <i><b >Ville</b></i> sera sélectionnable, sinon vous devrez les écrire manuellement.</li>" +
                            "<li><b>Région: </b>Dans ce champ, vous devez saisir ou sélectionner la région où se trouve le centre ou le service. Champ non disponible pour les pays suivants: <i>Andorre, Cuba, Porto Rico et Uruguay</i> en raison de la division territoriale de ces pays.</li>" +
                            "<li><b>Province: </b>Dans ce champ, vous devez saisir ou sélectionner la province où se trouve le centre ou le service.</li>" +
                            "<li><b>Ville: </b>Dans ce champ, vous devez saisir ou sélectionner la ville où se trouve le centre ou le service.</li>" +
                            "<li><b>Numéro de téléphone: </b>Dans ce champ, vous devez saisir le numéro de téléphone du centre ou du service. Si vous le souhaitez, vous pouvez sélectionner le préfixe correspondant au pays en le sélectionnant dans le menu déroulant de gauche, mais vous pouvez également le modifier avec le menu déroulant <i><b>Pays</b></i>. -menu déroulant.</li>" +
                            "<li><b>E-mail: </b>Dans ce champ, vous devez saisir l'e-mail correspondant au centre ou au service.</li>" +
                            "</ul>" +
                            "Si vous le souhaitez, vous pouvez ajouter une photo de profil du centre en cliquant sur <i><b>Changer de photo</b></i>.";
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    msg="Zentro edo zerbitzu bat behar bezala editatzeko, eremu hauek bete behar dituzu:" +
                            "<ul>" +
                            "<li><b>Zentroaren deskribapena: </b>Eremu honetan zentroaren edo zerbitzuaren deskribapena idatzi behar duzu</li>" +
                            "<li><b>Helbidea: </b>Eremu honetan zentroaren edo zerbitzuaren posta helbidea idatzi behar duzu, hiria, eskualdea, probintzia edo herrialdea sartu gabe. Adibidez: <i>Valdenúñez kalea, 8</i>.</li>" +
                            "<li><b>Herrialdea: </b>Eremu honetan zentroa edo zerbitzua dagoen herrialdea hautatu behar duzu. Hautatutako herrialdea bada<i>Espainia, Andorra, Argentina, Bolivia, Txile, Kolonbia, Costa Rica, Kuba, Dominikar Errepublika, Ekuador, El Salvador, Guatemala, Honduras, Mexiko, Nikaragua, Panama, Paraguai, Portugal, Peru, Portua Rico, Uruguay edo Venezuela</i>, <i><b>Eskualde</b></i>, <i><b>Probintzia</b></i> eta <i><b-eko eremuak >Hiria</b></i> hautagarria izango da, bestela eskuz idatzi beharko dituzu.</li>" +
                            "<li><b>Eskualdea: </b>Eremu honetan zentroa edo zerbitzua dagoen eskualdea sartu edo hautatu behar duzu. Eremua ez dago eskuragarri herrialde hauetarako: <i>Andorra, Kuba, Puerto Rico eta Uruguai</i>, aipatutako herrialdeen lurralde banaketa dela eta.</li>" +
                            "<li><b>Probintzia: </b>Eremu honetan zentroa edo zerbitzua dagoen probintzia sartu edo hautatu behar duzu.</li>" +
                            "<li><b>Hiria: </b>Eremu honetan zentroa edo zerbitzua dagoen hiria sartu edo hautatu behar duzu.</li>" +
                            "<li><b>Telefono-zenbakia: </b>Eremu honetan zentroaren edo zerbitzuaren telefono-zenbakia sartu behar duzu. Nahi izanez gero, herrialdeari dagokion aurrizkia hauta dezakezu ezkerreko goitibeherako menuan hautatuz, nahiz eta <i><b>Herrialdea</b></i> goitibeherako aukerarekin ere alda dezakezu. -behera menua.</li>" +
                            "<li><b>E-posta: </b>Eremu honetan zentroari edo zerbitzuari dagokion e-posta idatzi behar duzu.</li>" +
                            "</ul>" +
                            "Nahi baduzu, zentroko profileko argazki bat gehi dezakezu <i><b>Aldatu argazkia</b></i> sakatuta." ;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    msg="Per poder editar correctament un centre o servei, haureu d'emplenar els camps següents:" +
                            "<ul>" +
                            "<li><b>Descripció del centre: </b>En aquest camp haureu d'introduir la descripció del centre o servei</li>" +
                            "<li><b>Direcció: </b>En aquest camp haureu d'introduir l'adreça postal del centre o servei, sense introduir ciutat, ni regió, ni província ni país. Per exemple: <i>Carrer Valdenúñez, 8</i>.</li>" +
                            "<li><b>País: </b>En aquest camp heu de seleccionar el país on s'ubica el centre o servei. Si el país seleccionat és <i>Espanya, Andorra, Argentina, Bolívia, Xile, Colòmbia, Costa Rica, Cuba, República Dominicana, Equador, El Salvador, Guatemala, Hondures, Mèxic, Nicaragua, Panamà, Paraguai, Portugal, Perú, Port Rico, Uruguai o Veneçuela</i>, els camps de <i><b>Regió</b></i>, <i><b>Província</b></i> i <i><b >Ciutat</b></i> seran seleccionables, per contra els haurà d'escriure manualment.</li>" +
                            "<li><b>Regió: </b>En aquest camp haureu d'introduir o seleccionar la regió on s'ubica el centre o servei. Camp no disponible per als països següents: <i>Andorra, Cuba, Puerto Rico i Uruguai</i> per la de divisió territorial d'aquests països.</li>" +
                            "<li><b>Província: </b>En aquest camp haureu d'introduir o seleccionar la província on s'ubica el centre o servei.</li>" +
                            "<li><b>Ciutat: </b>En aquest camp haureu d'introduir o seleccionar la ciutat on s'ubica el centre o servei.</li>" +
                            "<li><b>Nombre de telèfon: </b>En aquest camp haureu d'introduir el número de telèfon del centre o servei. Si ho desitgeu, podeu seleccionar el prefix corresponent al país seleccionant-lo al desplegable de la part esquerra, encara que també podeu canviar-lo amb el desplegable <i><b>País</b></i>.</li>" +
                            "<li><b>Email: </b>En aquest camp haureu d'introduir l'email corresponent al centre o servei.</li>" +
                            "</ul>" +
                            "Si voleu, podeu afegir una fotografia de perfil del centre, prement sobre <i><b>Canviar foto</b></i>.";
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    msg="Om een centrum of dienst correct te kunnen bewerken, moet u de volgende velden invullen:" +
                            "<ul>" +
                            "<li><b>Beschrijving van het centrum: </b>In dit veld moet u de beschrijving van het centrum of de dienst invoeren</li>" +
                            "<li><b>Adres: </b>In dit veld moet u het postadres van het centrum of de dienst invoeren, zonder stad, regio, provincie of land in te voeren. Bijvoorbeeld: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Land: </b>In dit veld moet u het land selecteren waar het centrum of de dienst zich bevindt. Als het geselecteerde land<i>Spanje, Andorra, Argentinië, Bolivia, Chili, Colombia, Costa Rica, Cuba, Dominicaanse Republiek, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru, Haven is Rico, Uruguay of Venezuela</i>, de velden <i><b>Regio</b></i>, <i><b>Provincie</b></i> en <i><b >Plaats</b></i> kan worden geselecteerd, anders moet u ze handmatig schrijven.</li>" +
                            "<li><b>Regio: </b>In dit veld moet u de regio invoeren of selecteren waar het centrum of de dienst zich bevindt. Veld niet beschikbaar voor de volgende landen: <i>Andorra, Cuba, Puerto Rico en Uruguay</i> vanwege de territoriale indeling van genoemde landen.</li>" +
                            "<li><b>Provincie: </b>In dit veld moet u de provincie invoeren of selecteren waar het centrum of de dienst gevestigd is.</li>" +
                            "<li><b>Plaats: </b>In dit veld moet u de stad invoeren of selecteren waar het centrum of de dienst zich bevindt.</li>" +
                            "<li><b>Telefoonnummer: </b>In dit veld moet u het telefoonnummer van het centrum of de dienst invoeren. Als u wilt, kunt u het voorvoegsel dat overeenkomt met het land selecteren door dit te selecteren in het vervolgkeuzemenu aan de linkerkant, maar u kunt dit ook wijzigen met de vervolgkeuzelijst <i><b>Land</b></i> -downmenu.</li>" +
                            "<li><b>E-mail: </b>In dit veld moet u het e-mailadres invoeren dat overeenkomt met het centrum of de dienst.</li>" +
                            "</ul>" +
                            "Als u wilt, kunt u een profielfoto van het centrum toevoegen door op <i><b>Foto wijzigen</b></i> te klikken.";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    msg="Para editar correctamente un centro ou servizo, debes cubrir os seguintes campos:" +
                            "<ul>" +
                            "<li><b>Descrición do centro: </b>Neste campo debes introducir a descrición do centro ou servizo</li>" +
                            "<li><b>Enderezo: </b>Neste campo debes introducir o enderezo postal do centro ou servizo, sen introducir cidade, rexión, provincia ou país. Por exemplo: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>País: </b>Neste campo debes seleccionar o país onde se atopa o centro ou servizo. Se o país seleccionado é<i>España, Andorra, Arxentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador, O Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguai, Portugal, Perú, Porto Rico, Uruguai ou Venezuela</i>, os campos de <i><b>Rexión</b></i>, <i><b>Provincia</b></i> e <i><b >Cidade</b></i> será seleccionable, se non, terás que escribilos manualmente.</li>" +
                            "<li><b>Rexión: </b>Neste campo debes introducir ou seleccionar a rexión onde se atopa o centro ou servizo. Campo non dispoñible para os seguintes países: <i>Andorra, Cuba, Porto Rico e Uruguai</i> debido á división territorial dos devanditos países.</li>" +
                            "<li><b>Provincia: </b>Neste campo debes introducir ou seleccionar a provincia onde se atopa o centro ou servizo.</li>" +
                            "<li><b>Cidade: </b>Neste campo debes introducir ou seleccionar a cidade onde se atopa o centro ou servizo.</li>" +
                            "<li><b>Número de teléfono: </b>Neste campo debes introducir o número de teléfono do centro ou servizo. Se queres, podes seleccionar o prefixo correspondente ao país seleccionándoo no menú despregable da esquerda, aínda que tamén podes cambialo co menú <i><b>País</b></i>. -menú abaixo.</li>" +
                            "<li><b>Correo electrónico: </b>Neste campo debes introducir o correo electrónico correspondente ao centro ou servizo.</li>" +
                            "</ul>" +
                            "Se queres, podes engadir unha foto de perfil do centro facendo clic en <i><b>Cambiar foto</b></i>.";
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    msg="Um ein Zentrum oder eine Dienstleistung korrekt bearbeiten zu können, müssen Sie die folgenden Felder ausfüllen:" +
                            "<ul>" +
                            "<li><b>Beschreibung des Zentrums: </b>In diesem Feld müssen Sie die Beschreibung des Zentrums oder der Dienstleistung eingeben</li>" +
                            "<li><b>Adresse:</b>In diesem Feld müssen Sie die Postanschrift des Zentrums oder Dienstes eingeben, ohne Stadt, Region, Provinz oder Land anzugeben. Zum Beispiel: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Land:</b>In diesem Feld müssen Sie das Land auswählen, in dem sich das Zentrum oder der Dienst befindet. Wenn das ausgewählte Land Spanien, Andorra, Argentinien, Bolivien, Chile, Kolumbien, Costa Rica, Kuba, Dominikanische Republik, Ecuador, El Salvador, Guatemala, Honduras, Mexiko, Nicaragua, Panama, Paraguay, Portugal, Peru, Port ist Rico, Uruguay oder Venezuela</i>, die Felder <i><b>Region</b></i>, <i><b>Provinz</b></i> und <i><b >Stadt</b></i> kann ausgewählt werden, andernfalls müssen Sie sie manuell eingeben.</li>" +
                            "<li><b>Region:</b>In diesem Feld müssen Sie die Region eingeben oder auswählen, in der sich das Zentrum oder der Dienst befindet. Das Feld ist für die folgenden Länder nicht verfügbar: <i>Andorra, Kuba, Puerto Rico und Uruguay</i> aufgrund der territorialen Aufteilung dieser Länder.</li>" +
                            "<li><b>Provinz:</b>In diesem Feld müssen Sie die Provinz eingeben oder auswählen, in der sich das Zentrum oder der Dienst befindet.</li>" +
                            "<li><b>Stadt:</b>In diesem Feld müssen Sie die Stadt eingeben oder auswählen, in der sich das Zentrum oder die Dienstleistung befindet.</li>" +
                            "<li><b>Telefonnummer:</b>In diesem Feld müssen Sie die Telefonnummer des Zentrums oder Dienstes eingeben. Wenn Sie möchten, können Sie das dem Land entsprechende Präfix auswählen, indem Sie es aus dem Dropdown-Menü auf der linken Seite auswählen. Sie können es jedoch auch über das Dropdown-Menü <i><b>Land</b></i> ändern -Down-Menü.</li>" +
                            "<li><b>E-Mail: </b>In diesem Feld müssen Sie die E-Mail-Adresse eingeben, die dem Zentrum oder Dienst entspricht.</li>" +
                            "</ul>" +
                            "Wenn Sie möchten, können Sie ein Profilfoto des Centers hinzufügen, indem Sie auf <i><b>Foto ändern</b></i> klicken.";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    msg="Per poter modificare correttamente un centro o servizio è necessario compilare i seguenti campi:" +
                            "<ul>" +
                            "<li><b>Descrizione del centro: </b>In questo campo è necessario inserire la descrizione del centro o del servizio</li>" +
                            "<li><b>Indirizzo: </b>In questo campo è necessario inserire l'indirizzo postale del centro o servizio, senza inserire città, regione, provincia o paese. Ad esempio: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Paese: </b>in questo campo è necessario selezionare il paese in cui si trova il centro o il servizio. Se il paese selezionato è<i>Spagna, Andorra, Argentina, Bolivia, Cile, Colombia, Costa Rica, Cuba, Repubblica Dominicana, Ecuador, El Salvador, Guatemala, Honduras, Messico, Nicaragua, Panama, Paraguay, Portogallo, Perù, Porto Rico, Uruguay o Venezuela</i>, i campi di <i><b>Regione</b></i>, <i><b>Provincia</b></i> e <i><b >Città</b></i> saranno selezionabili, altrimenti dovrai scriverle manualmente.</li>" +
                            "<li><b>Regione: </b>in questo campo è necessario inserire o selezionare la regione in cui si trova il centro o il servizio. Campo non disponibile per i seguenti paesi: <i>Andorra, Cuba, Porto Rico e Uruguay</i> a causa della divisione territoriale di detti paesi.</li>" +
                            "<li><b>Provincia: </b>In questo campo è necessario inserire o selezionare la provincia in cui si trova il centro o il servizio.</li>" +
                            "<li><b>Città: </b>in questo campo è necessario inserire o selezionare la città in cui si trova il centro o il servizio.</li>" +
                            "<li><b>Numero di telefono: </b>In questo campo è necessario inserire il numero di telefono del centro o servizio. Se lo desideri, puoi selezionare il prefisso corrispondente al Paese selezionandolo dal menu a tendina a sinistra, ma puoi anche modificarlo con il drop <i><b>Paese</b></i> menu a discesa.</li>" +
                            "<li><b>Email: </b>In questo campo è necessario inserire l'email corrispondente al centro o servizio.</li>" +
                            "</ul>" +
                            "Se lo desideri, puoi aggiungere una foto del profilo del centro cliccando su <i><b>Cambia foto</b></i>.";
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    msg="Para editar corretamente um centro ou serviço é necessário preencher os seguintes campos:" +
                            "<ul>" +
                            "<li><b>Descrição do centro: </b>Neste campo deve introduzir a descrição do centro ou serviço</li>" +
                            "<li><b>Endereço: </b>Neste campo deve introduzir o endereço postal do centro ou serviço, sem introduzir cidade, região, província ou país. Por exemplo: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>País: </b>Neste campo deve selecionar o país onde se encontra o centro ou serviço. Se o país selecionado for<i>Espanha, Andorra, Argentina, Bolívia, Chile, Colômbia, Costa Rica, Cuba, República Dominicana, Equador, El Salvador, Guatemala, Honduras, México, Nicarágua, Panamá, Paraguai, Portugal, Peru, Porto Rico, Uruguai ou Venezuela</i>, os campos <i><b>Região</b></i>, <i><b>Província</b></i> e <i><b > Cidade</b></i> serão seleccionáveis, caso contrário terá de as escrever manualmente.</li>" +
                            "<li><b>Região: </b>Neste campo deve introduzir ou selecionar a região onde se encontra o centro ou serviço. Campo não disponível para os seguintes países: <i>Andorra, Cuba, Porto Rico e Uruguai</i> devido à divisão territorial dos referidos países.</li>" +
                            "<li><b>Província: </b>Neste campo deve introduzir ou selecionar a província onde se encontra o centro ou serviço.</li>" +
                            "<li><b>Cidade: </b>Neste campo deve introduzir ou selecionar a cidade onde se encontra o centro ou serviço.</li>" +
                            "<li><b>Número de telefone: </b>Neste campo deve introduzir o número de telefone da central ou serviço. Se desejar, pode selecionar o prefixo correspondente ao país, selecionando-o no menu suspenso à esquerda, embora também possa alterá-lo com o menu suspenso <i><b>País</b></i> menu -para baixo. </li>" +
                            "<li><b>E-mail: </b>Neste campo deve introduzir o e-mail correspondente ao centro ou serviço.</li>" +
                            "</ul>" +
                            "Se desejar, pode adicionar uma fotografia de perfil do centro clicando em <i><b>Alterar fotografia</b></i>.";
                }else{
                    msg="In order to correctly edit a center or service, you must fill out the following fields:" +
                            "<ul>" +
                            "<li><b>Description of the center: </b>In this field you must enter the description of the center or service</li>" +
                            "<li><b>Address: </b>In this field you must enter the postal address of the center or service, without entering city, region, province or country. For example: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Country: </b>In this field you must select the country where the center or service is located. If the selected country is<i>Spain, Andorra, Argentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, Dominican Republic, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru, Port Rico, Uruguay or Venezuela</i>, the fields of <i><b>Region</b></i>, <i><b>Province</b></i> and <i><b >City</b></i> will be selectable, otherwise you will have to write them manually.</li>" +
                            "<li><b>Region: </b>In this field you must enter or select the region where the center or service is located. Field not available for the following countries: <i>Andorra, Cuba, Puerto Rico and Uruguay</i> due to the territorial division of said countries.</li>" +
                            "<li><b>Province: </b>In this field you must enter or select the province where the center or service is located.</li>" +
                            "<li><b>City: </b>In this field you must enter or select the city where the center or service is located.</li>" +
                            "<li><b>Phone number: </b>In this field you must enter the telephone number of the center or service. If you wish, you can select the prefix corresponding to the country by selecting it from the drop-down menu on the left, although you can also change it with the <i><b>Country</b></i> drop-down menu.</li>" +
                            "<li><b>Email: </b>In this field you must enter the email corresponding to the center or service.</li>" +
                            "</ul>" +
                            "If you wish, you can add a profile photo of the center by clicking on <i><b>Change photo</b></i>.";
                }
                new android.app.AlertDialog.Builder(EditCenter.this)
                        .setTitle(getString(R.string.help))
                        .setMessage(Html.fromHtml(msg,0))
                        .create().show();
            }
        });


        Button register = findViewById(R.id.register_finished);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                base.setVisibility(View.GONE);

                background.setVisibility(View.VISIBLE);

                if(currCenter.getIdCenter() != -1 && !fields.get("centerDescription").isEmpty() && !fields.get("address").isEmpty() && !idCountry[0].equals("-2") && !fields.get("nameProvince").isEmpty() && !fields.get("nameRegion").isEmpty() && !fields.get("nameCity").isEmpty()
                        && FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone"))&&
                        ((FieldChecker.isPrecharged(idCountry[0]) && region[0] != null && idRegion[0] != -2 && province[0] != null && idProvince[0] != -2 && city[0] != null && idCity[0] != -2) ||
                                (!idCountry[0].equals("-2") && !fields.get("nameRegion").isEmpty() && !fields.get("nameProvince").isEmpty() && !fields.get("nameCity").isEmpty()))){

                    new Thread(()->{
                        int idCenter=currCenter.getIdCenter();
                        int idAddress=currCenter.getIdAddress();
                        int idOrganization=currCenter.getIdOrganization();
                        String orgType=currCenter.getOrgType();
                        String illness=currCenter.getIllness();

                        if(photoHasChanged){
                            imgCenterName=currCenter.getProfilePhoto();
                            if(imgCenterName.isEmpty()){
                                imgCenterName="CENTER_"+idCenter+"_"+idOrganization+"_"+orgType+"_"+illness+".webp";
                            }
                            FileManager.uploadFile(profilePhotoCenter, "profile-photos", imgCenterName);
                            try{
                                profilePhotoCenter.close();
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                        }



                        Address address=new Address(idAddress,fields.get("address"),idCity[0],idProvince[0],idRegion[0],idCountry[0],fields.get("nameCity"),fields.get("nameProvince"),fields.get("nameRegion"));
                        AddressesController.Update(idAddress,address);

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

                        List<String> translations= TranslatorController.getInstance().translate(descriptionText,Locale.getDefault().getLanguage());
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


                        Address currAddress=new Address(address.getIdAddress(),fields.get("address"),idCity[0],idProvince[0],idRegion[0],idCountry[0],fields.get("nameCity"),fields.get("nameProvince"),fields.get("nameRegion"));
                        AddressesController.Update(address.getIdAddress(),currAddress);
                        Center center=new Center(idOrganization,orgType,illness, idCenter,descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese,address.getIdAddress(),fields.get("telephoneCode")+" "+fields.get("telephone"),fields.get("email"),currCenter.getProfilePhoto());
                        CentersController.Update(idOrganization,orgType,illness, idCenter,center);
                        String msg="";
                        if(Locale.getDefault().getLanguage().equals("es")){
                            msg="La organización <b>"+descriptionSpanish+"</b> se ha modificado correctamente" ;
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            msg="Le centre <b>"+descriptionFrench+"</b> a été modifiée avec succès" ;
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            msg="<b>"+descriptionBasque+"</b> zentroa behar bezala aldatu da" ;
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            msg="El centre <b>"+descriptionCatalan+"</b> s'ha modificat correctament" ;
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            msg="Het<b>"+descriptionDutch+"</b>-centrum is correct aangepast" ;
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            msg="O centro <b>"+descriptionGalician+"</b> modificouse correctamente" ;
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            msg="Das <b>"+descriptionGerman+"</b>-Zentrum wurde erfolgreich geändert";
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            msg="Il centro <b>"+descriptionItalian+"</b> è stato modificato correttamente" ;
                        }else if(Locale.getDefault().getLanguage().equals("pt")) {
                            msg = "O centro <b>"+descriptionPortuguese+"</b> foi modificado com sucesso";
                        }else{
                            msg="The <b>"+descriptionEnglish+"</b> center has been successfully modified" ;
                        }
                        StringPasser.createInstance(msg);
                        runOnUiThread(()->{
                            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                            startActivity(intent);
                        });

                    }).start();
                }
                else{

                    String msg="<ul>";
                    int numErrors=0;
                    base.setVisibility(View.VISIBLE);
                    background.setVisibility(View.GONE);

                    if(fields.get("centerDescription").isEmpty()){
                        descriptionCenterField.setError(getString(R.string.please_description_center));
                        msg+="<li><b>"+getString(R.string.please_description_center)+"</b></li>";
                        numErrors++;
                    }
                    if(fields.get("address").isEmpty()){
                        addressNameField.setError(getString(R.string.please_address));
                        msg+="<li><b>"+getString(R.string.please_address)+"</b></li>";
                        numErrors++;
                    }
                    if(FieldChecker.isPrecharged(country[0].getIdCountry())){
                        if(idRegion[0]==-2){
                            msg+="<li><b>"+getString(R.string.please_sel_region)+"</b></li>";
                            numErrors++;
                        }
                        if(idProvince[0]==-2){
                            msg+="<li><b>"+getString(R.string.please_sel_province)+"</b></li>";
                            numErrors++;
                        }
                        if(idCity[0]==-2){
                            msg+="<li><b>"+getString(R.string.please_sel_city)+"</b></li>";
                            numErrors++;
                        }
                    }else if(country[0].getIdCountry().equals("-2")){
                        msg+="<li><b>"+getString(R.string.please_country)+"</b></li>";
                        numErrors++;
                    }else{
                        if(fields.get("nameRegion").isEmpty()){
                            nameRegionField.setError(getString(R.string.please_region));
                            msg+="<li><b>"+getString(R.string.please_region)+"</b></li>";
                            numErrors++;
                        }
                        if(fields.get("nameProvince").isEmpty()){
                            nameProvinceField.setError(getString(R.string.please_province));
                            msg+="<li><b>"+getString(R.string.please_province)+"</b></li>";
                            numErrors++;
                        }
                        if(fields.get("nameCity").isEmpty()){
                            nameCityField.setError(getString(R.string.please_city));
                            msg+="<li><b>"+getString(R.string.please_city)+"</b></li>";
                            numErrors++;
                        }
                    }
                    if(!FieldChecker.emailHasCorrectFormat(fields.get("email"))){
                        int idErr=-1;
                        if(fields.get("email").isEmpty()){
                            idErr=R.string.please_email_center;
                        }
                        else{
                            idErr=R.string.wrong_email_center;
                        }
                        emailField.setError(getString(idErr));
                        msg+="<li><b>"+getString(idErr)+"</b></li>";
                        numErrors++;
                    }
                    if(!(FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone")))){
                        int idErr=-1;
                        if(fields.get("telephone").isEmpty()){
                            idErr=R.string.please_phone_center;
                        }
                        else{
                            idErr=R.string.wrong_phone_center;
                        }
                        phoneField.setError(getString(idErr));
                        msg+="<li><b>"+getString(idErr)+"</b></li>";
                        numErrors++;
                    }
                    int idTitle=-1;
                    if(numErrors==1){
                        idTitle=R.string.error;
                    }
                    else{
                        idTitle=R.string.errors;
                    }
                    msg+="</ul>";
                    new android.app.AlertDialog.Builder(EditCenter.this)
                            .setTitle(getString(idTitle))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage(Html.fromHtml(msg,0))
                            .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            }

        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);

            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }

    private void regionSpinnerControl(){
        if(!idCountry[0].equals("-2")){
            if (FieldChecker.isPrecharged(idCountry[0])) {
                getRegionsByCountry(idCountry[0]);
            } else {
                regionSpinner.setVisibility(View.GONE);
                provinceSpinner.setVisibility(View.GONE);
                citySpinner.setVisibility(View.GONE);
                regionSpinnerAux.setVisibility(View.GONE);
                provinceSpinnerAux.setVisibility(View.GONE);
                citySpinnerAux.setVisibility(View.GONE);
                tilProvince.setVisibility(View.VISIBLE);
                tilRegion.setVisibility(View.VISIBLE);
                tilCity.setVisibility(View.VISIBLE);
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
            tilProvince.setVisibility(View.GONE);
            tilRegion.setVisibility(View.GONE);
            tilCity.setVisibility(View.GONE);
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

            getProvincesByRegion(idRegion[0],idCountry[0]);
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

            getCitiesByProvince(idProvince[0], idRegion[0], idCountry[0]);

        }else{
            fields.replace("nameProvince","");
            fields.replace("nameCity","");
            citySpinner.setVisibility(View.GONE);
            citySpinnerAux.setVisibility(View.VISIBLE);
        }
    }

    public void getRegionsByCountry(String idCountry){
        RegionsController.GetRegionsByCountry(idCountry, new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {
                runOnUiThread(()->{
                    regions=new ArrayList<>();
                    for(JsonObject reg: data){
                        int idRegion=reg.getAsJsonPrimitive("idRegion").getAsInt();
                        String nameSpanish=reg.getAsJsonPrimitive("nameSpanish").getAsString();
                        String nameEnglish=reg.getAsJsonPrimitive("nameEnglish").getAsString();
                        String nameFrench=reg.getAsJsonPrimitive("nameFrench").getAsString();
                        String nameBasque=reg.getAsJsonPrimitive("nameBasque").getAsString();
                        String nameCatalan=reg.getAsJsonPrimitive("nameCatalan").getAsString();
                        String nameDutch=reg.getAsJsonPrimitive("nameDutch").getAsString();
                        String nameGalician=reg.getAsJsonPrimitive("nameGalician").getAsString();
                        String nameGerman=reg.getAsJsonPrimitive("nameGerman").getAsString();
                        String nameItalian=reg.getAsJsonPrimitive("nameItalian").getAsString();
                        String namePortuguese=reg.getAsJsonPrimitive("namePortuguese").getAsString();
                        regions.add(new Region(idRegion,idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese));
                    }
                    if(regions.size()>1){
                        regions.add(0,auxRegList.get(0));
                        regionSpinner.setVisibility(View.VISIBLE);
                        regionSpinnerAux.setVisibility(View.GONE);
                        regionAdapter[0]=new RegionAdapter(EditCenter.this,regions);
                        regionAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        regionSpinner.setAdapter(regionAdapter[0]);
                    }
                    else{
                        region[0]=regions.get(0);
                        idRegion[0]=-1;
                        regionSpinner.setVisibility(View.GONE);
                        regionSpinnerAux.setVisibility(View.VISIBLE);
                        provinceSpinnerControl();
                    }

                    tilProvince.setVisibility(View.GONE);
                    tilRegion.setVisibility(View.GONE);
                    tilCity.setVisibility(View.GONE);
                });
            }

            @Override
            public void onError(String errorResponse) {
                runOnUiThread(()->{
                    Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
                });
            }
        });

    }

    public void getProvincesByRegion(int idRegion, String idCountry){
        ProvincesController.GetProvincesByRegion(idRegion, idCountry, new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {
                runOnUiThread(()->{
                    provinces=new ArrayList<>();
                    for(JsonObject reg:data){
                        int idProvince=reg.getAsJsonPrimitive("idProvince").getAsInt();
                        String nameSpanish=reg.getAsJsonPrimitive("nameSpanish").getAsString();
                        String nameEnglish=reg.getAsJsonPrimitive("nameEnglish").getAsString();
                        String nameFrench=reg.getAsJsonPrimitive("nameFrench").getAsString();
                        String nameBasque=reg.getAsJsonPrimitive("nameBasque").getAsString();
                        String nameCatalan=reg.getAsJsonPrimitive("nameCatalan").getAsString();
                        String nameDutch=reg.getAsJsonPrimitive("nameDutch").getAsString();
                        String nameGalician=reg.getAsJsonPrimitive("nameGalician").getAsString();
                        String nameGerman=reg.getAsJsonPrimitive("nameGerman").getAsString();
                        String nameItalian=reg.getAsJsonPrimitive("nameItalian").getAsString();
                        String namePortuguese=reg.getAsJsonPrimitive("namePortuguese").getAsString();
                        provinces.add(new Province(idProvince,idRegion,idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese));
                    }
                    if(provinces.size()>1) {
                        provinces.add(0,auxProList.get(0));
                    }
                    provinceAdapter[0] = new ProvinceAdapter(EditCenter.this, provinces);
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
                });
            }

            @Override
            public void onError(String errorResponse) {
                runOnUiThread(()->{
                    Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
                });
            }
        });
    }


    public void getCitiesByProvince(int idProvince, int idRegion, String idCountry) {
        CitiesController.GetCitiesByProvince(idProvince, idRegion, idCountry, new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {
                runOnUiThread(() -> {
                    cities = new ArrayList<>();
                    for (JsonObject reg : data) {
                        int idCity = reg.getAsJsonPrimitive("idCity").getAsInt();
                        String nameSpanish = reg.getAsJsonPrimitive("nameSpanish").getAsString();
                        String nameEnglish = reg.getAsJsonPrimitive("nameEnglish").getAsString();
                        String nameFrench = reg.getAsJsonPrimitive("nameFrench").getAsString();
                        String nameBasque = reg.getAsJsonPrimitive("nameBasque").getAsString();
                        String nameCatalan = reg.getAsJsonPrimitive("nameCatalan").getAsString();
                        String nameDutch = reg.getAsJsonPrimitive("nameDutch").getAsString();
                        String nameGalician = reg.getAsJsonPrimitive("nameGalician").getAsString();
                        String nameGerman = reg.getAsJsonPrimitive("nameGerman").getAsString();
                        String nameItalian = reg.getAsJsonPrimitive("nameItalian").getAsString();
                        String namePortuguese = reg.getAsJsonPrimitive("namePortuguese").getAsString();
                        cities.add(new City(idCity, idProvince, idRegion, idCountry, nameSpanish, nameEnglish, nameFrench, nameBasque, nameCatalan, nameDutch, nameGalician, nameGerman, nameItalian, namePortuguese));
                    }
                    if(!cities.isEmpty()){
                        if(cities.size()>1) {
                            cities.add(0,auxCityList.get(0));
                        }
                        cityAdapter[0] = new CityAdapter(EditCenter.this, cities);

                        cityAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        citySpinner.setAdapter(cityAdapter[0]);
                        citySpinnerAux.setVisibility(View.GONE);
                        citySpinner.setVisibility(View.VISIBLE);
                        if(cities.size()==1){
                            citySpinner.setSelection(0);
                            city[0]=(City) citySpinner.getSelectedItem();
                            idCity[0]=city[0].getIdCity();
                        }
                    }
                });
            }

            @Override
            public void onError(String errorResponse) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
                });
            }
        });

    }
}