package nd.rw.kittest.app;

import java.util.ArrayList;
import java.util.Date;

import nd.rw.kittest.app.Answer;

/**
 * Created by andrew on 08.04.2016.
 */
public class AnswerBundle {

    public Date dateTime;

    public ArrayList<Answer> answers = new ArrayList<>(9);

    public AnswerBundle() {
        dateTime = new Date();
    }

    public void reset(){
        answers = new ArrayList<>(9);
        dateTime = new Date();
    }
}
