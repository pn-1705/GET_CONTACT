package com.example.demoprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demoprovider.model.Contact;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    ListView lvContact;
    ArrayList<Contact> listContact;
    ArrayAdapter<Contact> adapterContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Anhxa();
        getAllContact();
    }

    private void getAllContact() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
        }

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        listContact.clear();

        while (cursor.moveToNext()) {
            int vtName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int vtPhone = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            String contactName = cursor.getString(vtName);
            String contactPhone = cursor.getString(vtPhone);

            Contact contact = new Contact(contactName, contactPhone);
            listContact.add(contact);
            adapterContact.notifyDataSetChanged();
        }
    }

    private void Anhxa() {
        lvContact = findViewById(R.id.lvContact);
        listContact = new ArrayList<>();
        adapterContact = new ArrayAdapter<>(
                ContactActivity.this, android.R.layout.simple_list_item_1, listContact
        );
        lvContact.setAdapter(adapterContact);
    }
}