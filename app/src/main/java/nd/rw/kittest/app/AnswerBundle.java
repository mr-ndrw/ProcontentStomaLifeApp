package nd.rw.kittest.app;

import java.util.ArrayList;
import java.util.Date;

import nd.rw.kittest.app.Answer;

/**
 * Created by andrew on 08.04.2016.
 */
public class AnswerBundle {

    public Date dateTime = new Date();

    public Answer[] answers = new Answer[9];

    public void reset(){
        answers = new Answer[9];
        dateTime = new Date();
    }
}
