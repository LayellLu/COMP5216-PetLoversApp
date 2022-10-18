package com.example.comp5216_petloversapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Add_post extends AppCompatActivity implements LocationListener {
    EditText title_blog, description_blog;
    Button upload;
    ImageView blog_image;

    Uri image_uri = null;

    private static String city="11";
    private static String state;
    private static String country;
    private static final int GALLERY_IMAGE_CODE = 100;
    private static final int CAMERA_IMAGE_CODE = 200;
    private static final int REQUEST_CODE = 300;

    ProgressDialog pd;
    FirebaseAuth auth;

    FusedLocationProviderClient fusedLocationClient;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        permission();
        title_blog = findViewById(R.id.title_blog);
        description_blog = findViewById(R.id.description_blog);
//        upload = findViewById(R.id.upload);
        blog_image = findViewById(R.id.post_image_blog);

        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        blog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });


//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = title_blog.getText().toString();
//                String description = description_blog.getText().toString();
//                if (title.isEmpty()) {
//                    title_blog.setError("Title is required");
//                    title_blog.requestFocus();
//                    return;
//                }
//                if (description.isEmpty()) {
//                    description_blog.setError("Description is required");
//                    description_blog.requestFocus();
//                    return;
//                }
//                if (image_uri == null) {
//                    Toast.makeText(Add_post.this, "Please select image", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                uploadData(title, description);
//            }
//        });
    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Add_post.this);
        builder.setTitle("Choose Image From");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    cameraPick();
                } else if (which == 1) {
                    galleryPick();
                }
            }
        });
        builder.create().show();
    }

    private void uploadData(String title, String description) {

        pd.setMessage("Uploading...");
        pd.show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        Date now = new Date();
        String timeStamp = formatter.format(now);
        String filePathAndName = "Picture/" + "post" + timeStamp;
        String email = auth.getCurrentUser().getEmail();
        //将email以@为分隔符分开，取第一部分作为用户名
        String[] split = email.split("@");
        String username = split[0];

        getLastLocation();


        if (blog_image.getDrawable() != null) {
            //upload image
            Bitmap bitmap = ((BitmapDrawable) blog_image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            //create the reference of storage in firebase
            StorageReference reference = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            reference.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            String downloadUri = uriTask.getResult().toString();

                            if (uriTask.isSuccessful()) {
                                FirebaseAuth user = FirebaseAuth.getInstance();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("UserEmail", user.getCurrentUser().getEmail());
                                hashMap.put("BlogId", user.getCurrentUser().getEmail() + '_' + timeStamp);
                                hashMap.put("BlogTitle", title);
                                hashMap.put("Image", downloadUri);
                                hashMap.put("Blog", description);
                                hashMap.put("Time", timeStamp);
                                hashMap.put("Location", country + "_ " + state + "_ " + city);
                                System.out.println("pLocation: " + country + "_ " + state + "_ " + city);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Blogs");
                                ref.child(username + "_" + timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                pd.dismiss();
                                                Toast.makeText(Add_post.this, "Post Published", Toast.LENGTH_SHORT).show();
                                                title_blog.setText("");
                                                description_blog.setText("");
                                                blog_image.setImageURI(null);
                                                image_uri = null;

                                                startActivities(new Intent[]{new Intent(Add_post.this, MainActivity.class)});
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(Add_post.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(Add_post.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

//    private void getLastLocation() {
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_CODE);
//            System.out.println("FFailed");
//        }else {
//            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @SuppressLint("SetTextI18n")
//                @Override
//                public void onSuccess(Location location) {
//                    if(location != null){
//                        Geocoder geocoder = new Geocoder(Add_post.this, Locale.getDefault());
//                        List<Address> addresses = null;
//                        try {
//                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            city = addresses.get(0).getLocality();
//                            state = addresses.get(0).getAdminArea();
//                            country = addresses.get(0).getCountryName();
//                            System.out.println("city: "+city);
//                            System.out.println("state: "+state);
//                            System.out.println("country: "+country);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }else {
//                        askPermission();
//                    }
//                }
//
//            });
//
//        }
//
//    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private void cameraPick() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, CAMERA_IMAGE_CODE);
    }

    private void galleryPick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_IMAGE_CODE);

    }

    private void permission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }

                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_IMAGE_CODE) {
                image_uri = data.getData();
                blog_image.setImageURI(image_uri);
            }
            if (requestCode == CAMERA_IMAGE_CODE) {
                blog_image.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = title_blog.getText().toString().trim();
        String description = description_blog.getText().toString().trim();
        if (title.isEmpty()) {
            title_blog.setError("Title is required");
            title_blog.requestFocus();
            return false;
        }
        if (description.isEmpty()) {
            description_blog.setError("Description is required");
            description_blog.requestFocus();
            return false;
        }
        if (image_uri == null) {
            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        uploadData(title, description);
        return super.onOptionsItemSelected(item);
    }

    private void getLastLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, Add_post.this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(Add_post.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            city = addresses.get(0).getLocality();
            country = addresses.get(0).getCountryName();
            state = addresses.get(0).getAdminArea();

            System.out.println("city: "+city);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}