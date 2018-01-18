package pt.isec.gps1718.g34.healthassistant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
import pt.isec.gps1718.g34.healthassistant.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final View rowView = inflater.inflate(R.layout.layout_prescription, null);

        Prescription prescription = listaPrescriptions.get(position);
        if (prescription == null)
            return rowView;

        // tv_Prescription_Nome
        TextView tv_Nome = rowView.findViewById(R.id.tv_Prescription_Nome);
        tv_Nome.setText(prescription.getNome());

        // tv_Prescription_Dosagem
        TextView tv_Dosagem = rowView.findViewById(R.id.tv_Prescription_Dosagem);
        tv_Dosagem.setText(prescription.getDosagem());

        // tv_Prescription_DataHora
        TextView tv_DataHora = rowView.findViewById(R.id.tv_Prescription_DataHora);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatada = sdf.format(prescription.getdInicio());

        tv_DataHora.setText(dateFormatada + " - " + prescription.gettInterval());
        return rowView;
    }
}
