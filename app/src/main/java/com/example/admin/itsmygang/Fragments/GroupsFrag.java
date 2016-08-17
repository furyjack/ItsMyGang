package com.example.admin.itsmygang.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.itsmygang.Activity.GroupchatActivity;
import com.example.admin.itsmygang.Model.Grouppojo;
import com.example.admin.itsmygang.R;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFrag extends Fragment {

    CreateGroupFragment frag;
    RecyclerView RvGroups;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    DatabaseReference mref= FirebaseDatabase.getInstance().getReference();
    DatabaseReference groups=mref.child("Users").child(auth.getCurrentUser().getUid()).child("Groups");
  ProgressBar pbar;
    public GroupsFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        frag=new CreateGroupFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_groups, container, false);
        RvGroups=(RecyclerView)rootview.findViewById(R.id.rv_groups);
        pbar= (ProgressBar) rootview.findViewById(R.id.pbar);
        RvGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        Firebase.setAndroidContext(getContext());

        FirebaseRecyclerAdapter<Grouppojo,GroupViewHolder> madapter=new FirebaseRecyclerAdapter<Grouppojo, GroupViewHolder>(Grouppojo.class,
                R.layout.customlistview,GroupViewHolder.class,groups) {
            @Override
            protected void populateViewHolder(GroupViewHolder viewHolder, final Grouppojo model, int position) {
                       final TextView t=  viewHolder.Text;
                       t.setText(model.getName());
                       t.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              Intent intent=new Intent(getContext(), GroupchatActivity.class);
                              intent.putExtra("gname",model.getUid());
                              startActivity(intent);
                          }
                      });

            }
        };
        RecyclerView.AdapterDataObserver observer= new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                pbar.setVisibility(View.GONE);
            }
        };
        madapter.registerAdapterDataObserver(observer);
        RvGroups.setAdapter(madapter);


        return rootview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_create_group)
        {
            frag.show(getFragmentManager(),"blah");


        }

        return super.onOptionsItemSelected(item);
    }


    public static  class GroupViewHolder extends RecyclerView.ViewHolder
    {
        TextView Text;

        public GroupViewHolder(View itemView) {
            super(itemView);
            Text=(TextView)itemView.findViewById(android.R.id.text1);
        }
    }




}
