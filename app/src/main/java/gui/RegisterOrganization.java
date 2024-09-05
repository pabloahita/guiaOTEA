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
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
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
import cli.user.User;
import gui.adapters.*;
import misc.FieldChecker;
import misc.ListCallback;
import otea.connection.controller.AddressesController;
import otea.connection.controller.CentersController;
import otea.connection.controller.CitiesController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.ProvincesController;
import otea.connection.controller.RegionsController;
import otea.connection.controller.TranslatorController;
import otea.connection.controller.UsersController;
import session.FileManager;
import session.Session;
import session.StringPasser;

public class RegisterOrganization extends AppCompatActivity {

    int numAttempts=0;

    List<Country> countries;

    List<Region> regions;

    List<Province> provinces;

    List<City> cities;


    CountryAdapter[] countryAdapter={null};

    PhoneCodeAdapter[] phoneCodeAdapter;

    RegionAdapter[] regionAdapter={null};

    ProvinceAdapter[] provinceAdapter={null};

    CityAdapter[] cityAdapter={null};
    
    Country[] country=new Country[1];

    Region[] region=new Region[1];

    Province[] province=new Province[1];

    City[] city=new City[1];

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

    Map<String,String> fields;
    Button imageOrgButton;

    ImageView imageOrg;


    InputStream profilePhotoOrg;

    InputStream profilePhotoDir;

    int selectedPhoto=-1;

    int[] idCity = {-1};
    int[] idProvince = {-1};
    int[] idRegion = {-1};
    String[] idCountry = {""};


    String imgOrgName="";

    boolean checked;


    List<Region> auxRegList=new ArrayList<>();
    List<Province> auxProList=new ArrayList<>();
    List<City> auxCityList=new ArrayList<>();

    TextInputLayout tilRegion;

    TextInputLayout tilProvince;

    TextInputLayout tilCity;

    ImageButton helpButton;

    ProgressBar pbRegion;

    ProgressBar pbProvince;

