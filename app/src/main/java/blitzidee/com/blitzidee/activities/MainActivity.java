package blitzidee.com.blitzidee.activities;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import blitzidee.com.blitzidee.R;
import blitzidee.com.blitzidee.adapter.TabAdapter;
import blitzidee.com.blitzidee.helper.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String[] select = {"Ideias", "Livros", "Animes"};
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createToolbar();

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_layout_id);
        viewPager = (ViewPager) findViewById(R.id.view_pager_id);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));
        slidingTabLayout.setViewPager(viewPager);


    }

}
