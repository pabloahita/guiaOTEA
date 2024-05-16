package gui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.android.material.textfield.TextInputLayout;

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
import gui.adapters.CityAdapter;
import gui.adapters.CountryAdapter;
import gui.adapters.OrgsAdapter;
import gui.adapters.PhoneCodeAdapter;
import gui.adapters.ProvinceAdapter;
import gui.adapters.RegionAdapter;
import misc.FieldChecker;
import otea.connection.controller.AddressesController;
import otea.connection.controller.CentersController;
import otea.connection.controller.TranslatorController;
import session.FileManager;
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

    boolean checked=false;

    Button imageCenterButton;

    ImageView imageCenter;

    InputStream profilePhotoCenter;

    String imgCenterName="";

    ImageButton helpButton;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        countries= new ArrayList<>();
        countries.add(new Country("-2","País","Country","Pays","Herrialdea","País","Land","País","Land","Paese","País","-",""));
        countries.addAll(Session.getInstance().getCountries());
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_center);

        base=findViewById(R.id.base);
        base.setVisibility(View.VISIBLE);

        tilRegion=findViewById(R.id.tilRegion);
        tilProvince=findViewById(R.id.tilProvince);
        tilCity=findViewById(R.id.tilCity);

        helpButton=findViewById(R.id.helpButton);


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
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
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
                        } catch (IOException e) {
                            throw new RuntimeException(e);
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
                    msg="Para poder registrar un centro de una organización o servicio, debe rellenar los siguientes campos:<ul><li>Para poder añadir una foto de perfil, presione sobre <i>Cambiar foto</i>. Posteriormente se le solicitará si quiere usar la cámara de su dispositivo o por lo contrario obtener alguna desde la galería de imágenes de su dispositivo.</li><li><b><i>Descripción del centro: </i></b>En este campo deberá introducir una descripción breve del centro de su organización o servicio. </li><li><b><i>Dirección: </i></b>En este campo deberá introducir la dirección postal del centro de la organización o servicio. No debe introducir ni el país, ni la región, ni la provincia, ni la ciudad. Por ejemplo:<i>Calle Las Rebolledas, s/n</i> o <i>Calle Valdenúñez, 8</i>. Se permiten también formatos de otros países al ser un campo de texto libre </li><li><b><i>País: </i></b>En este campo deberá seleccionar un país de los que se muestran en el desplegable.</li><li><b><i>Región: </i></b>En este campo, si el país seleccionado es <i>España, Estados Unidos de América, Argentina, Bolivia,  Chile, Colombia, Costa Rica, República Dominicana, Ecuador, El Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguay, Portugal, Perú o Venezuela</i>, este campo será  un desplegable con las diferentes regiones del país seleccionado, por lo contrario deberá de introducir el nombre de la región manualmente. Si el país seleccionado es <i>Andorra,  Cuba, Puerto Rico o Uruguay</i>, no precisa de seleccionar o escribir un nombre de región dada la división territorial de estos países </li><li><b><i>Provincia: </i></b>En este campo, si el país seleccionado es <i>España, Andorra, Estados Unidos de América, Argentina, Bolivia,  Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador, El Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguay, Portugal, Perú, Puerto Rico, Uruguay o Venezuela</i>, este campo será un desplegable con las diferentes provincias de la región (si procede) o país seleccionado, por lo contrario deberá de introducir el nombre de la provincia manualmente. </li><li><b><i>Ciudad: </i></b>En este campo, si el país seleccionado es <i>España, Andorra, Estados Unidos de América, Argentina, Bolivia,  Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador, El Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguay, Portugal, Perú, Puerto Rico, Uruguay o Venezuela</i>, este campo será un desplegable con las diferentes ciudades correspondientes a la provincia seleccionada, por lo contrario deberá de introducir el nombre de la ciudad manualmente. </li><li><b><i>Número de teléfono:  </i></b>En este campo deberá de introducir el número de teléfono del centro de la organización o del servicio. A la izquierda de dicho campo, puede seleccionar el prefijo telefónico correspondiente al país de su número de teléfono, aunque también se seleccione automáticamente al cambiar el valor del desplegable de países.</li><li><b><i>Email:  </i></b>En este campo deberá introducir el correo electrónico del centro de la organización o del servicio.</li></ul><b>No olvide aceptar que <i>Fundación Miradas</i> guarde registro sobre sus datos de acuerdo con la Ley Orgánica de Protección de Datos.</b>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    msg="Afin d'enregistrer un centre d'un organisme ou d'un service, vous devez remplir les champs suivants:<ul><li>Pour pouvoir ajouter une photo de profil, cliquez sur <i>Changer de photo</i>. Plus tard, il vous sera demandé si vous souhaitez utiliser l'appareil photo de votre appareil ou en obtenir un à partir de la galerie d'images de votre appareil </li><li><b><i>Description du centre: </i></ b>Dans. dans ce champ, vous devez saisir une brève description du centre de votre organisation ou service. </li><li><b><i>Adresse: </i></b>Dans ce champ, vous devez saisir l'adresse postale du centre de l'organisme ou du service. Vous ne devez pas indiquer le pays, la région, la province ou la ville. Par exemple:<i>Calle Las Rebolledas, s/n</i> ou <i>Calle Valdenúñez, 8</i>. Les formats d'autres pays sont également autorisés car il s'agit d'un champ de texte libre </li><li><b><i>Pays: </i></b>Dans ce champ, vous devez sélectionner un pays parmi ceux affichés dans le champ </li><li><b><i>Région: </i></b>Dans ce champ, si le pays sélectionné est <i>Espagne, États-Unis d'Amérique, Argentine, Bolivie, Chili, Colombie, Costa Rica, République Dominicaine, Équateur, El Salvador, Guatemala, Honduras, Mexique, Nicaragua, Panama, Paraguay, Portugal, Pérou ou Venezuela</i>, ce champ sera une liste déroulante avec les différentes régions du pays sélectionné, sinon vous devrez saisir le nom de la région manuellement. Si le pays sélectionné est <i>Andorre, Cuba, Porto Rico ou l'Uruguay</i>, vous n'avez pas besoin de sélectionner ou d'écrire un nom de région étant donné la division territoriale de ces pays </li><li><b>< i >Province: </i></b>Dans ce champ, si le pays sélectionné est <i>Espagne, Andorre, États-Unis d'Amérique, Argentine, Bolivie, Chili, Colombie, Costa Rica, Cuba, République Dominicaine, Équateur , El Salvador, Guatemala, Honduras, Mexique, Nicaragua, Panama, Paraguay, Portugal, Pérou, Porto Rico, Uruguay ou Venezuela</i>, ce champ sera une liste déroulante avec les différentes provinces de la région (le cas échéant ) ou le pays sélectionné. Sinon, vous devez saisir manuellement le nom de la province. </li><li><b><i>Ville: </i></b>Dans ce champ, si le pays sélectionné est <i>Espagne, Andorre, États-Unis d'Amérique, Argentine, Bolivie, Chili, Colombie, Costa Rica, Cuba, République dominicaine, Équateur, El Salvador, Guatemala, Honduras, Mexique, Nicaragua, Panama, Paraguay, Portugal, Pérou, Porto Rico, Uruguay ou Venezuela</i>, ce champ sera une liste déroulante liste avec les différentes villes correspondant à la province sélectionnée, sinon vous devez saisir le nom de la ville manuellement. </li><li><b><i>Numéro de téléphone: </i></b>Dans ce champ, vous devez saisir le numéro de téléphone de l'organisation ou du centre de service. À gauche de ce champ, vous pouvez sélectionner le préfixe téléphonique correspondant au pays de votre numéro de téléphone, bien qu'il soit également sélectionné automatiquement lors de la modification de la valeur de la liste déroulante pays.</li><li><b> <i>E-mail: </i></b>Dans ce champ, vous devez saisir l'adresse e-mail de l'organisation ou du centre de service </li></ul><b>N'oubliez pas d'accepter cette <i>Fundación Miradas</i> enregistre l'enregistrement de vos données conformément à la loi organique sur la protection des données.</b>";
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    msg="Erakunde edo zerbitzu bateko zentro bat erregistratzeko, eremu hauek bete behar dituzu:<ul><li>Profileko argazki bat gehitu ahal izateko, egin klik <i>Aldatu argazkia</i> aukeran. Geroago, zure gailuko kamera erabili nahi duzun edo bestela zure gailuko irudi-galeriatik lortu nahi duzun galdetuko zaizu </li><li><b><i>Zentroaren deskribapena: </i></b>In eremu honetan zure erakundearen edo zerbitzuaren zentroaren deskribapen laburra sartu behar duzu. </li><li><b><i>Helbidea: </i></b>Eremu honetan erakundearen edo zerbitzuaren zentroaren posta helbidea idatzi behar duzu. Ez zenuke herrialdean, eskualdean, probintzian edo hirian sartu behar. Adibidez:<i>Calle Las Rebolledas, s/n</i> edo <i>Calle Valdenúñez, 8</i>. Beste herrialde batzuetako formatuak ere onartzen dira testu libreko eremua denez </li><li><b><i>Herrialdea: </i></b>Eremu honetan herrialde bat aukeratu behar duzu herrialdean agertzen direnetatik. goitibehitza </li><li><b><i>Eskualdea: </i></b>Eremu honetan, hautatutako herrialdea <i>Espainia, Amerikako Estatu Batuak, Argentina, Bolivia, Txile , Kolonbia, Costa Rica, Dominikar Errepublika, Ekuador, El Salvador, Guatemala, Honduras, Mexiko, Nikaragua, Panama, Paraguai, Portugal, Peru edo Venezuela</i>, eremu hau goitibeherako zerrenda bat izango da eskualde ezberdinekin. hautatutako herrialdea, Bestela eskualdearen izena eskuz sartu beharko duzu. Hautatutako herrialdea <i>Andorra, Kuba, Puerto Rico edo Uruguai bada</i> bada, ez duzu eskualde-izenik hautatu edo idatzi behar herrialde horien lurralde-zatiketa kontuan hartuta </li><li><b>< i >Probintzia: </i></b>Eremu honetan, aukeratutako herrialdea <i>Espainia, Andorra, Amerikako Estatu Batuak, Argentina, Bolivia, Txile, Kolonbia, Costa Rica, Kuba, Dominikar Errepublika, Ekuador bada. , El Salvador, Guatemala, Honduras, Mexiko, Nikaragua, Panama, Paraguai, Portugal, Peru, Puerto Rico, Uruguai edo Venezuela</i>, eremu hau goitibeherako zerrenda bat izango da eskualdeko probintzia ezberdinekin (hala badagokio ) edo hautatutako herrialdea, Bestela, eskuz idatzi behar duzu probintziaren izena. </li><li><b><i>Hiria: </i></b>Eremu honetan, aukeratutako herrialdea <i>Espainia, Andorra, Amerikako Estatu Batuak, Argentina, Bolivia, Txile bada, Kolonbia, Costa Rica, Kuba, Dominikar Errepublika, Ekuador, El Salvador, Guatemala, Honduras, Mexiko, Nikaragua, Panama, Paraguai, Portugal, Peru, Puerto Rico, Uruguai edo Venezuela</i>, eremu hau goitibeherakoa izango da. zerrenda hautatutako probintziari dagozkion hiri ezberdinekin, bestela eskuz sartu behar duzu hiriaren izena. </li><li><b><i>Telefono zenbakia: </i></b>Eremu honetan erakundearen edo zerbitzu zentroaren telefono zenbakia sartu behar duzu. Eremu honen ezkerraldean, zure telefono-zenbakiaren herrialdeari dagokion telefono-aurrizkia hauta dezakezu, nahiz eta automatikoki hautatzen den herrialdeen goitibeherako zerrendaren balioa aldatzean.</li><li><b> <i>E-posta: </i></b>Eremu honetan erakundearen edo zerbitzu-zentroaren helbide elektronikoa sartu behar duzu </li></ul><b>Ez ahaztu <i>Fundación hori onartzea Miradas</i>ek zure datuen erregistroa gorde, Datuak Babesteko Lege Organikoaren arabera.</b>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    msg="Per poder registrar un centre d'una organització o servei, heu d'emplenar els camps següents:<ul><li>Per poder afegir una fotografia de perfil, premeu sobre <i>Canviar foto</i>. Posteriorment se us demanarà si voleu utilitzar la càmera del dispositiu o en cas contrari obtenir-ne des de la galeria d'imatges del dispositiu.</li><li><b><i>Descripció del centre: </i></ b>En aquest camp haureu d'introduir una descripció breu del centre de la vostra organització o servei. </li><li><b><i>Adreça: </i></b>En aquest camp heu d'introduir l'adreça postal del centre de l'organització o servei. No heu d'introduir ni el país, ni la regió, ni la província, ni la ciutat. Per exemple:<i>Carrer Les Rebolledas, s/n</i> o <i>Carrer Valdenúñez, 8</i>. Es permeten també formats d'altres països en ser un camp de text lliure </li><li><b><i>País: </i></b>En aquest camp haureu de seleccionar un país dels que es mostren a el desplegable.</li><li><b><i>Regió: </i></b>En aquest camp, si el país seleccionat és <i>Espanya, Estats Units d'Amèrica, Argentina, Bolívia, Xile , Colòmbia, Costa Rica, República Dominicana, Equador, El Salvador, Guatemala, Hondures, Mèxic, Nicaragua, Panamà, Paraguai, Portugal, Perú o Veneçuela</i>, aquest camp serà un desplegable amb les diferents regions del país seleccionat, per el contrari haurà dintroduir el nom de la regió manualment. Si el país seleccionat és <i>Andorra, Cuba, Puerto Rico o Uruguai</i>, no cal seleccionar o escriure un nom de regió atesa la divisió territorial d'aquests països </li><li><b><i >Província: </i></b>En aquest camp, si el país seleccionat és <i>Espanya, Andorra, Estats Units d'Amèrica, Argentina, Bolívia, Xile, Colòmbia, Costa Rica, Cuba, República Dominicana, Equador, El Salvador, Guatemala, Hondures, Mèxic, Nicaragua, Panamà, Paraguai, Portugal, Perú, Puerto Rico, Uruguai o Veneçuela</i>, aquest camp serà un desplegable amb les diferents províncies de la regió (si escau) o país seleccionat, altrament haurà d'introduir el nom de la província manualment. </li><li><b><i>Ciutat: </i></b>En aquest camp, si el país seleccionat és <i>Espanya, Andorra, Estats Units d'Amèrica, Argentina, Bolívia, Xile, Colòmbia, Costa Rica, Cuba, República Dominicana, Equador, El Salvador, Guatemala, Hondures, Mèxic, Nicaragua, Panamà, Paraguai, Portugal, Perú, Puerto Rico, Uruguai o Veneçuela</i>, aquest camp serà un desplegable amb les diferents ciutats corresponents a la província seleccionada, altrament haurà d'introduir el nom de la ciutat manualment. </li><li><b><i>Nombre de telèfon: </i></b>En aquest camp haureu d'introduir el número de telèfon del centre de l'organització o del servei. A l'esquerra d'aquest camp, podeu seleccionar el prefix telefònic corresponent al país del vostre número de telèfon, encara que també se seleccioneu automàticament en canviar el valor del desplegable de països.</li><li><b><i>Email: </i></b>En aquest camp haureu d'introduir el correu electrònic del centre de l'organització o del servei.</li></ul><b>No oblideu acceptar que <i>Fundació Mirades</i> deseu registre sobre les vostres dades d'acord amb la Llei Orgànica de Protecció de Dades.</b>";
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    msg="Om een centrum van een organisatie of dienst te registreren, moet u de volgende velden invullen:<ul><li>Om een profielfoto toe te voegen, klikt u op <i>Foto wijzigen</i>. Later wordt u gevraagd of u de camera van uw apparaat wilt hebben of er anderszins een wilt aanschaffen uit de afbeeldingengalerij van uw apparaat. </li><li><b><i>Beschrijving van het centrum: </i></ b >In dit veld moet u een korte beschrijving geven van het centrum van uw organisatie of dienst. </li><li><b><i>Adres: </i></b>In dit veld moet u het postadres van het centrum van de organisatie of dienst invoeren. U mag het land, de regio, de provincie of de stad niet betreden. Bijvoorbeeld:<i>Calle Las Rebolledas, s/n</i> of <i>Calle Valdenúñez, 8</i>. Ze staan ook formaten uit andere landen toe, omdat het een vrij tekstveld is. </li><li><b><i>Land: </i></b>In dit veld moet u een land selecteren uit de landen die in het veld worden weergegeven. vervolgkeuzelijst.</li><li><b><i>Regio: </i></b>In dit veld, als het geselecteerde land <i>Spanje, Verenigde Staten van Amerika, Argentinië, Bolivia, Chili, Colombia, Costa Rica, Dominicaanse Republiek, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru of Venezuela</i>, dit is een moeilijke situatie in de verschillende geselecteerde regio's van het land . en anders moet u de regionaam handmatig invoeren. Als u uw bestemming in Andorra, Cuba, Puerto Rico of Uruguay</i> kiest, is het niet nodig om de bestemming van uw land te selecteren </li><li><b><i >Provincie: </i>< /b >In dit veld, als het geselecteerde land <i>Spanje, Andorra, Verenigde Staten van Amerika, Argentinië, Bolivia, Chili, Colombia, Costa Rica, Cuba, Dominicaanse Republiek, Ecuador, El Salvador, Guatemala, Honduras, Mexico is , Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay of Venezuela</i>, dit kamp staat open voor de verschillende provincies van de regio (deze procedure) is geselecteerd, anders moet u de naam van de provincie handmatig invoeren . </li><li><b><i>Stad: </i></b>In dit veld, als het geselecteerde land <i>Spanje, Andorra, Verenigde Staten van Amerika, Argentinië, Bolivia, Chili, Steden in Colombia, Costa Rica, Cuba, Dominicaanse Republiek, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay of Venezuela die overeenkomen met de geselecteerde provincie, anders moet u de stad invoeren handmatig benoemen. </li><li><b><i>Telefoonnummer: </i></b>En dit is de introductie van het telefoonnummer van de organisatie en het servicecentrum. Ondertussen kunt u uw voorkeurstelefoonnummer selecteren op basis van uw telefoonnummer, evenals automatische telefoonnummerselectie.</li><li><b><i>E-mail: </i></b>Op In dit veld moet het e-mailadres van de organisatie of het servicecentrum worden ingevuld. </li></ul><b>Vergeet niet te accepteren dat <i>Fundación Miradas</i> uw gegevens bijhoudt in overeenstemming met het Organisch Reglement. Wet op gegevensbescherming.</b>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    msg="Para rexistrar un centro dunha organización ou servizo, debes cubrir os seguintes campos:<ul><li>Para poder engadir unha foto de perfil, fai clic en <i>Cambiar foto</i>. Máis tarde preguntaráselle se queres utilizar a cámara do teu dispositivo ou obter unha da galería de imaxes do teu dispositivo </li><li><b><i>Descrición do centro: </i></b>In neste campo debes introducir unha breve descrición do centro da túa organización ou servizo. </li><li><b><i>Enderezo: </i></b>Neste campo debes introducir o enderezo postal do centro da organización ou servizo. Non debes entrar no país, na rexión, na provincia ou na cidade. Por exemplo:<i>Calle Las Rebolledas, s/n</i> ou <i>Calle Valdenúñez, 8</i>. Tamén se permiten formatos doutros países xa que é un campo de texto libre </li><li><b><i>País: </i></b>Neste campo debes seleccionar un país entre os que aparecen no menú despregable </li><li><b><i>Rexión: </i></b>Neste campo, se o país seleccionado é <i>España, Estados Unidos de América, Arxentina, Bolivia, Chile , Colombia, Costa Rica, República Dominicana, Ecuador, O Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguai, Portugal, Perú ou Venezuela</i>, este campo será unha lista despregable coas diferentes rexións do país. país seleccionado, para En caso contrario, terá que introducir o nome da rexión manualmente. Se o país seleccionado é <i>Andorra, Cuba, Porto Rico ou Uruguai</i>, non é necesario seleccionar nin escribir un nome de rexión dada a división territorial destes países </li><li><b>< i >Provincia: </i></b>Neste campo, se o país seleccionado é <i>España, Andorra, Estados Unidos de América, Arxentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador , El Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguai, Portugal, Perú, Porto Rico, Uruguai ou Venezuela</i>, este campo será unha lista despregable coas distintas provincias da rexión (se procede ) ou país seleccionado, En caso contrario, debe introducir o nome da provincia manualmente. </li><li><b><i>Cidade: </i></b>Neste campo, se o país seleccionado é <i>España, Andorra, Estados Unidos de América, Arxentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, República Dominicana, Ecuador, O Salvador, Guatemala, Honduras, México, Nicaragua, Panamá, Paraguai, Portugal, Perú, Porto Rico, Uruguai ou Venezuela</i>, este campo será un menú despregable lista coas distintas cidades correspondentes á provincia seleccionada, se non, deberá introducir o nome da cidade manualmente. </li><li><b><i>Número de teléfono: </i></b>Neste campo debes introducir o número de teléfono da organización ou centro de servizos. Á esquerda deste campo, pode seleccionar o prefixo de teléfono correspondente ao país do seu número de teléfono, aínda que tamén se selecciona automaticamente ao cambiar o valor da lista despregable de países.</li><li><b> <i>Correo electrónico: </i></b>Neste campo debes introducir o enderezo de correo electrónico da organización ou centro de servizos </li></ul><b>Non esquezas aceptar esa <i>Fundación Miradas</i> garda o rexistro dos teus datos de acordo coa Lei Orgánica de Protección de Datos.</b>";
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    msg="Um ein Zentrum einer Organisation oder Dienstleistung zu registrieren, müssen Sie die folgenden Felder ausfüllen:<ul><li>Um ein Profilfoto hinzufügen zu können, klicken Sie auf <i>Foto ändern</i>. Später werden Sie gefragt, ob Sie die Kamera Ihres Geräts verwenden oder eine andere aus der Bildergalerie Ihres Geräts beziehen möchten. </li><li><b><i>Beschreibung des Zentrums: </i></ b>In In dieses Feld müssen Sie eine kurze Beschreibung des Zentrums Ihrer Organisation oder Dienstleistung eingeben. </li><li><b><i>Adresse: </i></b>In diesem Feld müssen Sie die Postanschrift des Zentrums der Organisation oder Dienstleistung eingeben. Sie sollten nicht das Land, die Region, die Provinz oder die Stadt eingeben. Zum Beispiel:<i>Calle Las Rebolledas, s/n</i> oder <i>Calle Valdenúñez, 8</i>. Formate aus anderen Ländern sind ebenfalls zulässig, da es sich um ein Freitextfeld handelt. </li><li><b><i>Land:</i></b>In diesem Feld müssen Sie ein Land aus den angezeigten Ländern auswählen Dropdown-Liste. </li><li><b><i>Region: </i></b>Wenn das ausgewählte Land <i>Spanien, Vereinigte Staaten von Amerika, Argentinien, Bolivien, Chile ist, Kolumbien, Costa Rica, Dominikanische Republik, Ecuador, El Salvador, Guatemala, Honduras, Mexiko, Nicaragua, Panama, Paraguay, Portugal, Peru oder Venezuela</i>, dieses Feld ist eine Dropdown-Liste mit den verschiedenen Regionen der ausgewähltes Land, andernfalls müssen Sie den Regionsnamen manuell eingeben. Wenn das ausgewählte Land <i>Andorra, Kuba, Puerto Rico oder Uruguay</i> ist, müssen Sie angesichts der territorialen Aufteilung dieser Länder keinen Regionsnamen auswählen oder eingeben </li><li><b>< i >Provinz: </i></b>In diesem Feld, wenn das ausgewählte Land <i>Spanien, Andorra, Vereinigte Staaten von Amerika, Argentinien, Bolivien, Chile, Kolumbien, Costa Rica, Kuba, Dominikanische Republik, Ecuador ist , El Salvador, Guatemala, Honduras, Mexiko, Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay oder Venezuela</i>, dieses Feld ist eine Dropdown-Liste mit den verschiedenen Provinzen der Region (falls zutreffend). ) oder ausgewähltes Land, andernfalls müssen Sie den Namen der Provinz manuell eingeben. </li><li><b><i>Stadt: </i></b>Wenn in diesem Feld das ausgewählte Land <i>Spanien, Andorra, Vereinigte Staaten von Amerika, Argentinien, Bolivien, Chile, Kolumbien, Costa Rica, Kuba, Dominikanische Republik, Ecuador, El Salvador, Guatemala, Honduras, Mexiko, Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay oder Venezuela</i>, dieses Feld ist ein Dropdown-Feld Liste mit den verschiedenen Städten, die der ausgewählten Provinz entsprechen, andernfalls müssen Sie den Stadtnamen manuell eingeben. </li><li><b><i>Telefonnummer: </i></b>In diesem Feld müssen Sie die Telefonnummer der Organisation oder des Servicecenters eingeben. Links von diesem Feld können Sie die Telefonvorwahl auswählen, die dem Land Ihrer Telefonnummer entspricht. Diese wird jedoch auch automatisch ausgewählt, wenn Sie den Wert der Länder-Dropdown-Liste ändern.</li><li><b> <i>E-Mail: </i></b>In diesem Feld müssen Sie die E-Mail-Adresse der Organisation oder des Servicecenters eingeben. </li></ul><b>Vergessen Sie nicht, diese <i>Fundación Miradas</i> zu akzeptieren speichert die Registrierung Ihrer Daten gemäß dem Datenschutzgesetz.</b>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    msg="Per registrare un centro di un'organizzazione o di un servizio, è necessario compilare i seguenti campi:<ul><li>Per poter aggiungere una foto del profilo, fare clic su <i>Cambia foto</i>. Successivamente ti verrà chiesto se desideri utilizzare la fotocamera del tuo dispositivo o altrimenti procurartene una dalla galleria immagini del tuo dispositivo </li><li><b><i>Descrizione del centro: </i></ b>In in questo campo devi inserire una breve descrizione del centro della tua organizzazione o servizio. </li><li><b><i>Indirizzo: </i></b>In questo campo è necessario inserire l'indirizzo postale del centro dell'organizzazione o del servizio. Non dovresti inserire il paese, la regione, la provincia o la città. Ad esempio:<i>Calle Las Rebolledas, s/n</i> o <i>Calle Valdenúñez, 8</i>. Sono ammessi anche formati provenienti da altri paesi in quanto è un campo di testo libero </li><li><b><i>Paese: </i></b>In questo campo è necessario selezionare un paese tra quelli mostrati nel riquadro menu a discesa </li><li><b><i>Regione: </i></b>in questo campo, se il paese selezionato è <i>Spagna, Stati Uniti d'America, Argentina, Bolivia, Cile , Colombia, Costa Rica, Repubblica Dominicana, Ecuador, El Salvador, Guatemala, Honduras, Messico, Nicaragua, Panama, Paraguay, Portogallo, Perù o Venezuela</i>, questo campo sarà un elenco a discesa con le diverse regioni del paese selezionato, altrimenti dovrai inserire manualmente il nome della regione. Se il Paese selezionato è <i>Andorra, Cuba, Porto Rico o Uruguay</i>, non è necessario selezionare o scrivere il nome di una regione data la divisione territoriale di questi Paesi </li><li><b>< i >Provincia: </i></b>In questo campo, se il paese selezionato è <i>Spagna, Andorra, Stati Uniti d'America, Argentina, Bolivia, Cile, Colombia, Costa Rica, Cuba, Repubblica Dominicana, Ecuador , El Salvador, Guatemala, Honduras, Messico, Nicaragua, Panama, Paraguay, Portogallo, Perù, Porto Rico, Uruguay o Venezuela</i>, questo campo sarà un elenco a discesa con le diverse province della regione (se applicabile ) o il paese selezionato, altrimenti è necessario inserire manualmente il nome della provincia. </li><li><b><i>Città: </i></b>In questo campo, se il paese selezionato è <i>Spagna, Andorra, Stati Uniti d'America, Argentina, Bolivia, Cile, Colombia, Costa Rica, Cuba, Repubblica Dominicana, Ecuador, El Salvador, Guatemala, Honduras, Messico, Nicaragua, Panama, Paraguay, Portogallo, Perù, Porto Rico, Uruguay o Venezuela</i>, questo campo sarà un menu a discesa elenco con le diverse città corrispondenti alla provincia selezionata, altrimenti è necessario inserire manualmente il nome della città. </li><li><b><i>Numero di telefono: </i></b>In questo campo è necessario inserire il numero di telefono dell'organizzazione o del centro servizi. A sinistra di questo campo puoi selezionare il prefisso telefonico corrispondente al paese del tuo numero di telefono, sebbene venga selezionato anche automaticamente quando si modifica il valore dell'elenco a discesa del paese.</li><li><b> <i>E-mail: </i></b>In questo campo è necessario inserire l'indirizzo e-mail dell'organizzazione o del centro servizi </li></ul><b>Non dimenticare di accettare che <i>Fundación Miradas</i> salva la registrazione dei tuoi dati in conformità con la Legge organica sulla protezione dei dati.</b>";
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    msg="Para cadastrar um centro de uma organização ou serviço, você deve preencher os seguintes campos:<ul><li>Para poder adicionar uma foto de perfil, clique em <i>Alterar foto</i>. Mais tarde, você será perguntado se deseja usar a câmera do seu dispositivo ou obter uma da galeria de imagens do seu dispositivo </li><li><b><i>Descrição do centro: </i></ b>Em. neste campo você deve inserir uma breve descrição do centro de sua organização ou serviço. </li><li><b><i>Endereço: </i></b>Neste campo você deve inserir o endereço postal do centro da organização ou serviço. Você não deve entrar no país, na região, na província ou na cidade. Por exemplo:<i>Calle Las Rebolledas, s/n</i> ou <i>Calle Valdenúñez, 8</i>. Formatos de outros países também são permitidos por se tratar de um campo de texto livre </li><li><b><i>País: </i></b>Neste campo você deve selecionar um país dentre aqueles mostrados na caixa menu suspenso </li><li><b><i>Região: </i></b>Neste campo, se o país selecionado for <i>Espanha, Estados Unidos da América, Argentina, Bolívia, Chile, Colômbia, Costa Rica, República Dominicana, Equador, El Salvador, Guatemala, Honduras, México, Nicarágua, Panamá, Paraguai, Portugal, Peru ou Venezuela</i>, este campo será uma lista suspensa com as diferentes regiões do país selecionado, caso contrário você terá que inserir o nome da região manualmente. Se o país selecionado for <i>Andorra, Cuba, Porto Rico ou Uruguai</i>, não será necessário selecionar ou escrever um nome de região dada a divisão territorial desses países </li><li><b>< i >Província: </i></b>Neste campo, se o país selecionado for <i>Espanha, Andorra, Estados Unidos da América, Argentina, Bolívia, Chile, Colômbia, Costa Rica, Cuba, República Dominicana, Equador , El Salvador, Guatemala, Honduras, México, Nicarágua, Panamá, Paraguai, Portugal, Peru, Porto Rico, Uruguai ou Venezuela</i>, este campo será uma lista suspensa com as diferentes províncias da região (se aplicável ) ou país selecionado, caso contrário, você deverá inserir o nome da província manualmente. </li><li><b><i>Cidade: </i></b>Neste campo, se o país selecionado for <i>Espanha, Andorra, Estados Unidos da América, Argentina, Bolívia, Chile, Colômbia, Costa Rica, Cuba, República Dominicana, Equador, El Salvador, Guatemala, Honduras, México, Nicarágua, Panamá, Paraguai, Portugal, Peru, Porto Rico, Uruguai ou Venezuela</i>, este campo será um menu suspenso lista com as diferentes cidades correspondentes à província selecionada, caso contrário deverá inserir o nome da cidade manualmente. </li><li><b><i>Número de telefone: </i></b>Neste campo você deve inserir o número de telefone da organização ou centro de serviço. À esquerda deste campo, você pode selecionar o prefixo telefônico correspondente ao país do seu número de telefone, embora também seja selecionado automaticamente ao alterar o valor da lista suspensa de países.</li><li><b> <i>E-mail: </i></b>Neste campo você deve inserir o endereço de e-mail da organização ou centro de serviço </li></ul><b>Não se esqueça de aceitar essa <i>Fundación Miradas</i> guarda o registo dos seus dados de acordo com a Lei Orgânica de Protecção de Dados.</b>";
                }else{
                    msg="In order to register a center of an organization or service, you must fill out the following fields:<ul><li>To be able to add a profile photo, click on <i>Change photo</i>. Later you will be asked if you want to use your device's camera or otherwise obtain one from your device's image gallery. </li><li><b><i>Description of the center: </i></ b>In this field you must enter a brief description of the center of your organization or service. </li><li><b><i>Address: </i></b>In this field you must enter the postal address of the center of the organization or service. You should not enter the country, the region, the province, or the city. For example:<i>Calle Las Rebolledas, s/n</i> or <i>Calle Valdenúñez, 8</i>. Formats from other countries are also allowed as it is a free text field </li><li><b><i>Country: </i></b>In this field you must select a country from those shown in the dropdown. </li><li><b><i>Region: </i></b>In this field, if the selected country is <i>Spain, United States of America, Argentina, Bolivia, Chile , Colombia, Costa Rica, Dominican Republic, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru or Venezuela</i>, this field will be a drop-down list with the different regions of the selected country, for Otherwise you will have to enter the region name manually. If the selected country is <i>Andorra, Cuba, Puerto Rico or Uruguay</i>, you do not need to select or write a region name given the territorial division of these countries </li><li><b><i >Province: </i></b>In this field, if the selected country is <i>Spain, Andorra, United States of America, Argentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, Dominican Republic, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay or Venezuela</i>, this field will be a drop-down list with the different provinces of the region (if applicable) or selected country, Otherwise, you must enter the name of the province manually. </li><li><b><i>City: </i></b>In this field, if the selected country is <i>Spain, Andorra, United States of America, Argentina, Bolivia, Chile, Colombia, Costa Rica, Cuba, Dominican Republic, Ecuador, El Salvador, Guatemala, Honduras, Mexico, Nicaragua, Panama, Paraguay, Portugal, Peru, Puerto Rico, Uruguay or Venezuela</i>, this field will be a drop-down list with the different cities corresponding to the selected province, otherwise you must enter the city name manually. </li><li><b><i>Phone number: </i></b>In this field you must enter the telephone number of the organization or service center. To the left of this field, you can select the telephone prefix corresponding to the country of your telephone number, although it is also selected automatically when changing the value of the country drop-down list.</li><li><b><i>Email: </i></b>In this field you must enter the email address of the organization or service center. </li></ul><b>Do not forget to accept that <i>Fundación Miradas</i> save registration of your data in accordance with the Organic Law on Data Protection.</b>";
                }
                new android.app.AlertDialog.Builder(RegisterNewCenter.this)
                        .setTitle(getString(R.string.help))
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

                base.setVisibility(View.GONE);

                background.setVisibility(View.VISIBLE);

                if(!fields.get("centerDescription").isEmpty() && !fields.get("address").isEmpty() && !idCountry[0].equals("-2") && !fields.get("nameProvince").isEmpty() && !fields.get("nameRegion").isEmpty() && !fields.get("nameCity").isEmpty()
                        && FieldChecker.isACorrectPhone(fields.get("telephoneCode")+fields.get("telephone")) && checked){

                    Organization org=Session.getInstance().getOrganization();
                    int numCenters= CentersController.GetAllByOrganization(org).size();
                    int idCenter=numCenters+1;
                    int numAddresses= AddressesController.GetAll().size();
                    int idOrganization=org.getIdOrganization();
                    String orgType=org.getOrgType();
                    String illness=org.getIllness();

                    if(profilePhotoCenter!=null){
                        imgCenterName="CENTER_"+idCenter+"_"+idOrganization+"_"+orgType+"_"+illness+".jpg";
                        FileManager.uploadFile(profilePhotoCenter, "profile-photos", imgCenterName);
                        try{
                            profilePhotoCenter.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }



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


                    Center center=new Center(idOrganization,orgType,illness, numCenters+1,descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese,address.getIdAddress(),fields.get("telephoneCode")+" "+fields.get("telephone"),fields.get("email"),imgCenterName);
                    CentersController.Create(center);

                    Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
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
                    new android.app.AlertDialog.Builder(RegisterNewCenter.this)
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
            intent.putExtra("userEmail",getIntent().getSerializableExtra("userEmail"));

            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
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

                tilProvince.setVisibility(View.GONE);
                tilRegion.setVisibility(View.GONE);
                tilCity.setVisibility(View.GONE);
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