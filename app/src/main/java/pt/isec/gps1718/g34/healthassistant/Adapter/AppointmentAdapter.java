package pt.isec.gps1718.g34.healthassistant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.isec.gps1718.g34.healthassistant.Base.Appointment;
import pt.isec.gps1718.g34.healthassistant.R;

/**
 * Created by DiogoMartinho on 09/12/2017.
 */

public class AppointmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Appointment> listaAppointments;

    private static LayoutInflater inflater = null;
    public AppointmentAdapter(Context mainActivity, ArrayList<Appointment> listaAppointments) {
        // TODO Auto-generated constructor stub
        context = mainActivity;
        this.listaAppointments = listaAppointments;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaAppointments.size();
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
        holder.tv.setText(listaAppointments.get(position).getNome());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + listaAppointments.get(position).getID(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
