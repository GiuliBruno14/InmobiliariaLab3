package com.giulietta.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaSessionCompat;

import com.giulietta.inmobiliaria.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {
    public static final String URLBASE = "http://192.168.100.3:5021/"; //MI API
    //public static final String URLBASE ="https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    public static SharedPreferences sp;
    public static String token;

    public static InmobiliariaService getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaService.class);
    }

    private static SharedPreferences getSharedPreference(Context context){
        if(sp == null ){
            sp = context.getSharedPreferences("usuario", 0);
        }
        return sp;
    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public static void borrarToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token");
        editor.apply();
    }

    public interface InmobiliariaService {
        @FormUrlEncoded
        @POST("api/Propietarios/login") //LOGIN
        Call<String> login (@Field("Email") String email, @Field("Password")String clave);

        //Obtener perfil del usuario
        @GET("api/propietarios/perfil")
        Call<Propietario> getPerfil(@Header("Authorization")String token);

    }

}
