package tiwaco.thefirstapp.CustomAdapter;

/**
 * Created by Admin on 3/5/2019.
 */

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = "https://thanhtoannb.tiwaco.com.vn:49404";
    public static final String CTN_URL = "http://113.176.94.250:8012/Service1.svc/";
    //   public static final String BASE_URL = "http://192.168.1.203:1024/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getAPIService1() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
