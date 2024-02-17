package gui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import miscClient.FileToolbox;

import com.fundacionmiradas.indicatorsevaluation.R;

import cli.user.Request;
import misc.FieldChecker;
import otea.connection.controller.*;

public class MainActivity extends AppCompatActivity {

    //Connection connection;

    Button sign_in;
    Button sign_up;
    Button request_reg;
    Button complete_reg;

    Button complete_req;

    TextView welcome;

    TextView please_email_req;

    EditText email_req;

    String emailReq;

    Drawable correct;

    ConstraintLayout final_background;

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startCallers();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.miradasLogo);
        Bitmap logoImg= FileToolbox.downloadImg("https://bbmiradas.fundacionmiradas.org/wp-content/uploads/2022/02/fundacion-miradas-1.png");
        logo.setImageBitmap(logoImg);
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        request_reg=findViewById(R.id.request_reg);
        complete_reg=findViewById(R.id.complete_reg);
        complete_req=findViewById(R.id.complete_req);
        welcome=findViewById(R.id.welcome);
        email_req=findViewById(R.id.email_req);
        please_email_req=findViewById(R.id.please_email_req);
        final_background=findViewById(R.id.final_background);
        correct= ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);
        please_email_req.setVisibility(View.GONE);
        request_reg.setVisibility(View.GONE);
        complete_reg.setVisibility(View.GONE);
        complete_req.setVisibility(View.GONE);
        email_req.setVisibility(View.GONE);
        final_background.setVisibility(View.GONE);

        //Button aux=findViewById(R.id.aux);

        sign_in.setOnClickListener(v -> {
            Intent intent = new Intent(this, gui.ui.startSession.StartSession.class);
            startActivity(intent);
        });
        sign_up.setOnClickListener(v -> {
            sign_in.setVisibility(View.GONE);
            sign_up.setVisibility(View.GONE);
            request_reg.setVisibility(View.VISIBLE);
            complete_reg.setVisibility(View.VISIBLE);
        });

        request_reg.setOnClickListener(v->{
            please_email_req.setVisibility(View.VISIBLE);
            request_reg.setVisibility(View.GONE);
            complete_reg.setVisibility(View.GONE);
            complete_req.setVisibility(View.VISIBLE);
            email_req.setVisibility(View.VISIBLE);
            welcome.setVisibility(View.GONE);
        });

        complete_reg.setOnClickListener(v->{

            /*Intent intent = new Intent(this, gui.Register.class);
            startActivity(intent);*/
        });

        complete_req.setOnClickListener(v->{
            if(!emailReq.equals("") && FieldChecker.emailHasCorrectFormat(emailReq)){
                final_background.setVisibility(View.VISIBLE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Request request=new Request(emailReq,-1,"");
                        RequestsController.Create(request);
                        final_background.setVisibility(View.GONE);
                    }
                }, 100);
                //Poner animación de carga como en otras actividades
            }else if(emailReq.equals("")){
                email_req.setError(getString(R.string.mandatory_email));
                email_req.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }else{
                email_req.setError(getString(R.string.wrong_email));
                email_req.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }

        });

        email_req.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailReq=charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!emailReq.equals("") && FieldChecker.emailHasCorrectFormat(emailReq)){
                    email_req.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                }else if(emailReq.equals("")){
                    email_req.setError(getString(R.string.mandatory_email));
                    email_req.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }else{
                    email_req.setError(getString(R.string.wrong_email));
                    email_req.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
            }
        });

        /*aux.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                   }
                               }
        );*/

    }

    public void startCallers(){
        AmbitsController.getInstance();
        SubAmbitsController.getInstance();
        SubSubAmbitsController.getInstance();
        AddressesController.getInstance();
        CentersController.getInstance();
        CitiesController.getInstance();
        CountriesController.getInstance();
        EvaluatorTeamsController.getInstance();
        EvidencesController.getInstance();
        IndicatorsController.getInstance();
        IndicatorsEvaluationRegsController.getInstance();
        IndicatorsEvaluationsController.getInstance();
        OrganizationsController.getInstance();
        ProvincesController.getInstance();
        RegionsController.getInstance();
        RequestsController.getInstance();
        UsersController.getInstance();
        TranslatorController.getInstance();
        FileToolbox.getInstance(getCacheDir(),getContentResolver());
        FileUploader.getInstance();
    }

    @Override
    public void onBackPressed() {
        if(sign_in.getVisibility()==View.GONE){
            if(request_reg.getVisibility()==View.GONE){
                please_email_req.setVisibility(View.GONE);
                welcome.setVisibility(View.VISIBLE);
                complete_req.setVisibility(View.GONE);
                request_reg.setVisibility(View.VISIBLE);
                complete_reg.setVisibility(View.VISIBLE);
            } else {
                sign_in.setVisibility(View.VISIBLE);
                sign_up.setVisibility(View.VISIBLE);
                request_reg.setVisibility(View.GONE);
                complete_reg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream stream=getContentResolver().openInputStream(data.getData());
                String aux=ImageUploader.uploadToBlobStorage(stream,"profile");

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
    }

}