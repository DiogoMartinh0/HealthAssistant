package pt.isec.gps1718.g34.healthassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.isec.gps1718.g34.healthassistant.Base.Prescription;

/**
 * Created by DiogoMartinho on 09/12/2017.
 */

public class PrescriptionAdapter extends BaseAdapter {
    Context context;
    ArrayList<Prescription> listaPrescriptions;

    private static LayoutInflater inflater = null;
    public PrescriptionAdapter(Context mainActivity, ArrayList<Prescription> listaPrescriptions) {
        // TODO Auto-generated constructor stub
        context = mainActivity;
        this.listaPrescriptions = listaPrescriptions;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaPrescriptions.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.layout_prescription, null);
        holder.tv = (TextView) rowView.findViewById(R.id.tv_Prescription_Nome);
        holder.tv.setText(listaPrescriptions.get(position).getNome());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + listaPrescriptions.get(position).getID(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
