package me.varunon9.remotecontrolpc.livescreen;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import me.varunon9.remotecontrolpc.MainActivity;
import me.varunon9.remotecontrolpc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveScreenFragment extends Fragment {

    private int xCord, yCord, initX, initY;
    boolean mouseMoved = false, moultiTouch = false;
    private ImageView screenshotImageView;
    private Timer timer;
    private int screenshotImageViewX, screenshotImageViewY;
    long currentPressTime, lastPressTime;
    Handler handler = new Handler();
    Runnable runnable;

    public LiveScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_live_screen, container, false);
        screenshotImageView = (ImageView) rootView.findViewById(R.id.screenshotImageView);
        ViewTreeObserver vto = screenshotImageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                screenshotImageViewX = screenshotImageView.getHeight();
                screenshotImageViewY = screenshotImageView.getWidth();
                ViewTreeObserver obs = screenshotImageView.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
//        screenshotImageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                if (MainActivity.clientSocket != null) {
//                    switch(event.getAction() & MotionEvent.ACTION_MASK){
//                        case MotionEvent.ACTION_DOWN:
//                            xCord = screenshotImageViewX - (int) event.getY();
//                            yCord = (int) event.getX();
//                            initX = xCord;
//                            initY = yCord;
//                            MainActivity.sendMessageToServer("MOUSE_MOVE_LIVE");
//                            //send mouse movement to server by adjusting coordinates
//                            MainActivity.sendMessageToServer((float) xCord / screenshotImageViewX);
//                            MainActivity.sendMessageToServer((float) yCord / screenshotImageViewY);
//                            mouseMoved = false;
//                            /*startTime = System.currentTimeMillis();
//                            clickCount++;*/
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            if (moultiTouch == false) {
//                                xCord = screenshotImageViewX - (int) event.getY();
//                                yCord = (int) event.getX();
//                                if ((xCord - initX) != 0 && (yCord - initY) != 0) {
//                                    initX = xCord;
//                                    initY = yCord;
//                                    MainActivity.sendMessageToServer("MOUSE_MOVE_LIVE");
//                                    //send mouse movement to server by adjusting coordinates
//                                    MainActivity.sendMessageToServer((float) xCord / screenshotImageViewX);
//                                    MainActivity.sendMessageToServer((float) yCord / screenshotImageViewY);
//                                    mouseMoved=true;
//                                }
//                            }
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            // supporting only single click
//                            currentPressTime = System.currentTimeMillis();
//                            long interval = currentPressTime - lastPressTime;
//                            if (interval >= 500 && !mouseMoved) {
//                                MainActivity.sendMessageToServer("LEFT_CLICK");
//                                delayedUpdateScreenshot();
//                            }
//                            lastPressTime = currentPressTime;
//                            break;
//                    }
//                }
//                return true;
//            }
//        });
        timer = new Timer();

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                updateScreenshot();
                handler.postDelayed(runnable, 1000);
            }
        }, 1000);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setTitle(getResources().getString(R.string.live_screen));
    }

    private void updateScreenshot() {
        if (MainActivity.clientSocket != null) {
            MainActivity.sendMessageToServer("SCREENSHOT_REQUEST");
            new UpdateScreenshot() {
                @Override
                public void receiveData(Object result) {
                    String path = (String) result;
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(-90);
                    try {
                        Bitmap rotated = Bitmap.createBitmap(bitmap ,0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        screenshotImageView.setImageBitmap(rotated);
                    } catch(Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
    }

    private void delayedUpdateScreenshot() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateScreenshot();
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
        timer.purge();
    }

}
