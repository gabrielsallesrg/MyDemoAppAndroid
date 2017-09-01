package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUsersData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface RandomUserApiInterface {

    @GET("/api/1.1/?results=15")
    Call<RandomUsersData> doGetRandomUsersData(@Query("page") int page);
}
