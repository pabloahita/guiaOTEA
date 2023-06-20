package cli.organization;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

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
    private long telephone;

    @SerializedName("email")
    private String email;

    @SerializedName("information")
    private String information;

    @SerializedName("emailOrgPrincipal")
    private String emailOrgPrincipal;

    @SerializedName("emailOrgConsultant")
    private String emailOrgConsultant;


    public AbstractOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant){
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


    @Override
    public void setEmailOrgConsultant(String emailOrgPrincipal) {this.emailOrgPrincipal=emailOrgPrincipal;}

    @Override
    public void setEmailOrgPrincipal(String emailOrgPrincipal) {this.emailOrgConsultant=emailOrgConsultant;}

    @Override
    public int getIdOrganization() {
        return idOrganization;
    }

    @Override
    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    @Override
    public String getOrganizationType() {
        return orgType;
    }

    @Override
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
    public long getTelephone() {
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


    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public void obtainAddress() throws IOException {
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
        setAddress(aux[0]);
    }

    public void setAddress(Address address) {this.address=address;}

    @Override
    public int getIdAddress(){return idAddress;}
    @Override
    public void setIdAddress(int idAddress) {this.idAddress=idAddress;}

    @Override
    public void setTelephone(long telephone) {
        this.telephone=telephone;
    }

    @Override
    public void setEmail(String email) {
        this.email=email;
    }

    @Override
    public void setInformation(String information) {
        this.information=information;
    }

    @Override
    public String getIllness() {
        return illness;
    }

    @Override
    public void setIllness(String illness) {
        this.illness = illness;
    }


}
