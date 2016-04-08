package nd.rw.kittest.app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Created by andrew on 08.04.2016.
 */
public interface AnswerService {

    @PUT("/answer")
    Call<AnswerBundle> putAnswerBundle(@Body AnswerBundle answerBundle);
}
