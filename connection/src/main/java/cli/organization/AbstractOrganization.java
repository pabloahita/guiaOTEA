package cli.organization;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.sql.Connection;

import cli.indicators.Evidence;
import cli.organization.data.Address;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.AddressesApi;
import otea.connection.ConnectionClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class AbstractOrganization implements Organization {

    @SerializedName("idOrganization")
    private int idOrganization;
    @SerializedName("orgType")
    private String orgType;
    @SerializedName("illness")
    private String illness;

    @SerializedName("nameOrg")
    private String name;

    @SerializedName("idAddress")
    private int idAddress;
    private Address address;

    @SerializedName("telephone")
    private int telephone;

    @SerializedName("email")
    private String email;

    @SerializedName("information")
    private String information;

    @SerializedName("emailOrgPrincipal")
    private String emailOrgPrincipal;

    @SerializedName("emailOrgConsultant")
    private String emailOrgConsultant;


    public AbstractOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, int telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant){
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setName(name);
        setIdAddress(idAddress);
        setTelephone(telephone);
        setEmail(email);
        setInformation(information);
        setEmailOrgPrincipal(emailOrgPrincipal);
        setEmailOrgConsultant(emailOrgPrincipal);
        try {
            obtainAddress();
        }catch(IOException e){}
    }

    private void setEmailOrgConsultant(String emailOrgPrincipal) {this.emailOrgPrincipal=emailOrgPrincipal;}

    private void setEmailOrgPrincipal(String emailOrgPrincipal) {this.emailOrgConsultant=emailOrgConsultant;}

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    @Override
    public String getOrganizationType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public int getTelephone() {
        return telephone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getInformation() {
        return information;
    }


    public void setName(String name) {
        this.name=name;
    }

    public Address obtainAddress() throws IOException {
        ConnectionClient cli=new ConnectionClient();
        Retrofit retrofit=cli.getRetrofit();
        AddressesApi api=retrofit.create(AddressesApi.class);
        Call<Address> call=api.Get(idAddress);
        Address[] aux=new Address[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Address> response = call.execute();
                        if (response.isSuccessful()) {
                            aux[0]=response.body();
                            return aux[0];
                        } else {
                            throw new IOException("Error: " + response.code() + " " + response.message());
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(address-> {
                    System.out.println("Address correctly obtained");
                }, error -> {
                    System.out.println(error.toString());
                });
        return aux[0];
    }

    public void setAddress(Address address) {this.address=address;}

    public int getIdAddress(){return idAddress;}
    public void setIdAddress(int idAddress) {this.idAddress=idAddress;}

    public void setTelephone(int telephone) {
        this.telephone=telephone;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setInformation(String information) {
        this.information=information;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }


}
