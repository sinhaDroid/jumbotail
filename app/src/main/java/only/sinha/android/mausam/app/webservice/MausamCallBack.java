package only.sinha.android.mausam.app.webservice;

import com.sinha.android.ExceptionTracker;
import com.sinha.android.Log;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MausamCallBack<T> implements Callback<T> {

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        onResponse(call, (Response<T>) Response.error(getEmptyResponseBody(), new okhttp3.Response.Builder()
                .code(0)
                .message(t.getMessage())
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build()));

        if (null != t) {
            ExceptionTracker.track(t);
        }

        Log.d("MausamCallBack", "Not authorize check");
    }

    private ResponseBody getEmptyResponseBody() {
        return new ResponseBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        };
    }
}