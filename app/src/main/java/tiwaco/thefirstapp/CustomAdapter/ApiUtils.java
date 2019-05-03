package tiwaco.thefirstapp.CustomAdapter;

/**
 * Created by Admin on 3/5/2019.
 */

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = "https://www.thanhtoan.tiwaco.com.vn/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
