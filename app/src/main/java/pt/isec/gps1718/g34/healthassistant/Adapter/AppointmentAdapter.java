package pt.isec.gps1718.g34.healthassistant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import pt.isec.gps1718.g34.healthassistant.Base.Appointment;
import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final View rowView = inflater.inflate(R.layout.layout_appointment, null);

        Appointment appointment = listaAppointments.get(position);
        if (appointment == null)
            return rowView;

        TextView tv_Nome = rowView.findViewById(R.id.tv_Appointment_Nome);
        tv_Nome.setText(appointment.getNome());

        TextView tv_DataHora = rowView.findViewById(R.id.tv_Appointment_DataHora);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatada = sdf.format(appointment.getdInicio());
        tv_DataHora.setText(dateFormatada);

        TextView tv_Localizacao = rowView.findViewById(R.id.tv_Appointment_Localizacao);
        tv_Localizacao.setText(appointment.getLocalizacao());

        return rowView;
    }
}
