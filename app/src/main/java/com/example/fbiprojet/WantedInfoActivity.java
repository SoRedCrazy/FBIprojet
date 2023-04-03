package com.example.fbiprojet;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class WantedInfoActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    ImageButton return_button;
    ImageButton like_button;
    ViewPager vp_pager;
    Wanted actual_wanted;
    TextView wanted_title;
    TextView tags;
    ImageView wanted_image;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted_info);

        Bundle b = getIntent().getExtras();
        actual_wanted = (Wanted) b.getSerializable("wanted");

        vp_pager = (ViewPager) findViewById(R.id.pageViewer);
        adapterViewPager = new PageAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, actual_wanted);
        vp_pager.setAdapter(adapterViewPager);
        TabLayout tabs= (TabLayout)findViewById(R.id.fragment_title);
        tabs.setupWithViewPager(vp_pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        wanted_title = findViewById(R.id.wanted_title);
        return_button = findViewById(R.id.return_button);
        like_button = findViewById(R.id.likebutton);
        wanted_image = findViewById(R.id.wanted_image);

        wanted_title.setText(actual_wanted.getTitle());
        Picasso.get().load(actual_wanted.getImages().getImageUrl(0)).into(wanted_image);

        return_button.setOnClickListener(view -> finish());

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        DBHandler handler = new DBHandler(this);

        like_button.setOnClickListener(view -> {
            int user_id = sharedPreferences.getInt("user_id", -1);
            Intent i;
            if(user_id == -1) {
                i = new Intent(this, ConnexionActivity.class);
                startActivity(i);
            } else {
                String uid = view.findViewById(R.id.uid_info).toString();
                handler.addlike(user_id, uid);
                Toast toast = Toast.makeText(this, "Like ajouté", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}