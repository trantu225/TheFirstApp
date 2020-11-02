package tiwaco.thefirstapp.CustomAdapter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import tiwaco.thefirstapp.DTO.JSONTHUTHEOUSERNAME;
import tiwaco.thefirstapp.DTO.RequestGetBillInfoDTO;
import tiwaco.thefirstapp.DTO.RequestPayBillAllNB;
import tiwaco.thefirstapp.DTO.RequestPayThuHo;
import tiwaco.thefirstapp.DTO.RequestTamThu;
import tiwaco.thefirstapp.DTO.RequestTinhTienNuoc;
import tiwaco.thefirstapp.DTO.ResponePayBillAllNB;
import tiwaco.thefirstapp.DTO.ResponePayTamThu;
import tiwaco.thefirstapp.DTO.ResponseObjectNhieuHDNB;
import tiwaco.thefirstapp.DTO.ResponseTinhTienNuoc;

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

    @POST("/GetBill_InfoNB")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    Call<ResponePayBillAllNB> GetBill_InfoNB(@Body RequestGetBillInfoDTO post);

    @POST("/FileDaThu")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    Call<ResponePayTamThu> FileDaThu(@Body JSONTHUTHEOUSERNAME post);

    @POST("/ThuHo")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    Call<ResponseObjectNhieuHDNB> ThuHo(@Body RequestPayThuHo post);

    @POST("/TinhTienNuoc")
    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000", "CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    Call<ResponseTinhTienNuoc> TinhTienNuoc(@Body RequestTinhTienNuoc post);



}
