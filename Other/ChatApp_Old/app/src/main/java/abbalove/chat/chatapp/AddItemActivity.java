package abbalove.chat.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import abbalove.chat.chatapp.models.Items;
import abbalove.chat.chatapp.models.Post;
import abbalove.chat.chatapp.models.User;
import abbalove.chat.chatapp.utils.PrefUtils;

public class AddItemActivity extends AppCompatActivity {
    private EditText nama,pembuat,harga;
    private Button btnsave;
    private ImageButton imgbtn;

    private static final int GALLERY_REQUEST = 1;

    private Uri imageUri = null;


    private DatabaseReference databaseReference;
    private User userData;
    private StorageReference mStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        nama = (EditText) findViewById(R.id.itemname);
        pembuat = (EditText) findViewById(R.id.creator);
        btnsave = (Button) findViewById(R.id.btnsave);
        harga = (EditText) findViewById(R.id.harga);
        imgbtn = (ImageButton) findViewById(R.id.imgbtn);




        databaseReference = FirebaseDatabase.getInstance().getReference("Item");
        userData = PrefUtils.getCurrentUser(this);
        mStorage = FirebaseStorage.getInstance().getReference();

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, GALLERY_REQUEST);
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Status Saved", Toast.LENGTH_SHORT).show();

                StorageReference filepath = mStorage.child("items").child(imageUri.getLastPathSegment());
                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override

                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        DatabaseReference newItem = databaseReference.push();
                        newItem.child("nama").setValue(nama);
                        newItem.child("pembuat").setValue(pembuat);
                        newItem.child("harga").setValue(harga);
                        newItem.child("image").setValue(downloadUrl.toString());






                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                imgbtn.setImageURI(imageUri);


            }
        }
    }
}
