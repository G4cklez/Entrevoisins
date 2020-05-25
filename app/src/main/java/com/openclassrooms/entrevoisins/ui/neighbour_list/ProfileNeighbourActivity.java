package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityProfileNeighbourBinding;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;

import org.w3c.dom.Text;


public class ProfileNeighbourActivity extends AppCompatActivity {

    private Neighbour mNeighbour;
    private FloatingActionButton mBtnFavorite;
    private Button backButton;
    private ActivityProfileNeighbourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_neighbour);

        Intent intent = getIntent();

        // Get information
        if (intent != null) {
            mNeighbour = (Neighbour) intent.getSerializableExtra("NEIGHBOUR");
        }

        initUI();
        setButtons();

    }


    public void initUI() {
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(binding.neighbourPicture);


        binding.neighbourName.setText(mNeighbour.getName());

        binding.textViewLocationContent.setText(mNeighbour.getAddress());

        binding.textViewMobileNumberContent.setText(mNeighbour.getPhoneNumber());

        binding.textViewWebsiteContent.setText(mNeighbour.getAvatarUrl());

        binding.textViewAboutContent.setText(mNeighbour.getAboutMe());
    }

    private void setButtons() {
        // Buttons

        mBtnFavorite = binding.buttonFavorite;

        if (mNeighbour.getFavorite())
            mBtnFavorite.setImageResource(R.drawable.ic_star_full_yellow_24dp);
        else
            mBtnFavorite.setImageResource(R.drawable.ic_star_empty_yellow_24dp);


        backButton = binding.backButton;

        mBtnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DummyNeighbourApiService().changeFavoriteStatus(mNeighbour.getId());
                mNeighbour.setFavorite(!mNeighbour.getFavorite());
                if (mNeighbour.getFavorite())
                    mBtnFavorite.setImageResource(R.drawable.ic_star_full_yellow_24dp);
                else
                    mBtnFavorite.setImageResource(R.drawable.ic_star_empty_yellow_24dp);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}


