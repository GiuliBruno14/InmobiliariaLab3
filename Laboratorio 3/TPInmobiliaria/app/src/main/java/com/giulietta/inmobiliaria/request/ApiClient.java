package com.giulietta.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaSessionCompat;

import com.giulietta.inmobiliaria.modelo.Contrato;
import com.giulietta.inmobiliaria.modelo.Inmueble;
import com.giulietta.inmobiliaria.modelo.Propietario;
import com.giulietta.inmobiliaria.modelo.TipoInmuebles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    public static final String URLBASE = "http://192.168.100.3:5021/"; //MI API
    //public static final String URLBASE ="https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    public static SharedPreferences sp;
    public static String token;

    public static InmobiliariaService getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
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
        //Actualizar Propietario
        @PUT("api/propietarios/actualizar")
        Call<String> actualizarPropietario(@Header("Authorization")String token,@Body Propietario propietario);

        //Resetear contraseña
        @FormUrlEncoded
        @POST("api/propietarios/email")
        Call<String> resetearPassword(@Field("email") String email);
        //Cambiar contraseña
        @FormUrlEncoded
        @PUT("api/propietarios/cambiarPassword")
        Call<String>cambiarPassword(@Header("Authorization")String token,@Field("passwordActual")String passwordActual, @Field("passwordNueva")String passwordNueva);
        //INMUEBLES
        @GET("api/inmuebles")
        Call<List<Inmueble>>obtenerInmuebles(@Header("Authorization") String token);
        @PUT("api/inmuebles/actualizar")
        Call<String> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @GET("api/inmuebles/tipos")
        Call<List<TipoInmuebles>> obtenerTipos();

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);
        @GET("api/inmuebles/contratos-vigentes")
        Call<List<Inmueble>>obtenerInmueblesConContratoVigente(@Header("Authorization") String token);
        @GET("api/contratos/inmueble/{id}")
        Call<Contrato> ObtenerContrato(@Header("Authorization") String token, @Path("id") int id);
    }

}
