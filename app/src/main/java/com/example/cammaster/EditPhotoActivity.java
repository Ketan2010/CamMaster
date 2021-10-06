package com.example.cammaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditPhotoActivity extends AppCompatActivity {
    ImageView imgedit;
    String path = "";
    Uri inputImageUri;
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FusedLocationProviderClient fusedLocationProviderClient;
    public String address;
    Button gotogallery, takephoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        gotogallery = findViewById(R.id.go_to_gallery);
        takephoto = findViewById(R.id.go_to_camera);
        gotogallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPhotoActivity.this, GalleryActivity.class));
            }
        });
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPhotoActivity.this, CameraActivity.class));
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(EditPhotoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null)
                    {
                        Geocoder geocoder = new Geocoder(EditPhotoActivity.this, Locale.getDefault());
                        try{
                            List<Address> addressList=  geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            address = addressList.get(0).getAddressLine(0);

                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(EditPhotoActivity.this, "Loaction NULL_ERROR.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(EditPhotoActivity.this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        imgedit = (ImageView) findViewById(R.id.imgedit);
        storageReference = FirebaseStorage.getInstance().getReference();
        path = getIntent().getExtras().getString("path");
        File imgfile = new File(path);
        if(imgfile.exists())
        {
            Bitmap mybitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
            imgedit.setImageBitmap(mybitmap);
        }
        inputImageUri = Uri.fromFile(new File(path));
        edit_trial();
    }

    public void edit_trial()
    {
        Intent dsintent = new Intent(this, DsPhotoEditorActivity.class);
        dsintent.setData(inputImageUri);
        //set output directory name
        dsintent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "CamMaster");
        //set toolbar color
        dsintent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, Color.parseColor("#471332"));
        //set background color
        dsintent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR, Color.parseColor("#FFFFFF"));
        //Hide tools
        dsintent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,
                new int[]{DsPhotoEditorActivity.TOOL_WARMTH, DsPhotoEditorActivity.TOOL_PIXELATE});
        startActivityForResult(dsintent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case 200:
                    Uri outputUri = data.getData();
                    uploadImageToFirebase(outputUri);
                    imgedit.setImageURI(outputUri);
                    break;
            }
        }
    }

    private void uploadImageToFirebase(Uri outputUri) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final StorageReference image = storageReference.child(userId + "/" + name);
        image.putFile(outputUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        String date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String time= new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                        ImageData image = new ImageData(uri.toString(), date, time, address);
                        mDatabase.child("users").child(userId).child("images").child(name).setValue(image);
                    }
                });
                Toast.makeText(EditPhotoActivity.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditPhotoActivity.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}