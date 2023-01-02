package me.varunon9.remotecontrolpc.connect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;

import java.net.Socket;
import java.util.ArrayList;

import me.varunon9.remotecontrolpc.Cons;
import me.varunon9.remotecontrolpc.IfaceClickHistory;
import me.varunon9.remotecontrolpc.IfaceMonitoring;
import me.varunon9.remotecontrolpc.MainActivity;
import me.varunon9.remotecontrolpc.R;
import me.varunon9.remotecontrolpc.RecyclerHistory;
import me.varunon9.remotecontrolpc.SpHelp;
import me.varunon9.remotecontrolpc.livescreen.LiveScreenFragment;
import me.varunon9.remotecontrolpc.server.Server;


public class ConnectFragment extends Fragment implements IfaceClickHistory {
    String TAG = "ConnectFragment";
//    private AdView mAdView;

    private Button connectButton;
    private TextView tvNote;
    private EditText ipAddressEditText, portNumberEditText;
    private SharedPreferences sharedPreferences;
    SpHelp mSpHelp;
    RecyclerView rv_history;
    TextView tv_history;
    RecyclerHistory adapter;
    String ipHistory;
    ArrayList<String> ipHistoryList;

    public ConnectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);
        ipAddressEditText = (EditText) rootView.findViewById(R.id.ipAddress);
        portNumberEditText = (EditText) rootView.findViewById(R.id.portNumber);
        connectButton = (Button) rootView.findViewById(R.id.connectButton);
        tvNote = (TextView) rootView.findViewById(R.id.tvNote);
        rv_history = rootView.findViewById(R.id.rv_history);
        tv_history = rootView.findViewById(R.id.tv_history);
        sharedPreferences = getActivity().getSharedPreferences("lastConnectionDetails", Context.MODE_PRIVATE);
        String lastConnectionDetails[] = getLastConnectionDetails();
        ipAddressEditText.setText(lastConnectionDetails[0]);
        portNumberEditText.setText(lastConnectionDetails[1]);
        if (MainActivity.clientSocket != null) {
            connectButton.setText("connected");
            connectButton.setEnabled(false);
        }
        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeConnection();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Monitoring");
        mSpHelp = new SpHelp(getContext());
        ipHistoryList = new ArrayList<String>();

        ipHistory = mSpHelp.getString(Cons.IP_HISTORY);
        Log.d(TAG, ipHistory);
        if (ipHistory != null) {
            for (String s : ipHistory.split(",")) {
                if (!s.equals("")) {
                    ipHistoryList.add(s);
                }
            }
        }

//        MobileAds.initialize(requireContext(), new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        mAdView = view.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        if (!ipHistoryList.isEmpty()) {
            adapter = new RecyclerHistory(ipHistoryList, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv_history.setLayoutManager(layoutManager);
            rv_history.setAdapter(adapter);
        } else {
            tv_history.setVisibility(View.GONE);
        }

        String help = getString(R.string.note_to_connect2);
        SpannableStringBuilder span = new SpannableStringBuilder(help);
        span.setSpan(
                new ForegroundColorSpan(Color.BLUE),
                help.length() - 1,
                help.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );

        tvNote.setText(span);

        tvNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
                dialog.setTitle("How to connect");
                dialog.setMessage(
                        "1. Make sure that PC server already installed Java Runtime Environment (JRE)\n" +
                                "2. Download and installed file remotecontrolPC.jar\n" +
                                "3. Run file remotecontrolPC and take ip address n port PC server from this file. Take ip address public if using wifi\n" +
                                "4. Input ip address n port PC server to application and press connect button\n" +
                                "Enjoy...\n");
                dialog.show();
            }
        });

    }

    public void makeConnection() {
        String ipAddress = ipAddressEditText.getText().toString();
        String port = portNumberEditText.getText().toString();
        if (ValidateIP.validateIP(ipAddress) && ValidateIP.validatePort(port)) {
            setLastConnectionDetails(new String[]{ipAddress, port});
            connectButton.setText("Connecting...");
            connectButton.setEnabled(false);
            new MakeConnection(ipAddress, port, getActivity()) {
                @Override
                public void receiveData(Object result) {
                    MainActivity.clientSocket = (Socket) result;
                    if (MainActivity.clientSocket == null) {
                        Toast.makeText(getActivity(), "Server is not listening", Toast.LENGTH_SHORT).show();
                        connectButton.setText("connect");
                        connectButton.setEnabled(true);
                    } else {
                        connectButton.setText("connected");
                        new Thread(new Runnable() {
                            public void run() {
                                new Server(getActivity()).startServer(Integer.parseInt(port));
                            }
                        }).start();

                        //Save IP address
                        String ipHistory = mSpHelp.getString(Cons.IP_HISTORY);

                        if (ipHistory == "NULL") {
                            mSpHelp.writeString(Cons.IP_HISTORY, ipAddress);
                        } else if (!ipHistory.contains(ipAddress)) {
                            ipHistory = ipHistory + "," + ipAddress;
                            mSpHelp.writeString(Cons.IP_HISTORY, ipHistory);
                        }

                        ((IfaceMonitoring) getActivity()).OnConnectMonitoring();
                    }
                }
            }.execute();
        } else {
            Toast.makeText(getActivity(), "Invalid IP Address or port", Toast.LENGTH_SHORT).show();
        }
    }

    private String[] getLastConnectionDetails() {
        String arr[] = new String[2];
        arr[0] = sharedPreferences.getString("lastConnectedIP", "");
        arr[1] = sharedPreferences.getString("lastConnectedPort", "3000");
        return arr;
    }

    private void setLastConnectionDetails(String arr[]) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastConnectedIP", arr[0]);
        editor.putString("lastConnectedPort", arr[1]);
        editor.apply();
    }

    @Override
    public void OnClickHistory(String ip) {
        ipAddressEditText.setText(ip);
        portNumberEditText.setText("3000");
        makeConnection();
    }

    @Override
    public void OnDeleteHistory(String ip) {
        final String ipA = ip;
        final AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
        dialog.setTitle("Confirmation");
        dialog.setMessage("Are you sure to delete " + ip+" ?");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteIPFromHistory(ipA);
                        dialog.dismiss();
                    }
                });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteIPFromHistory(String ip) {
        if (ipHistoryList != null) {
            for (String s : ipHistoryList) {
                if (s.contains(ip)) {
                    ipHistoryList.remove(s);
                }
            }
            if (ipHistory.contains(ip)) {
                ipHistory = ipHistory.replace(ip, "");
                mSpHelp.writeString(Cons.IP_HISTORY, ipHistory);
            }
            adapter.notifyDataSetChanged();
            if (ipHistoryList.isEmpty()){
                tv_history.setVisibility(View.GONE);
            }
        }
    }
}
