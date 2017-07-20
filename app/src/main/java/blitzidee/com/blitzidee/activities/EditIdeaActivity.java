package blitzidee.com.blitzidee.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.mapeadores.MapeadorIdea;
import blitzidee.com.blitzidee.model.Idea;

public class EditIdeaActivity extends AppCompatActivity {

    private Idea idea;
    private String oldTitle;
    private EditText editTextIdeaTitle;
    private EditText editTextIdeaDescription;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea);

        String ideaTitle = getIntent().getExtras().getString("ideaTitle");
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getApplicationContext());
        idea = mapeadorIdea.get(ideaTitle);
        oldTitle = idea.getTitle();

        editTextIdeaTitle = (EditText) findViewById(R.id.editTextIdeaTitle);
        editTextIdeaDescription = (EditText) findViewById(R.id.editTextIdeaDescription);

        editTextIdeaTitle.setText(idea.getTitle());
        editTextIdeaDescription.setText(idea.getDescription());

        createToolbar();
    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_idea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_confirm:
                doActionConfirm();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doActionConfirm() {
        idea.setTitle(editTextIdeaTitle.getText().toString());
        idea.setDescription(editTextIdeaDescription.getText().toString());
        saveIdeaOnDatabase(idea);
//        openIdeaActivity();
        onDestroy();
    }

    private void openIdeaActivity() {
        Intent intent = new Intent(this, IdeaActivity.class);
        intent.putExtra("ideaTitle", idea.getTitle());
        startActivity(intent);
        onDestroy();
    }

    private void saveIdeaOnDatabase(Idea idea) {
        MapeadorIdea mapeadorIdea = new MapeadorIdea(getApplicationContext());
        mapeadorIdea.updateIdea(oldTitle, idea);
        mapeadorIdea.close();
    }
}
