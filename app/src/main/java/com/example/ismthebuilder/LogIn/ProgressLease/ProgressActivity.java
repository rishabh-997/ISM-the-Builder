package com.example.ismthebuilder.LogIn.ProgressLease;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ismthebuilder.LogIn.Constructor.Available.AvailableList;
import com.example.ismthebuilder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressActivity extends AppCompatActivity
{
    AvailableList model;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.upload)
    TextView upload;
    @BindView(R.id.image_uploaded)
    ImageView imageView;
    @BindView(R.id.submit_report)
    Button submit;

    String final_stage, final_description, final_url;
    ProgressDialog progressDialog;

    FirebaseStorage firebasestorage= FirebaseStorage.getInstance();
    StorageReference storageReference;
    int capture = 10;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Progress");

    String doc_url="";
    Uri doc_data=null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);
        model=(AvailableList) getIntent().getExtras().getSerializable("project");
        storageReference=firebasestorage.getReference().child(model.getKey()+"/"+System.currentTimeMillis()+".jpg");
        Toast.makeText(this, model.getName(), Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");

        String[] stageList=new String[5];
        stageList[0] = "Not started";
        stageList[1] = "Construction material pending";
        stageList[2] = "Construction started";
        stageList[3] = "Half Made";
        stageList[4] = "Completed";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stageList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                final_stage=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(view -> submit_report());

        upload.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,capture);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==capture)&&(resultCode==RESULT_OK)&&(data!=null)&&(data.getData()!=null))
        {
            Toast.makeText(this, "Uploading ...", Toast.LENGTH_SHORT).show();
            progressDialog.show();
            doc_data=data.getData();
            storageReference.putFile(doc_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri data=uri;
                            doc_url = data.toString();
                            final_url=doc_url;
                            Toast.makeText(ProgressActivity.this, "Photo added Successfully", Toast.LENGTH_SHORT).show();

                            imageView.setImageBitmap(BitmapFactory.decodeFile(doc_url));
                        }
                    });
                }
            });
            progressDialog.dismiss();
        }
    }

    private void submit_report()
    {
        final_description = description.getText().toString();

        if(description.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();
        else if (final_stage.isEmpty())
            Toast.makeText(this, "Please select progress", Toast.LENGTH_SHORT).show();
        else if(final_url.isEmpty())
            Toast.makeText(this, "Please upload image of your progress", Toast.LENGTH_SHORT).show();
        else
        {
            ProgressModel progress_model = new ProgressModel(final_stage,final_description, final_url);
            databaseReference.child(model.getKey()).setValue(progress_model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(ProgressActivity.this, "Successfully submitted for review...", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
    }
}
