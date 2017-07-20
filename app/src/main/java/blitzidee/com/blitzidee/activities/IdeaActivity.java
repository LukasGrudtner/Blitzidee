package blitzidee.com.blitzidee.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.GregorianCalendar;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.mapeadores.MapeadorIdea;
import blitzidee.com.blitzidee.model.Idea;

public class IdeaActivity extends AppCompatActivity {

    private Idea idea;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewCreationDate;
    private TextView textViewEndDate;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        String ideaTitle = getIntent().getExtras().getString("ideaTitle");
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getApplicationContext());
        idea = mapeadorIdea.get(ideaTitle);

        textViewTitle = (TextView) findViewById(R.id.textViewIdeaTitle);
        textViewDescription = (TextView) findViewById(R.id.textViewIdeaDescription);
        textViewCreationDate = (TextView) findViewById(R.id.textViewIdeaCreationDate);
        textViewEndDate = (TextView) findViewById(R.id.textViewIdeaEndDate);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabIdeaConcluded);

        textViewTitle.setText(idea.getTitle());
        textViewDescription.setText(idea.getDescription());

        setCreationDateInTextView(idea.getCreationDate());
        setEndDateInTextView(idea.getEndDate());

        /* Action listener no botão concluded */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idea.setConclusion(true);
                idea.setEndDate(new GregorianCalendar());
                setEndDateInTextView(new GregorianCalendar());
                saveIdeaOnDatabase();
            }
        });

    }

    private void saveIdeaOnDatabase() {
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getApplicationContext());
        mapeadorIdea.put(idea);
        mapeadorIdea.close();
    }

    private void setCreationDateInTextView(GregorianCalendar creationDate) {
        String date;

        int day = creationDate.get(GregorianCalendar.DAY_OF_MONTH);
        if (day < 10) date = "0" + day; else date = String.valueOf(day);

        int month = creationDate.get(GregorianCalendar.MONTH);
        if (month < 10) date += "/0" + month; else date += "/" + month;

        int year = creationDate.get(GregorianCalendar.YEAR);
        date += "/" + year;

        textViewCreationDate.setText(date);
    }

    private void setEndDateInTextView(GregorianCalendar endDate) {
        String date;

        int dayEnd = endDate.get(GregorianCalendar.DAY_OF_MONTH);
        if (dayEnd < 10) date = "0" + dayEnd; else date = String.valueOf(dayEnd);

        int monthEnd = endDate.get(GregorianCalendar.MONTH);
        if (monthEnd < 10) date += "/0" + monthEnd; else date += "/" + monthEnd;

        int yearEnd = endDate.get(GregorianCalendar.YEAR);
        date += "/" + yearEnd;

        /* Verifica se a data de término é válida */
        if (!endDateIsValid(endDate))
            date = "";

        textViewEndDate.setText(date);
    }

    private boolean endDateIsValid(GregorianCalendar gregorianCalendar) {
        GregorianCalendar gcAux = new GregorianCalendar();
        gcAux.set(GregorianCalendar.YEAR, 1970);

        if (gregorianCalendar.get(GregorianCalendar.YEAR) == gcAux.get(GregorianCalendar.YEAR))
            return false;
        else
            return true;

    }
}
