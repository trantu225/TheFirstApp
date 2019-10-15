package tiwaco.thefirstapp.CustomAdapter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import tiwaco.thefirstapp.DTO.JSONTHUTHEOUSERNAME;
import tiwaco.thefirstapp.DTO.RequestPayBillAllNB;
import tiwaco.thefirstapp.DTO.RequestTamThu;
import tiwaco.thefirstapp.DTO.ResponePayBillAllNB;
import tiwaco.thefirstapp.DTO.ResponePayTamThu;

/**
 * Created by Admin on 3/5/2019.
 */

public interface APIService {
    @POST("/PayTamThu")

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    Call<ResponePayTamThu> updatetamthu(@Body RequestTamThu post);

    //https://www.thanhtoan.tiwaco.com.vn/PayAllThuNoiBo
    @POST("/PayAllThuNB")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    Call<ResponePayBillAllNB> PayAllThuNB(@Body RequestPayBillAllNB post);

    @POST("/FileDaThu")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    Call<ResponePayTamThu> FileDaThu(@Body JSONTHUTHEOUSERNAME post);
}
