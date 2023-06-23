package otea.connection.caller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.EvaluatedOrganization;
import cli.organization.EvaluatorOrganization;
import cli.organization.Organization;
import cli.organization.data.Address;
import cli.organization.data.EvaluatorTeam;
import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;
import cli.user.Administrator;
import cli.user.User;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;
import cli.user.OrganizationUser;


import otea.connection.ConnectionClient;
import otea.connection.api.AddressesApi;
import otea.connection.api.CitiesApi;
import otea.connection.api.CountriesApi;
import otea.connection.api.EvaluatorTeamsApi;
import otea.connection.api.EvidencesApi;
import otea.connection.api.IndicatorsApi;
import otea.connection.api.OrganizationsApi;
import otea.connection.api.ProvincesApi;
import otea.connection.api.RegionsApi;
import otea.connection.api.UsersApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.AsyncTask;
import android.util.Log;

public class Caller {

    ConnectionClient con;

    static Retrofit retrofit;

    public Caller(){
        con=new ConnectionClient();
        retrofit=con.getRetrofit();
    }
    public static List<Indicator> obtainIndicators(String illness){
        IndicatorsApi api=retrofit.create(IndicatorsApi.class);
        Call<List<Indicator>> call=api.GetAllByType(illness);
        AsyncTask<Void, Void, List<Indicator>> asyncTask = new AsyncTask<Void, Void, List<Indicator>>() {
            List<Indicator> resultList= null;
            @Override
            protected List<Indicator> doInBackground(Void... voids) {
                try {
                    Response<List<Indicator>> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(List<Indicator> indicatorList) {
                resultList=indicatorList;
            }

        };
        asyncTask.execute();
        try {
            List<Indicator> aux=asyncTask.get();
            List<Indicator> list=new LinkedList<>();
            for(Indicator i:aux){//Se hace así debido a que queremos asignar también las evidencias
                list.add(new Indicator(i.getIdIndicator(),i.getIndicatorType(),i.getDescriptionEnglish(),i.getDescriptionSpanish(),i.getDescriptionFrench(),i.getPriority()));
            }
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static EvaluatorTeam obtainEvaluatorTeam(int idEvaluatorTeam,int idEvaluatorOrganization,String orgTypeEvaluator,String illness){
        EvaluatorTeamsApi api=retrofit.create(EvaluatorTeamsApi.class);
        Call<EvaluatorTeam> call=api.Get(idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,illness);
        AsyncTask<Void, Void, EvaluatorTeam> asyncTask = new AsyncTask<Void, Void, EvaluatorTeam>() {
            EvaluatorTeam resultEvaluatorTeam = null;
            @Override
            protected EvaluatorTeam doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeam> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(EvaluatorTeam evaluatorTeam) {
                resultEvaluatorTeam=evaluatorTeam;
            }

        };
        asyncTask.execute();
        try {
            EvaluatorTeam evaluatorTeam=asyncTask.get();
            //Se devuelve el constructor para que obtenga los objetos usuario
            return new EvaluatorTeam(idEvaluatorTeam,evaluatorTeam.getCreationDate(),idEvaluatorOrganization,orgTypeEvaluator, illness, evaluatorTeam.getEmailConsultant(), evaluatorTeam.getEmailProfessional(), evaluatorTeam.getEmailResponsible(), evaluatorTeam.getPatient_name(), evaluatorTeam.getRelative_name());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Organization obtainOrganization(int idOrganization, String orgType, String illness) {
        OrganizationsApi api=retrofit.create(OrganizationsApi.class);
        Call<Organization> call=api.Get(idOrganization,orgType,illness);
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            Organization resultOrganization = null;
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Organization organization) {
                resultOrganization=organization;
            }

        };
        asyncTask.execute();
        try {
            Organization organization=asyncTask.get();
            if(organization!=null){
                if(organization.getOrganizationType().equals("EVALUATED") && organization.getIllness().equals("AUTISM")){
                    return new AutisticEvaluatedOrganization(idOrganization,orgType,illness,organization.getName(),organization.getIdAddress(),organization.getTelephone(),organization.getEmail(),organization.getInformation(),organization.getEmailOrgPrincipal(),organization.getEmailOrgConsultant());
                }
                else if(organization.getOrganizationType().equals("EVALUATED") && organization.getIllness().equals("AUTISM")){
                    return new AutisticEvaluatorOrganization(organization.getIdOrganization(),orgType,organization.getIllness(),organization.getName(),organization.getIdAddress(),organization.getTelephone(),organization.getEmail(),organization.getInformation());
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Address obtainAddress(int idAddress) {
        AddressesApi api = retrofit.create(AddressesApi.class);
        Call<Address> call = api.Get(idAddress);
        Address[] aux = new Address[1];
        AsyncTask<Void, Void, Address> asyncTask = new AsyncTask<Void, Void, Address>() {
            Address resultAddress = null;

            @Override
            protected Address doInBackground(Void... voids) {
                try {
                    Response<Address> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute (Address address){
                resultAddress=address;
            }

        };
        asyncTask.execute();
        try {
            Address address=asyncTask.get();
            //Se devuelve el constructor porque este llama a la obtención de los objetos City, Province, Region y Country
            return new Address(address.getIdAddress(), address.getName(), address.getNumber(), address.getFloor(), address.getApartment(), address.getZipCode(), address.getIdCity(), address.getIdProvince(), address.getIdRegion(), address.getIdCountry());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static City obtainCity(int idCity, int idProvince, int idRegion, String idCountry){
        CitiesApi api=retrofit.create(CitiesApi.class);
        Call<City> call=api.GetCity(idCity,idProvince,idRegion,idCountry);
        AsyncTask<Void, Void, City> asyncTask = new AsyncTask<Void, Void, City>() {
            City resultCity = null;
            @Override
            protected City doInBackground(Void... voids) {
                try {
                    Response<City> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(City city) {
                resultCity=city;
            }

        };
        asyncTask.execute();
        try {
            City city=asyncTask.get();
            //Se devuelve el constructor porque este llama a la obtención de los objetos Province, Region y Country
            return new City(city.getIdCity(),city.getIdProvince(),city.getIdRegion(),city.getIdCountry(),city.getCityName());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Province obtainProvince(int idProvince, int idRegion, String idCountry){
        ProvincesApi api=retrofit.create(ProvincesApi.class);
        Call<Province> call=api.GetProvince(idProvince,idRegion,idCountry);
        AsyncTask<Void, Void, Province> asyncTask = new AsyncTask<Void, Void, Province>() {
            Province resultProvince = null;
            @Override
            protected Province doInBackground(Void... voids) {
                try {
                    Response<Province> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Province province) {
                resultProvince=province;
            }

        };
        asyncTask.execute();
        try {
            Province province=asyncTask.get();
            //Se devuelve el constructor porque este llama a la obtención de los objetos Region y Country
            return new Province(province.getIdProvince(),province.getIdRegion(),province.getIdCountry(),province.getNameProvince());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Region obtainRegion(int idRegion, String idCountry){
        RegionsApi api=retrofit.create(RegionsApi.class);
        Call<Region> call=api.GetRegion(idRegion,idCountry);
        AsyncTask<Void, Void, Region> asyncTask = new AsyncTask<Void, Void, Region>() {
            Region resultRegion = null;
            @Override
            protected Region doInBackground(Void... voids) {
                try {
                    Response<Region> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Region region) {
                resultRegion=region;
            }

        };
        asyncTask.execute();
        try {
            Region region=asyncTask.get();
            //Se devuelve el constructor porque este llama a la obtención del objeto Country
            return new Region(region.getIdRegion(),region.getIdCountry(),region.getNameRegion());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }


     public static Country obtainCountry(String idCountry){
        CountriesApi api=retrofit.create(CountriesApi.class);
        Call<Country> call=api.GetCountry(idCountry);

        AsyncTask<Void, Void, Country> asyncTask = new AsyncTask<Void, Void, Country>() {
            Country resultCountry = null;
            @Override
            protected Country doInBackground(Void... voids) {
                try {
                    Response<Country> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Country country) {
                resultCountry=country;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static OrganizationUser obtainOrgUser(String email, Organization organization){
        UsersApi api = retrofit.create(UsersApi.class);
        Call<User> call = api.Get(email);
        User[] aux=new User[1];
        AsyncTask<Void, Void, User> asyncTask = new AsyncTask<Void, Void, User>() {
            User resultUser = null;
            @Override
            protected User doInBackground(Void... voids) {
                try {
                    Response<User> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(User user) {
                resultUser=user;
            }

        };
        asyncTask.execute();
        try {
            User user=asyncTask.get();
            if(organization.getIdOrganization()==user.getIdOrganization() && organization.getOrganizationType().equals(user.getOrgType()) && organization.getIllness().equals(user.getIllness())){
                if (user.getOrgType().equals("EVALUATED")) {
                    return new EvaluatedOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone(), (EvaluatedOrganization) organization);
                } else if (user.getOrgType().equals("EVALUATOR")) {
                    return new EvaluatorOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone(), (EvaluatorOrganization) organization);
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }


        return null;
    }
    public static User obtainUserForLogin(String email, String password){
        UsersApi api = retrofit.create(UsersApi.class);
        Call<User> call = api.GetForLogin(email, password);
        AsyncTask<Void, Void, User> asyncTask = new AsyncTask<Void, Void, User>() {
            User resultUser = null;
            @Override
            protected User doInBackground(Void... voids) {
                try {
                    Response<User> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(User user) {
                if(user!=null) {
                    resultUser = user;
                }
            }
        };
        asyncTask.execute();
        try {
            User user=asyncTask.get();
            if (user != null) {
                if(user.getUserType().equals("ORGANIZATION")){
                    if (user.getOrgType().equals("EVALUATED")) {
                        return new EvaluatedOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    } else if (user.getOrgType().equals("EVALUATOR")) {
                        return new EvaluatorOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    }
                } else if (user.getUserType().equals("ADMIN")) {
                    return new Administrator(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone());
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    public static User obtainUser(String email){
        UsersApi api = retrofit.create(UsersApi.class);
        Call<User> call = api.Get(email);
        AsyncTask<Void, Void, User> asyncTask = new AsyncTask<Void, Void, User>() {
            User resultUser = null;
            @Override
            protected User doInBackground(Void... voids) {
                try {
                    Response<User> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(User user) {
                resultUser=user;
            }

        };
        asyncTask.execute();
        try {
            User user=asyncTask.get();
            if (user != null) {
                if(user.getUserType().equals("ORGANIZATION")){
                    if (user.getOrgType().equals("EVALUATED")) {
                        return new EvaluatedOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    } else if (user.getOrgType().equals("EVALUATOR")) {
                        return new EvaluatorOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    }
                } else if (user.getUserType().equals("ADMIN")) {
                    return new Administrator(user.getFirst_name(), user.getLast_name(), user.getOrgType(), user.getEmailUser(), user.getPasswordUser(), user.getTelephone());
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    public static List<Evidence> obtainEvidences(int idIndicator, String indicatorType){
        EvidencesApi api=retrofit.create(EvidencesApi.class);
        Call<List<Evidence>> call=api.GetAllByIndicator(idIndicator,indicatorType);
        List<Evidence>[] result=new List[1];
        AsyncTask<Void, Void, List<Evidence>> asyncTask = new AsyncTask<Void, Void, List<Evidence>>() {
            List<Evidence> resultList = null;
            @Override
            protected List<Evidence> doInBackground(Void... voids) {
                try {
                    Response<List<Evidence>> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(List<Evidence> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Evidence addEvidence(Evidence evidence){
        EvidencesApi api=retrofit.create(EvidencesApi.class);
        Call<Evidence> call=api.Create(evidence.getIdEvidence(),evidence.getIdIndicator(),evidence.getIndicatorType(),evidence.getDescriptionEnglish(),evidence.getDescriptionSpanish(),evidence.getDescriptionFrench(),evidence.getValue());
        AsyncTask<Void, Void, Evidence> asyncTask = new AsyncTask<Void, Void, Evidence>() {
            Evidence resultEvidence = null;
            @Override
            protected Evidence doInBackground(Void... voids) {
                try {
                    Response<Evidence> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Evidence evidence) {
                resultEvidence=evidence;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Evidence updateEvidence(int idEvidence, int idIndicator, String indicatorType, Evidence evidence) {
        EvidencesApi api=retrofit.create(EvidencesApi.class);
        Call<Evidence> call=api.Update(idEvidence,idIndicator,indicatorType,evidence);
        AsyncTask<Void, Void, Evidence> asyncTask = new AsyncTask<Void, Void, Evidence>() {
            Evidence resultEvidence = null;
            @Override
            protected Evidence doInBackground(Void... voids) {
                try {
                    Response<Evidence> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Evidence evidence) {
                resultEvidence=evidence;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Evidence deleteEvidence(int idEvidence, int idIndicator, String indicatorType) {
        EvidencesApi api=retrofit.create(EvidencesApi.class);
        Call<Evidence> call=api.Delete(idEvidence,idIndicator,indicatorType);
        AsyncTask<Void, Void, Evidence> asyncTask = new AsyncTask<Void, Void, Evidence>() {
            Evidence resultEvidence = null;
            @Override
            protected Evidence doInBackground(Void... voids) {
                try {
                    Response<Evidence> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Evidence evidence) {
                resultEvidence=evidence;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    /*public void addIndicatorEvaluationRegs(List<IndicatorsEvaluationReg> indicators){
        UsersApi api = retrofit.create(UsersApi.class);
        Call<User> call = api.Get(email);
        User[] aux=new User[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<User> response = call.execute();
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
                .observeOn(Schedulers.io())
                .subscribe(user-> {
                    Log.d("CORRECT","Evaluated user correctly obtained");
                }, error -> {
                    Log.d("ERROR",error.toString());
                });
        OrganizationUser user=null;
        if(aux[0].getOrgType()=="EVALUATED" && organization instanceof EvaluatedOrganization) {
            user=new EvaluatedOrganizationUser(aux[0].getFirst_name(), aux[0].getLast_name(), aux[0].getEmailUser(), aux[0].getPasswordUser(), aux[0].getTelephone(), (EvaluatedOrganization) organization);
        }
        if(aux[0].getOrgType()=="EVALUATOR" && organization instanceof EvaluatorOrganization) {
            user=new EvaluatorOrganizationUser(aux[0].getFirst_name(), aux[0].getLast_name(), aux[0].getEmailUser(), aux[0].getPasswordUser(), aux[0].getTelephone(), (EvaluatorOrganization) organization);
        }
        return user;
    }*/


}
