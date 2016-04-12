package br.com.rufino.o.breno.exemplocontatos;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ExemploListaContatosActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor c = getContentResolver().query(uri, null, null, null, null);

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();



        String[] from= new String[] {"pessoa", "tel"};
        int[] to= new int[] {android.R.id.text1, android.R.id.text2};
        int layout = android.R.layout.two_line_list_item;

        while(c.moveToNext()) {
            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phones = getContentResolver().query
                    (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="+ contactId, null, null, null);
            if(phones.moveToNext()) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("pessoa", c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                item.put("tel", phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                list.add(item);

            }

        }

        setListAdapter(new SimpleAdapter(this, list, layout, from, to));
        c.close();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String item = o.toString();
        Toast.makeText(this, "Selecionado: " + item, Toast.LENGTH_LONG).show();
    }
}
