package me.varunon9.remotecontrolpc;

import android.Manifest;
import android.annotation.TargetApi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.navigation.NavigationView;
import com.ngedev.core.HelperAds;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

import me.varunon9.remotecontrolpc.connect.ConnectFragment;
import me.varunon9.remotecontrolpc.filedownload.FileDownloadFragment;
import me.varunon9.remotecontrolpc.filetransfer.FileTransferFragment;
import me.varunon9.remotecontrolpc.help.HelpFragment;
import me.varunon9.remotecontrolpc.imageviewer.ImageViewerFragment;
import me.varunon9.remotecontrolpc.keyboard.KeyboardFragment;
import me.varunon9.remotecontrolpc.livescreen.LiveScreenFragment;
import me.varunon9.remotecontrolpc.mediaplayer.MediaPlayerFragment;
import me.varunon9.remotecontrolpc.poweroff.PowerOffFragment;
import me.varunon9.remotecontrolpc.presentation.PresentationFragment;
import me.varunon9.remotecontrolpc.server.Server;
import me.varunon9.remotecontrolpc.touchpad.TouchpadFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IfaceMonitoring {

    String TAG = "MainActivity";
    public static Socket clientSocket = null;
    public static ObjectInputStream objectInputStream = null;
    public static ObjectOutputStream objectOutputStream = null;
    private static AppCompatActivity thisActivity;
    private boolean doubleBackToExitPressedOnce = false;
    MenuItem action_live_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);

        // selecting connect fragment by default
        replaceFragment(R.id.nav_connect);

        thisActivity = this;

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        Log.d(TAG, "wii "+drawer);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkForPermission();
        }

        HelperAds.showAds(getApplicationContext(), this, R.string.menu_ads);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkForPermission() {
        if (thisActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (thisActivity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(thisActivity, "Read Permission is necessary to transfer", Toast.LENGTH_LONG).show();
                thisActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                thisActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                //2 is integer constant for WRITE_EXTERNAL_STORAGE permission, uses in onRequestPermissionResult
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(me.varunon9.remotecontrolpc.R.menu.main, menu);
        action_live_screen = menu.findItem(R.id.action_live_screen);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==android.R.id.home){
            finish();
        }

        onNavigationItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        replaceFragment(id);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(int id) {
        Fragment fragment = null;

        if (id == R.id.nav_connect) {
            fragment = new ConnectFragment();
        } else if (id == R.id.nav_touchpad) {
            fragment = new TouchpadFragment();
        } else if (id == R.id.nav_keyboard) {
            fragment = new KeyboardFragment();
        } else if (id == R.id.nav_file_transfer) {
            fragment = new FileTransferFragment();
        } else if (id == R.id.nav_file_download) {
            fragment = new FileDownloadFragment();
        } else if (id == R.id.nav_image_viewer) {
            fragment = new ImageViewerFragment();
        } else if (id == R.id.nav_media_player) {
            fragment = new MediaPlayerFragment();
        } else if (id == R.id.nav_presentation) {
            fragment = new PresentationFragment();
        } else if (id == R.id.nav_power_off) {
            fragment = new PowerOffFragment();
        } else if (id == R.id.action_help) {
            fragment = new HelpFragment();
        } else if (id == R.id.action_live_screen) {
            fragment = new LiveScreenFragment();
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            if (MainActivity.clientSocket != null) {
                MainActivity.clientSocket.close();
            }
            if (MainActivity.objectOutputStream != null) {
                MainActivity.objectOutputStream.close();
            }
            if (MainActivity.objectInputStream != null) {
                MainActivity.objectInputStream.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        Server.closeServer();
    }

    //this method is called from fragments to send message to server (Desktop)
    public static void sendMessageToServer(String message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "STRING");
            /*try {
                MainActivity.objectOutputStream.writeObject(message);
                MainActivity.objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
                socketException();
            }*/
        }
    }

    public static void sendMessageToServer(int message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "INT");
            /*try {
                MainActivity.objectOutputStream.writeObject(message);
                MainActivity.objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
                socketException();
            }*/
        }
    }

    public static void socketException() {
        //Toast.makeText(thisActivity, "Connection Closed", Toast.LENGTH_LONG).show();
        if (MainActivity.clientSocket != null) {
            try {
                MainActivity.clientSocket.close();
                MainActivity.objectOutputStream.close();
                MainActivity.clientSocket = null;
            } catch(Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void sendMessageToServer(float message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "FLOAT");
            /*try {
                MainActivity.objectOutputStream.writeObject(message);
                MainActivity.objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
                socketException();
            }*/
        }
    }

    public static void sendMessageToServer(long message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "LONG");
            /*try {
                MainActivity.objectOutputStream.writeObject(message);
                MainActivity.objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
                socketException();
            }*/
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Click again to download", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Failed to download", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Click again to download", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "File Transfer will not work", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void OnConnectMonitoring() {
        //Launch live preview
        //action_live_screen.setVisible(true);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new LiveScreenFragment());
        fragmentTransaction.commit();
    }
}