    ProgressBar pbCity;




    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_organization);


        ConstraintLayout base=findViewById(R.id.base);
        ConstraintLayout loading = findViewById(R.id.final_background);

        countries = new ArrayList<>();
        countries.add(new Country("-2", "País", "Country", "Pays", "Herrialdea", "País", "Land", "País", "Land", "Paese", "País", "-", ""));
        countries.addAll(Session.getInstance().getCountries());

        phoneCodeAdapter = new PhoneCodeAdapter[2];

        countryAdapter[0] = new CountryAdapter(RegisterOrganization.this, countries);
        countryAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        phoneCodeAdapter[0] = new PhoneCodeAdapter(RegisterOrganization.this, countries);
        phoneCodeAdapter[1] = new PhoneCodeAdapter(RegisterOrganization.this, countries);


        base.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);

        EditText nameOrgField = findViewById(R.id.org_name_reg);
        EditText addressNameField = findViewById(R.id.name_address_reg);
        nameProvinceField = findViewById(R.id.foreign_province_reg);
        nameRegionField = findViewById(R.id.foreign_region_reg);
        nameCityField = findViewById(R.id.foreign_city_reg);
        EditText emailField = findViewById(R.id.email_reg);
        EditText orgPhoneField = findViewById(R.id.phone_reg);
        EditText moreInfoField = findViewById(R.id.more_info_org_reg);
        countrySpinner = findViewById(R.id.spinner_countries_reg);
        regionSpinner = findViewById(R.id.spinner_regions_reg);
        regionSpinnerAux = findViewById(R.id.spinner_regions_reg_aux);
        provinceSpinner = findViewById(R.id.spinner_provinces_reg);
        provinceSpinnerAux = findViewById(R.id.spinner_provinces_reg_aux);
        citySpinner = findViewById(R.id.spinner_cities_reg);
        citySpinnerAux = findViewById(R.id.spinner_cities_reg_aux);
        Spinner phoneCode1 = findViewById(R.id.phonecode1);
        pbRegion=findViewById(R.id.progressBarRegion);
        pbProvince=findViewById(R.id.progressBarProvince);
        pbCity=findViewById(R.id.progressBarCity);

        tilRegion=findViewById(R.id.tilRegion);
        tilProvince=findViewById(R.id.tilProvince);
        tilCity=findViewById(R.id.tilCity);

        auxRegList.add(new Region(-2, "-2", "Región", "Region", "Région", "Eskualdea", "Regió", "Region", "Rexión", "Region", "Regione", "Região"));
        RegionAdapter adapterRegAux = new RegionAdapter(RegisterOrganization.this, auxRegList);

        auxProList.add(new Province(-2, -2, "-2", "Provincia", "Province", "Province", "Probintzia", "Província", "Provincie", "Provincia", "Provinz", "Provincia", "Província"));
        ProvinceAdapter adapterProAux = new ProvinceAdapter(RegisterOrganization.this, auxProList);

        auxCityList.add(new City(-2, -2, -2, "-2", "Ciudad", "City", "Ville", "Hiri", "Ciutat", "Stad", "Cidade", "Stadt", "Città", "Cidade"));
        CityAdapter adapterCitAux = new CityAdapter(RegisterOrganization.this, auxCityList);

        regionSpinnerAux.setAdapter(adapterRegAux);
        regionSpinnerAux.setEnabled(false);
        regionSpinnerAux.setAlpha(0.5f);
        provinceSpinnerAux.setAdapter(adapterProAux);
        provinceSpinnerAux.setEnabled(false);
        provinceSpinnerAux.setAlpha(0.5f);
        citySpinnerAux.setAdapter(adapterCitAux);
        citySpinnerAux.setEnabled(false);
        citySpinnerAux.setAlpha(0.5f);
        countrySpinner.setAdapter(countryAdapter[0]);
        countrySpinner.setEnabled(true);
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);

        EditText emailDirField = (EditText) findViewById(R.id.email_dir_reg);

        Drawable correct = ContextCompat.getDrawable(getApplicationContext(), R.drawable.baseline_check_circle_24);

        imageOrgButton=findViewById(R.id.uploadPhoto);
        imageOrg=findViewById(R.id.profilePhoto);

        helpButton=findViewById(R.id.helpButton);




        fields = new HashMap<String, String>();
        fields.put("nameOrg", "");
        fields.put("address", "");
        fields.put("nameCity", "");
        fields.put("nameProvince", "");
        fields.put("nameRegion", "");
        fields.put("telephoneCodeOrg", "");
        fields.put("telephoneOrg", "");
        fields.put("emailOrg", "");
        fields.put("information", "");
        fields.put("emailDir", "");



        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        try {
                            profilePhotoOrg = getContentResolver().openInputStream(uri);
                            imageOrg.setImageURI(uri);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }catch(NullPointerException ignored){

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
                            profilePhotoOrg = bs;
                            imageOrg.setImageBitmap(bitmap);
                            bs.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }catch(NullPointerException ignored){

                        }
                    }
                });

        imageOrgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPhoto=0;
                new AwesomeInfoDialog(v.getContext())
                        .setTitle(R.string.change_photo)
                        .setMessage(R.string.select_source)
                        .setColoredCircle(R.color.miradas_color)
                        .setCancelable(true)
                        .setDialogIconAndColor(android.R.drawable.ic_menu_camera,R.color.white)
                        .setPositiveButtonText(getString(R.string.camera))
                        .setPositiveButtonbackgroundColor(R.color.miradas_color)
                        .setPositiveButtonTextColor(R.color.white)
                        .setNegativeButtonText(getString(R.string.gallery))
                        .setNegativeButtonbackgroundColor(R.color.miradas_color)
                        .setNegativeButtonTextColor(R.color.white)
                        .setPositiveButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                mTakePicture.launch(null);
                            }
                        })
                        .setNegativeButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                mGetContent.launch("image/*");
                            }
                        })
                        .show();
            }
        });

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                country[0] = countryAdapter[0].getItem(position);
                fields.replace("telephoneCodeOrg",phoneCodeAdapter[0].getItem(position).getPhone_code());
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
                idCity[0] = city[0].getIdCity();
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


        nameOrgField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameOrgField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input_text = s.toString();
                nameOrgField.setError(null);
                if (input_text.isEmpty()) {
                    nameOrgField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                } else {
                    nameOrgField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                addressNameField.setError(null);
                if (input_text.isEmpty()) {
                    addressNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                } else {
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
                String input_text = s.toString();
                nameRegionField.setError(null);
                if (input_text.isEmpty()) {
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                } else {
                    nameRegionField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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
                nameProvinceField.setError(null);
                if (input_text.isEmpty()) {
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                } else {
                    nameProvinceField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                if (input_text.isEmpty()) {
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
                orgPhoneField.setError(null);
                if (FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+fields.get("telephoneOrg"))) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                } else{
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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
                String inputText=s.toString();
                orgPhoneField.setError(null);
                if (FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+inputText)) {
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                } else{
                    orgPhoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
                fields.replace("telephoneOrg",inputText);
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
                emailField.setError(null);
                if (FieldChecker.emailHasCorrectFormat(inputText)) {
                    emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                } else {
                    emailField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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
                        emailDirField.setError(null);
                        if (FieldChecker.emailHasCorrectFormat(inputText)) {
                            emailDirField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                        } else {
                            emailDirField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                        }
                        fields.replace("emailDir",inputText);
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
                    msg="Para poder registrar correctamente una organización, debe complimentar con los siguientes campos:" +
                            "<ul>" +
                            "<li><b>Nombre de la organización: </b>En este campo deberá introducir el nombre de la organización a añadir.</li>" +
                            "<li><b>Dirección: </b>En este campo deberá introducir la dirección postal de la organización, sin introducir ciudad, ni región, ni provincia ni país. Por ejemplo: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>País: </b>En este campo debe seleccionar el país donde se ubica su organización. Si el país seleccionado es <i>España, Andorra, Argentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador, El Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguay, Portugal, Perú, Puerto Rico, Uruguay o Venezuela</i>, los campos de <i><b>Región</b></i>, <i><b>Provincia</b></i> y <i><b>Ciudad</b></i> serán seleccionables, por lo contrario tendrá que escribirlos de forma manual.</li>" +
                            "<li><b>Región: </b>En este campo deberá introducir o seleccionar la región donde se ubica la organización. Campo no disponible para los siguientes países: <i>Andorra, Cuba, Puerto Rico y Uruguay</i> por la de división territorial de dichos países.</li>" +
                            "<li><b>Provincia: </b>En este campo deberá introducir o seleccionar la provincia donde se ubica la organización.</li>" +
                            "<li><b>Ciudad: </b>En este campo deberá introducir o seleccionar la ciudad donde se ubica la organización.</li>" +
                            "<li><b>Email: </b>En este campo deberá introducir el email correspondiente a la organización.</li>" +
                            "<li><b>Número de teléfono: </b>En este campo deberá introducir el número de teléfono de la organización. Si lo desea, puede seleccionar el prefijo correspondiente al país seleccionándolo en el desplegable de la parte izquierda, aunque también puede cambiarlo con el desplegable <i><b>País</b></i>.</li>" +
                            "<li><b>Más información: </b>En este campo podrá introducir información adicional sobre la información si así lo considera oportuno.</li>" +
                            "<li><b>Email del director: </b>En este campo deberá introducir el email correspondiente al del director de la organización, para que pueda darse de alta en la aplicación.</li>" +
                            "</ul>" +
                            "Si lo desea, puede agregar una fotografía de perfil presionando sobre <i><b>Cambiar foto</b>.</i>" +
                            "<br>" +
                            "<b>No olvide que debe aceptar que <i>Fundación Miradas</i> guarde registro sobre los datos de la organización acorde a la Ley Orgánica de Protección de Datos</b>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    msg="Afin d'enregistrer correctement une organisation, vous devez remplir les champs suivants :" +
                            "<ul>" +
                            "<li><b>Nom de l'organisation: </b>Dans ce champ, vous devez saisir le nom de l'organisation à ajouter.</li>" +
                            "<li><b>Adresse: </b>Dans ce champ, vous devez saisir l'adresse postale de l'organisation, sans saisir de ville, de région, de province ou de pays. Par exemple: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Pays: </b>Dans ce champ, vous devez sélectionner le pays où se trouve votre organisation. Si le pays sélectionné est<i>Espagne, Andorre, Argentine, Bolivie, Chili, Colombie, Costa Rica, Cuba, République dominicaine, Équateur, El Salvador, Guatemala, Honduras, Mexique, Nicaragua, Panama, Paraguay, Portugal, Pérou, Port Rico, Uruguay ou Venezuela</i>, les champs <i><b>Région</b></i>, <i><b>Province</b></i> et <i><b >Ville</b></i> sera sélectionnable, sinon vous devrez les écrire manuellement.</li>" +
                            "<li><b>Région: </b>Dans ce champ, vous devez saisir ou sélectionner la région où se trouve l'organisation. Champ non disponible pour les pays suivants: <i>Andorre, Cuba, Porto Rico et Uruguay</i> en raison de la division territoriale de ces pays.</li>" +
                            "<li><b>Province: </b>Dans ce champ, vous devez saisir ou sélectionner la province où se trouve l'organisation.</li>" +
                            "<li><b>Ville: </b>Dans ce champ, vous devez saisir ou sélectionner la ville où se trouve l'organisation.</li>" +
                            "<li><b>E-mail: </b>Dans ce champ, vous devez saisir l'e-mail correspondant à l'organisation.</li>" +
                            "<li><b>Numéro de téléphone: </b>Dans ce champ, vous devez saisir le numéro de téléphone de l'organisation. Si vous le souhaitez, vous pouvez sélectionner le préfixe correspondant au pays en le sélectionnant dans le menu déroulant de gauche, mais vous pouvez également le modifier avec le menu déroulant <i><b>Pays</b></i>. -menu déroulant.</li>" +
                            "<li><b>Plus d'informations: </b>Dans ce champ, vous pouvez saisir des informations supplémentaires sur les informations si vous le jugez approprié.</li>" +
                            "<li><b>E-mail du directeur: </b>Dans ce champ, vous devez saisir l'e-mail correspondant au directeur de l'organisation, afin de pouvoir vous inscrire dans l'application.</li>" +
                            "</ul>" +
                            "Si vous le souhaitez, vous pouvez ajouter une photo de profil en cliquant sur <i><b>Changer de photo</b>.</i>" +
                            "<br>" +
                            "<b>N'oubliez pas que vous devez accepter que la <i>Fundación Miradas</i> conserve des enregistrements des données de l'organisation conformément à la loi organique sur la protection des données.</b>";
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    msg="Erakunde bat behar bezala erregistratzeko, ondoko eremuak bete behar dituzu:" +
                            "<ul>" +
                            "<li><b>Erakundearen izena: </b>Eremu honetan gehitu nahi duzun erakundearen izena idatzi behar duzu.</li>" +
                            "<li><b>Helbidea: </b>Eremu honetan erakundearen posta helbidea sartu behar duzu, hiria, eskualdea, probintzia edo herrialdea sartu gabe. Adibidez: <i>Valdenúñez kalea, 8</i>.</li>" +
                            "<li><b>Herrialdea: </b>Eremu honetan zure erakundea dagoen herrialdea hautatu behar duzu. Hautatutako herrialdea bada<i>Espainia, Andorra, Argentina, Bolivia, Txile, Kolonbia, Costa Rica, Kuba, Dominikar Errepublika, Ekuador, El Salvador, Guatemala, Honduras, Mexiko, Nikaragua, Panama, Paraguai, Portugal, Peru, Portua Rico, Uruguay edo Venezuela</i>, <i><b>Eskualde</b></i>, <i><b>Probintzia</b></i> eta <i><b-eko eremuak >Hiria</b></i> hautagarria izango da, bestela eskuz idatzi beharko dituzu.</li>" +
                            "<li><b>Eskualdea: </b>Eremu honetan erakundea dagoen eskualdea sartu edo hautatu behar duzu. Eremua ez dago eskuragarri herrialde hauetarako: <i>Andorra, Kuba, Puerto Rico eta Uruguai</i>, aipatutako herrialdeen lurralde banaketa dela eta.</li>" +
                            "<li><b>Probintzia: </b>Eremu honetan erakundea dagoen probintzia sartu edo hautatu behar duzu.</li>" +
                            "<li><b>Hiria: </b>Eremu honetan erakundea dagoen hiria sartu edo hautatu behar duzu.</li>" +
                            "<li><b>Emaila: </b>Eremu honetan erakundeari dagokion emaila sartu behar duzu.</li>" +
                            "<li><b>Telefono-zenbakia: </b>Eremu honetan erakundearen telefono-zenbakia sartu behar duzu. Nahi izanez gero, herrialdeari dagokion aurrizkia hauta dezakezu ezkerreko goitibeherako menuan hautatuz, nahiz eta <i><b>Herrialdea</b></i> goitibeherako aukerarekin ere alda dezakezu. -behera menua.</li>" +
                            "<li><b>Informazio gehiago: </b>Eremu honetan informazioari buruzko informazio gehigarria sar dezakezu egokitzat jotzen baduzu.</li>" +
                            "<li><b>Zuzendariaren emaila: </b>Eremu honetan erakundeko zuzendariari dagokion emaila sartu behar duzu, aplikazioan izena eman ahal izateko.</li>" +
                            "</ul>" +
                            "Nahi baduzu, profileko argazki bat gehi dezakezu <i><b>Argazkia Aldatu</b> aukeran klik eginez.</i>" +
                            "<br>" +
                            "<b>Ez ahaztu onartu behar duzula <i>Fundación Miradas</i> erakundearen datuen erregistroak gordetzen dituela Datuak Babesteko Lege Organikoaren arabera.</b>";;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    msg="Per poder registrar correctament una organització, heu de complir amb els camps següents:" +
                            "<ul>" +
                            "<li><b>Nom de l'organització: </b>En aquest camp haureu d'introduir el nom de l'organització a afegir.</li>" +
                            "<li><b>Adreça: </b>En aquest camp heu d'introduir l'adreça postal de l'organització, sense introduir ciutat, ni regió, ni província ni país. Per exemple: <i>Carrer Valdenúñez, 8</i>.</li>" +
                            "<li><b>País: </b>En aquest camp heu de seleccionar el país on s'ubica la vostra organització. Si el país seleccionat és <i>Espanya, Andorra, Argentina, Bolívia, Xile, Colòmbia, Costa Rica, Cuba, República Dominicana, Equador, El Salvador, Guatemala, Hondures, Mèxic, Nicaragua, Panamà, Paraguai, Portugal, Perú, Port Rico, Uruguai o Veneçuela</i>, els camps de <i><b>Regió</b></i>, <i><b>Província</b></i> i <i><b >Ciutat</b></i> seran seleccionables, per contra els haurà d'escriure manualment.</li>" +
                            "<li><b>Regió: </b>En aquest camp haureu d'introduir o seleccionar la regió on s'ubica l'organització. Camp no disponible per als països següents: <i>Andorra, Cuba, Puerto Rico i Uruguai</i> per la de divisió territorial d'aquests països.</li>" +
                            "<li><b>Província: </b>En aquest camp haureu d'introduir o seleccionar la província on s'ubica l'organització.</li>" +
                            "<li><b>Ciutat: </b>En aquest camp haureu d'introduir o seleccionar la ciutat on s'ubica l'organització.</li>" +
                            "<li><b>Email: </b>En aquest camp haureu d'introduir el correu electrònic corresponent a l'organització.</li>" +
                            "<li><b>Nombre de telèfon: </b>En aquest camp haureu d'introduir el número de telèfon de l'organització. Si ho desitgeu, podeu seleccionar el prefix corresponent al país seleccionant-lo al desplegable de la part esquerra, encara que també podeu canviar-lo amb el desplegable <i><b>País</b></i>.</li>" +
                            "<li><b>Més informació: </b>En aquest camp podreu introduir informació addicional sobre la informació si així ho considereu oportú.</li>" +
                            "<li><b>Email del director: </b>En aquest camp haureu d'introduir l'email corresponent al del director de l'organització, perquè us pugueu donar d'alta a l'aplicació.</li>" +
                            "</ul>" +
                            "Si voleu, podeu afegir una fotografia de perfil prement sobre <i><b>Canviar foto</b>.</i>" +
                            "<br>" +
                            "<b>No oblideu que heu d'acceptar que <i>Fundación Miradas</i> guardi registre sobre les dades de l'organització d'acord amb la Llei Orgànica de Protecció de Dades.</b>";
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    msg="Om een organisatie correct te registreren, moet u de volgende velden invullen:" +
                            "<ul>" +
                            "<li><b>Naam organisatie: </b>In dit veld moet u de naam invoeren van de organisatie die u wilt toevoegen.</li>" +
                            "<li><b>Adres: </b>In dit veld moet u het postadres van de organisatie invullen, zonder stad, regio, provincie of land in te vullen. Bijvoorbeeld: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Land: </b>In dit veld moet u het land selecteren waar uw organisatie gevestigd is. Als het geselecteerde land<i>Spanje, Andorra, Argentinië, Bolivia, Chili, Colombia, Costa Rica, Cuba, Dominicaanse Republiek, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru, Haven is Rico, Uruguay of Venezuela</i>, de velden <i><b>Regio</b></i>, <i><b>Provincie</b></i> en <i><b >Plaats</b></i> kan worden geselecteerd, anders moet u ze handmatig schrijven.</li>" +
                            "<li><b>Regio: </b>In dit veld moet u de regio invoeren of selecteren waar de organisatie gevestigd is. Veld niet beschikbaar voor de volgende landen: <i>Andorra, Cuba, Puerto Rico en Uruguay</i> vanwege de territoriale indeling van genoemde landen.</li>" +
                            "<li><b>Provincie: </b>In dit veld moet u de provincie invoeren of selecteren waar de organisatie gevestigd is.</li>" +
                            "<li><b>Stad: </b>In dit veld moet u de stad invoeren of selecteren waar de organisatie gevestigd is.</li>" +
                            "<li><b>E-mail: </b>In dit veld moet u het e-mailadres invoeren dat overeenkomt met de organisatie.</li>" +
                            "<li><b>Telefoonnummer: </b>In dit veld moet u het telefoonnummer van de organisatie invullen. Als u wilt, kunt u het voorvoegsel dat overeenkomt met het land selecteren door dit te selecteren in het vervolgkeuzemenu aan de linkerkant, maar u kunt dit ook wijzigen met de vervolgkeuzelijst <i><b>Land</b></i> -downmenu.</li>" +
                            "<li><b>Meer informatie: </b>In dit veld kunt u aanvullende informatie over de informatie invoeren als u dit passend acht.</li>" +
                            "<li><b>E-mail van de directeur: </b>In dit veld moet u het e-mailadres invoeren dat overeenkomt met de directeur van de organisatie, zodat u zich kunt registreren in de applicatie.</li>" +
                            "</ul>" +
                            "Als u wilt, kunt u een profielfoto toevoegen door op <i><b>Foto wijzigen</b> te klikken.</i>" +
                            "<br>" +
                            "<b>Vergeet niet dat u moet accepteren dat <i>Fundación Miradas</i> de gegevens van de organisatie bijhoudt in overeenstemming met de organieke wet op gegevensbescherming.</b>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    msg="Para rexistrar correctamente unha organización, debes cubrir os seguintes campos:\n" +
                            "<ul>\n" +
                            "<li><b>Nome da organización: </b>Neste campo debes introducir o nome da organización que queres engadir.</li>\n" +
                            "<li><b>Enderezo: </b>Neste campo debes introducir o enderezo postal da organización, sen introducir cidade, rexión, provincia ou país. Por exemplo: <i>Calle Valdenúñez, 8</i>.</li>\n" +
                            "<li><b>País: </b>Neste campo debes seleccionar o país onde se atopa a túa organización. Se o país seleccionado é<i>España, Andorra, Arxentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador, O Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguai, Portugal, Perú, Porto Rico, Uruguai ou Venezuela</i>, os campos de <i><b>Rexión</b></i>, <i><b>Provincia</b></i> e <i><b >Cidade</b></i> será seleccionable, se non, terás que escribilos manualmente.</li>\n" +
                            "<li><b>Rexión: </b>Neste campo debes introducir ou seleccionar a rexión onde se atopa a organización. Campo non dispoñible para os seguintes países: <i>Andorra, Cuba, Porto Rico e Uruguai</i> debido á división territorial dos devanditos países.</li>\n" +
                            "<li><b>Provincia: </b>Neste campo debes introducir ou seleccionar a provincia onde se atopa a organización.</li>\n" +
                            "<li><b>Cidade: </b>Neste campo debes introducir ou seleccionar a cidade onde se atopa a organización.</li>\n" +
                            "<li><b>Email: </b>Neste campo debes introducir o correo electrónico correspondente á organización.</li>\n" +
                            "<li><b>Número de teléfono: </b>Neste campo debes introducir o número de teléfono da organización. Se queres, podes seleccionar o prefixo correspondente ao país seleccionándoo no menú despregable da esquerda, aínda que tamén podes cambialo co menú <i><b>País</b></i>. -menú abaixo.</li>\n" +
                            "<li><b>Máis información: </b>Neste campo pode introducir información adicional sobre a información se o considera oportuno.</li>\n" +
                            "<li><b>Email do director: </b>Neste campo debes introducir o correo electrónico correspondente ao director da organización, para poder rexistrarte na aplicación.</li>\n" +
                            "</ul>\n" +
                            "Se queres, podes engadir unha foto de perfil facendo clic en <i><b>Cambiar foto</b>.</i>\n" +
                            "<br>\n" +
                            "<b>Non esquezas que debes aceptar que a <i>Fundación Miradas</i> leva un rexistro dos datos da organización de acordo coa Lei Orgánica de Protección de Datos.</b>";
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    msg="Um eine Organisation korrekt zu registrieren, müssen Sie die folgenden Felder ausfüllen:" +
                         "<ul>" +
                         "<li><b>Name der Organisation: </b>In diesem Feld müssen Sie den Namen der Organisation eingeben, die hinzugefügt werden soll.</li>" +
                        "<li><b>Adresse:</b>In diesem Feld müssen Sie die Postanschrift der Organisation eingeben, ohne Stadt, Region, Provinz oder Land einzugeben. Zum Beispiel: <i>Calle Valdenúñez, 8</i > .</li>" +
                        "<li><b>Land:</b>In diesem Feld müssen Sie das Land auswählen, in dem sich Ihre Organisation befindet. Wenn das ausgewählte Land <i>Spanien, Andorra, Argentinien, Bolivien, Chile, Kolumbien, Costa Rica ist, Kuba, Dominikanische Republik, Ecuador, El Salvador, Guatemala, Honduras, Mexiko, Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay oder Venezuela</i>, die Felder <i><b>Region</b> </i>, <i><b>Provinz</b></i> und <i><b>Stadt</b></i> sind auswählbar, andernfalls müssen Sie sie manuell schreiben.< /li>" +
                        "<li><b>Region:</b>In diesem Feld müssen Sie die Region eingeben oder auswählen, in der sich die Organisation befindet. Das Feld ist für die folgenden Länder nicht verfügbar: <i>Andorra, Kuba, Puerto Rico und Uruguay</ i > durch die territoriale Aufteilung dieser Länder.</li>" +
                        "<li><b>Provinz:</b>In diesem Feld müssen Sie die Provinz eingeben oder auswählen, in der sich die Organisation befindet.</li>" +
                         "<li><b>Stadt:</b>In diesem Feld müssen Sie die Stadt eingeben oder auswählen, in der sich die Organisation befindet.</li>" +
                         "<li><b>E-Mail: </b>In diesem Feld müssen Sie die E-Mail-Adresse der Organisation eingeben.</li>" +
                                                    "<li><b>Telefonnummer: </b>In diesem Feld müssen Sie die Telefonnummer der Organisation eingeben. Wenn Sie möchten, können Sie das dem Land entsprechende Präfix auswählen, indem Sie es aus dem Dropdown-Menü auswählen Sie können es aber auch mit der Dropdown-Liste <i><b>Land</b></i> ändern.</li>" +
                         "<li><b>Weitere Informationen:</b>In diesem Feld können Sie zusätzliche Informationen zu den Informationen eingeben, wenn Sie dies für angemessen halten.</li>" +
                         "<li><b>E-Mail des Direktors: </b>In diesem Feld müssen Sie die E-Mail-Adresse des Direktors der Organisation eingeben, damit Sie sich in der Bewerbung registrieren können.</li>" +
                         "</ul>" +
                         "Wenn Sie möchten, können Sie ein Profilfoto hinzufügen, indem Sie auf <i><b>Foto ändern</b> klicken.</i>" +
                         "<br>" +
                         "<b>Vergessen Sie nicht, dass Sie akzeptieren müssen, dass <i>Fundación Miradas</i> Aufzeichnungen über die Daten der Organisation in Übereinstimmung mit dem Datenschutzgesetz führt</b>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    msg="Per registrare correttamente un'organizzazione è necessario completare i seguenti campi:" +
                            "<ul>" +
                            "<li><b>Nome dell'organizzazione: </b>In questo campo devi inserire il nome dell'organizzazione da aggiungere.</li>" +
                            "<li><b>Indirizzo: </b>In questo campo è necessario inserire l'indirizzo postale dell'organizzazione, senza inserire città, regione, provincia o paese. Ad esempio: <i>Calle Valdenúñez, 8</i > .</li>" +
                            "<li><b>Paese: </b>in questo campo devi selezionare il paese in cui si trova la tua organizzazione. Se il paese selezionato è <i>Spagna, Andorra, Argentina, Bolivia, Cile, Colombia, Costa Rica, Cuba, Repubblica Dominicana, Ecuador, El Salvador, Guatemala, Honduras, Messico, Nicaragua, Panama, Paraguay, Portogallo, Perù, Porto Rico, Uruguay o Venezuela</i>, i campi <i><b>Regione</b> </i>, <i><b>Provincia</b></i> e <i><b>Città</b></i> saranno selezionabili, altrimenti dovrai scriverli manualmente.< /li>" +
                            "<li><b>Regione: </b>in questo campo è necessario inserire o selezionare la regione in cui si trova l'organizzazione. Campo non disponibile per i seguenti paesi: <i>Andorra, Cuba, Porto Rico e Uruguay</ i > dalla divisione territoriale di detti paesi.</li>" +
                            "<li><b>Provincia: </b>In questo campo è necessario inserire o selezionare la provincia in cui ha sede l'organizzazione.</li>" +
                            "<li><b>Città: </b>In questo campo è necessario inserire o selezionare la città in cui ha sede l'organizzazione.</li>" +
                            "<li><b>E-mail: </b>In questo campo è necessario inserire l'e-mail corrispondente all'organizzazione.</li>" +
                            "<li><b>Numero di telefono: </b>In questo campo è necessario inserire il numero di telefono dell'organizzazione. Se lo desideri, puoi selezionare il prefisso corrispondente al Paese selezionandolo dal menu a tendina su a sinistra, anche se puoi anche modificarlo dal menu a discesa <i><b>Paese</b></i>.</li>" +
                            "<li><b>Ulteriori informazioni: </b>In questo campo puoi inserire ulteriori informazioni sulle informazioni se lo ritieni opportuno.</li>" +
                            "<li><b>E-mail del direttore: </b>In questo campo è necessario inserire l'e-mail corrispondente al direttore dell'organizzazione, in modo da potersi registrare nell'applicazione.</li>" +
                            "</ul>" +
                            "Se lo desideri, puoi aggiungere una foto del profilo facendo clic su <i><b>Cambia foto</b>.</i>" +
                            "<br>" +
                            "<b>Non dimenticare che devi accettare che <i>Fundación Miradas</i> conservi un registro dei dati dell'organizzazione in conformità con la Legge Organica sulla Protezione dei Dati</b>";
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    msg="Para registar corretamente uma organização, deve preencher os seguintes campos:" +
                            "<ul>" +
                            "<li><b>Nome da organização: </b>Neste campo deve introduzir o nome da organização a adicionar.</li>" +
                            "<li><b>Endereço: </b>Neste campo deve introduzir o endereço postal da organização, sem introduzir cidade, região, província ou país. Por exemplo: <i>Calle Valdenúñez, 8</i > .< /li>" +
                            "<li><b>País: </b>Neste campo deve selecionar o país onde se encontra a sua organização. Se o país selecionado for <i>Espanha, Andorra, Argentina, Bolívia, Chile, Colômbia, Costa Rica, Cuba , República Dominicana, Equador, El Salvador, Guatemala, Honduras, México, Nicarágua, Panamá, Paraguai, Portugal, Peru, Porto Rico, Uruguai ou Venezuela</i>, os campos <i><b>Região</b> < /i>, <i><b>Província</b></i> e <i><b>Cidade</b></i> serão seleccionáveis, caso contrário terá de os escrever manualmente. < / li>" +
                            "<li><b>Região: </b>Neste campo deve introduzir ou selecionar a região onde se encontra a organização. Campo não disponível para os seguintes países: <i>Andorra, Cuba, Porto Rico e Uruguai</ i > pela divisão territorial dos referidos países.</li>" +
                            "<li><b>Província: </b>Neste campo deve introduzir ou selecionar a província onde se encontra a organização.</li>" +
                            "<li><b>Cidade: </b>Neste campo deve introduzir ou selecionar a cidade onde se encontra a organização.</li>" +
                            "<li><b>E-mail: </b>Neste campo deve introduzir o e-mail correspondente à organização.</li>" +
                            "<li><b>Número de telefone: </b>Neste campo deve introduzir o número de telefone da organização. Se desejar, pode selecionar o prefixo correspondente ao país, selecionando-o no menu suspenso em à esquerda, embora possa também pode alterá-lo com o menu suspenso <i><b>País</b></i>.</li>" +
                            "<li><b>Mais informação: </b>Neste campo pode introduzir informação adicional sobre a informação se considerar apropriado.</li>" +
                            "<li><b>E-mail do diretor: </b>Neste campo deve introduzir o e-mail correspondente ao diretor da organização, para que se possa registar na aplicação.</li>" +
                            "</ul>" +
                            "Se desejar, pode adicionar uma fotografia de perfil clicando em <i><b>Alterar fotografia</b>.</i>" +
                            "<br>" +
                            "<b>Não se esqueça que deve aceitar que a <i>Fundación Miradas</i> mantenha registos dos dados da organização de acordo com a Lei Orgânica de Proteção de Dados</b>";
                }else{
                    msg="In order to correctly register an organization, you must complete the following fields:" +
                            "<ul>" +
                            "<li><b>Name of the organization: </b>In this field you must enter the name of the organization to add.</li>" +
                            "<li><b>Address: </b>In this field you must enter the postal address of the organization, without entering city, region, province or country. For example: <i>Calle Valdenúñez, 8</i>.</li>" +
                            "<li><b>Country: </b>In this field you must select the country where your organization is located. If the selected country is<i>Spain, Andorra, Argentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, Dominican Republic, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay or Venezuela</i>, the fields of <i><b>Region</b></i>, <i><b>Province</b></i> and <i><b >City</b></i> will be selectable, otherwise you will have to write them manually.</li>" +
                            "<li><b>Region: </b>In this field you must enter or select the region where the organization is located. Field not available for the following countries: <i>Andorra, Cuba, Puerto Rico and Uruguay</i> due to the territorial division of said countries.</li>" +
                            "<li><b>Province: </b>In this field you must enter or select the province where the organization is located.</li>" +
                            "<li><b>City: </b>In this field you must enter or select the city where the organization is located.</li>" +
                            "<li><b>Email: </b>In this field you must enter the email corresponding to the organization.</li>" +
                            "<li><b>Phone number: </b>In this field you must enter the phone number of the organization. If you wish, you can select the prefix corresponding to the country by selecting it from the drop-down menu on the left, although you can also change it with the <i><b>Country</b></i> drop-down menu.</li>" +
                            "<li><b>More information: </b>In this field you can enter additional information about the information if you consider it appropriate.</li>" +
                            "<li><b>Director's email: </b>In this field you must enter the email corresponding to the director of the organization, so that you can register in the application.</li>" +
                            "</ul>" +
                            "If you wish, you can add a profile photo by clicking on <i><b>Change photo</b>.</i>" +
                            "<br>" +
                            "<b>Do not forget that you must accept that <i>Fundación Miradas</i> keeps records of the organization's data in accordance with the Organic Law on Data Protection</b>";
                }
                new android.app.AlertDialog.Builder(RegisterOrganization.this)
                        .setTitle(getString(R.string.help))
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setMessage(Html.fromHtml(msg,0))
                        .create().show();
            }
        });


        CheckBox acceptLOPD = findViewById(R.id.accept_LOPD);

        Button register = findViewById(R.id.register_finished);
        register.setAlpha(0.5f);

        acceptLOPD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                      if (isChecked) {
                                                          register.setAlpha(1f);
                                                      } else {
                                                          register.setAlpha(0.5f);
                                                      }
                                                      checked=isChecked;
                                                  }
                                              }
        );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AwesomeProgressDialog chargingScreenDialog=new AwesomeProgressDialog(RegisterOrganization.this)
                        .setTitle(R.string.please_wait_reg_orgs)
                        .setMessage(R.string.please_wait)
                        .setColoredCircle(R.color.miradas_color)
                        .setCancelable(false);
                chargingScreenDialog.show();

                new Thread(()->{
                    if(!fields.get("nameOrg").isEmpty() && !fields.get("address").isEmpty() && (FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+fields.get("telephoneOrg")))
                            && FieldChecker.emailHasCorrectFormat(fields.get("emailOrg")) && FieldChecker.emailHasCorrectFormat(fields.get("emailDir")) &&
                            UsersController.Get(fields.get("emailDir")) == null &&
                            ((FieldChecker.isPrecharged(idCountry[0]) && idRegion[0]!=-2 && idProvince[0]!=-2 && idCity[0]!=-2) ||
                                    (!idCountry[0].equals("-2") && !fields.get("nameRegion").isEmpty() && !fields.get("nameProvince").isEmpty() && !fields.get("nameCity").isEmpty())) && checked){

                        List<Organization> orgs = OrganizationsController.getInstance().GetAllEvaluatedOrganizations();

                        int[] idOrganization = {-1};
                        if(orgs.isEmpty()){
                            idOrganization[0]=1;
                        }else{
                            idOrganization[0]=orgs.get(orgs.size()-1).getIdOrganization()+1;
                        }
                        String orgType = "EVALUATED";
                        String illness = "AUTISM";

                        List<Address> addresses= AddressesController.getInstance().GetAll();
                        int[] idAddress={-1};
                        if(addresses.isEmpty()){
                            idAddress[0]=1;
                        }else{
                            idAddress[0]=addresses.get(addresses.size()-1).getIdAddress()+1;
                        }


                        runOnUiThread(()->{
                            if(profilePhotoOrg!=null){
                                imgOrgName="ORG_"+idOrganization[0]+"_"+orgType+"_"+illness+".webp";
                                new Thread(()->{
                                    FileManager.uploadFile(profilePhotoOrg, "profile-photos", imgOrgName);
                                    try{
                                        profilePhotoOrg.close();
                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }
                                }).start();
                            }


                            new Thread(()->{
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
                                if(!informationText.isEmpty()){
                                    List<String> translations=TranslatorController.getInstance().translate(informationText, Locale.getDefault().getLanguage());
                                    if(Locale.getDefault().getLanguage().equals("es")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= informationText;
                                        informationFrench= translations.get(1);
                                        informationBasque= translations.get(2);
                                        informationCatalan= translations.get(3);
                                        informationDutch= translations.get(4);
                                        informationGalician= translations.get(5);
                                        informationGerman= translations.get(6);
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench=informationText;
                                        informationBasque= translations.get(2);
                                        informationCatalan= translations.get(3);
                                        informationDutch= translations.get(4);
                                        informationGalician= translations.get(5);
                                        informationGerman= translations.get(6);
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench= translations.get(2);
                                        informationBasque=informationText;
                                        informationCatalan= translations.get(3);
                                        informationDutch= translations.get(4);
                                        informationGalician= translations.get(5);
                                        informationGerman= translations.get(6);
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench= translations.get(2);
                                        informationBasque= translations.get(3);
                                        informationCatalan=informationText;
                                        informationDutch= translations.get(4);
                                        informationGalician= translations.get(5);
                                        informationGerman= translations.get(6);
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench= translations.get(2);
                                        informationBasque= translations.get(3);
                                        informationCatalan= translations.get(4);
                                        informationDutch=informationText;
                                        informationGalician= translations.get(5);
                                        informationGerman= translations.get(6);
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench= translations.get(2);
                                        informationBasque= translations.get(3);
                                        informationCatalan= translations.get(4);
                                        informationDutch= translations.get(5);
                                        informationGalician=informationText;
                                        informationGerman= translations.get(6);
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("de")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench= translations.get(2);
                                        informationBasque= translations.get(3);
                                        informationCatalan= translations.get(4);
                                        informationDutch= translations.get(5);
                                        informationGalician= translations.get(6);
                                        informationGerman=informationText;
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("it")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench= translations.get(2);
                                        informationBasque= translations.get(3);
                                        informationCatalan= translations.get(4);
                                        informationDutch= translations.get(5);
                                        informationGalician= translations.get(6);
                                        informationGerman= translations.get(7);
                                        informationItalian=informationText;
                                        informationPortuguese= translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                                        informationEnglish= translations.get(0);
                                        informationSpanish= translations.get(1);
                                        informationFrench= translations.get(2);
                                        informationBasque= translations.get(3);
                                        informationCatalan= translations.get(4);
                                        informationDutch= translations.get(5);
                                        informationGalician= translations.get(6);
                                        informationGerman= translations.get(7);
                                        informationItalian= translations.get(8);
                                        informationPortuguese=informationText;
                                    }else{
                                        informationEnglish= informationText;
                                        informationSpanish= translations.get(0);
                                        informationFrench= translations.get(1);
                                        informationBasque= translations.get(2);
                                        informationCatalan= translations.get(3);
                                        informationDutch= translations.get(4);
                                        informationGalician= translations.get(5);
                                        informationGerman= translations.get(6);
                                        informationItalian= translations.get(7);
                                        informationPortuguese= translations.get(8);
                                    }

                                }




                                Address address = new Address(idAddress[0], fields.get("address"), idCity[0],idProvince[0],idRegion[0],idCountry[0],fields.get("nameCity"),fields.get("nameProvince"),fields.get("nameRegion"));

                                Organization organization=new Organization(idOrganization[0],orgType,illness,fields.get("nameOrg"),idAddress[0],fields.get("emailOrg"),fields.get("telephoneCodeOrg")+" "+fields.get("telephoneOrg"),informationSpanish,informationEnglish,informationFrench,informationBasque,informationCatalan,informationDutch,informationGalician,informationGerman,informationItalian,informationPortuguese,imgOrgName);
                                User director=new User(fields.get("emailDir"),"DIRECTOR","","","","",idOrganization[0],orgType,illness,"",0);
                                AddressesController.getInstance().Create(address);
                                OrganizationsController.getInstance().Create(organization);
                                UsersController.getInstance().Create(director);
                                CentersController.getInstance().Create(new Center(organization.getIdOrganization(),organization.getOrganizationType(),organization.getIllness(),1,"Headquarters","Sede principal","Siège social","Egoitza","Seu principal","Hoofdkwartier","Sede principal","Hauptsitz","Sede principale","Sede principal",idAddress[0],fields.get("telephoneCodeOrg")+" "+fields.get("telephoneOrg"),fields.get("emailOrg"),imgOrgName));

                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                int idTitle=R.string.organization_registered;
                                int idMsg=R.string.notify_director;
                                StringPasser.createInstance(idTitle,idMsg);
                                StringPasser.getInstance().setFlag(1);


                                runOnUiThread(()->{
                                    Intent intent=new Intent(getApplicationContext(), MainMenu.class);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                });
                            }).start();
                        });




                    }
                    else{
                        runOnUiThread(()->{
                            String msg="<ul>";
                            int numErrors=0;
                            //base.setVisibility(View.VISIBLE);
                            //loading.setVisibility(View.GONE);
                            chargingScreenDialog.hide();
                            if(fields.get("nameOrg").isEmpty()){
                                nameOrgField.setError(getString(R.string.please_org_name));
                                numErrors++;
                                msg+="<li><b>"+getString(R.string.please_org_name)+"</b></li>";
                            }
                            if(fields.get("address").isEmpty()){
                                addressNameField.setError(getString(R.string.please_address));
                                numErrors++;
                                msg+="<li><b>"+getString(R.string.please_address)+"</b></li>";
                            }
                            if(FieldChecker.isPrecharged(country[0].getIdCountry())){
                                if(region[0]==null || region[0].getIdRegion()==-2){
                                    msg+="<li><b>"+getString(R.string.please_sel_region)+"</b></li>";
                                    numErrors++;
                                }
                                if(province[0]==null || province[0].getIdProvince()==-2){
                                    msg+="<li><b>"+getString(R.string.please_sel_province)+"</b></li>";
                                    numErrors++;
                                }
                                if(city[0]==null || city[0].getIdCity()==-2){
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
                            if(!FieldChecker.isACorrectPhone(fields.get("telephoneCodeOrg")+fields.get("telephoneOrg"))){int idErr=-1;
                                if(fields.get("telephoneOrg").isEmpty()){
                                    idErr=R.string.please_org_phone;
                                }
                                else{
                                    idErr=R.string.wrong_org_phone;
                                }
                                orgPhoneField.setError(getString(idErr));
                                msg+="<li><b>"+getString(idErr)+"</b></li>";
                                numErrors++;
                            }
                            if(!FieldChecker.emailHasCorrectFormat(fields.get("emailOrg"))){
                                int idErr=-1;
                                if(fields.get("emailOrg").isEmpty()){
                                    idErr=R.string.please_email_org;
                                }
                                else{
                                    idErr=R.string.wrong_email_org;
                                }
                                emailField.setError(getString(idErr));
                                msg+="<li><b>"+getString(idErr)+"</b></li>";
                                numErrors++;
                            }
                            if(!FieldChecker.emailHasCorrectFormat(fields.get("emailDir"))){
                                int idErr=-1;
                                if(fields.get("emailOrg").isEmpty()){
                                    idErr=R.string.please_email_dir;
                                }
                                else{
                                    idErr=R.string.wrong_email_dir;
                                }
                                emailDirField.setError(getString(idErr));
                                msg+="<li><b>"+getString(idErr)+"</b></li>";
                                numErrors++;
                            }
                            else{
                                int idErr=R.string.dir_email_in_db;
                                emailDirField.setError(getString(idErr));
                                msg+="<li><b>"+getString(idErr)+"</b></li>";
                                numErrors++;
                            }
                            if(!checked){
                                msg+="<li><b>"+getString(R.string.you_must_LOPD)+"</b></li>";
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
                            new AwesomeErrorDialog(RegisterOrganization.this)
                                    .setTitle(idTitle)
                                    .setMessage(Html.fromHtml(msg,0))
                                    .setColoredCircle(com.aminography.primedatepicker.R.color.redA700)
                                    .setCancelable(true).setButtonText(getString(R.string.understood))
                                    .setButtonBackgroundColor(com.aminography.primedatepicker.R.color.redA700)
                                    .setButtonText(getString(R.string.understood))
                                    .setErrorButtonClick(new Closure() {
                                        @Override
                                        public void exec() {
                                            // click
                                        }
                                    })
                                    .show();
                        });

                    }
                }).start();




            }
        });


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            setResult(RESULT_CANCELED,intent);
            finish();
        }
        return super.onKeyDown(keyCode,event);
    }



    private void regionSpinnerControl(){
        if(citySpinner.getVisibility()==View.VISIBLE){
            citySpinner.setVisibility(View.GONE);
            citySpinnerAux.setVisibility(View.VISIBLE);
        }
        if(provinceSpinner.getVisibility()==View.VISIBLE){
            provinceSpinner.setVisibility(View.GONE);
            provinceSpinnerAux.setVisibility(View.VISIBLE);
        }
        if(regionSpinner.getVisibility()==View.VISIBLE){
            regionSpinner.setVisibility(View.GONE);
            regionSpinnerAux.setVisibility(View.VISIBLE);
        }
        if(!idCountry[0].equals("-2")){
            if (FieldChecker.isPrecharged(idCountry[0])) {
                getRegionsByCountry(idCountry[0]);
            } else {

                idRegion[0]=-1;
                idProvince[0]=-1;
                idCity[0]=-1;
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
        if(citySpinner.getVisibility()==View.VISIBLE){
            citySpinner.setVisibility(View.GONE);
            citySpinnerAux.setVisibility(View.VISIBLE);
        }
        if(provinceSpinner.getVisibility()==View.VISIBLE){
            provinceSpinner.setVisibility(View.GONE);
            provinceSpinnerAux.setVisibility(View.VISIBLE);
        }
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
        if(citySpinner.getVisibility()==View.VISIBLE){
            citySpinner.setVisibility(View.GONE);
            citySpinnerAux.setVisibility(View.VISIBLE);
        }
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

        pbRegion.setVisibility(View.VISIBLE);
        RegionsController.GetRegionsByCountryASync(idCountry, new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {
                new Thread(()-> {
                    regions=new ArrayList<>();
                    for(JsonObject reg: data){
                        int idRegion=reg.getAsJsonPrimitive("idRegion").getAsInt();
                        String name=reg.getAsJsonPrimitive("name").getAsString();
                        String nameSpanish = "";
                        String nameEnglish = "";
                        String nameFrench = "";
                        String nameBasque = "";
                        String nameCatalan = "";
                        String nameDutch = "";
                        String nameGalician = "";
                        String nameGerman = "";
                        String nameItalian = "";
                        String namePortuguese = "";
                        if(Locale.getDefault().getLanguage().equals("es")){
                            nameSpanish=name;
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            nameFrench=name;
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            nameBasque=name;
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            nameCatalan=name;
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            nameDutch=name;
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            nameGalician=name;
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            nameGerman=name;
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            nameItalian=name;
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            namePortuguese=name;
                        }else{
                            nameEnglish=name;
                        }
                        regions.add(new Region(idRegion,idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese));
                    }
                    runOnUiThread(() -> {

                        if (regions.size() > 1) {
                            regions.add(0, auxRegList.get(0));
                            pbRegion.setVisibility(View.GONE);
                            regionSpinner.setVisibility(View.VISIBLE);
                            regionSpinnerAux.setVisibility(View.GONE);
                            regionAdapter[0] = new RegionAdapter(RegisterOrganization.this, regions);
                            regionAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                            regionSpinner.setAdapter(regionAdapter[0]);
                        } else {
                            region[0] = regions.get(0);
                            idRegion[0] = -1;
                            pbRegion.setVisibility(View.GONE);
                            regionSpinner.setVisibility(View.GONE);
                            regionSpinnerAux.setVisibility(View.VISIBLE);
                            provinceSpinnerControl();
                        }

                        tilProvince.setVisibility(View.GONE);
                        tilRegion.setVisibility(View.GONE);
                        tilCity.setVisibility(View.GONE);
                    });
                }).start();
            }

            @Override
            public void onError(String errorResponse) {
                runOnUiThread(()->{
                    Intent intent=new Intent(getApplicationContext(), MainMenu.class);
                    setResult(RESULT_CANCELED,intent);
                    finish();
                });
            }
        });

    }

    public void getProvincesByRegion(int idRegion, String idCountry){
        pbProvince.setVisibility(View.VISIBLE);
        ProvincesController.GetProvincesByRegionASync(idRegion, idCountry, new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {

                new Thread(()->{
                    provinces=new ArrayList<>();
                    for(JsonObject reg:data){
                        int idProvince=reg.getAsJsonPrimitive("idProvince").getAsInt();
                        String name=reg.getAsJsonPrimitive("name").getAsString();
                        String nameSpanish = "";
                        String nameEnglish = "";
                        String nameFrench = "";
                        String nameBasque = "";
                        String nameCatalan = "";
                        String nameDutch = "";
                        String nameGalician = "";
                        String nameGerman = "";
                        String nameItalian = "";
                        String namePortuguese = "";
                        if(Locale.getDefault().getLanguage().equals("es")){
                            nameSpanish=name;
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            nameFrench=name;
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            nameBasque=name;
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            nameCatalan=name;
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            nameDutch=name;
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            nameGalician=name;
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            nameGerman=name;
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            nameItalian=name;
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            namePortuguese=name;
                        }else{
                            nameEnglish=name;
                        }
                        provinces.add(new Province(idProvince,idRegion,idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese));
                    }

                    runOnUiThread(()->{
                        if(provinces.size()>1) {
                            provinces.add(0,auxProList.get(0));
                        }
                        provinceAdapter[0] = new ProvinceAdapter(RegisterOrganization.this, provinces);
                        provinceAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        provinceSpinner.setAdapter(provinceAdapter[0]);
                        provinceSpinnerAux.setVisibility(View.GONE);
                        pbProvince.setVisibility(View.GONE);
                        provinceSpinner.setVisibility(View.VISIBLE);
                        if(provinces.size()==1){
                            provinceSpinner.setSelection(0);
                            province[0]=(Province) provinceSpinner.getSelectedItem();
                            idProvince[0]=province[0].getIdProvince();
                            citySpinnerControl();
                        }
                    });
                }).start();

            }

            @Override
            public void onError(String errorResponse) {
                runOnUiThread(()->{
                    Intent intent=new Intent(getApplicationContext(), MainMenu.class);
                    setResult(RESULT_CANCELED,intent);
                    finish();
                });
            }
        });
    }


    public void getCitiesByProvince(int idProvince, int idRegion, String idCountry) {
        pbCity.setVisibility(View.VISIBLE);
        CitiesController.GetCitiesByProvinceASync(idProvince, idRegion, idCountry, new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {
                new Thread(()->{
                    cities = new ArrayList<>();
                    for (JsonObject reg : data) {
                        int idCity = reg.getAsJsonPrimitive("idCity").getAsInt();
                        String name=reg.getAsJsonPrimitive("name").getAsString();
                        String nameSpanish = "";
                        String nameEnglish = "";
                        String nameFrench = "";
                        String nameBasque = "";
                        String nameCatalan = "";
                        String nameDutch = "";
                        String nameGalician = "";
                        String nameGerman = "";
                        String nameItalian = "";
                        String namePortuguese = "";
                        if(Locale.getDefault().getLanguage().equals("es")){
                            nameSpanish=name;
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            nameFrench=name;
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            nameBasque=name;
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            nameCatalan=name;
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            nameDutch=name;
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            nameGalician=name;
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            nameGerman=name;
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            nameItalian=name;
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            namePortuguese=name;
                        }else{
                            nameEnglish=name;
                        }
                        cities.add(new City(idCity, idProvince, idRegion, idCountry, nameSpanish, nameEnglish, nameFrench, nameBasque, nameCatalan, nameDutch, nameGalician, nameGerman, nameItalian, namePortuguese));
                    }
                    runOnUiThread(() -> {

                        if(!cities.isEmpty()){
                            if(cities.size()>1) {
                                cities.add(0,auxCityList.get(0));
                            }
                            cityAdapter[0] = new CityAdapter(RegisterOrganization.this, cities);

                            cityAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                            citySpinner.setAdapter(cityAdapter[0]);
                            citySpinnerAux.setVisibility(View.GONE);
                            pbCity.setVisibility(View.GONE);
                            citySpinner.setVisibility(View.VISIBLE);
                            if(cities.size()==1){
                                citySpinner.setSelection(0);
                                city[0]=(City) citySpinner.getSelectedItem();
                                idCity[0]=city[0].getIdCity();
                            }
                        }
                    });
                }).start();

            }

            @Override
            public void onError(String errorResponse) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    setResult(RESULT_CANCELED,intent);
                    finish();
                });
            }
        });

    }



}