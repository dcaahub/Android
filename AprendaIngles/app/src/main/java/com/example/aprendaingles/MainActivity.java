package com.example.aprendaingles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.aprendaingles.Fragment.AnimalFragment;
import com.example.aprendaingles.Fragment.NumeroFragment;
import com.example.aprendaingles.Fragment.VogalFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);

        getSupportActionBar().setElevation(0);

        //Configurar adapter para as abas
        FragmentPagerItemAdapter adapter =  new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Animais", AnimalFragment.class)
                        .add("Números", NumeroFragment.class)
                        .add("Vogais", VogalFragment.class)
                        .create()

        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }
}
